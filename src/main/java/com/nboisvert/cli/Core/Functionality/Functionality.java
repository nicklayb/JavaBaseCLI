package com.nboisvert.cli.Core.Functionality;

import com.nboisvert.cli.Core.Application;
import com.nboisvert.cli.Core.Command.Command;
import com.nboisvert.cli.Core.Command.Option;
import com.nboisvert.cli.Core.Command.Parameter;
import com.nboisvert.cli.Core.Decorator.Decorable;
import com.nboisvert.cli.Core.Services.Date;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Functionality
 *
 * Base class of a functionality
 */
abstract public class Functionality implements Decorable
{
    /**
     * Command provided
     */
    private Command command;

    /**
     * Verbose mode
     */
    @Option(key = "verbose", flag = 'v')
    public boolean verbose;

    /**
     * Environment path file
     */
    @Parameter(key = "env", fallback = "./.env")
    public String envrionmentPath;

    /**
     * Application that runs the functionality
     */
    private Application application;

    /**
     * Constructor
     */
    public Functionality() { }

    /**
     * Command setter
     *
     * @param command provided
     */
    public void setCommand(Command command)
    {
        this.command = command;
    }

    /**
     * Gets the command
     *
     * @return Command
     */
    public Command getCommand()
    {
        return this.command;
    }

    /**
     * Handles exception thrown with verbose parameter
     *
     * @param exception Thrown exception to print
     */
    protected void handleException(Exception exception)
    {
        this.handleException(exception, null);
    }

    /**
     * Handles exception thrown with verbose parameter
     *
     * @param exception Thrown exception to print
     */
    protected void handleException(Exception exception, String message)
    {
        if (message != null) {
            System.out.println(message);
        }
        if (this.verbose) {
            exception.printStackTrace();
        }
    }

    /**
     * Set the application
     * @param application that runs the functionality
     */
    public void setApplication(Application application)
    {
        this.application = application;
    }
}
