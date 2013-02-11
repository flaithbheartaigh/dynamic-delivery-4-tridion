package com.capgemini.tridion.apps.listings;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.dd4t.contentmodel.GenericComponent;
import org.dd4t.contentmodel.GenericPage;
import org.dd4t.core.factories.impl.GenericComponentFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.capgemini.tridion.cde.constants.Constants;
import com.tridion.broker.StorageException;
import com.tridion.broker.querying.CriteriaFactory;
import com.tridion.broker.querying.Query;
import com.tridion.broker.querying.criteria.Criteria;
import com.tridion.broker.querying.criteria.content.ItemSchemaCriteria;
import com.tridion.broker.querying.criteria.content.ItemTypeCriteria;
import com.tridion.broker.querying.criteria.content.PublicationCriteria;
import com.tridion.broker.querying.filter.LimitFilter;
import com.tridion.broker.querying.sorting.SortParameter;
import com.tridion.util.TCMURI;

public class NewsList extends AbstractController {
    private static Logger logger = Logger.getLogger(NewsList.class);

    public static String NEWSLIST_COMPS_KEY = "news_list_comps_key";
    
    private GenericComponentFactory genericComponentFactory;
    
	public GenericComponentFactory getGenericComponentFactory() {
		return genericComponentFactory;
	}

	public void setGenericComponentFactory(
			GenericComponentFactory genericComponentFactory) {
		this.genericComponentFactory = genericComponentFactory;
	}

	@SuppressWarnings("deprecation")
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		if(logger.isDebugEnabled()){
			logger.debug("Entering NewsList");
		}
		
		ModelAndView mav = new ModelAndView("newslist");

		
        try {
            // define a criteria on schema number 254 (newsitem)
            ItemSchemaCriteria isNewsItem = new ItemSchemaCriteria(116120);

            // Only return items of type 16 (components)
            ItemTypeCriteria isComponent = new ItemTypeCriteria(16);

            // get publicationid from the page, and filter query on that too
            GenericPage page =
                    (GenericPage) request
                            .getAttribute(Constants.PAGE_MODEL_KEY);
            TCMURI uri = new TCMURI(page.getId());

            PublicationCriteria isPublication =
                    new PublicationCriteria(uri.getPublicationId());

            // Now, lets concatenate the schema is component criteria
            Criteria itemCritery = CriteriaFactory.And(isNewsItem, isComponent);
            Criteria itemAndPublicationCriteria =
                    CriteriaFactory.And(itemCritery, isPublication);

            // Add these criteria to a query
            Query query = new Query();
            query.setCriteria(itemAndPublicationCriteria);

            // Limit the results to max 5, and sort them ascending on
            // publication date. all available sorting options are constants on
            // the SortParameter class
            query.addLimitFilter(new LimitFilter(5));
            SortParameter sortParameter =
                    new SortParameter(
                            SortParameter.ITEMS_INITIAL_PUBLICATION_DATE,
                            SortParameter.ASCENDING);
            query.addSorting(sortParameter);

            // Run the query
            String[] itemResults;
            try {
                itemResults = query.executeQuery();

                List<GenericComponent> comps = new ArrayList<GenericComponent>();

                // parse array to List of components
                for (String result : itemResults) {
                    
                    /*
                     * Load the components from the factory. Note that we're using getEmbeddedComponent
                     * as we know that in the example these components are found embedded on the page.
                     */
                    GenericComponent comp = 
                    		(GenericComponent) genericComponentFactory.getEmbeddedComponent(result);
                    
                    if(comp != null){
                    	comps.add(comp);
                    }                    	
                }

                mav.addObject(NEWSLIST_COMPS_KEY, comps);

            } catch (StorageException se) {
                logger.error("Error while RUNNING query: " + se.getMessage(),
                        se);
            } catch (Exception ex) {
                logger.error("Error while PARSING query: " + ex.getMessage(),
                        ex);
            }
        } catch (Exception ex) {
            logger.error("Error while BUILDING query: " + ex.getMessage(), ex);
        }
		
		return mav;
	}

}
