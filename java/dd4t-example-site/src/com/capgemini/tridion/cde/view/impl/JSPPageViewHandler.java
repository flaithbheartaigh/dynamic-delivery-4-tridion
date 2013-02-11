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
package com.capgemini.tridion.cde.view.impl;

import org.dd4t.contentmodel.Component;

import com.capgemini.tridion.cde.constants.Constants;
import com.capgemini.tridion.cde.view.BaseJSPViewHandler;

public class JSPPageViewHandler extends BaseJSPViewHandler<Component> {
    public JSPPageViewHandler() {

        super();
        setModelKey(Constants.PAGE_MODEL_KEY);     
    }

}
