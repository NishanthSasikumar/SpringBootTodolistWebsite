package com.todoist.web.utils;

import ch.qos.logback.classic.spi.ILoggingEvent;

import org.jspecify.annotations.NullMarked;
import org.springframework.boot.logging.structured.StructuredLogFormatter;

class MyCustomFormat implements StructuredLogFormatter<ILoggingEvent> {

    @Override
    @NullMarked
    public String format(ILoggingEvent event) {
        return "time=" + event.getInstant() + " level=" + event.getLevel() + " message=" + event.getMessage() + "\n";
    }

}
