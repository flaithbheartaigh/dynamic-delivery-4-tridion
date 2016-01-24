# Introduction #

Building the framework using Maven requires that you have a valid license for SDL Tridion and make the Tridion API jar-files available in a local repository.


# Details #

There are six jar file dependencies that contain various Tridion APIs which are not open source and requires a license for SDL Tridion. Hence these files are not available from any public maven repository.

  * cd\_broker.jar
  * cd\_core.jar
  * cd\_linking.jar
  * cd\_cache.jar
  * cd\_datalayer.jar
  * cd\_model.jar

These jar-files are available in the software distribution from SDL that you get access to when you purchase a license for SDL Tridion.
So, to be able build the project you need to obtain a license for the SDL Tridion and then make the jar-files available in a local repository using the maven metadata given for the same files in the POM of dynamic delivery.



## Unit testing ##

Several unit test classes have been added to the project. At the moment they have all been set to be ignored. The reason for this is that they were developed for the original version of dynamic delivery that was using the 2009 APIs. After updating the framework to run with Tridion 2011, the tests no longer work. The project focused on getting the framework code running with 2011 instead of the tests. So, until the tests are also updated, all of them will be annotated to be ignored to allow the maven builds to pass.