# DD4T templates and Tridion versions #
Tridion templates are C# classes (well, there are other types but let's not get into those). These classes reside inside assemblies, which need references to some native Tridion assemblies to do their work. The template developer creates his classes in a Class Library project, builds the DLL and uploads it to Tridion.
The thing to realize is that Tridion templates are never executed on the developer's own machine, but always on a Tridion content manager server - hence the uploading. This means that you do not need to have your own TCM installation. You do, however, need to have some of Tridion's own DLLs available on your local computer, in order to compile your template assembly before it is uploaded.

In the DD4T environment, we are not allowed to upload the actual Tridion DLLs to the source code repository - after all, Tridion is commercial software. Instead, we set up a number of folders where the system expects to find these DLLs. If you (or the company you work for) have a valid Tridion license, you can simply copy these DLLs into the folder of your choice.

The Tridion DLLs must be copied to the following folder: `trunk\dependencies\Tridion <VERSION> DLLs`.

If, for example, you plan to develop DD4T templates and you are using Tridion 2009, copy the required DLLs to trunk\dependencies\Tridion 2009 DLLs.

You can find out which DLLs you need to copy by looking at the 'missing files.txt' in the '`Tridion <VERSION> DLLs`' folder.
Next, copy the same files to the folder 'trunk\dependencies\Tridion Active'.

Note: you should never check in any of these DLLs! Just keep them in your local folder.


# Working with multiple Tridion versions #
Imagine you want to test your DD4T templates on multiple Tridion systems, each with its own version. In that case, you must first make sure you copy the the relevant DLLs to each of the '`Tridion <VERSION> DLLs`' folders. For example, if you want to test with Tridion 2011 GA as well as 2011 SP1, you need to copy the correct DLLs to 'Tridion 2011 DLLs' and 'Tridion 2011 SP1 DLLs'.

You can now switch between Tridion versions easily, using the Tridion Version Switcher.exe. This tool can be found in the trunk\dependencies folder. Just double-click and select the version of your choice. The tool will remove all DLLs from the 'Tridion Active' folder, and copy all DLLs from the version-specific folder into the 'Tridion Active' folder.

It is also possible to configure the version switcher as an external tool in Visual Studio, as follows:

  1. Inside VS, click on Tools > External Tools...
  1. Click 'Add'
  1. Enter 'DD4T - Switch Tridion Versions' as Title
  1. In the Command box, navigate to the Tridion Version Switcher.exe
  1. In Initial Directory, enter the full path to the trunk\dependencies folder on your system (e.g. D:\Projects\DD4T\trunk\dependencies).
  1. Click 'OK' to save this configuration.

> You can now select 'DD4T - Switch Tridion Versions' from the Tools menu.