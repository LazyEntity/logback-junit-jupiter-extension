package junit.jupiter.log.logback;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import junit.jupiter.log.common.LogExtension;
import junit.jupiter.log.common.LogRow;
import junit.jupiter.log.common.LogUtils;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
        Collection<Logger> logs = getLoggersFromClass(logClass, withParents, loggerContext);
        ListLogbackAppender appender = buildAppender();

        return LogbackExtension.builder()
                .appender(appender)
                .loggerContext(loggerContext)
                .loggers(logs)
                .build();
    }

    private static LoggerContext getLoggerContext() {
        return (LoggerContext) LoggerFactory.getILoggerFactory();
    }

    private static Collection<Logger> getLoggersFromClass(Class<?> logClass, boolean withParents, LoggerContext loggerContext) {
        Collection<Class<?>> logClasses = withParents ?
                LogUtils.extractParentsWithCurrentClass(logClass) :
                Collections.singleton(logClass);

        return logClasses.stream()
                .map(loggerContext::getLogger)
                .collect(Collectors.toSet());
    }

    private static ListLogbackAppender buildAppender() {
        LogbackEventToLogbackRowMapper converterILoggingEventToLogRow = new LogbackEventToLogbackRowMapper();
        List<LogRow> logRows = Collections.synchronizedList(new ArrayList<>());

        return new ListLogbackAppenderImpl(converterILoggingEventToLogRow, logRows);
    }
}
