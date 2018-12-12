package com.katalon.platform.api.extension.ui.toolbar.impl;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.Category;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IParameter;
import org.eclipse.core.commands.Parameterization;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.commands.MCommandsFactory;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.menu.MHandledToolItem;
import org.eclipse.e4.ui.model.application.ui.menu.MMenu;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuFactory;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBar;
import org.eclipse.e4.ui.model.application.ui.menu.MToolItem;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.katalon.platform.api.extension.Extension;
import com.katalon.platform.api.extension.ExtensionListener;
import com.katalon.platform.api.extension.ui.toolbar.ToolItemWithMenuDescription;
import com.katalon.platform.internal.EclipseContextService;


@SuppressWarnings("restriction")
public class AddToolItemWithMenuExtensionListenter implements ExtensionListener {

	private Map<String, String> toolItemWithMenuRegistries = new HashMap<>();

	@Override
	public void register(Extension extension) {
		if (extension.implementationClass() instanceof ToolItemWithMenuDescription) {
			ToolItemWithMenuDescription toolItemWithMenuDescription = (ToolItemWithMenuDescription) extension
					.implementationClass();
			EModelService modelService = EclipseContextService.getWorkbenchService(EModelService.class);
            ECommandService commandService = EclipseContextService.getWorkbenchService(ECommandService.class);
			MApplication application = EclipseContextService.getWorkbenchService(MApplication.class);
			MUIElement groupElement = modelService.find("com.kms.katalon.composer.toolbar", application);
			
			if (!(groupElement instanceof MElementContainer)) {
				return;
			}

            Category category = commandService.defineCategory("empty", "", "");
            Command command = commandService.defineCommand(toolItemWithMenuDescription.toolItemId(), "", "", category,
                    new IParameter[0]);
            command.setHandler(new AbstractHandler() {
                @Override
                public boolean isEnabled() {
                    return toolItemWithMenuDescription.isItemEnabled();
                }

                @Override
                public Object execute(ExecutionEvent event) throws ExecutionException {
                	toolItemWithMenuDescription.defaultEventHandler();
                	return null;
                }
            });

            ParameterizedCommand parameterizedCommand = new ParameterizedCommand(command, new Parameterization[0]);

			MElementContainer<?> container = (MElementContainer<?>) groupElement;

			if (!(container instanceof MToolBar)) {
				return;
			}
			
			MToolBar toolbar = (MToolBar) container;
			MHandledToolItem toolItem = MMenuFactory.INSTANCE.createHandledToolItem();
			toolItem.setLabel(toolItemWithMenuDescription.name());
            toolItem.setWbCommand(parameterizedCommand);
            toolItem.setCommand(MCommandsFactory.INSTANCE.createCommand());
			toolItem.setIconURI(toolItemWithMenuDescription.iconUrl());
			toolItem.setElementId(toolItemWithMenuDescription.toolItemId());
			toolItem.setEnabled(toolItemWithMenuDescription.isItemEnabled());
			
			// Create and set MMenu, otherwise toolItem won't be a DROP_DOWN but a POP_UP
			MMenu mMenu = MMenuFactory.INSTANCE.createMenu();
			Menu menu = toolItemWithMenuDescription.getMenu((ToolBar) toolbar.getWidget());
			mMenu.setWidget(menu);
			toolItem.setMenu(mMenu);
			UISynchronize uiSync = EclipseContextService.getWorkbenchService(UISynchronize.class);
            
			uiSync.syncExec(() -> {
				toolbar.getChildren().add(toolItem);
	            ToolItem widgetToolItem = (ToolItem) toolItem.getWidget();
	            Rectangle rect = widgetToolItem.getBounds();
	            Point pt = widgetToolItem.getParent().toDisplay(new Point(rect.x, rect.y));
	            menu.setLocation(pt.x, pt.y + rect.height);
	            menu.setVisible(true);
				toolItemWithMenuRegistries.put(extension.extensionId(), toolItemWithMenuDescription.toolItemId());
			});
		}
	}

	@Override
	public void deregister(Extension extension) {
		EModelService modelService = EclipseContextService.getWorkbenchService(EModelService.class);
		ECommandService commandService = EclipseContextService.getWorkbenchService(ECommandService.class);
		MApplication application = EclipseContextService.getWorkbenchService(MApplication.class);
		String extensionId = extension.extensionId();
		if (toolItemWithMenuRegistries.containsKey(extensionId)) {
			String id = toolItemWithMenuRegistries.get(extensionId);
			MUIElement element = modelService.find(id, application);
			if (element != null) {
				Command command = commandService.getCommand(id);
				command.setHandler(null);

				UISynchronize uiSync = EclipseContextService.getWorkbenchService(UISynchronize.class);
				uiSync.syncExec(() -> {
					MElementContainer<MUIElement> parent = element.getParent();
					element.setToBeRendered(false);
					element.setVisible(false);
					parent.getChildren().remove(element);
				});
			}
			toolItemWithMenuRegistries.remove(extensionId);
		}
	}
}
