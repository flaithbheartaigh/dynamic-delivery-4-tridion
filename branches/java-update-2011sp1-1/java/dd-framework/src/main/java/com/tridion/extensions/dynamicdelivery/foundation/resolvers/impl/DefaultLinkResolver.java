/**  
 *  Copyright 2011 Capgemini & SDL
 * 
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 * 
 *        http://www.apache.org/licenses/LICENSE-2.0
 * 
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.tridion.extensions.dynamicdelivery.foundation.resolvers.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.tridion.extensions.dynamicdelivery.foundation.contentmodel.Component;
import com.tridion.extensions.dynamicdelivery.foundation.contentmodel.GenericComponent;
import com.tridion.extensions.dynamicdelivery.foundation.contentmodel.Page;
import com.tridion.extensions.dynamicdelivery.foundation.contentmodel.Schema;
import com.tridion.extensions.dynamicdelivery.foundation.contentmodel.impl.PublicationImpl;
import com.tridion.extensions.dynamicdelivery.foundation.resolvers.LinkResolver;
import com.tridion.extensions.dynamicdelivery.foundation.util.TridionUtils;
import com.tridion.linking.ComponentLink;
import com.tridion.linking.Link;
import com.tridion.util.TCMURI;

public class DefaultLinkResolver implements LinkResolver {

	private Map<String, String> schemaToUrlMappings;
	private String schemaKey;
	private boolean encodeUrl = true;
	private String contextPath;

	private static Logger logger = Logger.getLogger(DefaultLinkResolver.class);

	@Override
	public String resolve(Component component) {
		return resolve(component,null);
	}
	
	@Override
	public String resolve(Component component, Page page) {

		String resolvedUrl = null;
		if (component != null) {
		    // option 1 - handle multimedia
		    if(component instanceof GenericComponent){
		        GenericComponent comp = (GenericComponent) component;
		        if(comp.getMultimedia() != null){
		            resolvedUrl = comp.getMultimedia().getUrl();
		        }
		        
		    }
		    
			
			
		    Schema schema = component.getSchema();
		    
			// option 2 - handle by schema
			if(resolvedUrl == null){			    
			    resolvedUrl = findUrlMapping(schema);
			}

			// option 3 - use componentLinker
			if (resolvedUrl == null) {
				if (component.getPublication() == null) {
					try {
						TCMURI tcmUri = new TCMURI(component.getId());
						component.setPublication(new PublicationImpl(
								TridionUtils.getPublicationUri(tcmUri
										.getPublicationId())));
					} catch (ParseException e) {
						if (logger.isDebugEnabled()) {
							logger.debug(
									"Problem parsing the uri for component: "
											+ component.getId(), e);
						}
					}
				}
				if (component.getPublication() != null) {
					if (page == null) {
						resolvedUrl = resolve(component.getId());	
					} else {
						resolvedUrl = resolve(component.getId(), page.getId());	
					}
				}
				if (logger.isDebugEnabled()
						&& (resolvedUrl == null || "".equals(resolvedUrl))) {
					logger.debug("Not possible to resolve url for component: "
							+ component.getId());
				}

			} else {
				resolvedUrl = replacePlaceholders(resolvedUrl,
						"%COMPONENTURI%", component.getId());
				resolvedUrl = replacePlaceholders(resolvedUrl,
						"%COMPONENTTITLE%", component.getTitle());
				resolvedUrl = replacePlaceholders(resolvedUrl, "%SCHEMAURI%",
						schema.getId());
				resolvedUrl = replacePlaceholders(resolvedUrl, "%SCHEMATITLE%",
						schema.getTitle());
			}
			if (contextPath != null && contextPath.length() > 0) {
				component.setResolvedUrl(contextPath + resolvedUrl);				
			} else {
				component.setResolvedUrl(resolvedUrl);
			}
		}
		return resolvedUrl;
	}
	

	@Override
	public String resolve(String componentId) {
		try {
			TCMURI tcmUri = new TCMURI(componentId);
			ComponentLink clink = new ComponentLink(tcmUri.getPublicationId());
			Link link = clink.getLink(tcmUri.getItemId()); 

			if (link.isResolved()) {
				return link.getURL();
			}
		}
		catch(ParseException e){
			logger.warn("Not possible to parse id: " + componentId);
		}
		return null;
	}

	@Override
	public String resolve(String componentId, String pageId) {
		try {
			TCMURI tcmUri = new TCMURI(componentId);
			ComponentLink clink = new ComponentLink(tcmUri.getPublicationId());
			
			Link link = clink.getLink(pageId, componentId, "tcm:0-0-0", "", "", true, false); 

			if (link.isResolved()) {
				return link.getURL();
			}
		}
		catch(ParseException e){
			logger.warn("Not possible to parse id: " + componentId);
		}
		return null;
	}

	private String replacePlaceholders(String resolvedUrl, String placeholder,
			String replacementText) {

		StringBuffer sb = new StringBuffer();
		if (replacementText != null && !"".equals(replacementText)) {
			if (getEncodeUrl()) {
				try {
					replacementText = URLEncoder.encode(replacementText,
							"UTF-8");
				} catch (UnsupportedEncodingException e) {
					logger.warn("Not possible to encode string: "
							+ replacementText, e);
					return "";
				}
			}

			Pattern p = Pattern.compile(placeholder);
			Matcher m = p.matcher(resolvedUrl);

			while (m.find()) {
				m.appendReplacement(sb, replacementText);
			}
			m.appendTail(sb);
		}
		return sb.toString();
	}

	private String findUrlMapping(Schema schema) {

		String key = "";
		if ("id".equals(schemaKey)) {
			try {
				TCMURI tcmUri = new TCMURI(schema.getId());
				key = String.valueOf(tcmUri.getItemId());
			} catch (ParseException e) {
				return null;
			}
		} else if ("title".equals(schemaKey)) {
			key = schema.getTitle();
		} else {
			// use uri as default key
			key = schema.getId();
		}

		return getSchemaToUrlMappings().get(key);
	}

	public Map<String, String> getSchemaToUrlMappings() {
		if (schemaToUrlMappings == null) {
			this.schemaToUrlMappings = new HashMap<String, String>();
		}
		return schemaToUrlMappings;
	}

	public void setSchemaToUrlMappings(Map<String, String> schemaToUrlMappings) {
		this.schemaToUrlMappings = schemaToUrlMappings;
	}

	public String getSchemaKey() {
		return schemaKey;
	}

	public void setSchemaKey(String schemaKey) {
		this.schemaKey = schemaKey;
	}

	public boolean getEncodeUrl() {
		return encodeUrl;
	}

	public void setEncodeUrl(boolean encodeUrl) {
		this.encodeUrl = encodeUrl;
	}
	
	@Override
	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	@Override
	public String getContextPath() {
		return contextPath;
	}

}
