package com.katalon.platform.internal.ui.toolbar;

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
import org.eclipse.e4.ui.model.application.ui.menu.MMenuFactory;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBar;
import org.eclipse.e4.ui.workbench.modeling.EModelService;

import com.katalon.platform.api.Extension;
import com.katalon.platform.api.extension.ToolItemDescription;
import com.katalon.platform.api.lifecycle.ExtensionListener;
import com.katalon.platform.internal.EclipseContextService;

public class AddToolItemExtensionListener implements ExtensionListener {

    private Map<String, String> toolItemRegistries = new HashMap<>();

    @Override
    public void register(Extension extension) {
        EModelService modelService = EclipseContextService.getWorkbenchService(EModelService.class);
        ECommandService commandService = EclipseContextService.getWorkbenchService(ECommandService.class);
        MApplication application = EclipseContextService.getWorkbenchService(MApplication.class);

        if (modelService == null || commandService == null || application == null) {
            return;
        }

        if (extension.getImplementationClass() instanceof ToolItemDescription) {
            ToolItemDescription toolItemDescription = (ToolItemDescription) extension.getImplementationClass();
            MUIElement groupElement = modelService.find("com.kms.katalon.composer.toolbar", application);

            if (!(groupElement instanceof MElementContainer)) {
                return;
            }

            Category category = commandService.defineCategory("empty", "", "");
            Command command = commandService.defineCommand(toolItemDescription.toolItemId(), "", "", category,
                    new IParameter[0]);
            command.setHandler(new AbstractHandler() {
                @Override
                public boolean isEnabled() {
                    return toolItemDescription.isItemEnabled();
                }

                @Override
                public Object execute(ExecutionEvent event) throws ExecutionException {
                    toolItemDescription.handleEvent();
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
            toolItem.setLabel(toolItemDescription.name());
            toolItem.setWbCommand(parameterizedCommand);
            toolItem.setCommand(MCommandsFactory.INSTANCE.createCommand());
            toolItem.setIconURI(toolItemDescription.iconUrl());
            toolItem.setElementId(toolItemDescription.toolItemId());

            UISynchronize uiSync = EclipseContextService.getWorkbenchService(UISynchronize.class);
            uiSync.syncExec(() -> {
                toolbar.getChildren().add(toolItem);
            });

            toolItemRegistries.put(extension.getExtensionId(), toolItemDescription.toolItemId());
        }
    }

    @Override
    public void deregister(Extension extension) {
        EModelService modelService = EclipseContextService.getWorkbenchService(EModelService.class);
        ECommandService commandService = EclipseContextService.getWorkbenchService(ECommandService.class);
        MApplication application = EclipseContextService.getWorkbenchService(MApplication.class);
        if (modelService == null || commandService == null || application == null) {
            return;
        }

        String extensionId = extension.getExtensionId();
        if (toolItemRegistries.containsKey(extensionId)) {
            String id = toolItemRegistries.get(extensionId);
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
            toolItemRegistries.remove(extensionId);
        }
    }
}
