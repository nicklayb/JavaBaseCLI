package com.nboisvert.cli.Core.Services;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Formats string through computed formatters
 */
public class StringFormatter
{
    /**
     * The formatters
     */
    private static HashMap<String, Function<String, String>> formattters;

    /**
     * Gets the formatters
     *
     * @return Map of key and function
     */
    protected static HashMap<String, Function<String, String>> getFormattters()
    {
        if (StringFormatter.formattters == null) {
            StringFormatter.reset();
        }
        return StringFormatter.formattters;
    }

    /**
     * Loads the basic formatters
     */
    private static void loadFormatters()
    {
        StringFormatter.register("date", (key) -> Date.now(Date.DATE_FORMAT));
        StringFormatter.register("time", (key) -> Date.now(Date.TIME_FORMAT));
    }

    /**
     * Formats a string though the formatters
     *
     * @param input with parameter
     * @return formatted string
     */
    public static String format(String input)
    {
        String output = input;
        for (Map.Entry<String, Function<String, String>> entry : StringFormatter.getFormattters().entrySet()) {
            output = output.replace(StringFormatter.getQualifiedKey(entry.getKey()), entry.getValue().apply(entry.getKey()));
        }
        return output;
    }

    /**
     * Registers a formatter
     *
     * @param key of the formatter
     * @param formatter function to execute
     */
    public static void register(String key, Function<String, String> formatter)
    {
        if (StringFormatter.getFormattters().containsKey(key)) {
            StringFormatter.getFormattters().replace(key, formatter);
        } else {
            StringFormatter.getFormattters().put(key, formatter);
        }
    }

    /**
     * Unregisters a formatter
     *
     * @param key of the formatter
     */
    public static void unregister(String key)
    {
        if (StringFormatter.getFormattters().containsKey(key)) {
            StringFormatter.getFormattters().remove(key);
        }
    }

    /**
     * Gets qualified parameter name
     *
     * @param key of the parameter name
     * @return qualified parameter name
     */
    private static String getQualifiedKey(String key)
    {
        return String.format("{%s}", key);
    }

    /**
     * Clears the formatters
     */
    public static void clear()
    {
        StringFormatter.formattters = new HashMap<>();
    }

    /**
     * Resets the formatters
     */
    public static void reset()
    {
        StringFormatter.clear();
        StringFormatter.loadFormatters();
    }
}
