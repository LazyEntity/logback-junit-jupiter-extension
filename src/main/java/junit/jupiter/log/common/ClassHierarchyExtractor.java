package junit.jupiter.log.common;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class ClassHierarchyExtractor implements Function<Class<?>, Collection<Class<?>>> {
    @Override
    public Collection<Class<?>> apply(Class<?> coreClass) {
        Set<Class<?>> classes = new HashSet<>();
        Class<?> currentClass = coreClass;

        while (isNotObjectClass(currentClass)) {
            addClasses(classes, currentClass);
            currentClass = currentClass.getSuperclass();
        }

        return classes;
    }

    private void addClasses(Set<Class<?>> classes, Class<?> currentClass) {
        putInterfaces(classes, currentClass);
        classes.add(currentClass);
    }

    private boolean isNotObjectClass(Class<?> currentClass) {
        return !Object.class.equals(currentClass);
    }

    private void putInterfaces(Set<Class<?>> classes, Class<?> currentClass) {
        Class<?>[] interfaces = currentClass.getInterfaces();
        List<Class<?>> classList = Arrays.asList(interfaces);
        classes.addAll(classList);
    }
}
