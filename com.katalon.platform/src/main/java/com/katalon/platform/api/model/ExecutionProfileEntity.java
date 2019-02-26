package com.katalon.platform.api.model;

import java.util.List;

public interface ExecutionProfileEntity extends Entity {
    boolean isDefaultProfile();
    
    List<VariableEntity> getVariables();
}
