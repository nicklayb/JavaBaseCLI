package com.nboisvert.cli.Core.Command;

import com.sun.org.apache.xpath.internal.Arg;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Command
 *
 * Extracts arguments provide by command line into a Command
 */
public class Command
{
    /**
     * Option identifier
     */
    private static final String OPTION_IDENTIFIER = "--";

    /**
     * Parameter identifier
     */
    private static final String PARAMETER_IDENTIFIER = "--";

    /**
     * Flag identifier
     */
    private static final String FLAG_IDENTIFIER = "-";

    /**
     * Arguments as they are provided
     */
    private String[] rawArguments;

    /**
     * Option arguments
     */
    private List<String> options;

    /**
     * Parameter arguments
     */
    private HashMap<String, String> parameters;

    /**
     * Arguments that are not options, parameters or flags
     */
    private List<String> arguments;

    /**
     * Flag arguments
     */
    private List<Character> flags;

    /**
     * Constructor
     *
     * @param rawArguments Array of string as provided from the Main
     */
    public Command(String[] rawArguments)
    {
        this.rawArguments = rawArguments;
    }

    /**
     * Gets the options
     *
     * @return List of options
     */
    public List<String> getOptions()
    {
        if (this.options == null) {
            this.options = this.extractOptions();
        }
        return this.options;
    }

    /**
     * Gets the parameters
     *
     * @return Map of the parameters and their values
     */
    public HashMap<String, String> getParameters()
    {
        if (this.parameters == null) {
            this.parameters = this.extractParameters();
        }
        return this.parameters;
    }

    /**
     * Gets the arguments
     *
     * @return List of arguments
     */
    public List<String> getArguments()
    {
        if (this.arguments == null) {
            this.arguments = this.extractArguments();
        }
        return this.arguments;
    }

    /**
     * Gets the flags
     *
     * @return List of character
     */
    public List<Character> getFlags()
    {
        if (this.flags == null) {
            this.flags = this.extractFlags();
        }
        return this.flags;
    }

    /**
     * Extracts flags from the raw arguments
     *
     * @return List of Character
     */
    private List<Character> extractFlags()
    {
        List<Character> flags = new ArrayList<>();
        this.getRawArguments().forEach(item -> {
            if (this.isFlag(item)) {
                char[] splitted = item.substring(Command.FLAG_IDENTIFIER.length()).toCharArray();
                for(char car : splitted) {
                    if (!flags.contains(car))
                        flags.add(car);
                }
            }
        });
        return flags;
    }

    /**
     * Extracts arguments from the raw arguments
     *
     * @return List of arguments
     */
    private List<String> extractArguments()
    {
        List<String> arguments = new ArrayList<>();
        this.getRawArguments().forEach(item -> {
            if (!this.hasIdentifier(item)) {
                arguments.add(item);
            }
        });
        return arguments;
    }

    /**
     * Checks if an item has any identifier
     * @param item to check
     * @return if the item has any identifier
     */
    private boolean hasIdentifier(String item)
    {
        return this.hasIdentifier(item, Command.OPTION_IDENTIFIER) || this.hasIdentifier(item, Command.PARAMETER_IDENTIFIER) || this.hasIdentifier(item, Command.FLAG_IDENTIFIER);
    }

    /**
     * Checks if an item has specific identifier
     *
     * @param item to check
     * @param identifier to match
     * @return if the item matches the identifier
     */
    private boolean hasIdentifier(String item, String identifier)
    {
        return item.startsWith(identifier);
    }

    /**
     * Extracts parameters from raw arguments
     *
     * @return Map for the parameters and their values
     */
    private HashMap<String,String> extractParameters()
    {
        HashMap<String, String> parameters = new HashMap<>();
        this.getRawArguments().forEach(item -> {
            if (this.isParameter(item)) {
                String[] splitted = item.substring(Command.PARAMETER_IDENTIFIER.length()).split("=", 2);
                parameters.put(splitted[0], splitted[1]);
            }
        });
        return parameters;
    }

    /**
     * Checks if an argument is a parameter
     *
     * @param item to check
     * @return true if parameter
     */
    private boolean isParameter(String item)
    {
        return this.hasIdentifier(item, Command.PARAMETER_IDENTIFIER) && item.contains("=");
    }

    /**
     * Checks if has options
     *
     * @param option to lookup
     * @return true if the option is present
     */
    public boolean hasOption(String option)
    {
        return this.getOptions().contains(option);
    }

    /**
     * Check if has parameter
     *
     * @param key parameter to lookup
     * @return true if the parameter is present
     */
    public boolean hasParameter(String key)
    {
        return this.getParameters().containsKey(key);
    }

