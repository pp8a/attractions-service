package com.attractions.utility;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.attractions.data.TestData;

class UtilityTest {
	/**
     * Tests that the specified class cannot be instantiated.
     *
     * @param clazz the class to test.
     */
	@ParameterizedTest
    @MethodSource("provideClasses")
    void testClassCannotBeInstantiated(Class<?> clazz) {
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            constructor.setAccessible(true);
            Throwable exception = assertThrows(InvocationTargetException.class, () -> {
                constructor.newInstance();
            }).getCause();
            assertTrue(exception instanceof UnsupportedOperationException, 
            		"Expected UnsupportedOperationException");
        }
    }
	
	 /**
     * Provides the classes to be tested.
     *
     * @return a stream of classes.
     */
	private static Stream<Class<?>> provideClasses() {
        return Stream.of(
           TestData.class
        );
    }

}
