package com.quiz.beanframework.annotation;

import com.quiz.beanframework.bean.BeanScope;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @AutowireBean annotation on class field enables bean injection to this field. Class should be
 * marked as @Bean to allow bean injection. BeanCreationException will be thrown if there is no such
 * bean in context
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface AutowireBean {
    BeanScope scope() default BeanScope.SINGLETON;
}
