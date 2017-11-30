package com.nboisvert.cli.Core.Command;

import com.nboisvert.cli.Core.Environment.Environment;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CommandTest
{
    private Command command;

    class TestItem {
        @Argument(position = 0, name = "Name")
        public String arg;

        @Argument(position = 5, name = "Name", fallback = "Fallback")
        public String argFallback;

        @Parameter(key = "val")
        public String value;

        @Parameter(key = "unfedined")
        public String unProvided;

        @Parameter(key = "undefined", fallback = "fallback")
        public String fallback;

        @Option(key = "opt")
        public boolean option;

        @Option(key = "demo", flag = 'd')
        public boolean flag;

        @Option(key = "demo", flag = 's')
        public boolean unchecked;
    }

    @Test
    public void shouldHaveFlag()
    {
        assertTrue(command.hasFlag("d"));
    }

    @Test
    public void shouldNotHaveFlag()
    {
        assertFalse(command.hasFlag("k"));
    }

    @Test
    public void shouldHaveOption()
    {
        assertTrue(command.hasOption("opt"));
    }

    @Test
    public void shouldHaveParameter()
    {
        assertTrue(command.hasParameter("val"));
    }

    @Test
    public void shouldGetParameterValue()
    {
        assertEquals("value", command.getParameterValue("val"));
    }

    @Test
    public void shouldGetParameterDefaultValue()
    {
        assertEquals("default", command.getParameterValue("undef", "default"));
    }

    @Test
    public void shouldHaveArgument()
    {
        assertTrue(command.getArguments().contains("arg"));
    }

    @Test
    public void shouldAttachParameter()
    {
        TestItem item = this.getAttachedTestItem();
        assertEquals("value", item.value);
    }

    @Test
    public void shouldAttachUnprovidedParameter()
    {
        TestItem item = this.getAttachedTestItem();
        assertEquals("", item.unProvided);
    }

    @Test
    public void shouldAttachFallbackParameter()
    {
        TestItem item = this.getAttachedTestItem();
        assertEquals("fallback", item.fallback);
    }

    @Test
    public void shouldAttachOption()
    {
        TestItem item = this.getAttachedTestItem();
        assertTrue(item.option);
    }

    @Test
    public void shouldAttachFlag()
    {
        TestItem item = this.getAttachedTestItem();
        assertTrue(item.flag);
    }

    @Test
    public void shouldNotAttachUndefined()
    {
        TestItem item = this.getAttachedTestItem();
        assertFalse(item.unchecked);
    }

    @Test
    public void shouldAttachArgument()
    {
        TestItem item = this.getAttachedTestItem();
        assertEquals("arg", item.arg);
    }

    @Test
    public void shouldAttachArgumentFallback()
    {
        TestItem item = this.getAttachedTestItem();
        assertEquals("Fallback", item.argFallback);
    }

    private TestItem getAttachedTestItem()
    {
        TestItem item = new TestItem();
        this.command.attach(item);
        return item;
    }

    @Before
    public void setUp()
    {
        this.command = this.getCommand();
    }

    private Command getCommand()
    {
        return new Command(new String[]{"-d", "-q", "-fg", "--opt", "--val=value", "arg"});
    }
}