package com.nboisvert.cli.Core.Services;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.function.Function;

import static org.junit.Assert.*;

public class StringFormatterTest
{
    @Test
    public void shouldFormatString()
    {
        String expected = String.format("%s.%s", Date.now(Date.DATE_FORMAT), Date.now(Date.TIME_FORMAT));
        String input = "{date}.{time}";
        assertEquals(expected, StringFormatter.format(input));
    }

    @Test
    public void shouldRegisterFormatter()
    {
        String expected = "New value";
        String key = "key";
        Function<String, String> formatter = (val) -> expected;
        String input = String.format("{%s}", key);
        StringFormatter.register(key, formatter);
        assertEquals(expected, StringFormatter.format(input));
    }

    @Test
    public void shouldUnRegisterFormatter()
    {
        String expected = String.format("{date}.%s", Date.now(Date.TIME_FORMAT));
        String input = "{date}.{time}";
        StringFormatter.unregister("date");
        assertEquals(expected, StringFormatter.format(input));
    }

    @Before
    public void setUp() throws Exception
    {
        StringFormatter.reset();
    }
}