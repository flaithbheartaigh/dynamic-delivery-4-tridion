﻿using System;
using System.Linq;
using System.Xml;
using System.Xml.Serialization;

using Tridion.ContentDelivery.DynamicContent;
using Tridion.ContentDelivery.DynamicContent.Filters;
using Tridion.ContentDelivery.DynamicContent.Query;
using Query = Tridion.ContentDelivery.DynamicContent.Query.Query;
using Tridion.ContentDelivery.Meta;
using Tridion.ContentDelivery.Web.Linking;

using DD4T.ContentModel;
using DD4T.ContentModel.Exceptions;
using DD4T.ContentModel.Factories;
//using DD4T.Utils;
using System.Collections.Generic;

using System.Web.Caching;
using System.Web;
using DD4T.ContentModel.Contracts.Providers;
using System.Data.SqlClient;
using System.Configuration;
using System.Data;

namespace DD4T.Providers.SDLTridion2011sp1
{
    /// <summary>
    /// 
    /// </summary>
    public class TridionBinaryProvider : BaseProvider, IBinaryProvider
    {

		private static IDictionary<string, DateTime> lastPublishedDates = new Dictionary<string, DateTime>();


        #region IBinaryProvider Members

        public byte[] GetBinaryByUri(string uri)
        {
            throw new NotImplementedException();
        }

        public byte[] GetBinaryByUrl(string url)
        {
            string encodedUrl = HttpUtility.UrlPathEncode(url); // ?? why here? why now?
            
            Query findBinary = new Query();
            PublicationURLCriteria urlCriteria = new PublicationURLCriteria(url);
            //MultimediaCriteria isBinary = new MultimediaCriteria(true);

            //Criteria allCriteria = CriteriaFactory.And(isBinary, urlCriteria);
            Criteria allCriteria = urlCriteria;
            findBinary.Criteria = allCriteria;
            if (this.PublicationId != 0)
            {
                PublicationCriteria correctSite = new PublicationCriteria(this.PublicationId);
                allCriteria.AddCriteria(correctSite);
            }

            string[] binaryUri = findBinary.ExecuteQuery();

            if (binaryUri.Length == 0)
            {
                
                // TODO: find out how to retrieve binary data
            }

            throw new NotImplementedException();
        }

        public DateTime GetLastPublishedDateByUrl(string url)
        {
            // code supplied by Daniel Neagu that does not work because I don't have the DAOs or StorageManagerFactory objects at all....
            //Com.Tridion.Storage.Dao.BinaryContentDAO bmDAO = (Com.Tridion.Storage.Dao.BinaryContentDAO) Com.Tridion.Storage.StorageManagerFactory.GetDAO(1, "BinaryContent");
            //TCDURI tcdUri = new TCDURI(uri);
            //Com.Tridion.Storage.BinaryContent bm = bmDAO.FindByPrimaryKey(1, 23, "variant");



            
            throw new NotImplementedException();
        }

        public DateTime GetLastPublishedDateByUri(string uri)
        {
            throw new NotImplementedException();
        }
        #endregion
    }
}
