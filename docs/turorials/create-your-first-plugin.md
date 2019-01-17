## Create your first Katalon Studio plugin

This tutorial walks you through creating your first Katalon Studio plugin. A Katalon Studio plugin is a Maven-based Java project, and also an [OSGI bundle](https://www.google.com/search?q=ogsi+bundle&oq=ogsi+bundle&aqs=chrome..69i57j0l5.2074j0j7&sourceid=chrome&ie=UTF-8) project.

A Katalon Studio contains these components:
- The `pom.xml` describes your plugin, what it is (name, version, vendor,...).
- The `plugin.xml` tells Katalon Studio about all extensions of your plugin.
- The code is packaged in your plugin.

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

Tips: We can create a Java project by:
- Using Maven(mvn) command via [this turorial](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html)
- Using Eclipse IDE via [this turorial](https://www.tech-recipes.com/rx/39279/create-a-new-maven-project-in-eclipse/)
- Using IntelliJ IDE via [this turorial](https://www.jetbrains.com/help/idea/maven-support.html)

### Step 2: Update `pom.xml`
Update your `pom.xml` file with this template:

```
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
	<groupId>com.mycomany.plugin</groupId>
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

After that, we need to update the plugin description in `pom.xml`
```
	<!-- Your plugin description here-->
	<groupId>com.mycomany</groupId>
	<artifactId>my-first-plugin</artifactId>
	<version>1.0.0</version>
```

and public export package:
```
	<!-- REPLACE ME: Change your public export package here -->
    <Export-Package>com.mycompany.plugin*</Export-Package>
```

Build your project `mvn clean package` and wait a minute until the **BUILD SUCCESS** message from command line.

#### Step 3: Create your first hello extension

Let create a class implements `PluginActivationListener` under `com.mycompany.plugin` package:
```
package com.mycompany.plugin;

import com.katalon.platform.api.Plugin;
import com.katalon.platform.api.extension.PluginActivationListener;

public class MyPluginActivationListener implements PluginActivationListener {
    @Override
    public void afterActivation(Plugin plugin) {
        System.out.println("Hello, my plugin is: " + plugin.getPluginId());
    }
}
```

Let open `plugin.xml` under `src/main/resources` and add this template:
```
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

After that, we need to update the value of `implementationClass` to the class name the above. In my example: it should be `com.mycompany.plugin.MyPluginActivationListener`

Let build your project again `mvn clean package` and wait a minute until the **BUILD SUCCESS** message from command line.

After the build completed, there is a `${your-artifactId}.jar` under the `target` folder, we will need this to launch your plugin in the next step.

![Build Plugin Successfully](/docs/tutorials/images/img_build_plugin.png)

### Step 5: Launch your plugin in Katalon Studio

Open Katalon Studio v6.0.3 (beta), then activate **Event Log** tab that's nearby `Console Log` tab. All your plugin's message will be displayed here.

Launch your plugin by clicking on **Plugin/Install Plugin** menu and choose the jar was mentioned above.

You should see the notification message `Plugin installed successfully` from Katalon Studio and a message `Hello, my plugin is: com.mycomany.my-first-katalon-plugin` was displayed in `Event Log` tab. Success!

![Load Plugin Successfully](/docs/tutorials/images/img_load_plugin.png)

### Next steps

We will take closer about the source code of `my-first-katalon-plugin` and explain key concepts.

You can find the source code of this tutorial at.

Another good sample plugin to start is [Slack Integration](https://github.com/katalon-studio/katalon-studio-slack-plugin). You can try it in KS build.

All Katalon Studio built-in extension points are declared in this [source](https://github.com/katalon-studio/katalon-studio-platform/blob/master/com.katalon.platform/plugin.xml). You can take a look at this.