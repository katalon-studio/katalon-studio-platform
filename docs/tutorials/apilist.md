# Katalon Studio Platform

This open source platform is for developing Katalon Studio plugins.

### Sample plugins
[Plugin Platform Test](https://github.com/katalon-studio-samples/plugin-platform-test)

## Running mode (IDE or KRE)
```java
ExecutionController ctl = ApplicationManager.getInstance()
                .getControllerManager()
                .getController(ExecutionController.class);

System.out.println("Running mode: " + ctl.getRunningMode());
```

## JRE location
```java
ExecutionController ctl = ApplicationManager.getInstance()
                .getControllerManager()
                .getController(ExecutionController.class);

System.out.println("JRE location: " + ctl.getJreLocation());
```

## Test Suite JUnit report location
```java
ReportController ctl = ApplicationManager.getInstance()
                        .getControllerManager()
                        .getController(ReportController.class);
                        
ReportEntity report = ctl.getReport(null, reportId);
System.out.println("Test execution completed - Junit file path: " + report.getJunitReportLocation());
```
## Test Suite Collection JUnit report location
```java
ReportCollectionController ctl = ApplicationManager.getInstance()
                        .getControllerManager()
                        .getController(ReportCollectionController.class);
                        
ReportCollectionEntity report = ctl.getReport(reportFolderPath);
System.out.println("Test suite collection execution completed - Junit file path: "
                            + report.getJunitReportLocation());                    
```
