package com.nboisvert.cli.Core.Functionality;

import com.nboisvert.cli.Core.Decorator.Decorable;
import com.nboisvert.cli.Core.Decorator.Decorator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class FunctionalityDecorator extends Decorator
{
    /**
     * Constructor
     *
     * @param decorable Functionality to decorate
     */
    public FunctionalityDecorator(Functionality decorable)
    {
        super(decorable);
    }

    /**
     * Runs the functionality decorated
     */
    @Override
    public void run()
    {
        this.bootFunctionality();
        this.getDecorable().execute();
        this.dismantleFunctionality();
    }

    /**
     * Invoke the boot function if exists
     *
     * @throws InvocationTargetException  If the method is not invokable
     * @throws IllegalAccessException  If the method is not accessible
     */
    private void bootFunctionality()
    {
        try {
            this.invokeIfExist(this.extractBootMethod());
        } catch (InvocationTargetException | IllegalAccessException ignored) {}
    }

    /**
     * Extracts the Boot method with the annotation
     *
     * @return Method
     */
    private Method extractBootMethod()
    {
        for(Method method : this.getFunctionaltyMethods()) {
            if (method.isAnnotationPresent(Boot.class)) {
                return method;
            }
        }
        return null;
    }

    /**
     * Invoke a method if it is not null
     * @param method Method to be invoked
     * @throws InvocationTargetException If the method is not invokable
     * @throws IllegalAccessException If the method is not accessible
     */
    private void invokeIfExist(Method method) throws InvocationTargetException, IllegalAccessException
    {
        if (method != null) {
            method.invoke(this.getDecorable());
        }
    }

    /**
     * Invoke the dismantle function if exists
     *
     * @throws InvocationTargetException If the method is not invokable
     * @throws IllegalAccessException If the method is not accessible
     */
    private void dismantleFunctionality()
    {
        try {
            this.invokeIfExist(this.extractDismantleMethod());
        } catch (InvocationTargetException | IllegalAccessException ignored) {}
    }

    /**
     * Extracts the Dismantle method with the annotation
     *
     * @return Method
     */
    private Method extractDismantleMethod()
    {
        for(Method method : this.getFunctionaltyMethods()) {
            if (method.isAnnotationPresent(Dismantle.class)) {
                return method;
            }
        }
        return null;
    }

    /**
     * Gets the functionality Methods
     *
     * @return Method[] of the functionality
     */
    private Method[] getFunctionaltyMethods()
    {
        return this.getDecorable().getClass().getMethods();
    }
}
