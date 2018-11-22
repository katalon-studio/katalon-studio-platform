package com.katalon.platform.internal.extension;

import com.katalon.platform.api.extension.Extension;

public class ExtensionImpl implements Extension {

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((extensionId == null) ? 0 : extensionId.hashCode());
        result = prime * result + ((extensionPointId == null) ? 0 : extensionPointId.hashCode());
        result = prime * result + ((implementationClass == null) ? 0 : implementationClass.hashCode());
        result = prime * result + ((pluginId == null) ? 0 : pluginId.hashCode());
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
        ExtensionImpl other = (ExtensionImpl) obj;
        if (extensionId == null) {
            if (other.extensionId != null)
                return false;
        } else if (!extensionId.equals(other.extensionId))
            return false;
        if (extensionPointId == null) {
            if (other.extensionPointId != null)
                return false;
        } else if (!extensionPointId.equals(other.extensionPointId))
            return false;
        if (implementationClass == null) {
            if (other.implementationClass != null)
                return false;
        } else if (!implementationClass.equals(other.implementationClass))
            return false;
        if (pluginId == null) {
            if (other.pluginId != null)
                return false;
        } else if (!pluginId.equals(other.pluginId))
            return false;
        return true;
    }

    private final String pluginId;

    private final String extensionId;

    private final String extensionPointId;

    private final Object implementationClass;
    
    public ExtensionImpl(String pluginId, String extensionId, String extensionPointId, Object implementationClass) {
        this.pluginId = pluginId;
        this.extensionId = extensionId;
        this.extensionPointId = extensionPointId;
        this.implementationClass = implementationClass;
    }

    @Override
    public String pluginId() {
        return pluginId;
    }

    @Override
    public String extensionId() {
        return extensionId;
    }

    @Override
    public String extensionPointId() {
        return extensionPointId;
    }

    @Override
    public Object implementationClass() {
        return implementationClass;
    }
}
