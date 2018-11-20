# katalon-studio-platform
Katalon Studio Platform is an open source project that provides some public APIs for Katalon Studio plugins can interact, contribute external features to Katalon Studio.

## Package structure
com.katalon.platform.api.*: official Katalon Studio public APIs packages that exposes some default extension points and some utility services that allows to interact with Katalon Studio.

com.katalon.platform.internal.*: internal packages that allows katalon-studio-platform can connect with Katalon Studio. Should NOT be called from other plugins.

## Build
mvn clean package
