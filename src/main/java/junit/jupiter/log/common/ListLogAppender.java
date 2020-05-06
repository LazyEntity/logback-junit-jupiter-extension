package junit.jupiter.log.common;

import java.util.List;

public interface ListLogAppender {
    List<LogRow> getLogRows();
    void clear();
}
