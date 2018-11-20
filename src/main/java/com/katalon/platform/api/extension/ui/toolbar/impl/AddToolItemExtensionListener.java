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
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.commands.MCommandsFactory;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.menu.MHandledToolItem;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuFactory;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBar;
import org.eclipse.e4.ui.workbench.modeling.EModelService;

import com.katalon.platform.api.extension.Extension;
import com.katalon.platform.api.extension.ExtensionListener;
import com.katalon.platform.api.extension.ui.toolbar.ToolItemDescription;
import com.katalon.platform.internal.ApplicationServiceImpl;

@SuppressWarnings("restriction")
public class AddToolItemExtensionListener implements ExtensionListener {

    private Map<String, String> toolItemRegistries = new HashMap<>();

    @Override
    public void register(Extension extension) {
        if (extension.implementationClass() instanceof ToolItemDescription) {
            ToolItemDescription toolItemDescription = (ToolItemDescription) extension.implementationClass();
            EModelService modelService = ApplicationServiceImpl.get(EModelService.class);
            ECommandService commandService = ApplicationServiceImpl.get(ECommandService.class);
            MApplication application = ApplicationServiceImpl.get(MApplication.class);

            MUIElement groupElement = modelService.find(toolItemDescription.groupId(), application);

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
            toolItem.setTooltip(toolItemDescription.tooltip());
            toolItem.setWbCommand(parameterizedCommand);
            toolItem.setCommand(MCommandsFactory.INSTANCE.createCommand());
            toolItem.setIconURI(toolItemDescription.iconUrl());
            toolItem.setElementId(toolItemDescription.toolItemId());

            toolbar.getChildren().add(toolItem);

            toolItemRegistries.put(extension.extensionId(), toolItemDescription.toolItemId());
        }
    }

    @Override
    public void deregister(Extension extension) {
        EModelService modelService = ApplicationServiceImpl.get(EModelService.class);
        ECommandService commandService = ApplicationServiceImpl.get(ECommandService.class);
        MApplication application = ApplicationServiceImpl.get(MApplication.class);
        String extensionId = extension.extensionId();
        if (toolItemRegistries.containsKey(extensionId)) {
            String id = toolItemRegistries.get(extensionId);
            MUIElement element = modelService.find(id, application);
            if (element != null) {
                Command command = commandService.getCommand(id);
                command.setHandler(null);

                MElementContainer<MUIElement> parent = element.getParent();
                element.setToBeRendered(false);
                element.setVisible(false);
                parent.getChildren().remove(element);
            }
            toolItemRegistries.remove(extensionId);
        }
    }
}
