# Introduction #

Installing Dynamic Delivery Quickstart requires the following prequisites in place: <br>
1. A running Tridion 2011 infrastructure <br>
2. A DB-based content delivery <br>
3. Tomcat (installed with access to content delivery DB) <br>
4. Eclipse <br>
5. Tortoise (or some other SVN client) <br>
<br>
The steps to take are: <br>
1. Checkout the SVN tree <br>
2. Prepare your Tridion 2011 CME <br>
3. Import the content porter package <br>
4. Prepare your Tridion 2011 CDE <br>
5. Add Tridion jars, dlls, configuration & licences <br>
6. Add projects to eclipse <br>
7. Run antscript <br>
8. Configure dynamic delivery <br>
9. Connect to tomcat and run! <br>


<h2>1. Checkout the SVN tree</h2>

Using Tortoise (or some other SVN client), do a checkout of the trunk at googlecode.<br>
<br>
<h2>2. Prepare your Tridion 2011 CME</h2>

<< No longer necessary due to ILMerge being used >><br>
<br>
<h2>3. Import the content porter package</h2>

Run the content porter package from the build folder into your CME. Please reboot your Tridion server once done - template code being imported like that will fail unless stuff is restarted.<br>
<br>
<h2>4. Prepare your Tridion 2011 CDE</h2>

Configure your deployer. The only items that can sit on filesystem are pages and binaries - but be aware that DD4T does not want publishing to happen into the tomcat filesystem; publish to filesystem to somewhere else. You can, of course, choose to publish all into the DB!<br>
<br>
<h2>5. Add Tridion jars, dlls, configuration & licences</h2>
In the SVN checkout there are no Tridion DLL's, jars, configuration files or licences. Please fill the following locations:<br>
\dependencies\db-driver-lib -- place your DB driver jarfile here<br>
\dependencies\third-party-lib -- place the third-party-lib jars (from the Tridion installation files) here<br>
\dependencies\Tridion 2011 DLLs -- (for templating) -- place the Tridion DLL's used for templating here<br>
\dependencies\Tridion jarfiles -- place the Tridion CDE jarfiles (from the Tridion installation files) here<br>
\dependencies\Tridion tools -- (for templating) place your tcmuploadassembly.exe here<br>
\java\dd4t-example-site\WebContent\WEB-INF\classes -- place your CDE license and CDE configuration files (cd_link, logback and cd_storage) here.<br> Configure your storage conf like you've configured your deployer.<br>

<h2>6. Add projects to eclipse</h2>
In your eclipse, add the folders under \java as eclipse projects. You will see that both projects have lots of build errors due to lacking jarfiles.<br>
<br>
<h2>7. Run antscript</h2>
In the dd4t-example0site project, there is an antscript called "ant-scripts.xml". Open it, and run the "load-jarfiles" task. This task will copy every single jar into the WEB-INF\LIB folder of the dd4t-example-site project. After running the task, refresh all projects - afterwards they should both be free of build errors.<br>
<br>
Please note that the other projects use the WEB-INF-lib folder of the dd4t-example-site project as their libraries.<br>
<br>
<h2>8. Configure dynamic delivery</h2>
Inside of the \java\dd4t-example-site\webcontent\web-inf directory, open the urlmapping.xml file. Here, provide the content-porter imported publication id to the "dd4t" controller. This binds your example website to the imported publication. Also provide the news schema id to the "newslist" bean.<br>
<br>
Inside of the \java\dd-example\webcontent\web-inf directory, open the siteeditsettings.xml file. Here, if you want to use siteedit, configure your content manager host and set the enabled to true.<br>
<br>
<h2>9. Connect to tomcat and run!</h2>
Finally, connect the dd4t-example-site project to your local tomcat and run!