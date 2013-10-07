package org.dd4t.servlet.filters;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tridion.storage.BinaryContent;
import com.tridion.storage.BinaryMeta;
import com.tridion.storage.BinaryVariant;
import com.tridion.storage.ItemMeta;
import com.tridion.storage.StorageManagerFactory;
import com.tridion.storage.StorageTypeMapping;
import com.tridion.storage.dao.BinaryContentDAO;
import com.tridion.storage.dao.BinaryVariantDAO;
import com.tridion.storage.dao.ItemDAO;

/**
 * Simple filter, designed to stream Tridion binaries from the broker onto the 
 * filesystem. It enables localhost development on a DD4T implementation to run,
 * whereas on regular environments these assets are served directly from apache.
 * 
 * @author Rogier Oudshoorn
 *
 */
public class ImageFilter implements Filter {
	public static Logger logger = LoggerFactory.getLogger(ImageFilter.class);
	
	private ServletContext context;

	@Override
	public void destroy() {
		// nothing to destroy		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		
		try{
			HttpServletRequest hreq = (HttpServletRequest) req;
			String url = hreq.getRequestURI();
			url = url.replace(hreq.getContextPath(), "");
			// detect if request is binary
			if(!url.endsWith(".html")){
	        	if(logger.isDebugEnabled())
		        	logger.debug("Examening image url: "+url);
	        	
				// check if we can find the said binary
	        	BinaryVariantDAO metaDao = (BinaryVariantDAO) StorageManagerFactory.getDAO(200, StorageTypeMapping.BINARY_VARIANT);
	        	List<BinaryVariant> potentials = metaDao.findByURL(url);
	        	
	        	if(logger.isDebugEnabled()){
		        	logger.debug("Got potentials: "+potentials);
	        	}
	        	
	        	// no binary, no action
	        	if(potentials == null || potentials.size() == 0){
	        		throw new Exception("Can't find binary, ignoring ...");
	        	}
	        	
	        	BinaryVariant variant = potentials.get(0);	        
	        	BinaryMeta meta = variant.getBinaryMeta();
				
	        	ItemDAO itemDao = (ItemDAO) StorageManagerFactory.getDAO(200, StorageTypeMapping.ITEM_META);
	        	ItemMeta item = itemDao.findByPrimaryKey(meta.getPublicationId(), meta.getItemId());	        
	        	
	        	
	        	// first, replace url spaces with normal ones
				url = url.replace("%20", " ");
				
				if(logger.isDebugEnabled()){
					logger.debug("Trying to check file on url "+url+" with context "+context);
				}
				
				// check if item is on filesystem
		        File file = new File(context.getRealPath(url));
	
		        if(logger.isDebugEnabled())
		        	logger.debug("Got file which exists "+file.exists()+" at path "+file.getAbsolutePath());

		        // refresh the file it's not there
		        boolean refresh = !file.exists();
		        
		        // alternatively, if if is, check if the modified is ok
		        if(file.exists()){
		        	if(logger.isDebugEnabled()){
		        		logger.debug("Existing file has timestamp of "+file.lastModified()+" versus broker timestamp "+item.getLastPublishDate().getTime());
		        	}
		        	
		        	if(file.lastModified() < item.getLastPublishDate().getTime()){
		        		refresh = true;		        		
		        	}		        	
		        }
		        // if the file doesn't exist, set it to creatable
		        else{
		        	// also check parent directory
		        	File parent = file.getParentFile();
		        	
		        	if(!parent.exists()){
		        		parent.mkdir();		        		
		        	}
		        	
		        	// and create this file
		        	file.createNewFile();
		        }
		        		
		        if(logger.isDebugEnabled())
		        	logger.debug("Will refresh image: "+refresh);
		        
		        // if refresh, stream item from broker onto filesystem so the appserver can server it
		        if(refresh){	        	
		    		BinaryContentDAO dao = (BinaryContentDAO) StorageManagerFactory.getDAO(
		    				200, StorageTypeMapping.BINARY_CONTENT);
		    				    		
		    		BinaryContent content = dao.findByPrimaryKey(meta.getPublicationId(), meta.getItemId(), variant.getBinaryVariantId().getVariantId());
		    			    		
		    		FileOutputStream fos = new FileOutputStream(file, true);

		    		fos.write(content.getContent());
		    		fos.close();
		        }
			}
		}
		catch(Exception ex){
			logger.error("Unable to process filter", ex);
		}
		
		chain.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		// nothing to config
		
		context = config.getServletContext();
	}

}
