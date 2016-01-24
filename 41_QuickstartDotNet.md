# Introduction #

This is a quickstart tutorial to get you up and running with a Sample ASP.NET MVC4 project. It provides you with a simple basic project you can use to boot up your first DD4T project.

# Prerequisites #
  * You installed the DD4T Templates. (If not: check out the download section)
  * Visual Studio 2012 or 2013 with the latest NuGet Package Manager installed ([Install NuGet Package Manager](http://visualstudiogallery.msdn.microsoft.com/27077b70-9dad-4c64-adcf-c7cf6bc9970c))

## 1. Download the Visual Studio Template ##
Go to the 'download'  section and download the file ' DD4T.1.31-MVC4-Tridion2013-VS2012-VS2013.vsix '.
After downloading, install it.
You now have a new Project Template called 'DD4T MVC4 Web Application'.

## 2. Create a new project in Visual Studio based on the new template ##
Open Visual Studio and choose 'New Project'.
Locate the new 'DD4T MVC4 Web Application'  template and click 'Ok'.

## 3. Get Dependencies ##
Enable Package Restore http://docs.nuget.org/docs/reference/package-restore and build the project

## 4. Install your Tridion Content Delivery Instance ##
There are 2 ways of doing this:

  * Inline: create a 'lib' and 'config' directory IN the 'bin' folder of the web project and copy the appropriate files (jars, dll's, configs)
  * Global: Create a 'TRIDION\_HOME' environment variable and point it to your Tridion Content Delivery instance.

Refer to the SDL Tridion documentation for detailed information on how to setup a Tridion Content Delivery Instance.

**Note: you can only run in IIS Express if you use the 32 bit DLLs**

## 5. Adjust the Web.config ##
Open the web.config and add this setting:
```
<add key="DD4T.PublicationId" value="7"/>
```
Change it's value to your publication id.

## 6. RUN ##
If you have published anything you can now run your website.
Note: the first time there will be a warning on the screen that it cannot find certain views. This is expected behavior since you have not yet build your first view. Read the warning and act upon it. See the example views for hints.