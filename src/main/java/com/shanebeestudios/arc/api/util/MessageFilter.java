package com.shanebeestudios.arc.api.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.filter.AbstractFilter;
import org.apache.logging.log4j.message.Message;

public class MessageFilter extends AbstractFilter {

    private boolean enabled = true;
    private int count = 0;

    public MessageFilter() {
        // Filter out Skript's "Minecraft id 'mod:item' is not valid" while Skript is loading aliases
        Logger rootLogger = (Logger) LogManager.getRootLogger();
        rootLogger.addFilter(this);
    }

    public void disable() {
        this.enabled = false;
        Util.log("Filtered out &e%s&7 alias errors from Skript!", this.count);
    }

    @Override
    public Result filter(LogEvent event) {
        return event == null ? Result.NEUTRAL : checkMessage(event.getMessage().getFormattedMessage());
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, Message msg, Throwable t) {
        return checkMessage(msg.getFormattedMessage());
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, Object msg, Throwable t) {
        return checkMessage(msg.toString());
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, String msg, Object... params) {
        return checkMessage(msg);
    }

    private Result checkMessage(String message) {
        if (this.enabled && message.contains("    Minecraft id ") && message.contains("is not valid")) {
            count++;
            return Result.DENY;
        }
        return Result.NEUTRAL;
    }

}
