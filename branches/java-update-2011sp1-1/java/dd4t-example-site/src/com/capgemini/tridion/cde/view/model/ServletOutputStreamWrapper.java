/**  
 *  Copyright 2011 Nordea
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
package com.capgemini.tridion.cde.view.model;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletOutputStream;

/**
 * Little wrapper class wraps around a servletOutputStream to catch what's written to it to place it
 * in the correct final content.
 * 
 * @author Rogier Oudshoorn
 *
 */
public class ServletOutputStreamWrapper extends ServletOutputStream {
    private ByteArrayOutputStream fBuffer;
    private boolean writtenTo;

    public ServletOutputStreamWrapper(){
        fBuffer = new ByteArrayOutputStream();
        writtenTo = false; 	    	
    }
	
    /**
     * Override func, catching the data written to it.
     * 
     */
	@Override
	public void write(int aByte) {
	     fBuffer.write(aByte);
	     
	     if(!writtenTo) writtenTo = true;
	}
	
	/**
	 * toString() method returns whatever is written to this stream as a string.
	 * 
	 */
	public String toString(){
		String out = super.toString();
		
	    try {
			out = new String(fBuffer.toByteArray(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	    
	    return out;
	}
	
	/**
	 * Function indicates if this outputstream has been written to.
	 * 
	 * @return
	 */
	public boolean isWrittenTo(){
		return writtenTo;
	}	
}
