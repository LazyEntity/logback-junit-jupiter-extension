package samples;

import junit.jupiter.log.common.LogExtension;
import junit.jupiter.log.logback.LogbackExtensionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class ClassWithHierarchyExtensionExample {
    @RegisterExtension
    static LogExtension logExtension = LogbackExtensionFactory.forClass(B.class, true);

    @Test
    void hierarchy_logs_capture() {
        Logger testLogger = LoggerFactory.getLogger("TestLogger");
        testLogger.info("Test Logger");
        B b = new B();
        b.logB();
        b.logA();
        b.logI();
        b.logI2();
        b.logLoggerWithNotClassName();

        assertThat(logExtension.getLogList())
                .hasSize(4)
                .noneMatch(logRow -> "TestLogger".equals(logRow.getLoggerName()))
                .noneMatch(logRow -> "NotClassName".equals(logRow.getLoggerName()))
                .anyMatch(logRow -> "samples.ClassWithHierarchyExtensionExample$B".equals(logRow.getLoggerName()))
                .anyMatch(logRow -> "samples.ClassWithHierarchyExtensionExample$A".equals(logRow.getLoggerName()))
                .anyMatch(logRow -> "samples.ClassWithHierarchyExtensionExample$I".equals(logRow.getLoggerName()))
                .anyMatch(logRow -> "samples.ClassWithHierarchyExtensionExample$I2".equals(logRow.getLoggerName()));
    }


    interface I {
        Logger logger = LoggerFactory.getLogger(I.class);

        default void logI() {
            logger.info("I class log");
        }
    }

    interface I2 {
        Logger logger = LoggerFactory.getLogger(I2.class);

        default void logI2() {
            logger.info("I2 class log");
        }
    }

    static class LoggerWithNotClassName {
        private static final Logger log = LoggerFactory.getLogger("NotClassName");

        void logLoggerWithNotClassName() {
            log.info("Logger With Not Class Name");
        }
    }

    static class A extends LoggerWithNotClassName implements I {
        private static final Logger log = LoggerFactory.getLogger(A.class);

        void logA() {
            log.info("A class log");
        }
    }

    static class B extends A implements I2 {
        private static final Logger log = LoggerFactory.getLogger(B.class);

        void logB() {
            log.info("B class log");
        }
    }
}
