package com.katalon.platform.api.extension;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.katalon.platform.api.model.ProjectEntity;
import com.katalon.platform.api.model.TestSuiteEntity;

/**
 * TestSuiteUIViewDescription is the interface of
 * <code>com.katalon.platform.api.extension.testSuiteUIViewDescription</code>
 * extension point that allows client plugins can:
 * <ul>
 * <li>Add a custom composite to test suite view</li>
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
 *           extensionPointId="com.katalon.platform.api.extension.testSuiteUIViewDescription"
 *           implementationClass="<name of your implementation class>"
 *     </point>
 * </extension>
 * }
 * </pre>
 * 
 * The <i>implementationClass</i> is the full qualified name of the class that implements
 * TestSuiteUIViewDescription.
 * 
 * @since 1.0.9
 *
 */
public interface TestSuiteUIViewDescription {
	
	String EXTENSION_POINT_ID = "com.katalon.platform.api.extension.testSuiteUIViewDescription";
	
	default boolean isEnabled(ProjectEntity projectEntity) {
		return true;
	}
	
	String getName();
	
	Class<? extends TestSuiteUIView> getTestSuiteUIView();	
	
	default boolean shouldBeRendered() {
		return true;
	}

    public interface PartActionService {
        
    	void markDirty();
        
        boolean isDirty();
    }
    
    public interface TestSuiteUIView {
    	Control onCreateView(Composite parent, PartActionService partActionService, 
    			TestSuiteEntity testSuiteEntity);
    	
    	default void onSaveSuccess(TestSuiteEntity updatedTestSuite) {
    		
    	}
    	
    	default void onSaveFailure(Exception exception) {
    		
    	}    	
    }
}