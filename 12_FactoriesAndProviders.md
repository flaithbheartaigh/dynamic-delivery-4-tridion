# Design #
![http://dynamic-delivery-4-tridion.googlecode.com/svn/wiki/images/FactoriesAndProvidersDesign.png](http://dynamic-delivery-4-tridion.googlecode.com/svn/wiki/images/FactoriesAndProvidersDesign.png)


# Details #

  * The providers should ONLY contain code that interact with a source system such as Tridion.
  * The factories are responsible for deserialization and caching
  * Parameters of the factory methods should be simple types (strings, integers, etc) as much as possible. Factories are allowed to return DD4T-specific types (from the DD4T.ContentModel.Contracts namespace).