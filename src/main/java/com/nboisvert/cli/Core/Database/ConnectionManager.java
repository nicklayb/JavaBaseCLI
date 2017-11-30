package com.nboisvert.cli.Core.Database;

import com.nboisvert.cli.Core.Environment.Environment;
import com.nboisvert.cli.Core.Environment.EnvironmentVariable;
import java.util.HashMap;

/**
 * Connection manager
 *
 * Registers connection by attaching environment to them
 */
public class ConnectionManager
{
    /**
     * Default connection as provided by the DB_CONNECTION environment variable
     */
    @EnvironmentVariable(key = "DB_CONNECTION", fallback = "sql")
    public String defaultConnection;

    /**
     * Connections registered
     */
    private HashMap<String, ConnectionString> connections = new HashMap<>();

    /**
     * Checks if a connection has been registered
     * @return
     */
    public boolean hasConnections()
    {
        return this.connections.size() > 0;
    }

    /**
     * Registers a connection
     *
     * @param key name of the connection
     * @param connectionString Implementation of the connection string
     */
    public void register(String key, ConnectionString connectionString)
    {
        Environment.attach(connectionString);
        this.connections.put(key, connectionString);
    }

    /**
     * Removes a connection from the manager
     *
     * @param key of the connection to remove
     */
    public void unregister(String key)
    {
        this.connections.remove(key);
    }

    /**
     * Gets a connection string
     *
     * @param key of the connection string to get
     * @return ConnectionString
     * @throws UnregisteredConnectionException if the connection is not registered
     */
    public ConnectionString get(String key) throws UnregisteredConnectionException
    {
        if (!this.connections.containsKey(key)) {
            throw new UnregisteredConnectionException(key);
        }
        return this.connections.get(key);
    }

    /**
     * Get the default connection string
     *
     * @return Connection String by default
     * @throws UnregisteredConnectionException if no connection string are registered
     */
    public ConnectionString getDefault() throws UnregisteredConnectionException
    {
        if (!this.hasConnections()) {
            throw new UnregisteredConnectionException();
        }
        return this.get(this.getDefaultConnectionName());
    }

    /**
     * Gets the name of the default connection name
     *
     * @return String
     */
    public String getDefaultConnectionName()
    {
        if (this.defaultConnection == null) {
            return this.connections.keySet().toArray()[0].toString();
        }
        return this.defaultConnection;
    }
}
