# Katalon Studio Platform

This open source platform is for developing Katalon Studio plugins.
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

## JUnit report location
```java
ReportController ctl = ApplicationManager.getInstance()
                        .getControllerManager()
                        .getController(ReportController.class);
                        
ReportEntity report = ctl.getReport(null, reportId);
System.out.println("Test execution completed - Junit file path: " + report.getJunitReportLocation());
```
