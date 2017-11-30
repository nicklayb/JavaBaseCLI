package com.nboisvert.cli.Core.Environment;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class EnvironmentTest
{
    @Before
    public void setUp() throws Exception
    {
        Environment.clear();
        Environment.load(this.getEnvironmentFile());
    }

    @Test
    public void shouldLoadEnvironement() throws Exception
    {
        assertEquals(this.getEnvironementVariables().size(), Environment.size());
    }

    @Test
    public void shouldHaveVariable() throws Exception
    {
        assertTrue(Environment.has("ENV_VAR1"));
    }

    @Test
    public void shouldNotHaveVariable() throws Exception
    {
        assertFalse(Environment.has("UNDEFINED"));
    }

    @Test
    public void shouldHaveCorrectVariableValue() throws Exception
    {
        String key = "ENV_VAR1";
        assertEquals(this.getEnvironementVariables().get(key), Environment.get(key));
    }

    @Test
    public void shouldHaveCorrectVariableValueWithFallback() throws Exception
    {
        String key = "ENV_VAR1";
        assertEquals(this.getEnvironementVariables().get(key), Environment.get(key, "FALLBACK"));
    }

    @Test
    public void shouldHaveFallbackValue() throws Exception
    {
        assertEquals("FALLBACK", Environment.get("UNDEFINED", "FALLBACK"));
    }

    @Test
    public void shouldHaveNullOnBadVariable() throws Exception
    {
        assertEquals(null, Environment.get("UNDEFINED"));
    }

    @Test
    public void shouldSetVariableValue() throws Exception
    {
        String expected = "new value";
        String key = "ENV_VAR1";
        Environment.set(key, expected);
        assertEquals(expected, Environment.get("ENV_VAR1"));
    }

    @Test
    public void shouldAttachVariables() throws Exception
    {
        class Test {
            @EnvironmentVariable(key = "ENV_VAR1")
            public String variable;
        }
        Test testClass = new Test();
        Environment.attach(testClass);
        assertEquals(Environment.get("ENV_VAR1"), testClass.variable);
    }

    @Test
    public void shouldAttachDefaultVariables() throws Exception
    {
        class Test {
            @EnvironmentVariable(key = "VARIABLE", fallback = "fallback")
            public String variable;
        }
        Test testClass = new Test();
        Environment.attach(testClass);
        assertEquals("fallback", testClass.variable);
    }

    @Test
    public void shouldNotAttachVariables() throws Exception
    {
        class Test {
            @EnvironmentVariable(key = "VARIABLE")
            public String variable;
        }
        Test testClass = new Test();
        Environment.attach(testClass);
        assertEquals("", testClass.variable);
    }

    private HashMap<String, String> getEnvironementVariables()
    {
        HashMap<String, String> vars = new HashMap<>();
        vars.put("ENV_VAR1", "value1");
        vars.put("ENV_VAR2", "value2");
        vars.put("ENV_VAR3", "value3");
        return vars;
    }

    @Test
    public void shouldAddVariableToExpected()
    {
        for(Map.Entry<String, String> entry : this.getExpectation().entrySet()) {
            Environment.expect(entry.getKey(), entry.getValue());
        }
        assertEquals(this.getExpectation(), Environment.getExpected());
    }

    @Test
    public void shouldExportVariables()
    {
        List<String> exported = new ArrayList<>();
        for(Map.Entry<String, String> entry : this.getExpectation().entrySet()) {
            Environment.expect(entry.getKey(), entry.getValue());
            exported.add(String.format("%s=%s", entry.getKey(), entry.getValue()));
        }
        assertEquals(exported, Environment.export());
    }

    private HashMap<String, String> getExpectation()
    {
        HashMap<String, String> expected = new HashMap<>();
        expected.put("DB_VAR1", "fallback");
        expected.put("DB_VAR2", "");
        return expected;
    }

    private List<String> getEnvironmentFile()
    {
        List<String> rows = new ArrayList<>();
        for (Map.Entry<String, String> entry : this.getEnvironementVariables().entrySet()) {
            rows.add(String.format("%s=%s", entry.getKey(), entry.getValue()));
        }
        return rows;
    }
}