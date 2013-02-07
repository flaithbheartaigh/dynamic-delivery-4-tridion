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
package com.tridion.extensions.dynamicdelivery.foundation.resolvers;

import com.tridion.extensions.dynamicdelivery.foundation.contentmodel.Component;
import com.tridion.extensions.dynamicdelivery.foundation.contentmodel.Page;

/**
 * Interface for link resolvers.
 * 
 * @author bjornl
 *
 */
public interface LinkResolver {

	public String resolve(Component component, Page page);

	public String resolve(Component component);

	public String resolve(String componentId);

	public String resolve(String componentId, String pageId);
	
	public void setContextPath(String contextPath);

	public String getContextPath();	
}
