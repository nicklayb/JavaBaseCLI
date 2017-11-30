package com.nboisvert.cli.Core.Decorator;

/**
 * Decorator
 *
 * Wraps a decorable and allows setup and teardown actions
 */
abstract public class Decorator
{
    /**
     * Decorable to wrap
     */
    private final Decorable decorable;

    /**
     * Constructor
     *
     * @param decorable to wrap
     */
    public Decorator(Decorable decorable)
    {
        this.decorable = decorable;
    }

    /**
     * Gets the decorable
     *
     * @return Decorable
     */
    public Decorable getDecorable()
    {
        return this.decorable;
    }

    /**
     * Run de decorable instance
     */
    abstract public void run();
}
