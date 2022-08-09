package com.anjunar.blomst;

import com.google.common.collect.Sets;
import com.google.common.reflect.ClassPath;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Application;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Patrick Bittner on 21.05.2015.
 */
@ApplicationPath("service")
public class SimplicityApplication extends Application {
    public static Set<Class<?>> findAllClassesUsingGoogleGuice(String packageName) {
        try {
            return ClassPath.from(ClassLoader.getSystemClassLoader())
                    .getAllClasses()
                    .stream()
                    .filter(clazz -> clazz.getPackageName().startsWith(packageName))
                    .map(clazz -> {
                        try {
                            return clazz.load();
                        } catch (NoClassDefFoundError e) {
                            return String.class;
                        }
                    })
                    .filter((clazz) -> clazz.isAnnotationPresent(Path.class) && ! clazz.isInterface())
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
