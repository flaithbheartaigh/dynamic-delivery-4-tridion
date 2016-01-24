DD4T can be used to create a Tridion-driven MVC application.

# Let's look at Tridion #

Before we explain DD4T, let's first take a closer look at Tridion's publishing model. A Tridion system consists of two separate parts: Content Management and Content Delivery. They can be installed on different machines.

The **Content Management** system is where the authors and editors do their work. They create content, tag it with metadata, upload images, create web pages, and manage their web site using the Tridion Content Manager (or TCM).

The **Content Delivery** system (TCD) is less visible. It is the system that delivers content to an end point, usually a web site (but it could well be a mobile site or another medium). The process of transferring content and pages from the TCM to the TCD is called publishing.

There are two ways to publish content:

  1. By creating files (e.g. html, jsp, asp, aspx)
  1. By creating database records

In the latter case, the pages and content are stored in a database called the 'broker'. Tridion offers an extensive API to retrieve the pages and content from the broker.

So why don't we just write an MVC application on top of the broker API, you might ask. Fair question. The reason is that for Tridion, publishing equals transforming the content into HTML. In most Tridion implementations the pages are rendered to HTML before they are deployed to the TCD environment, regardless of whether you choose to deploy to the file system or the database.
This takes away most of the fun of MVC. After all, separating the model from the view is what MVC is all about.

# DD4T to the rescue #

DD4T solves this problem by making sure the pages and content are published to the broker as data, without any HTML rendering. Like Tridion it consists of two parts:

  * a set of Tridion Templates that convert the pages and content into portable XML
  * a .NET API that converts the portable XML into a simple but powerful object model, plus classes that help you use this model in an MVC application

The following diagram shows the key parts of the architecture:

![http://dynamic-delivery-4-tridion.googlecode.com/svn/wiki/images/XMLflow.png](http://dynamic-delivery-4-tridion.googlecode.com/svn/wiki/images/XMLflow.png)


As you can see in this diagram, the role of the editor and the (web site) visitor are completely normal. In fact, this is an important aspect of the DD4T architecture: it doesn't change any functionality for the web site visitor or the editor. The main differences with a typical Tridion implementation are:

  * everything is published to the broker database, nothing goes to the file system (pages, binaries, component presentations)
  * the Tridion templates use the DD4T library to generate XML, templates NEVER generate HTML markup!!
  * the DD4T library consumes the XML and converts it into objects (Pages, Components, etc) which are passed on to the MVC application
  * The HTML markup and web functionality is generated in the MVC application through controllers and views


If we zoom in on the right hand side of the diagram, it looks like this:

![http://dynamic-delivery-4-tridion.googlecode.com/svn/wiki/images/DD4T-2.png](http://dynamic-delivery-4-tridion.googlecode.com/svn/wiki/images/DD4T-2.png)

Are you ready to give it a try? Go to [Installation](Installation.md) and set up your own environment in minutes.