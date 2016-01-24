# Introduction - Creating the DD4T XML Templates for Publishing #

**Can use the installer from the downloads section instead**

To develop templates using DD4T, you need to perform some steps manually. This is done for two reasons:

  * We are not allowed to include Tridion DLLs and other artefacts in our open source repository
  * Addresses, user names, folders etc are unique to each Tridion implementation.



# Details #

These are the steps to create a local development environment for DD4T template code. Prerequisites:
  * VS2010
  * Ankh subversion plugin
  * A Tridion Content Manager (development) server

Note: you do not need a local Tridion installation.

  * Check out the DD4T trunk from SVN (https://dynamic-delivery-4-tridion.googlecode.com/svn/trunk)
  * Copy the following Tridion DLLs to the folder DD4T\dependencies\Tridion 2011 DLLs:
```
Tridion.Common.dll
Tridion.ContentManager.Common.dll
Tridion.ContentManager.dll
Tridion.ContentManager.Publishing.dll
Tridion.ContentManager.TemplateTypes.dll
Tridion.ContentManager.Templating.dll
Tridion.ContentManager.Templating.SiteEdit.dll
Tridion.Logging.dll
```
  * Use the dependencies/TridionVersionSwitcher.exe to copy in the correct version of Tridion DLLs to the Tridion Active folder
  * Copy the file TcmUploadAssembly.exe to the folder DD4T\dependencies\Tridion Tools
  * Create a config.xml using the TcmUploadAssembly.exe and place it in the same Tridion Tools folder (see Tridion documentation). Make sure it contains values for targetURL, userName, password and uploadPDB (true).
  * Create a file called TcmUploadAssembly.bat in the Tridion Tools folder, with the following contents:
```
%1 %2 %3 /folder:tcm:XX-XX-XX
```
  * Replace tcm:XX-XX-XX with the URI of the folder where you want to store the templates
  * Open the solution DD4T\dotnet\Dynamic Delivery Publishing.sln and build it
  * The first time, the build will fail with the message:
```
Error: Unable to load one or more of the requested types. Retrieve the LoaderExceptions property for more information.
```

  * Go to the folder DD4T\build and copy the following DLLs to your Tridion Content Management server:
```
DD4T.ContentModel.Contracts.dll
DD4T.ContentModel.dll
DD4T.Templates.Base.dll
```
  * Add these DLLs to the GAC using the gacutil /i command (open a VS command window as administrator)
  * Reset IIS on the TCM server
  * Go back to Visual Studio on your local work station and build again. The build should now be succesful, and the TBBs should be available in Tridion in the folder of your choice.