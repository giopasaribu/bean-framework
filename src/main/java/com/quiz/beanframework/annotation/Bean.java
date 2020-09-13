package com.quiz.beanframework.annotation;

import com.quiz.beanframework.bean.BeanScope;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Bean annotation allows to specify class, which should be auto-discovered and loaded by bean
 * framework context
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Bean {
    BeanScope scope() default BeanScope.SINGLETON;
}
