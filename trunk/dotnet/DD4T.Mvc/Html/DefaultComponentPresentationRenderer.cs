﻿using System;
using System.ComponentModel.Composition;
using System.Linq;
using System.Text;
using System.Web.Configuration;
using System.Web.Mvc;
using System.Web.Mvc.Html;
using DD4T.ContentModel;

namespace DD4T.Mvc.Html
{
    [Export(typeof(IComponentPresentationRenderer))]
    public class DefaultComponentPresentationRenderer : IComponentPresentationRenderer
    {
        public static IComponentPresentationRenderer Create()
        {
            return new DefaultComponentPresentationRenderer();
        }

        public MvcHtmlString ComponentPresentations(IPage tridionPage, HtmlHelper htmlHelper, string[] includeComponentTemplate, string includeSchema)
        {
            StringBuilder sb = new StringBuilder();
            foreach (IComponentPresentation cp in tridionPage.ComponentPresentations)
            {
                if (includeComponentTemplate != null && !MustInclude(cp.ComponentTemplate, includeComponentTemplate))
                    continue;
                if ((!string.IsNullOrEmpty(includeSchema)) && !MustInclude(cp.Component.Schema, includeSchema))
                    continue;
                cp.Page = tridionPage;
                sb.Append(RenderComponentPresentation(cp, htmlHelper));

            }
            return MvcHtmlString.Create(sb.ToString());
        }

        private static bool MustInclude(IComponentTemplate itemToCheck, string[] pattern)
        {
            if (pattern.Length == 0)
            {
                return true; // if no template was specified, always render the component presentation (so return true)
            }
            if (pattern[0].StartsWith("tcm:"))
            {
                return pattern.Any<string>(item => item.Equals(itemToCheck.Id));
            }
            else
            {
                // pattern does not start with tcm:, we will treat it as a (part of a) title
                IField view;
                if (itemToCheck.MetadataFields != null && itemToCheck.MetadataFields.TryGetValue("view", out view))
                {
                    return pattern.Any<string>(item => view.Value.ToLower().StartsWith(item.ToLower()));
                }
                else
                {
                    System.Diagnostics.Trace.TraceError("view for {0} not set", itemToCheck.Title);
                    return false;
                }
            }
        }

        private static bool MustInclude(ISchema itemToCheck, string pattern)
        {
            bool reverse = false;
            if (pattern.StartsWith("!"))
            {
                // reverse sign of the boolean
                reverse = true;
                pattern = pattern.Substring(1);
            }

            if (pattern.StartsWith("tcm:"))
            {
                return itemToCheck.Id == pattern ^ reverse; // use 'exclusive or' to reverse the sign if necessary!
            }

            return itemToCheck.Title.ToLower().StartsWith(pattern.ToLower()) ^ reverse;
        }

        private static MvcHtmlString RenderComponentPresentation(IComponentPresentation cp, HtmlHelper htmlHelper)
        {
            string controller = WebConfigurationManager.AppSettings["Controller"];
            string action = WebConfigurationManager.AppSettings["Action"]; 
        
            
            if (cp.ComponentTemplate.MetadataFields.ContainsKey("controller"))
            {
                controller = cp.ComponentTemplate.MetadataFields["controller"].Value;
            }
            if (cp.ComponentTemplate.MetadataFields.ContainsKey("action"))
            {
                action = cp.ComponentTemplate.MetadataFields["action"].Value;
            }

            //return ChildActionExtensions.Action(htmlHelper, action, controller, new { componentPresentation = ((ComponentPresentation)cp) });
            return htmlHelper.Action(action, controller, new { componentPresentation = ((ComponentPresentation)cp) });
        }

    }
}
