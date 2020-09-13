package com.quiz.beanframework;

import com.quiz.beanframework.annotation.Bean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BeanContextTest {

    private BeanContext subject;

    @BeforeEach
    public void setup() {
        subject = new BeanContext("");
    }

    @Test
    void getImplementationClassTest() {
        //given
        Map<Class<?>, Bean> discoveredClasses = subject.getDiscoveredClasses();

        Class<?> expected = ArrayList.class;
        discoveredClasses.put(expected, null);

        //when
        Class<?> actual = subject.getImplementationClass(List.class);

        //then
        assertEquals(expected, actual);
    }
}