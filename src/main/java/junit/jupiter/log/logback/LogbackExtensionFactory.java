package junit.jupiter.log.logback;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import junit.jupiter.log.common.ClassHierarchyExtractor;
import junit.jupiter.log.common.LogExtension;
import junit.jupiter.log.common.LogRow;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class LogbackExtensionFactory {
    private static final String ALL_LOGGERS = "ROOT";

    public static LogExtension forAllLoggers() {
        return forLogger(ALL_LOGGERS);
    }

    public static LogExtension forLogger(String loggerName) {
        LoggerContext loggerContext = getLoggerContext();
        Logger logger = loggerContext.getLogger(loggerName);
        ListLogbackAppender appender = buildAppender();

        return LogbackExtension.builder()
                .appender(appender)
                .loggerContext(loggerContext)
                .loggers(Collections.singleton(logger))
                .build();
    }

    public static LogExtension forClass(Class<?> logClass) {
        return forClass(logClass, true);
    }

    public static LogExtension forClass(Class<?> logClass, boolean withParents) {
        LoggerContext loggerContext = getLoggerContext();
        Collection<Logger> logs = getLogs(logClass, withParents, loggerContext);
        ListLogbackAppender appender = buildAppender();

        return LogbackExtension.builder()
                .appender(appender)
                .loggerContext(loggerContext)
                .loggers(logs)
                .build();
    }

    private static Collection<Logger> getLogs(Class<?> logClass, boolean withParents, LoggerContext loggerContext) {
        ClassHierarchyExtractor classHierarchyExtractor = new ClassHierarchyExtractor();
        ClassToLoggersConverter classToLoggersConverter = new ClassToLoggersConverter(loggerContext, classHierarchyExtractor);
        return classToLoggersConverter.apply(logClass, withParents);
    }

    private static LoggerContext getLoggerContext() {
        return (LoggerContext) LoggerFactory.getILoggerFactory();
    }

    private static ListLogbackAppender buildAppender() {
        LogbackEventToLogbackRowMapper logbackEventToLogbackRowMapper = new LogbackEventToLogbackRowMapper();
        List<LogRow> logRows = Collections.synchronizedList(new ArrayList<>());

        return new ListLogbackAppenderImpl(logbackEventToLogbackRowMapper, logRows);
    }
}
