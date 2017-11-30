package com.nboisvert.cli.Core.Services;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class File
{
    /**
     * Reads a file line by line and returns in a list
     *
     * @param filepath path of the file to load
     * @return List of the file rows
     * @throws IOException if the file throws error
     */
    public static List<String> read(String filepath) throws IOException
    {
        List<String> lines = new ArrayList<>();
        String line;
        BufferedReader reader = new BufferedReader(new FileReader(filepath));
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        reader.close();
        return lines;
    }

    /**
     * Writes a file line by line from a list
     *
     * @param filepath of the file to write
     * @param lines to write
     * @throws IOException if the file throws error
     */
    public static void write(String filepath, List<String> lines) throws IOException
    {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));
        for(String line : lines) {
            writer.write(line);
        }
        writer.close();
    }

    /**
     * Writes a file with one line
     *
     * @param filepath of the file to write
     * @param line to write
     * @throws IOException if the file throws error
     */
    public static void write(String filepath, String line) throws IOException
    {
        List<String> lines = new ArrayList<>();
        lines.add(line);
        File.write(filepath, lines);
    }

    /**
     * Checks if a file exits
     *
     * @param filepath of the file to check
     * @return true if the file exists
     */
    public static boolean exists(String filepath)
    {
        java.io.File file = new java.io.File(filepath);
        return file.exists() && !file.isDirectory();
    }
}
