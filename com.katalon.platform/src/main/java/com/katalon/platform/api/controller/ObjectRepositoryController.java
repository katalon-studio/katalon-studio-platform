package com.katalon.platform.api.controller;

import java.util.List;

import com.katalon.platform.api.exception.ResourceException;
import com.katalon.platform.api.model.FolderEntity;
import com.katalon.platform.api.model.ProjectEntity;
import com.katalon.platform.api.model.TestObjectEntity;
import com.katalon.platform.api.model.testobject.BasicRestRequestEntity;
import com.katalon.platform.api.model.testobject.BasicSoapRequestEntity;
import com.katalon.platform.api.model.testobject.BasicWebElementEntity;
import com.katalon.platform.api.model.testobject.RestRequestEntity;
import com.katalon.platform.api.model.testobject.SoapRequestEntity;
import com.katalon.platform.api.model.testobject.WebElementEntity;
import com.katalon.platform.api.service.ProjectManager;

/**
 * ObjectRepositoryController is a unique KS Controller to help KS plugins can manipulate {@link TestObjectEntity} in
 * the file system.
 * 
 * @see #getTestObject(ProjectEntity, String)
 * @see #renameTestObject(ProjectEntity, String, String)
 * @see #getChildTestObjects(ProjectEntity, FolderEntity)
 * @see #newWebElement(ProjectEntity, FolderEntity, String, BasicWebElementEntity)
 * @see #updateWebElement(ProjectEntity, String, BasicWebElementEntity)
 * @see #newRestRequest(ProjectEntity, FolderEntity, String, BasicRestRequestEntity)
 * @see #updateWebElement(ProjectEntity, String, BasicWebElementEntity)
 * @see #newRestRequest(ProjectEntity, FolderEntity, String, BasicRestRequestEntity)
 * @see #updateSoapRequest(ProjectEntity, String, BasicSoapRequestEntity)
 * 
 * @since 1.0.9
 */
public interface ObjectRepositoryController extends Controller {
    /**
     * Returns an instance of TestObjectEntity of the given <code>project</code> by the given <code>testObjectId</code>
     * 
     * @param project an instance of working project. The current working project can retrieve by using
     * {@link ProjectManager#getCurrentProject()}.
     * 
     * @param testObjectId id of TestObjectEntity
     * @return an instance of TestObjectEntity
     * @see WebElementEntity
     * @see RestRequestEntity
     * @see SoapRequestEntity
     * @throws ResourceException if KS could not read or access test object.
     * 
     * @since 1.0.9
     */
    TestObjectEntity getTestObject(ProjectEntity project, String testObjectId) throws ResourceException;

    /**
     * Renames the TestObjectEntity of the given <code>project</code> by the given <code>testObjectId</code>
     * 
     * @param project an instance of working project. The current working project can retrieve by using
     * {@link ProjectManager#getCurrentProject()}.
     * 
     * @param testObjectId id of TestObjectEntity
     * @param newName new name of the test object 
     * @return the renamed TestObjectEntity
     * @see WebElementEntity
     * @see RestRequestEntity
     * @see SoapRequestEntity
     * @throws ResourceException if KS could not rename the test object.
     * 
     * @since 1.0.9
     */
    TestObjectEntity renameTestObject(ProjectEntity project, String testObjectId, String newName)
            throws ResourceException;

    /**
     * Lists the children test objects under a folder.
     * 
     * @param project project an instance of working project. The current working project can retrieve by using
     * {@link ProjectManager#getCurrentProject()}.
     * @param parentFolder parent folder that needs to check.
     * @return list of TestObjectEntity. The list can be empty but not null.
     * @throws ResourceException thrown if KS could not read or access test cases under parent folder.
     * 
     * @since 1.0.9
     */
    List<TestObjectEntity> getChildTestObjects(ProjectEntity project, FolderEntity parentFolder)
            throws ResourceException;

