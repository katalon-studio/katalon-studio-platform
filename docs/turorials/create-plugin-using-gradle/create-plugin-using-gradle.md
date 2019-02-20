#Create Katalon Stuido platform plugin using Gradle
This tutorial will guide you create a Katalon Studio plugin as a Java Gradle-based project. The plugin in this tutorial does this thing:
- Listens to the [plugin activation event](https://github.com/katalon-studio/katalon-studio-platform/blob/master/com.katalon.platform/src/main/java/com/katalon/platform/api/extension/PluginActivationListener.java) then prints a hello message after the plugin was installed successfully in Katalon Studio.

You can find the source code of this tutorial [here](https://github.com/katalon-studio/katalon-studio-sample-plugin).

As the previous tutorial, we talked about what a Katalon Sudio plarform plugin is. A Katalon Studio plugin is an [OSGI bundle](http://spring.io/blog/2008/02/18/creating-osgi-bundles/) project. A Katalon Gradle-based plugin contains these components:
- The `build.gradle` to describe your plugin - what it is (name, version, vendor), what it does, list dependencies.
- The `plugin.xml` to let Katalon Studio know about all the extensions of your plugin.
- All packaged codes of your plugin.

#### Prerequisites:

1. Java SDK 1.8
2. Gradle 4.4+
3. Download Katalon Studio v6.0.4 (beta): [win32](https://s3.amazonaws.com/katalon/release-beta/6.0.4/Katalon_Studio_Windows_32.zip), [win64](https://s3.amazonaws.com/katalon/release-beta/6.0.4/Katalon_Studio_Windows_64.zip), [macOS](https://s3.amazonaws.com/katalon/release-beta/6.0.4/Katalon+Studio.dmg), and [linux64](https://s3.amazonaws.com/katalon/release-beta/6.0.4/Katalon_Studio_Linux_64.tar.gz).

### Step 1: Create a Java Gradle-based project

Let's create your Gradle Maven-based project with project structure looks like this:
```
├─── src/
│   └─── main/
│       ├─── java/
│       └─── resources/
│           └─── plugin.xml
└─── build.gradle
```

### Step 2: Update `build.gradle`

```groovy
plugins {
    id 'java'
    id "com.diffplug.gradle.osgi.bndmanifest" version "3.17.1"
}

ext {
    // REPLACE ME: Your plugin description here
    groupId = 'com.mycompany.plugin'
    artifactId = 'my-first-katalon-plugin'
    exportedPackaged = 'com.mycompany.plugin*'
    bundleVersion = '1.0.0'
}

group "$groupId"
version "$bundleVersion"

sourceCompatibility = 1.8

repositories {
    mavenCentral()
}

configurations {
    api
}

dependencies {
    implementation 'com.katalon:com.katalon.platform:1.0.6'
}

configurations.all {
    exclude group: 'org.eclipse.platform', module: 'org.eclipse.swt.${osgi.platform}'
}

jar {
    from fileTree("$buildDir/api/") {
        exclude 'META-INF/**'
    }
}

task extractApi(type: Sync) {
    dependsOn configurations.api

    from (configurations.api.collect { zipTree(it) })

    into "$buildDir/api/"
}

jar.dependsOn extractApi

jar.manifest.attributes(
        '-exportcontents': 'com.diffplug.*',
        '-removeheaders': 'Bnd-LastModified,Bundle-Name,Created-By,Tool,Private-Package,Require-Capability',
        'Import-Package': '',
        'DynamicImport-Package': '*',
        'Export-Package': "$exportedPackaged",
        'Bundle-SymbolicName': "${groupId}.${artifactId};singleton:=true",
        'Bundle-Version': "${bundleVersion}",
)
```

During this tutorial, we are using the example declarations below. These declarations can be changed depend on your specific needs:
- **com.mycompany.plugin** is groupId.
- **my-first-katalon-plugin** is artifactId.
- **com.mycompany.plugin** is the exported package.

The tags can be changed under **REPLACE ME** if needed.

### Step 3:
Katalon Studio Platform allows client plugins to contribute to Katalon Studio core features. All of the plugins are described at [this link](https://github.com/katalon-studio/katalon-studio-platform/blob/master/com.katalon.platform/plugin.xml). 
There are many `extension` tags here. Each of these tags is an `Extension Point` describing specifications to allow client plugins hook into Katalon Studio platform. A plugin can contribute to many extension points by declaring it in the `plugin.xml` file.

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
        System.out.println("Hello, my plugin that's using Gralde build is: " + plugin.getPluginId());
    }
}
```

### Step 5: Build your plugin
Type `./gradlew build` and wait until the **BUILD SUCCESSUL** message is displayed from the command line window.

![Gradle Build Plugin Successfully](https://raw.githubusercontent.com/katalon-studio/katalon-studio-platform/dev/docs/turorials/create-plugin-using-gradle/images/img_build_plugin.png)

After the build completed, there is a `my-first-katalon-plugin-1.0.0.jar` under the `build/libs` folder. We need this to launch your plugin in the next step.

### Step 6: Test your plugin
Open Katalon Studio v6.0.4 (beta) and activate **Event Log** tab that's nearby `Console Log` tab. All your plugin's message will be displayed here.

Launch your plugin by clicking on **Plugin/Install Plugin** menu and choose the jar was mentioned above.

You should be able to see the notification message `Plugin installed successfully` from Katalon Studio and the message `Hello, my plugin is: com.mycomany.my-first-katalon-plugin` is displayed in `Event Log` tab. Success!

![Load Plugin Successfully](https://raw.githubusercontent.com/katalon-studio/katalon-studio-platform/dev/docs/turorials/create-plugin-using-gradle/images/img_load_plugin.png)

### FAQ: How can we use third party dependencies using Gradle

To add a dependency to your project, specify a dependency configuration `compile` in the `dependencies` block of your build.gradle file.

For example, the following build.gradle file declares [jslack](https://github.com/seratch/jslack) as a third party dependency:
```groovy
dependencies {
    // Dependency on a remote binary
    compile 'com.github.seratch:jslack:1.1.6'
}
```