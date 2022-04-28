# Katalon Studio Platform

This open source platform is for developing Katalon Studio plugins.

## Companion products

### Katalon TestOps

[Katalon TestOps](https://analytics.katalon.com) is a web-based application that provides dynamic perspectives and an insightful look at your automation testing data. You can leverage your automation testing data by transforming and visualizing your data; analyzing test results; seamlessly integrating with such tools as Katalon Studio and Jira; maximizing the testing capacity with remote execution.

* Read our [documentation](https://docs.katalon.com/katalon-analytics/docs/overview.html).
* Ask a question on [Forum](https://forum.katalon.com/categories/katalon-analytics).
* Request a new feature on [GitHub](CONTRIBUTING.md).
* Vote for [Popular Feature Requests](https://github.com/katalon-analytics/katalon-analytics/issues?q=is%3Aopen+is%3Aissue+label%3Afeature-request+sort%3Areactions-%2B1-desc).
* File a bug in [GitHub Issues](https://github.com/katalon-analytics/katalon-analytics/issues).

### Katalon Studio
[Katalon Studio](https://www.katalon.com) is a free and complete automation testing solution for Web, Mobile, and API testing with modern methodologies (Data-Driven Testing, TDD/BDD, Page Object Model, etc.) as well as advanced integration (JIRA, qTest, Slack, CI, Katalon TestOps, etc.). Learn more about [Katalon Studio features](https://www.katalon.com/features/).

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

[Create your first Katalon Studio plugin](https://github.com/katalon-studio/katalon-studio-platform/blob/master/docs/tutorials/create-your-first-plugin.md)

[Create Katalon Studio plugin using Gradle](https://github.com/katalon-studio/katalon-studio-platform/blob/master/docs/tutorials/create-plugin-using-gradle/create-plugin-using-gradle.md)

[Debug your plugin using Eclipse IDE](https://github.com/katalon-studio/katalon-studio-platform/blob/master/docs/tutorials/how-to-debug-your-plugin/how-to-debug-your-plugin-using-Eclipse.md)

[API list](https://github.com/katalon-studio/katalon-studio-platform/blob/master/docs/tutorials/apilist.md)

## Build

Requirements:
- JDK 1.8
- Maven 3.3+

`mvn clean install`

#### Test Javadoc
`mvn javadoc:javadoc`
