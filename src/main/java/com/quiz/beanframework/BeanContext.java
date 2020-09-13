package com.quiz.beanframework;

import com.quiz.beanframework.annotation.Bean;
import com.quiz.beanframework.bean.BeanScope;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BeanContext {

    private final Map<Class<?>, Bean> discoveredClasses = new HashMap<>();
    private final Map<Class<?>, Object> contextBeanMap = new HashMap<>();

    private final String contextPath;

    public BeanContext(String contextPath) {
        this.contextPath = contextPath;
    }

    /**
     * Returns exact class object if given type is assignable from it
     * @param typeClass to search for exact implementation
     * @return class object
     */
    Class<?> getImplementationClass(Class<?> typeClass) {
        Set<Class<?>> contextClasses = discoveredClasses.keySet();

        for (Class<?> clazz : contextClasses) {
            if (typeClass.isAssignableFrom(clazz)) {
                return clazz;
            }
        }

        return null;
    }

    /**
     * Returns bean objecvt from context for given class
     * @param typeClass to search in context
     * @param <T> class type
     * @return object if class is loaded into the context, null otherwise
     */
    @SuppressWarnings("unchecked")
    public <T> T getBeanForType(Class<T> typeClass) {
        Class<?> implementationClass = getImplementationClass(typeClass);

        if (implementationClass == null) {
            return null;
        }

        return (T) contextBeanMap.get(implementationClass);
    }

    /**
     * Returns bean scope type for given class type
     * @param typeClass to search in context
     * @return bean scope
     */
    BeanScope getScopeForType(Class<?> typeClass) {
        Class<?> implementationClass = getImplementationClass(typeClass);

        if (implementationClass == null) {
            return null;
        }

        return discoveredClasses.get(implementationClass).scope();
    }

    public String getContextPath() {
        return contextPath;
    }

    Map<Class<?>, Bean> getDiscoveredClasses() {
        return discoveredClasses;
    }

    Map<Class<?>, Object> getContextBeanMap() {
        return contextBeanMap;
    }
}
