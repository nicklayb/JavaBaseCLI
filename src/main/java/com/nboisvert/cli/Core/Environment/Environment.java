package com.nboisvert.cli.Core.Environment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Environment singleton
 */
public class Environment
{
    /**
     * Variables of the environment
     */
    private HashMap<String, String> variables = new HashMap<>();

    /**
     * Expectations catched by attaching
     */
    private HashMap<String, String> expected = new HashMap<>();

    /**
     * Single instance of the Environment
     */
    private static Environment instance = new Environment();

    /**
     * Gets the instance
     *
     * @return Environment unique instance
     */
    private static Environment getInstance()
    {
        return Environment.instance;
    }

    /**
     * Constructor
     */
    private Environment() { }

    /**
     * Sets a variable with a value
     *
     * @param key of the variable
     * @param value of the variable
     */
    private void setVariable(String key, String value)
    {
        if (this.variables.containsKey(key)) {
            this.variables.replace(key, value);
        } else {
            this.variables.put(key, value);
        }
    }

    /**
     * Gets a variable value
     *
     * @param key of the variable to get
     * @return String value of the variable
     */
    private String getVariable(String key)
    {
        return this.variables.get(key);
    }

    /**
     * Gets a variable value with a fallback
     *
     * @param key of the value to get
     * @param fallback value if the key does not exists
     *
     * @return String value of the variable
     */
    private String getVariable(String key, String fallback)
    {
        return this.variables.getOrDefault(key, fallback);
    }

    /**
     * Checks if a variable is present
     *
     * @param key to lookup
     * @return true if the key is present
     */
    public static boolean has(String key)
    {
        return Environment.getInstance().variables.containsKey(key);
    }

    /**
     * Sets a variable value
     *
     * @param key of the value to set
     * @param value to set
     */
    public static void set(String key, String value)
    {
        Environment.getInstance().setVariable(key, value);
    }

    /**
     * Gets a variable value
     *
     * @param key of the variable
     * @return String value of the variable
     */
    public static String get(String key)
    {
        return Environment.get(key, null);
    }

    /**
     * Gets a variable value with a fallback
     *
     * @param key of the variable
     * @param fallback value to set if key not provided
     *
     * @return String value of the variable
     */
    public static String get(String key, String fallback)
    {
        return Environment.getInstance().getVariable(key, fallback);
    }

    /**
     * Loads a list of raw string formatter KEY=VALUE
     *
     * @param rows List of string
     */
    public static void load(List<String> rows)
    {
        rows.forEach(row -> {
            if (row.contains("=")) {
                Environment.setRaw(row);
            }
        });
    }

    /**
     * Sets a variable value from a raw string
     *
     * @param raw String
     */
    private static void setRaw(String raw)
    {
        String[] splitted = raw.split("=", 2);
        Environment.set(splitted[0], splitted[1]);
    }

    /**
     * Attaches environment variables to a module
     *
     * @param module to attach
     * @param <T> type
     */
    public static <T> void attach(T module)
    {
        for(Field field : module.getClass().getFields()) {
            if (field.isAnnotationPresent(EnvironmentVariable.class)) {
                EnvironmentVariable variable = field.getAnnotation(EnvironmentVariable.class);
                try {
                    field.set(module, Environment.get(variable.key(), variable.fallback()));
                } catch (IllegalAccessException ignored) {}
            }
        }
    }

    /**
     * Gets the size of the environment
     *
     * @return int
     */
    public static int size()
    {
        return Environment.getInstance().variables.size();
    }

    /**
     * Clears the whole environment
     */
    public static void clear()
    {
        Environment.getInstance().expected = new HashMap<>();
        Environment.getInstance().variables = new HashMap<>();
    }

    /**
     * Add a variable to the expectation
     *
     * @param expectation to add
     */
    public static void expect(String expectation, String fallback)
    {
        Environment.getInstance().expected.put(expectation, fallback);
    }

    /**
     * Gets the expected variables
     *
     * @return List of expected variable
     */
    public static HashMap<String, String> getExpected()
    {
        return Environment.getInstance().expected;
    }

    /**
     * Exports the expactation to a writable format
     *
     * @return List of variables and their fallback
     */
    public static List<String> export()
    {
        List<String> expected = new ArrayList<>();
        for(Map.Entry<String, String> entry : Environment.getExpected().entrySet()) {
            expected.add(String.format("%s=%s", entry.getKey(), entry.getValue()));
        }
        return expected;
    }
}
