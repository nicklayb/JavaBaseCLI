package com.nboisvert.cli.Core.Database;

import com.nboisvert.cli.Core.Database.QueryBuilder.Query;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Database class
 *
 * Manage interactions with the database
 */
public class Database
{
    /**
     * Connection manager
     */
    private static ConnectionManager connectionManager;

    /**
     * Driver loaded
     */
    private static List<String> driverLoaded = new ArrayList<>();

    /**
     * Connection string
     */
    public ConnectionString connectionString;

    /**
     * Constructor
     *
     * @param connectionString to instantiate
     */
    private Database(ConnectionString connectionString)
    {
        this.connectionString = connectionString;
    }

    /**
     * Retrieve a result set from the database with Query object
     *
     * @param query Query to execute
     * @return Result set
     * @throws SQLException if SQL errors are thrown
     */
    public ResultSet retrieve(Query query) throws SQLException, DriverNotFoundException
    {
        return this.retrieve(this.connectionString.prepare(query));
    }

    /**
     * Retrieve a result set from a statement
     *
     * @param statement to execute
     * @return Result set
     * @throws SQLException if SQL errors are thrown
     */
    private ResultSet retrieve(PreparedStatement statement) throws SQLException
    {
        return statement.executeQuery();
    }

    /**
     * Executes a query
     *
     * @param query to execute
     * @return true if the query had been successful
     * @throws SQLException if an error occurred
     */
    public boolean execute(Query query) throws SQLException, DriverNotFoundException
    {
        return this.execute(this.connectionString.prepare(query));
    }

    /**
     * Executes a statement
     *
     * @param statement to execute
     * @return true if the query had been successful
     * @throws SQLException if an error occurred
     */
    private boolean execute(PreparedStatement statement) throws SQLException
    {
        return statement.execute();
    }

    /**
     * Creates a database connection from the default connection
     *
     * @return Database instance with default connection
     * @throws UnregisteredConnectionException if no connection are registered
     */
    public static Database connection() throws UnregisteredConnectionException
    {
        return new Database(Database.getConnectionManager().getDefault());
    }

    /**
     * Creates a database connection from a connection
     *
     * @param key of the connection to use
     * @return Database instance with the connection
     * @throws UnregisteredConnectionException if the connection is not registered
     */
    public static Database connection(String key) throws UnregisteredConnectionException
    {
        return new Database(Database.getConnectionManager().get(key));
    }

    /**
     * Gets the connection manager
     *
     * @return ConnectionManager
     */
    public static ConnectionManager getConnectionManager()
    {
        if (Database.connectionManager == null) {
            Database.connectionManager = new ConnectionManager();
        }
        return Database.connectionManager;
    }

    /**
     * Checks if a driver is loaded
     *
     * @param driver to lookup
     * @return true if the driver is loaded
     */
    public static boolean hasLoadedDriver(String driver)
    {
        return Database.driverLoaded.contains(driver);
    }

    /**
     * Loads a driver
     *
     * @param driver to load
     * @return true if the driver successfully loaded
     */
    public static boolean loadDriver(String driver)
    {
        try {
            Class.forName(driver);
            Database.driverLoaded.add(driver);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
