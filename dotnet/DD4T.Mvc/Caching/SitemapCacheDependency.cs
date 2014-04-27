﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web.Caching;
using System.Threading;
using DD4T.ContentModel.Contracts.Factories;
using DD4T.Factories;

namespace DD4T.Mvc.Caching
{
    public class SitemapCacheDependency : CacheDependency
    {
        private Timer timer;

        public DateTime LastPublishDate { get; private set; }

        public string SitemapUrlPath { get; private set; }

        public virtual IPageFactory PageFactory { get; set; }

        public SitemapCacheDependency(int pollTime, string sitemapUrlPath, IPageFactory pageFactory)
        {
            this.PageFactory = pageFactory;
            timer = new Timer(
                new TimerCallback(CheckDependencyCallback),
                this, 0, pollTime);
            SitemapUrlPath = sitemapUrlPath;
            
            LastPublishDate = PageFactory.GetLastPublishedDateByUrl(SitemapUrlPath);
        }

        private void CheckDependencyCallback(object sender)
        {
           
            DateTime lastPublishedDate = PageFactory.GetLastPublishedDateByUrl(SitemapUrlPath);
            if (lastPublishedDate > LastPublishDate)
            {
                base.NotifyDependencyChanged(this, EventArgs.Empty);
                timer.Dispose();
            }
        }

        protected override void DependencyDispose()
        {
            timer.Dispose();
            base.DependencyDispose();
        }

    }
}
