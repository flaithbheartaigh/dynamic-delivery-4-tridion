Although there is no class called **PageController** in DD4T, there will almost always be one in your application. DD4T has an interface IPageController that you can implement, as well as a TridionBaseController, which is an abstract class that contains a complete implementation of the basic functionality.

To create a very simple PageController, just use the following code:

```
using System;
using System.Web;
using System.Web.Mvc;
using DD4T.Mvc;
using DD4T.ContentModel;

namespace MyWeb
{
    public class PageController : TridionControllerBase, IPageController
}
```


The basic functionality of the PageController is described in the page about Dynamic Delivery for Tridion. The available actions in this controller are described in more detail here.

# PageController.Page #
This action is normally mapped to all URLs that do not match any of the other rules in an application.

**The action receives the entire URL as its input parameter** The PageFactory.TryGetPage method is called. If there is no valid page (PageNotFoundException is thrown), the action raises a 404 exception.
**The name of the page view is read from the 'view' metadata field of the page template** This view is called (it must normally be stored in /Views/Page/VIEWNAME.cshtml)

# PageController.PreviewPage #
Can be mapped to any url (e.g. /Preview), but only if the method is POST.

This action is provided to support preview in the Tridion CME. It works together with the PreviewPage template building block.
The action reads an XML definition of the page that is posted to it, and uses that to create a Page object with. It does NOT have to go to the broker database at all. However, the end result is exactly the same. This is how we can achieve a 100% WYSIWYG and functional preview.