## Create your first Katalon Studio plugin
This tutorial will walk you through creating your first Katalon Studio plugin. The plugin in this tutorial does two things:
- Listens to the [plugin activation event](https://github.com/katalon-studio/katalon-studio-platform/blob/master/com.katalon.platform/src/main/java/com/katalon/platform/api/extension/PluginActivationListener.java) then prints a hello message after the plugin was installed successfully in Katalon Studio.
- Listens to the [test execution event](https://github.com/katalon-studio/katalon-studio-platform/blob/master/com.katalon.platform/src/main/java/com/katalon/platform/api/extension/EventListenerInitializer.java) then prints a report message in Console.

You can find the source code of this tutorial [here](https://github.com/katalon-studio/katalon-studio-sample-plugin).

A Katalon Studio plugin is a Maven-based Java project, and also an [OSGI bundle](http://spring.io/blog/2008/02/18/creating-osgi-bundles/) project. A plugin contains these components:
- The `pom.xml` to describe your plugin, what it is (name, version, vendor,etc).
- The `plugin.xml` to let Katalon Studio know about all the extensions of your plugin.
- All packaged codes of your plugin.

#### Prerequisites:

1. Java SDK 1.8
2. Maven 3.3+
3. Download Katalon Studio v6.0.3 (beta) in this link.

### Step 1: Create a Maven-based Java project
Let's create your Java Maven-based project with project structure looks like this:
```
├─── src/
│   └─── main/
│       ├─── java/
│       └─── resources/
│           └─── plugin.xml
└─── pom.xml
```

During this tutorial, we are using the example declarations below. These declarations can be changed depend on your specific needs:
- **com.mycompany.plugin** is groupId.
- **my-first-katalon-plugin** is artifactId.
- **com.mycompany.plugin** is the default package.


### Step 2: Update `pom.xml`
Update your `pom.xml` file with the template below:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">

	<modelVersion>4.0.0</modelVersion>

	<parent>
		<groupId>com.katalon</groupId>
		<artifactId>com.katalon.platform.parent</artifactId>
		<version>1.0.5</version>
	</parent>

    <!-- REPLACE ME: Your plugin description here -->
	<groupId>com.mycompany.plugin</groupId>
	<artifactId>my-first-katalon-plugin</artifactId>
	<version>1.0.0</version>

	<packaging>bundle</packaging>

	<dependencies>
		<!-- Katalon Platform dependencies-->
		<dependency>
			<groupId>com.katalon</groupId>
			<artifactId>com.katalon.platform</artifactId>
			<version>1.0.5</version>
		</dependency>
	</dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-dependency-plugin</artifactId>
				<version>2.4</version>
				<executions>
					<execution>
						<id>unpack-dependencies</id>
						<phase>prepare-package</phase>
						<goals>
							<goal>unpack-dependencies</goal>
						</goals>
						<configuration>
							<excludes>com/katalon/platform/**,org/eclipse/**,org/osgi/**</excludes>
							<includes>**/*.class</includes>
							<outputDirectory>${project.build.outputDirectory}</outputDirectory>
						</configuration>
					</execution>
				</executions>
			</plugin>
			<plugin>
				<groupId>org.apache.felix</groupId>
				<artifactId>maven-bundle-plugin</artifactId>
				<extensions>true</extensions>
				<configuration>
					<instructions>
						<Bundle-SymbolicName>${project.groupId}.${project.artifactId};singleton:=true</Bundle-SymbolicName>
						<Bundle-Version>${project.version}</Bundle-Version>
						<Import-Package></Import-Package>
						<DynamicImport-Package>*</DynamicImport-Package>
						<_noee>true</_noee>
						<_nouse>true</_nouse>

						<!-- REPLACE ME: Change your public export package here -->
						<Export-Package>com.mycompany.plugin*</Export-Package>
					</instructions>
				</configuration>
			</plugin>
		</plugins>
	</build>
</project>
```
The tags can be changed under **REPLACE ME** if needed.

### Step 3:
Katalon Studio Platform allows client plugins to contribute to Katalon Studio core features. All of the plugins are described at [this link](https://github.com/katalon-studio/katalon-studio-platform/blob/master/com.katalon.platform/plugin.xml). 
There are many `extension` tags here. Each of these tags is a `Extension Point` describing specifications to allow client plugins hook into Katalon Studio platform. A plugin can contribute to many extension points by declaring it in `plugin.xml` file.

For example, we want to `Subscribe plugin installation event`:
```xml
 <extension
   		point="com.katalon.platform.extensions_point">
	  <point
            id="com.katalon.platform.api.extension.pluginActivationListener"
            interfaceClass="com.katalon.platform.api.extension.PluginActivationListener"
            serviceClass="com.katalon.platform.internal.lifecycle.PluginActivationListenerService">
      </point>	
</extension>
```
Above declarations mean:
- id: the id of the extension point.
- interfaceClass: the required interface class that client plugins should provide the implementation to extend this feature.
- serviceClass: an internal service class.

To extend this extension point, we need to declare in `plugin.xml` as shown below:
```xml
<plugin>
	<extension
	        point="com.katalon.platform.extensions">
	    <point
	          id="com.mycompany.plugin.myFirstExtensionId"
	          extensionPointId="com.katalon.platform.api.extension.pluginActivationListener"
	          implementationClass="com.mycompany.plugin.MyPluginActivationListener"
	    </point>
	</extension>
</plugin>
```
Above declarations mean:
- id: the unique id of the your extension. You can replace by any name but it must be unique.
- extensionPointId: the id of the extension point. Simply change the id if you want to extend other extension points.
- implementationClass: the class that implements the `interfaceClass` mentioned at Step 3. Next, we create it.

### Step 4: Create your `implementationClass`
Create a class file `MyPluginActivationListener` under `src/java/main` folder:
```java
package com.mycompany.plugin;

import com.katalon.platform.api.Plugin;
import com.katalon.platform.api.extension.PluginActivationListener;

public class MyPluginActivationListener implements PluginActivationListener {
	// After this plugin is activated, we will print a hello message to console.
    @Override
    public void afterActivation(Plugin plugin) {
        System.out.println("Hello, my plugin is: " + plugin.getPluginId());
    }
}
```

### Step 5: Build your plugin
Type `mvn clean package` and wait until the **BUILD SUCCESS** message is displayed from the command line window.

![Build Plugin Successfully](https://raw.githubusercontent.com/katalon-studio/katalon-studio-platform/dev/docs/turorials/images/img_build_plugin.png)

After the build completed, there is a `my-first-katalon-plugin.jar` under the `target` folder, we will need this to launch your plugin in the next step.

### Step 6: Test your plugin
Open Katalon Studio v6.0.3 (beta) and activate **Event Log** tab that's nearby `Console Log` tab. All your plugin's message will be displayed here.

Launch your plugin by clicking on **Plugin/Install Plugin** menu and choose the jar was mentioned above.

You should be able to see the notification message `Plugin installed successfully` from Katalon Studio and the message `Hello, my plugin is: com.mycomany.my-first-katalon-plugin` is displayed in `Event Log` tab. Success!

![Load Plugin Successfully](https://raw.githubusercontent.com/katalon-studio/katalon-studio-platform/dev/docs/turorials/images/img_load_plugin.png)

### Step 7: Create an [execution event](https://github.com/katalon-studio/katalon-studio-platform/blob/master/com.katalon.platform/src/main/java/com/katalon/platform/api/extension/EventListenerInitializer.java) extension
Now, we back to [`plugin.xml`](https://github.com/katalon-studio/katalon-studio-platform/blob/master/com.katalon.platform/plugin.xml) file of Katalon Studio Platform, and find the declaration of `Subscribe KS execution event`:
```xml
<extension
    	point="com.katalon.platform.extensions_point">
    <point
        id="com.katalon.platform.api.extension.eventListener"
        interfaceClass="com.katalon.platform.api.event.EventListenerInitializer"
        serviceClass="com.katalon.platform.internal.event.EventListenerService">
    </point>
</extension>
```

Now, we need to declare our extension in `plugin.xml`
```xml
<extension
		point="com.katalon.platform.extensions">
	<point
		id="com.mycompany.plugin.myAnotherExtensionId"
		extensionPointId="com.katalon.platform.api.extension.eventListener"
		implementationClass="com.mycompany.plugin.MyExecutionEventListener">
	</point>
</extension>
```

and create `implementationClass`:
```java
package com.mycompany.plugin;

import org.osgi.service.event.Event;

import com.katalon.platform.api.event.EventListener;
import com.katalon.platform.api.event.ExecutionEvent;
import com.katalon.platform.api.execution.TestSuiteExecutionContext;
import com.katalon.platform.api.extension.EventListenerInitializer;
public class MyExecutionEventListener implements EventListenerInitializer {

    @Override
    public void registerListener(EventListener listener) {
        listener.on(Event.class, event -> {
            if (ExecutionEvent.TEST_SUITE_FINISHED_EVENT.equals(event.getTopic())) {
                ExecutionEvent eventObject = (ExecutionEvent) event.getProperty("org.eclipse.e4.data");

                TestSuiteExecutionContext testSuiteContext = (TestSuiteExecutionContext) eventObject
                        .getExecutionContext();

                System.out.println("Test execution completed: " + testSuiteContext.getReportId());
            }
        });
    }
}
```

This `implementationClass` just prints an execution completed message to console but you can extend this class to do more business logics. A good example for this is [Slack Integration Plugin](https://github.com/katalon-studio/katalon-studio-slack-plugin). You can see how it integrates with Slack application.

### Step 8: Reload your plugin
Type `mvn clean package` and wait until the **BUILD SUCCESS** message is displayed in the command line window.

Click on `Plugin/Uninstall Plugin` to uninstall your first loaded plugin.

Click on `Plugin/Install Plugin` and choose the jar file again.

### Step 9: Test execution event
Run the test suite and wait until the execution finished.

Look at the `Event Log` and the message should be displayed as shown below:
![Execution message](https://raw.githubusercontent.com/katalon-studio/katalon-studio-platform/dev/docs/turorials/images/img_execution_message.png)
