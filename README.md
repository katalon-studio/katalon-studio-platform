# Katalon Studio Platform

This open source platform is for developing Katalon Studio plugins.

## Basic Concepts

### Plugins

Each *Katalon Studio plugin* is a Maven-based Java project.

A plugin can contribute functions to Katalon Studio through its *extensions*.

### Extensions and Extension Points

An *extension point* is an area where plugins can contribute functions to Katalon Studio. Each function is called an *extension*.

Extensions are defined in the `plugin.xml` file.

```
<extension point="com.katalon.platform.extensions">
    <point
        id="com.example.my_extension_id"
        extensionPointId="id_of_the_extension_point"
        implementationClass="com.example.MyExtensionClassImpl">
    </point>
</extension>
```

## Package structure

com.katalon.platform.api.*: Contains extension points and APIs for interacting with Katalon Studio.

com.katalon.platform.internal.*: Internal packages that **should not** be used directly by plugins.

## Getting started

[Create your first Katalon Studio plugin](/docs/tutorials/create-your-first-plugin.md)

## Build

Requirements:
- JDK 1.8
- Maven 3.3+

`mvn clean install`

#### Test Javadoc
`mvn javadoc:javadoc`
