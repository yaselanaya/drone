package com.test.drone.base;

import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestUtil {

    public static Method getMethod(Class clazz, String methodName) {
        return Stream.of(clazz.getDeclaredMethods()).filter(method -> method.getName().equals(methodName)).findFirst()
                .orElse(null);
    }

    public static String buildBasicEndpointUrl(String[] path, Class clazz) {

        if (!clazz.isAnnotationPresent(RequestMapping.class))
            return buildPath(new String[] {}) + buildPath(path);

        RequestMapping controllerRequestMapping = (RequestMapping) clazz.getAnnotation(RequestMapping.class);

        return buildPath(controllerRequestMapping.path()) + buildPath(path);
    }

    private static String buildPath(String[] path) {
        return Arrays.stream(path).map(currentPath -> currentPath + "/").collect(Collectors.joining());
    }
}
