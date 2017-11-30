package com.nboisvert.cli.Core.Database;

public class UnregisteredConnectionException extends Exception
{
    public UnregisteredConnectionException()
    {
        super("No connections registered");
    }

    public UnregisteredConnectionException(String connectionName)
    {
        super(String.format("No such connection %s", connectionName));
    }
}
