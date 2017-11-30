package com.nboisvert.cli.Core.Database;

public class DriverNotFoundException extends Exception
{
    public DriverNotFoundException(String driver)
    {
        super(String.format("Unable to load driver %s", driver));
    }
}
