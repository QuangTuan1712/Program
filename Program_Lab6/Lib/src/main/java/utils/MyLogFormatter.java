package utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class MyLogFormatter extends Formatter {
    @Override
    public String format(LogRecord record) {
        SimpleDateFormat logTime = new SimpleDateFormat("MM.dd.yyyy HH:mm:ss");
        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(record.getMillis());
        return logTime.format(cal.getTime())
                + "  "
                + record.getSourceClassName().substring(
                record.getSourceClassName().lastIndexOf(".")+1)
                + "."
                + record.getSourceMethodName()
                + "()\n"
                + record.getLevel()
                + ": "
                + record.getMessage() + "\n";
    }
}
