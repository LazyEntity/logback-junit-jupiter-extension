package junit.jupiter.log.logback;

import junit.jupiter.log.common.Level;
import junit.jupiter.log.common.LogRow;
import junit.jupiter.log.common.LogThrowableInfo;
import lombok.Builder;
import lombok.Value;

import java.util.Map;

@Value
@Builder
public class LogbackRow implements LogRow {
    String threadName;
    String loggerName;
    String message;
    Level level;
    LogThrowableInfo throwableInfo;
    Long timeStamp;
    Map<String, String> mdc;

    @Override
    public String getMdcValue(String key) {
        return mdc.get(key);
    }
}
