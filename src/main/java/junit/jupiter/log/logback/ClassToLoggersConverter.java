package junit.jupiter.log.logback;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.Collections;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public final class ClassToLoggersConverter implements BiFunction<Class<?>, Boolean, Collection<Logger>> {
    private final LoggerContext loggerContext;
    private final Function<Class<?>, Collection<Class<?>>> classHierarchyExtractor;

    @Override
    public Collection<Logger> apply(Class<?> logClass, Boolean withParents) {
        Collection<Class<?>> logClasses = withParents ?
                classHierarchyExtractor.apply(logClass) :
                Collections.singleton(logClass);

        return logClasses.stream()
                .map(loggerContext::getLogger)
                .collect(Collectors.toSet());
    }
}