# Katalon Studio Platform

Katalon Studio Platform is an open source project that provides some public APIs for Katalon Studio plugins can interact, contribute external features to Katalon Studio.

## Basic Concepts
### Plugin
Represents a Java project that can extend Katalon Studio features.

A plugin can contribute features, utilities to Katalon Studio and other plugins
by its extensions and can allow other plugins contribute to itself by extension points.

### Extension Point
Katalon Studio Platform provides and allows other plugins to provide many extension points.
A extension point is a public spot that allow a plugin can collect extensions from other plugins.

To define an extension point, we need to add these source code to `plugin.xml` file.
```
<extension
         point="com.katalon.platform.extensions_point">
      <point
            id="com.example.my_extension_point_id"
            interfaceClass="com.example.MyExtensionPointInterfaceClass"
            serviceClass="com.example.MyExtensionPointServiceClass">
      </point>
</extension>
```
`id` (required): Unique id of your extension point.

`interfaceClass` (required): Your defined interface class that describes enough requirements.
Other extensions that wants to contribute to this extension point will implement this interface.

`serviceClass` (optional): An implementation of ExtensionListener. This class will be notified after an extension is registered, de-registered to
your owned extension point.

### Extension
An extension points to an extension point to extend features of the pointed extension point.

To define an extension, we need to add to `plugin.xml` by following these sample:
```
<extension
           point="com.katalon.platform.extensions">
        <point
                id="com.example.my_extension_id"
                extensionPointId="id_of_the_extension_point"
                implementationClass="com.example.MyExtensionClassImpl">
        </point>
</extension>
```
`id` (required): Unique id of your point.

`implementationClass` (required): An implementation of the extension point interface class that is described in extension point's plugin.

## Package structure
com.katalon.platform.api.*: Katalon Studio public APIs packages that exposes some default extension points and some utility services that
allows user plugins can interact with Katalon Studio.

com.katalon.platform.internal.*: Internal packages that allows katalon-studio-platform can connect with Katalon Studio. Should NOT be called from user plugins.

## Build

Requirements:
- JDK 1.8
- Maven 3.3+

`mvn clean install`
