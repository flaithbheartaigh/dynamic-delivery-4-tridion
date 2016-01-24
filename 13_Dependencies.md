# Design #
![http://dynamic-delivery-4-tridion.googlecode.com/svn/wiki/images/dependencies.png](http://dynamic-delivery-4-tridion.googlecode.com/svn/wiki/images/dependencies.png)


# Details #
The goal is to make DD4T usable in as many situations as possible. This is achieved by minimizing the number of dependencies on every level.

Examples of how DD4T can be used:

  * From a Windows Service, Windows Form or Console Application, DD4T.Factory can be used to retrieve pages and components from Tridion.
  * From an ASP.Net web application or web site, DD4T.Factory can be used to retrieve pages and components as 'plain old dotnet objects'.
  * From an ASP.Net web application or web site, DD4T.Web can be used to retrieve sitemaps and resource files (resx) from the Tridion broker.
  * From an ASP.Net MVC 3.0 web application, DD4T.Mvc can be used to wire up controllers to the page factory and component factory automatically. This gives you a Tridion-driven MVC site in a matter of minutes.

# Work in progress #
> The diagram shows the desired situation; the reality at this moment is different:
  * There is no DD4T.Web; this logic is included in DD4T.Mvc (result: some DD4T functionality like sitemaps require MVC 3.0 now, where this would not be strictly necessary)
  * There are too many dependencies on DD4T.\ContentModel because it contains the \TcmUri class. This class must be moved to Contracts, reducing the number of dependencies.
  * The providers should ONLY contain code that interact with a source system such as Tridion.