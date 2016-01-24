DD4T has an open caching architecture based on 'cache agents'. Out of the box you can choose between three cache agents:

**The DefaultCacheAgent uses the System.Runtime.Caching API that was introduced in .NET 4.0. This API can be used from web applications as well as Windows applications, services etc.** The WebCacheAgent uses the System.Web.Cache API. This cache agent is recommended if you plan to use DD4T with .NET 3.0 and below.
**The NullCacheAgent can be used to switch off caching completely.**


# Configuring a cache agent #
To configure the cache agent of your choice, you just need to set the CacheAgent property of the PageFactory, ComponentFactory or any other DD4T factory. This can be done in code as follows:

```
PageFactory pageFactory = new PageFactory();
pageFactory.CacheAgent = new WebCacheAgent();
```

It is of course also possible to inject this dependency using Unity or another DI framework.
If you do not insert a CacheAgent, the DefaultCacheAgent is used.

The DefaultCacheAgent is configured through the Web.config or App.config of your application.


# Implement your own cache agent #
If you are not satisfied with the cache agents that come out of the box, you can write your own caching logic. Just create a class that implements the interface DD4T.ContentModel.Contracts.Caching.ICacheAgent. You need to implement some methods like:

**object Load(string key)** void Store(String key, object item)

This way it is easy to wire DD4T up to an existing caching framework, or hand-roll your own.