    /**
     * Creates a new web UI element under <code>parentFolder</code> with the given <code>newName</code> and
     * <code>newWebElement</code>
     * 
     * @param project an instance of working project. The current working project can retrieve by using
     * {@link ProjectManager#getCurrentProject()}.
     * @param parentFolder parent folder of web element
     * @param newName name of the web element
     * @param newWebElement populate all fields to the new created WebElementEntity
     * @return new created WebElementEntity
     * @throws ResourceException if KS could not create web element.
     * 
     * @since 1.0.9
     */
    WebElementEntity newWebElement(ProjectEntity project, FolderEntity parentFolder, String newName,
            BasicWebElementEntity newWebElement) throws ResourceException;

    /**
     * Updates an existing web element of the project by the specified <code>updatedWebElement</code>
     * 
     * @param project an instance of working project. The current working project can retrieve by using
     * {@link ProjectManager#getCurrentProject()}.
     * @param webElementId id of the web element
     * @param updatedWebElement populate all fields to the new updated WebElementEntity
     * @return updated WebElementEntity
     * 
     * @throws ResourceException if KS could not update the web element.
     * 
     * @since 1.0.9
     */
    WebElementEntity updateWebElement(ProjectEntity project, String webElementId,
            BasicWebElementEntity updatedWebElement) throws ResourceException;

    /**
     * Creates a new web service REST request entity under <code>parentFolder</code> with the given
     * <code>newName</code> and <code>newRestRequest</code>
     * 
     * @param project an instance of working project. The current working project can retrieve by using
     * {@link ProjectManager#getCurrentProject()}.
     * @param parentFolder parent folder of the REST request entity
     * @param newName name of the REST request entity
     * @param newRestRequest populate all fields to the new created RestRequestEntity
     * @return new created RestRequestEntity
     * @throws ResourceException if KS could not create web element.
     * 
     * @since 1.0.9
     */
    RestRequestEntity newRestRequest(ProjectEntity project, FolderEntity parentFolder, String newName,
            BasicRestRequestEntity newRestRequest) throws ResourceException;

    /**
     * Updates an existing REST request entity by the specified <code>updatedRestRequest</code>
     * 
     * @param project an instance of working project. The current working project can retrieve by using
     * {@link ProjectManager#getCurrentProject()}.
     * @param restRequestId id of the REST request entity
     * @param updatedRestRequest populate all fields to the updated RestRequestEntity
     * @return new created RestRequestEntity
     * @throws ResourceException if KS could not update the REST request entity.
     * 
     * @since 1.0.9
     */
    RestRequestEntity updateRestRequest(ProjectEntity project, String restRequestId,
            BasicRestRequestEntity updatedRestRequest) throws ResourceException;

    /**
     * Creates a new web service SOAP request entity under <code>parentFolder</code> with the given
     * <code>newName</code> and the <code>newSoapRequest</code> template
     * 
     * @param project an instance of working project. The current working project can retrieve by using
     * {@link ProjectManager#getCurrentProject()}.
     * @param parentFolder parent folder of the REST request entity
     * @param newName name of the REST request entity
     * @param newSoapRequest populate all fields to the new created SoapRequestEntity. If null, KS will return the
     * default SoapRequestEntity.
     * @return new created SoapRequestEntity
     * @throws ResourceException if KS could not create the SOAP request entity.
     * 
     * @since 1.0.9
     */
    SoapRequestEntity newSoapRequest(ProjectEntity project, FolderEntity parentFolder, String newName,
            BasicSoapRequestEntity newSoapRequest) throws ResourceException;

    /**
     * Updates an existing SOAP request entity by the specified <code>updatedSoapRequest</code>
     * 
     * @param project an instance of working project. The current working project can retrieve by using
     * {@link ProjectManager#getCurrentProject()}.
     * @param soapRequestId id of the SOAP request entity.
     * @param updatedSoapRequest populate all fields to the updated SoapRequestEntity
     * @return the updated SoapRequestEntity
     * @throws ResourceException if KS could not update the SOAP request entity.
     * 
     * @since 1.0.9
     */
    SoapRequestEntity updateSoapRequest(ProjectEntity project, String soapRequestId,
            BasicSoapRequestEntity updatedSoapRequest) throws ResourceException;
}
