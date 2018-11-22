package com.katalon.platform.internal.extension;

import com.katalon.platform.api.extension.ExtensionListener;
import com.katalon.platform.api.extension.ExtensionPoint;

public class ExtensionPointImpl implements ExtensionPoint {

    private final String pluginId;

    private final String extensionPointId;

    private final String interfaceClassName;

    private final ExtensionListener serviceClass;

    public ExtensionPointImpl(String pluginId, String extensionPointId, String interfaceClassName,
            ExtensionListener serviceClass) {
        this.pluginId = pluginId;
        this.extensionPointId = extensionPointId;
        this.interfaceClassName = interfaceClassName;
        this.serviceClass = serviceClass;
    }

    @Override
    public String pluginId() {
        return pluginId;
    }

    @Override
    public String extensionPointId() {
        return extensionPointId;
    }

    @Override
    public String interfaceClassName() {
        return interfaceClassName;
    }

    @Override
    public ExtensionListener serviceClass() {
        return serviceClass;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((extensionPointId == null) ? 0 : extensionPointId.hashCode());
        result = prime * result + ((interfaceClassName == null) ? 0 : interfaceClassName.hashCode());
        result = prime * result + ((pluginId == null) ? 0 : pluginId.hashCode());
        result = prime * result + ((serviceClass == null) ? 0 : serviceClass.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ExtensionPointImpl other = (ExtensionPointImpl) obj;
        if (extensionPointId == null) {
            if (other.extensionPointId != null)
                return false;
        } else if (!extensionPointId.equals(other.extensionPointId))
            return false;
        if (interfaceClassName == null) {
            if (other.interfaceClassName != null)
                return false;
        } else if (!interfaceClassName.equals(other.interfaceClassName))
            return false;
        if (pluginId == null) {
            if (other.pluginId != null)
                return false;
        } else if (!pluginId.equals(other.pluginId))
            return false;
        if (serviceClass == null) {
            if (other.serviceClass != null)
                return false;
        } else if (!serviceClass.equals(other.serviceClass))
            return false;
        return true;
    }
}
