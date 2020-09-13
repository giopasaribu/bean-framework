package com.quiz.beanframework;

import com.quiz.beanframework.annotation.AutowireBean;
import com.quiz.beanframework.annotation.Bean;
import com.quiz.beanframework.bean.BeanScope;
import com.quiz.beanframework.exception.BeanCreationException;
import com.quiz.beanframework.exception.DefaultConstructorNotFoundException;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class BeanContextLoader {

    private final BeanContext context;

    public BeanContextLoader(BeanContext context) {
        this.context = context;
    }

    /**
     * Load application context for inner BeanContext
     * @throws BeanCreationException if context can't be loaded
     */
    public void loadContext() throws BeanCreationException {
        discoverBeanClasses();
        loadBeans();
    }

    /**
     * Populate discovered classes to BeanContext
     */
    private void discoverBeanClasses() {
        String contextPath = context.getContextPath();
        Map<Class<?>, Bean> discoveredClasses = context.getDiscoveredClasses();

        Reflections reflections = new Reflections(contextPath);
        Set<Class<?>> beanClasses = reflections.getTypesAnnotatedWith(Bean.class);
        for (Class<?> clazz : beanClasses) {
            Bean beanAnnotation = clazz.getAnnotation(Bean.class);
            discoveredClasses.put(clazz, beanAnnotation);
        }
    }

    /**
     * Load discovered classes to BeanContext
     */
    private void loadBeans() throws BeanCreationException {
        Map<Class<?>, Bean> discoveredClasses = context.getDiscoveredClasses();
        discoveredClasses.keySet().forEach(this::loadBean);
    }

    /**
     *
     * @param beanClass value to load into the context
     * @return loaded bean object
     */
    private Object loadBean(Class<?> beanClass) throws BeanCreationException {

        Map<Class<?>, Object> contextBeanMap = context.getContextBeanMap();
        BeanScope scope = context.getScopeForType(beanClass);
        if (contextBeanMap.get(beanClass) != null && scope != BeanScope.PROTOTYPE) {
            return contextBeanMap.get(beanClass);
        }

        Object object = createBean(beanClass);

        Arrays.stream(beanClass.getDeclaredFields())
                .filter(this::hasAutowiredBean)
                .forEach(f -> loadField(object, f));

        contextBeanMap.put(beanClass, object);
        return object;
    }

    /**
     * Create context bean using reflection API
     * @param clazz value for bean instantiation
     * @return new bean for given class
     * @throws BeanCreationException if error occurred during bean creation
     */
    private Object createBean(Class<?> clazz) throws BeanCreationException {
        Optional<Constructor<?>> constructorOptional =
                Arrays.stream(clazz.getDeclaredConstructors())
                        .filter(c -> c.getGenericParameterTypes().length == 0)
                        .findAny();

        if (!constructorOptional.isPresent()) {
            throw new DefaultConstructorNotFoundException(
                    "String there is no default constructor for " + clazz.getName());
        }

        Constructor<?> constructor = constructorOptional.get();
        constructor.setAccessible(true);
        try {
            return constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new BeanCreationException(e);
        }
    }

    /**
     * Check if given field has @AutowireBean annotation
     * @param field to check if it has annotation
     * @return true or false depending on annotation presence.
     */
    private boolean hasAutowiredBean(Field field) {
        return field.getAnnotation(AutowireBean.class) != null;
    }

    /**
     * Get field from context or create if it is not presented and set as field value
     * @param bean value for field insertion
     * @param field value for bean injection
     * @throws BeanCreationException if error occurred during bean creation
     */
    private void loadField(Object bean, Field field) throws BeanCreationException {
        field.setAccessible(true);

        Class<?> fieldClass = field.getType();
        BeanScope scope = context.getScopeForType(fieldClass);

        Object fieldObject = context.getBeanForType(fieldClass);
        if (fieldObject == null || scope == BeanScope.PROTOTYPE) {
            Class<?> implementationClass = context.getImplementationClass(fieldClass);
            fieldObject = loadBean(implementationClass);
            context.getContextBeanMap().put(implementationClass, fieldObject);
        }

        try {
            field.set(bean, fieldObject);
        } catch (IllegalAccessException e) {
            throw new BeanCreationException(e);
        }
    }
}
