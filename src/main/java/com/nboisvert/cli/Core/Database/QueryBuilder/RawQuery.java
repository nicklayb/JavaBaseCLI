package com.nboisvert.cli.Core.Database.QueryBuilder;

public class RawQuery implements Query
{
    private String query;

    public RawQuery(String query)
    {
        this.query = query;
    }

    @Override
    public String getQueryString()
    {
        return this.query;
    }
}
