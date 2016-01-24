## Before you start ##
To install the DD4T templates, you need to have:

  * A Tridion Content Manager installed somewhere
  * The address of the content manager, a user name and a password
  * The URI (unique Tridion ID) of the folder where you want to install the templates.

If you don't know in which folder to install the templates, just pick any folder in the publication where you normally create templates. You can always move them later.

## Install the DD4T template building blocks ##

  * Download the latest DD4T.Templates zip file from the [project home page](http://code.google.com/p/dynamic-delivery-4-tridion/).
  * Unzip the file anywhere on your drive.
  * Double-click on the file Setup.exe.

> You will be asked to enter the address of the Tridion Content Manager, a user name, password and folder URI. A set of DD4T template building blocks is now uploaded into Tridion automatically.

## Create some templates ##

To use the new TBBs, you need to create at least one page template and one component template. The best way to do this is to start the Tridion template builder.

  * Create a page template
  * Insert the 'Generate dynamic page' TBB into it
  * Save and close
  * Create a component template
  * Insert the 'Generate dynamic component' TBB into it
  * Save and close


---

That's it. You are now ready to create and publish Tridion content. Next, find out how to configure DD4T for your [Java](JavaInstallation.md) or [.NET](DotNetInstallation.md) web application.