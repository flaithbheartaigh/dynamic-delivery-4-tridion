# Introduction #

This is a quickstart tutorial to get you up and running with a Sample ASP.NET MVC3 project. It provides you with a simple basic project you can use to boot up your first DD4T project.

# Prerequisites #
  * You installed the DD4T Templates. (If not, read the [Template Installation section](TemplateInstallation.md))
  * Visual Studio 2010 with the NuGet Package Manager installed ([Install NuGet Package Manager](http://visualstudiogallery.msdn.microsoft.com/27077b70-9dad-4c64-adcf-c7cf6bc9970c))

## 1. Download the Visual Studio Template ##
Go to the 'download'  section and download the file 'Web.DD4T.vsix'.
After downloading, install it.
You now have a new Project Template called 'DD4T.Web'.

## 2. Create a new project in Visual Studio based on the new template ##
Open Visual Studio and choose 'New Project'.
Locate the new 'DD4T.Web'  template and click 'Ok'.

## 3. Install the newest version of the framework with NuGet ##
Go to the NuGet package-manager-console and typ in the following command:

Install-Package DD4TFramework

MAKE SURE THAT YOU SELECT THE 'DD4T.Web.Mvc' PROJECT AS DEFAULT PROJECT IN THE PACKAGE-MANAGER-CONSOLE!

## 4. Install your Tridion Content Delivery Instance ##
There are 2 ways of doing this:

  * Inline: create a 'lib' and 'config' directory IN the 'bin' folder of 'Your Website' and copy the appropriate files (jars, dll's, configs)
  * Global: Create a 'TRIDION\_HOME' environment variable and point it to your Tridion Content Deliver instance.

Refer to the SDL Tridion documentation for detailed information on how to setup a Tridion Content Delivery Instance.

Don't forget to add a reference to Tridion.ContentDelivery.dll, Tridion.ContentDelivery.Configuration.dll and Tridion.ContentDelivery.Interop.dll to Your Website.

## 5. Adjust the Web.config ##
Open the web.config and find this setting:


&lt;add key="WebSite.Your.Website.PublicationId" value="7"/&gt;



Change its value to your publication id or set to 0 if your URLs are unique across publications.

## 6. RUN ##
If you have published anything you can now run your website.
Note: the first time there will be a warning on the screen that it cannot find certain views. This is expected behavior since you have not yet build your first view. This is explained in [the next section, Page Views](DotNetPageViews.md).