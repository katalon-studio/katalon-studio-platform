package com.katalon.platform.internal;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;

import com.katalon.platform.api.Application;
import com.katalon.platform.api.service.ApplicationManager;

public class PlatformActivator extends org.eclipse.core.runtime.Plugin {

    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);

        ApplicationServiceImpl
                .lookupBundleContext(context);

        context.addServiceListener(new ServiceListener() {

            @Override
            public void serviceChanged(ServiceEvent event) {
                if (context.getServiceReference(Application.class) != null) {
                    ApplicationManager
                            .setApplication(context.getService(context.getServiceReference(Application.class)));

                    context.removeServiceListener(this);
                }
            }
        });
    }
}
