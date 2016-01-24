While the PageController is basically a straightforward implementation of the 'C' in MVC, the ComponentController is a little bit different. In most MVC applications, there is only ONE controller and ONE view per request. We have seen in the page about DD4T how the page view is mapped to page templates in Tridion. However, Tridion has two kinds of templates: page templates and component templates. This is a very powerful concept. Let's see why.

A Tridion-driven web site is mostly managed by marketeers. What marketeers want most of all is control over there site. They want to be able to tell the system exactly what each snippet of content (= Component) should look like.
A typical implementation contains 3 or 4 page types and 15 - 20 schemas (types of content). If we would do without component templates, this means we would need between 45 and 80 page templates to handle all the possible combinations. Or, if we limit ourselves to 3 or 4 page templates, we would have to make these so complex that they are able to handle those different content types, with different designs and business rules for each of them.

By using component templates we reduce the complexity of our templates enormously: we only have to create 3 or 4 fairly simple page templates, and 15 to 20 equally simple component templates!

However, we did have to tweak MVC a tiny little bit to achieve this.

![http://dynamic-delivery-4-tridion.googlecode.com/svn/wiki/images/ComponentController.png](http://dynamic-delivery-4-tridion.googlecode.com/svn/wiki/images/ComponentController.png)


The upper part of the diagram should be familiar to you. When a page view calls ons of the !RenderComponentPresentationsXX methods, a helper class is called which determines the controller and action to use for the component presentation. The helper stores the component presentation inside the request (in the RouteData) and calls the controller/action.

The controller and action can be configured in two places:
**By adding an appSetting to the Web.config with the key 'Controller' and another one with the key 'Action'** By adding metadata fields 'Controller' and 'Action' to the component template in Tridion
The second will override the first.

Normally, the action called will be ComponentController.ComponentPresentation. This action does the following:

  1. Read the component presentation from the RouteData;
  1. Read the view name from the metadata of the component template;
  1. Store the component presentation in the view bag;
  1. Dispatch the request to this view, using the component as the model.

For more information about using component views, see [Component Views](http://code.google.com/p/dynamic-delivery-4-tridion/wiki/26_ComponentViews).