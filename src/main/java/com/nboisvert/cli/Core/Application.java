package com.nboisvert.cli.Core;

import com.nboisvert.cli.Core.Command.Command;
import com.nboisvert.cli.Core.Command.Parameter;
import com.nboisvert.cli.Core.Database.Database;
import com.nboisvert.cli.Core.Environment.Environment;
import com.nboisvert.cli.Core.Functionality.*;
import com.nboisvert.cli.Core.Services.File;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Application class
 *
 * Will execute the functionality and attach
 * fields from the command line
 */
public class Application
{
    /**
     * Functionality to run
     */
    private Functionality functionality;

    /**
     * Provided command
     */
    private Command command;

    @Parameter(key = "env", fallback = "./.env")
    public String environmentFilepath;

    /**
     * Construct the application
     */
    public Application(String[] args)
    {
        this.command = new Command(args);
        this.getCommand().attach(this);
        this.loadEnvironment();
    }

    private void loadEnvironment()
    {
        if (File.exists(this.environmentFilepath)){
            try {
                Environment.load(File.read(this.environmentFilepath));
            } catch (IOException e) {
                System.out.println(String.format("Environment file could not be loaded : %s", e.getMessage()));
            }
        }
        this.attachEnvironments();
    }

    /**
     * Attach environment to classes
     */
    private void attachEnvironments()
    {
        List<Object> objs = new ArrayList<>();
        objs.add(Database.getConnectionManager());
        objs.forEach(Environment::attach);
    }

    /**
     * Prepare the application to run the functionality by
     * associating arguments
     *
     * @param functionality
     */
    public void prepare(Functionality functionality)
    {
        this.functionality = functionality;
        this.functionality.setCommand(this.command);
        this.functionality.setApplication(this);
        this.getCommand().attach(this.functionality);
    }

    /**
     * Prepare and run the provided functionality
     *
     * @param functionality Functionality
     */
    public void run(Functionality functionality)
    {
        this.prepare(functionality);
        this.run();
    }

    /**
     * Run the provided functionality and
     * execute surrounding methods if provided
     */
    public void run()
    {
        try {
            this.runDecorated();
        }
        catch (Exception exception) {
            this.handleCrash(exception);
        }
    }

    /**
     * Run the functionality decorated
     */
    private void runDecorated()
    {
        new FunctionalityDecorator(this.functionality).run();
    }

    /**
     * Handle Functionality crash
     *
     * @param exception Exception
     */
    private void handleCrash(Exception exception)
    {
        System.out.println(String.format("ERROR: An error occured during execution : %s", exception.getMessage()));
    }

    /**
     * Gets the functionality
     *
     * @return The functionality to run
     */
    public Functionality getFunctionality()
    {
        return this.functionality;
    }

    /**
     * Gets the command
     *
     * @return Command
     */
    private Command getCommand()
    {
        return this.command;
    }

}
