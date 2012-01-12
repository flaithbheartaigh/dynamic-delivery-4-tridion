﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web;
using DD4T.ContentModel;
using DD4T.ContentModel.Factories;
using DD4T.ContentModel.Contracts.Providers;
using DD4T.Providers.SDLTridion2011sp1;
using DD4T.ContentModel.Contracts.Caching;

namespace DD4T.Factories
{

    public class LinkFactory : FactoryBase, ILinkFactory
    {
        const string CacheKeyFormat = "Link_{0}";
        const string CacheKeyFormatExtended = "Link_{0}_{1}_{2}";
        const string CacheValueNull = "UnresolvedLink_{0}_{1}_{2}";

        //private const string uriPrefix = "tcm:";
        private static TcmUri emptyTcmUri = new TcmUri("tcm:0-0-0");
        private ILinkProvider _linkProvider = null;
        private Dictionary<int,ILinkProvider> _linkProviders = new Dictionary<int,ILinkProvider>();

        public ILinkProvider LinkProvider { get; set; }
        //public ILinkProvider LinkProvider
        //{
        //    get
        //    {
        //        // TODO: implement DI
        //        if (_linkProvider == null)
        //        {
        //            _linkProvider = new TridionLinkProvider();
        //            _linkProvider.PublicationId = this.PublicationId;
        //        }
        //        return _linkProvider;
        //    }
        //    set
        //    {
        //        _linkProvider = value;
        //    }
        //}
 
        public LinkFactory()
        {
        }

        private object lock1 = new object();
        private ILinkProvider GetLinkProvider(string uri)
        {
            TcmUri u = new TcmUri(uri);
            if (u == null)
                // invalid uri, return null
                return null;

            if (_linkProviders.ContainsKey(u.PublicationId))
                return _linkProviders[u.PublicationId];
            lock (lock1)
            {
                if (!_linkProviders.ContainsKey(u.PublicationId)) // we must test again, because in the mean time another thread might have added a record to the dictionary!
                {
                    Type t = LinkProvider.GetType();
                    ILinkProvider lp = (ILinkProvider)Activator.CreateInstance(t);
                    lp.PublicationId = u.PublicationId;
                    _linkProviders.Add(u.PublicationId, lp);
                }
            }
            return _linkProviders[u.PublicationId];
        }

        public string ResolveLink(string componentUri)
        {
            string cacheKey = String.Format(CacheKeyFormat, componentUri);
            string link = (string) CacheAgent.Load(cacheKey);
            if (link != null)
            {
                if (link.Equals(CacheValueNull))
                {
                    return null;
                }
                return link;
            }
            else
            {
                ILinkProvider lp = GetLinkProvider(componentUri);
                if (lp == null)
                    return string.Empty;
                string resolvedUrl = lp.ResolveLink(componentUri);
                if (resolvedUrl == null)
                {
                    //CacheAgent.Store(cacheKey, "Link", CacheValueNull, new List<string>() { String.Format(ComponentFactory.CacheKeyFormatByUri, componentUri) });
                    CacheAgent.Store(cacheKey, "Link", CacheValueNull);
                }
                else
                {
                    //CacheAgent.Store(cacheKey, "Link", resolvedUrl, new List<string>() { String.Format(ComponentFactory.CacheKeyFormatByUri, componentUri) });
                    CacheAgent.Store(cacheKey, "Link", resolvedUrl);
                }
                return resolvedUrl;
            }
        }

        public string ResolveLink(string sourcePageUri, string componentUri, string excludeComponentTemplateUri)
        {
            string cacheKey = String.Format(CacheKeyFormatExtended, sourcePageUri, componentUri, excludeComponentTemplateUri);
            string link = (string) CacheAgent.Load(cacheKey);
            if (link != null)
            {
                if (link.Equals(CacheValueNull))
                {
                    return null;
                }
                return link;
            }
            else
            {
                string resolvedUrl = LinkProvider.ResolveLink(sourcePageUri, componentUri, excludeComponentTemplateUri);
                if (resolvedUrl == null)
                {
                    //CacheAgent.Store(cacheKey, "Link", CacheValueNull, new List<string>() { String.Format("ComponentByUri_{0}", componentUri) });
                    CacheAgent.Store(cacheKey, "Link", CacheValueNull);
                }
                else
                {
                    //CacheAgent.Store(cacheKey, "Link", resolvedUrl, new List<string>() { String.Format("ComponentByUri_{0}", componentUri) });
                    CacheAgent.Store(cacheKey, "Link", resolvedUrl);
                } 
                return resolvedUrl;
            }
        }
        public override DateTime GetLastPublishedDateCallBack(string key, object cachedItem)
        {
            throw new NotImplementedException();
        }
    }
}