    /**
     * Checks if has String flag
     *
     * @param flag to lookup
     * @return true if the flag is present
     */
    public boolean hasFlag(String flag)
    {
        return this.hasFlag(flag.charAt(0));
    }

    /**
     * Checks if has Character flag
     *
     * @param flag to lookup
     * @return true if the flag is present
     */
    public boolean hasFlag(Character flag)
    {
        return this.getFlags().contains(flag);
    }

    /**
     * Checks if an option or flag are present
     *
     * @param option to lookup
     * @param flag to lookup
     * @return true if the flag or the option are present
     */
    public boolean hasOptionOrFlag(String option, Character flag)
    {
        return this.hasOption(option) || this.hasFlag(flag);
    }

    /**
     * Checks if an argument is present
     * @param position
     * @return
     */
    private boolean hasArgument(int position)
    {
        return this.getArguments().size() > 0 && this.getArguments().size() > position;
    }

    /**
     * Gets a parameter value from a key
     *
     * @param key of the parameter to find
     * @return Value of the parameter
     */
    public String getParameterValue(String key)
    {
        return this.getParameters().get(key);
    }

    /**
     * Gets an argument value from his index
     *
     * @param index of the argument
     * @return the value of the argument
     */
    public String getArgumentValue(int index)
    {
        return this.getArguments().get(index);
    }

    /**
     * Gets an argument value from his index or a fallback
     *
     * @param index of the argument
     * @return the value of the argument
     */
    public String getArgumentValue(int index, String fallback)
    {
        return this.hasArgument(index) ? this.getArgumentValue(index) : fallback;
    }

    /**
     * Checks if an item is an option
     *
     * @param item to check
     * @return true if an option
     */
    private boolean isOption(String item)
    {
        return this.hasIdentifier(item, Command.OPTION_IDENTIFIER) && !item.contains("=");
    }

    /**
     * Checks if an item is an flag
     *
     * @param item to check
     * @return true if an flag
     */
    private boolean isFlag(String item)
    {
        return !this.isOption(item) && !this.isParameter(item) && this.hasIdentifier(item, Command.FLAG_IDENTIFIER);
    }

    /**
     * Extracts options from raw arguments
     *
     * @return List of options
     */
    private List<String> extractOptions()
    {
        List<String> options = new ArrayList<>();
        this.getRawArguments().forEach(item -> {
            if (this.isOption(item)) {
                options.add(item.substring(Command.OPTION_IDENTIFIER.length()));
            }
        });
        return options;
    }

    /**
     * Get raw arguments
     *
     * @return List of raw arguments
     */
    private List<String> getRawArguments()
    {
        return Arrays.asList(this.rawArguments);
    }

    /**
     * Get parameter value of default
     *
     * @param key of the parameter
     * @param fallback if the key is not present
     * @return Parameter's value
     */
    public String getParameterValue(String key, String fallback)
    {
        return (this.hasParameter(key)) ? this.getParameterValue(key) : fallback;
    }

    /**
     * Associates Parameter arguments
     *
     * @param module module to associate
     * @param <T> type
     * @param field to attach
     * @throws IllegalAccessException If the Field is not accessible
     */
    private <T> void associateParameters(T module, Field field) throws IllegalAccessException
    {
        if (field.isAnnotationPresent(Parameter.class) && field.getType() == String.class) {
            Parameter parameter = field.getAnnotation(Parameter.class);
            field.set(module, this.getParameterValue(parameter.key(), parameter.fallback()));
        }
    }

    /**
     * Associates Option arguments
     *
     * @param module module to associate
     * @param <T> type
     * @param field to attach
     * @throws IllegalAccessException If the Field is not accessible
     */
    private <T> void associateOptions(T module, Field field) throws IllegalAccessException
    {
        if (field.isAnnotationPresent(Option.class) && field.getType() == boolean.class) {
            Option option = field.getAnnotation(Option.class);
            field.set(module, this.hasOptionOrFlag(option.key(), option.flag()));
        }
    }

    /**
     * Associates Options
     *
     * @param module module to associate
     * @param <T> type
     * @param field to attach
     * @throws IllegalAccessException If the Field is not accessible
     */
    private <T> void associateArguments(T module, Field field) throws IllegalAccessException
    {
        if (field.isAnnotationPresent(Argument.class) && field.getType() == String.class) {
            Argument argument = field.getAnnotation(Argument.class);
            field.set(module, this.getArgumentValue(argument.position(), argument.fallback()));
        }
    }

    /**
     * Attaches arguments to module
     *
     * @param module to attach
     * @param <T> type
     */
    public <T> void attach(T module)
    {
        try {
            for(Field field : module.getClass().getFields()) {
                this.associateArguments(module, field);
                this.associateParameters(module, field);
                this.associateOptions(module, field);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
