package com.katalon.platform.api.extension;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.katalon.platform.api.model.ProjectEntity;
import com.katalon.platform.api.model.TestSuiteCollectionEntity;

/**
 * TestSuiteCollectionUIViewDescription is the interface of
 * <code>com.katalon.platform.api.extension.testSuiteCollectionUIViewDescription</code>
 * extension point that allows client plugins can:
 * <ul>
 * <li>Add a custom composite to test suite collection view</li>
 * </ul>
 * <p>
 * Register in the <code>plugin.xml</code> like this:
 * 
 * <pre>
 * {@code
 * <extension
 *        point="com.katalon.platform.extensions">
 *     <point
 *           id="<your extension id>"
 *           extensionPointId="com.katalon.platform.api.extension.testSuiteCollectionUIViewDescription"
 *           implementationClass="<name of your implementation class>"
 *     </point>
 * </extension>
 * }
 * </pre>
 * 
 * The <i>implementationClass</i> is the full qualified name of the class that implements
 * TestSuiteCollectionUIViewDescription.
 * 
 * @since 1.0.10
 *
 */
public interface TestSuiteCollectionUIViewDescription {
    /**
     * Id of this extension point
     * 
     * @since 1.0.10
     */
	String EXTENSION_POINT_ID = "com.katalon.platform.api.extension.testSuiteCollectionUIViewDescription";
	
    /**
     * @param projectEntity the working project
     * @return true if this view is visible. Otherwise, false.
     * @since 1.0.10
     */
	default boolean isEnabled(ProjectEntity projectEntity) {
		return true;
	}
	
    /**
     * @return Name of this view
     * @since 1.0.10
     */
	String getName();
	
    /**
     * @return clazz that implements TestSuiteCollectionUIView
     * @since 1.10..0
     */
	Class<? extends TestSuiteCollectionUIView> getTestSuiteCollectionUIView();	
	
	default boolean shouldBeRendered() {
		return true;
	}

    /**
     * A utility that helps this view interact with Test Suite Collection view.
     * 
     * @since 1.0.10
     */
    public interface PartActionService {
        /**
         * Marks the test suite collection view is able to save.
         * 
         * @since 1.0.10
         */
    	void markDirty();
    	
        /**
         * Checks the test suite collection view is able to save or not.
         * 
         * @return true if test suite collection view needs to save. Otherwise, false.
         * @since 1.0.10
         */
        boolean isDirty();
    }
    

    /**
     * Describes how the view is displayed.
     * 
     * @since 1.0.10
     */
    public interface TestSuiteCollectionUIView {
        /**
         * Creates the view
         * 
         * @param parent parent view of this view
         * @param partActionService utility service that helps to interact with Test Suite collection view.
         * @param testSuiteCollectionEntity the working test suite collection
         * @return the created view
         * @since 1.0.10
         */
    	Control onCreateView(Composite parent, PartActionService partActionService, 
    			TestSuiteCollectionEntity testSuiteCollectionEntity);
    	
    	/**
    	 * The callback function after the Test Suite Collection view has been rendered.
    	 * Data initialization should occur inside this implementation
    	 * 
    	 * @since 1.0.10
    	 */
    	void onPostCreateView();
        
    	/**
         * The success callback function after the current editing test suite collection successfully saved.
         * 
         * @param updatedTestSuiteCollection the updated TestSuiteCollectionEntity
         * 
         * @since 1.0.10
         */
    	default void onSaveSuccess(TestSuiteCollectionEntity updatedTestSuiteCollection) {
    		
    	}
    	
        /**
         * The failure callback function after the current editing test suite collection was unable to save.
         * 
         * @param exception the root cause failure
         * 
         * @since 1.0.10
         */
    	default void onSaveFailure(Exception exception) {
    		
    	}    	
    }
}
