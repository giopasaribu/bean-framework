package com.quiz.beanframework;

import com.quiz.beanframework.exception.BeanCreationException;

public class BeanFramework {

    /** Bean context value, which will store beans loaded to the framework */
    private static BeanContext context;

    /**
     * This method allows to get bean context used by Bean Framework
     *
     * @return BeanContext object with loaded beans
     */
    public static BeanContext getContext() {
        return context;
    }

    /**
     * This method starts Bean Framework and search for configuration class in current folder and
     * subfolders, then it tries to load all beans for a given context path. If config is not
     * presented - exception is thrown.
     */
    public static void run(String contextPath) throws BeanCreationException {
        context = createContext(contextPath);
        loadContext();
    }

    /**
     * Generates bean context used by Bean Framework
     *
     * @param contextPath value used to search for beans
     * @return BeanContext object for given context path
     */
    private static BeanContext createContext(String contextPath) {
        return new BeanContext(contextPath);
    }

    /**
     * This method tries to discover and load all classes annotated with @Bean for current context
     *
     * @throws BeanCreationException if it is not possible to load bean marked as @AutowireBean
     */
    private static void loadContext() throws BeanCreationException {
        BeanContextLoader contextLoader = new BeanContextLoader(context);
        contextLoader.loadContext();
    }
}
