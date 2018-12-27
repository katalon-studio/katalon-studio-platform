package com.katalon.platform.api.controller;

import com.katalon.platform.api.exception.ResourceException;
import com.katalon.platform.api.model.FolderEntity;

public interface FolderController {
    FolderEntity getFolder(String folderId) throws ResourceException;
}
