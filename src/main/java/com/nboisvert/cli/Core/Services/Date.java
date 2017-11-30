package com.nboisvert.cli.Core.Services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Date
{
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String TIME_FORMAT = "HH-mm-ss";

    public static String now(String format)
    {
        return DateTimeFormatter.ofPattern(format).format(LocalDateTime.now());
    }
}
