## Create your first Katalon Studio plugin

This tutorial walks you through creating your first Katalon Studio plugin. The plugin can print a hello message in Console after it was installed successfully in Katalon Studio and hooks into Katalon Studio execution event.

You can find the source code of this tutorial at: (https://github.com/katalon-studio/katalon-studio-sample-plugin)https://github.com/katalon-studio/katalon-studio-sample-plugin.

A Katalon Studio plugin is a Maven-based Java project, and also an [OSGI bundle](https://www.google.com/search?q=ogsi+bundle&oq=ogsi+bundle&aqs=chrome..69i57j0l5.2074j0j7&sourceid=chrome&ie=UTF-8) project. A plugin contains these components:
- The `pom.xml` describes your plugin, what it is (name, version, vendor,...).
- The `plugin.xml` tells Katalon Studio about all extensions of your plugin.
- All packaged codes of your plugin.

#### Prerequisites:

1. Java SDK 1.8
2. Maven 3.3+
3. Download Katalon Studio v6.0.3 (beta) in this link.

### Step 1: Create a Maven-based Java project
Let create your Java Maven-based project with project structure looks like this:
```
├─── src/
│   └─── main/
│       ├─── java/
│       └─── resources/
│           └─── plugin.xml
└─── pom.xml
```

During this tutorial, we assume that (you can replace these declarations if you want):
- com.mycompany.plugin is groupId
- my-first-katalon-plugin is artifactId
- com.mycompany.plugin is default package


### Step 2: Update `pom.xml`
Update your `pom.xml` file with this template:

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

(Optional) You can change the tags under **REPLACE ME** if you want.

#### Step 3: 
What a plugin can do in Katalon Studio is described [here](https://github.com/katalon-studio/katalon-studio-platform/blob/master/com.katalon.platform/plugin.xml). You will see many `extension` tags here. Once of them is called `Extension Point` that describes a way to allow client plugins can hook into Katalon Studio system. A plugin can provide many functions of that, called `Extension` and declare them in `plugin.xml`.

Let assume we need to `Subcribe plugin installation event`
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
This declaration means:
- id: the id of the extension point
- interfaceClass: the interface class that requires client plugins should provide the implementation to extend this feature.
- serviceClass: a internal service class.

#### Step 4:
To extend this extension point, we need to declare in `plugin.xml` like this:
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
This declaration means:
- id: the unique id of the your extension. You can replace by any name but it must be unique.
- extensionPointId: the id of the extension point above. If we want to extend other extension point, you just need to replace this by other extension point id.
- implementationClass: the class that implements the `interfaceClass` was mention at Step 3. Now, we create it.

#### Step 6: Create your `implementationClass`
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

### Step 7: Build your plugin
Type `mvn clean package` and wait a minute until the **BUILD SUCCESS** message from command line.

![Build Plugin Successfully](https://raw.githubusercontent.com/katalon-studio/katalon-studio-platform/dev/docs/turorials/images/img_build_plugin.png)

After the build completed, there is a `my-first-katalon-plugin.jar` under the `target` folder, we will need this to launch your plugin in the next step.

### Step 8: Test your plugin with Katalon Studio

Open Katalon Studio v6.0.3 (beta), then activate **Event Log** tab that's nearby `Console Log` tab. All your plugin's message will be displayed here.

Launch your plugin by clicking on **Plugin/Install Plugin** menu and choose the jar was mentioned above.

You should see the notification message `Plugin installed successfully` from Katalon Studio and a message `Hello, my plugin is: com.mycomany.my-first-katalon-plugin` was displayed in `Event Log` tab. Success!

![Load Plugin Successfully](https://raw.githubusercontent.com/katalon-studio/katalon-studio-platform/dev/docs/turorials/images/img_load_plugin.png)

### Step 9: Create another extension
Now we back to [`plugin.xml`](https://github.com/katalon-studio/katalon-studio-platform/blob/master/com.katalon.platform/plugin.xml) file of Katalon Studio Platform, and find the declaration of `Subcribe KS execution event`:
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

Ok, we declare our extension in `plugin.xml` and `implementationClass`
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

                System.out.println("Test execution completed: " + testSuiteContext.getId());
            }
        });
    }
}
```

This `implementationClass` just prints an execution completed message to console but you can extend this class to do more business stuff here. A good sample for this is [Slack Integration Plugin](https://github.com/katalon-studio/katalon-studio-slack-plugin). You can try how it integrates with Slack.

### Step 10: Reload your plugin
Type `mvn clean package` and wait a minute until the **BUILD SUCCESS** message from command line.

Click on `Plugin/Uninstall Plugin` to uninstall your first loaded plugin.

Click on `Plugin/Install Plugin` and choose the jar file again.

### Step 11: Test execution event
Run a test suite and wait until the execution finished.

Look at the `Event Log` and see the message will display here, like this:

![Execution message](https://raw.githubusercontent.com/katalon-studio/katalon-studio-platform/dev/docs/turorials/images/img_execution_message.png)