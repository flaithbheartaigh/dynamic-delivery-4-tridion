We assume that everyone working with DD4T has at least a basic understanding of the MVC pattern. As a quick refresher, you can check out the flow of a typical ASP.Net MVC page request in the diagram below (java-based MVC frameworks are very similar).

![http://dynamic-delivery-4-tridion.googlecode.com/svn/wiki/images/MVCflow.png](http://dynamic-delivery-4-tridion.googlecode.com/svn/wiki/images/MVCflow.png)

The diagram is taken from a very helpful 'starter' page: http://www.codeproject.com/KB/dotnet/MVC_architecture.aspx.

To sum it up, the most important components of MVC are:

**URL parsing: the application determines which controller and action to use based on the URL** Controller.Action: the specified action (a method of a controller) is called; the action retrieves a model and passes it on to a view
**Model: the model represents the business domain; it contains mostly data definitions but also actions directly related to the data** View: the model is transformed into a look and feel

**Note: in the diagram, ASPX is used for the views. It is actually more common in MVC projects to use a different view language: Razor.**

# URL ownership #

DD4T is all about letting you use a standard MVC approach as much as possible. However, there is one big exception. In a regular MVC application, the app developer has complete control over the URLs. He or she simply maps the different screens of the application to logical URLs of the type /Controller/Action/Parameters.
A DD4T-driven application is different. Not the developer, but the content manager determines the URLs. Tridion is leading: the MVC application must handle every requested URL, regardless of its structure. We do this by mapping all URLs to the PageController.Page action.

There are some exceptions, such as when we need to implement specific functionality on the site, like search. But as a rule, each URL is handled by the same controller and action!