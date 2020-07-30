package log;

import ch.qos.logback.classic.LoggerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.StaticLoggerBinder;

import java.util.List;

/**
 * Created by Niki on 2018/8/30 14:44
 */
public class LogDemo {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogDemo.class);

    public static void main(String[] args) {
        LoggerContext loggerContext = (LoggerContext) StaticLoggerBinder.getSingleton().getLoggerFactory();
        List<ch.qos.logback.classic.Logger> loggers = loggerContext.getLoggerList();
        for (ch.qos.logback.classic.Logger logger : loggers) {
            System.out.println(logger.getName());
        }
        LOGGER.info("Logger对象列表={}",loggers);
        LOGGER.info("test");
    }
}
