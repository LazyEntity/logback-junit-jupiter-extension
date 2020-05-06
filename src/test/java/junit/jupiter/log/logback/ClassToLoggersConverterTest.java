package junit.jupiter.log.logback;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClassToLoggersConverterTest {
    @InjectMocks
    private ClassToLoggersConverter classToLoggersConverter;

    @Spy
    private final LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
    @Mock
    private Function<Class<?>, Collection<Class<?>>> classHierarchyExtractor;

    @Test
    void class_to_loggers_convert_with_parents() {
        Logger loggerA = (Logger) LoggerFactory.getLogger(A.class);
        Logger loggerB = (Logger) LoggerFactory.getLogger(B.class);
        when(classHierarchyExtractor.apply(B.class)).thenReturn(Arrays.asList(A.class, B.class));

        // call
        Collection<Logger> loggers = classToLoggersConverter.apply(B.class, true);

        assertThat(loggers)
                .hasSize(2)
                .contains(loggerA, loggerB)
                .isInstanceOf(Set.class);
    }

    static class A {
    }

    static class B extends A {
    }
}