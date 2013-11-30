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
package com.tridion.extensions.dynamicdelivery.foundation.factories;

import com.tridion.extensions.dynamicdelivery.foundation.contentmodel.Component;
import com.tridion.extensions.dynamicdelivery.foundation.contentmodel.exceptions.ItemNotFoundException;
import com.tridion.extensions.dynamicdelivery.foundation.contentmodel.exceptions.NotAuthorizedException;
import com.tridion.extensions.dynamicdelivery.foundation.request.RequestContext;

public interface ComponentFactory extends Factory {
	/**
	 * Get a Component by its uri. No security available; the method will fail if a
	 * SecurityFilter is configured on the factory.
	 * @param uri
	 * @param context
	 * @return
	 * @throws ItemNotFoundException
	 * @throws NotAuthorizedException
	 */
	public Component getComponent(String uri) throws ItemNotFoundException, NotAuthorizedException;

	/**
	 * Get a Component by its uri. The request context is used by the security filter (if there is one).
	 * @param uri
	 * @param context
	 * @return
	 * @throws ItemNotFoundException
	 * @throws NotAuthorizedException
	 */
	public Component getComponent(String uri, RequestContext context) throws ItemNotFoundException, NotAuthorizedException;
	
	/**
	 * Get a component by its uri and component template uri. No security available; the method will fail if a
	 * SecurityFilter is configured on the factory.
	 * @param componentUri
	 * @param componentTemplateUri
	 * @param context
	 * @return
	 * @throws ItemNotFoundException
	 * @throws NotAuthorizedException
	 */
	public Component getComponent(String componentUri, String componentTemplateUri) throws ItemNotFoundException, NotAuthorizedException;

	/**
	 * Get a component by its uri and component template uri. The request context is used by the security filter (if there is one).
	 * @param componentUri
	 * @param componentTemplateUri
	 * @param context
	 * @return
	 * @throws ItemNotFoundException
	 * @throws NotAuthorizedException
	 */
	public Component getComponent(String componentUri, String componentTemplateUri, RequestContext context) throws ItemNotFoundException, NotAuthorizedException;
	
}
