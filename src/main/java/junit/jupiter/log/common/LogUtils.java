package junit.jupiter.log.common;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LogUtils {
    public static Collection<Class<?>> extractParentsWithCurrentClass(Class<?> childClass) {
        Set<Class<?>> classes = new HashSet<>();
        Class<?> currentClass = childClass;

        while (isNotObjectClass(currentClass)) {
            putInterfaces(classes, currentClass);
            classes.add(currentClass);

            currentClass = currentClass.getSuperclass();
        }

        return classes;
    }

    private static boolean isNotObjectClass(Class<?> currentClass) {
        return !Object.class.equals(currentClass);
    }

    private static void putInterfaces(Set<Class<?>> classes, Class<?> currentClass) {
        Class<?>[] interfaces = currentClass.getInterfaces();
        List<Class<?>> classList = Arrays.asList(interfaces);
        classes.addAll(classList);
    }
}
