
# Design considerations
There are two annotations to provide dependency injection mechanism
1) `@Bean` annotation and `scope` parameter allow marking class to be auto-discovered in class
path. `scope` parameter supports `SINGLETON` and `PROTOTYPE` values to define creation strategy.
Bean will be considered as `SINGLETON` scope omitted.
2) `@Autowire` annotation allows marking class field with interface type to enable bean injection for that field.
Bean framework will try to load particular implementation of that interface from context. If there is no such
bean in context then `BeanCreationException` is thrown.

# Limitations
1) Current implementation support only classes with default constructor
2) Circular dependencies not handled

# Getting started
Follow these steps to start using Bean Framework
1) Run `mvn package` to build framework jar
2) Include this jar to your project using maven dependency or as plain jar
3) Add `org.reflections.reflections` as a second dependency
4) Annotate required classes with `@Bean` and their dependencies with `@AutowireBean`
5) Call `BeanFramework.run(String contextPath)` to create application context for given path
6) Get necessary beans from `BeanFramework.getContext()` and `BeanContext.getBeanForType(Class<?> typeClass)`
