package junit.jupiter.log.logback;

import lombok.Builder;
import lombok.Value;
import junit.jupiter.log.common.LogThrowableInfo;

@Value
@Builder
public class LogbackThrowableInfo implements LogThrowableInfo {
    String message;
    String className;
}
