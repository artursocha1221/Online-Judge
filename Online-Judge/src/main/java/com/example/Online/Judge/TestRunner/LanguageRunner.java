package com.example.Online.Judge.TestRunner;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

abstract public class LanguageRunner {
    private final String partPath = "C:\\Users\\Artur\\Desktop\\enviroment\\";

    abstract String getCodeFile();
    abstract String getStartFile();

    private String normalise(String text) {
        int firstNonWhite = 0;
        while (text.charAt(firstNonWhite) < '!' || text.charAt(firstNonWhite) > '~')
            ++firstNonWhite;
        int lastNonWhite = text.length() - 1;
        while (text.charAt(lastNonWhite) < '!' || text.charAt(lastNonWhite) > '~')
            --lastNonWhite;
        StringBuilder normalised = new StringBuilder("");
        for (int i = firstNonWhite; i <= lastNonWhite; ++i) {
            char c = text.charAt(i);
            if (c >= '!' && c <= '~')
                normalised.append(c);
            if (c == ' ' && normalised.charAt(normalised.capacity() - 1) != ' ')
                normalised.append(c);
        }
        return normalised.toString();
    }

    private void createAndSave(String path, String content) {
        try {
            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException e) {
            System.out.print("Write error");
        }
    }

    private String readOutput(String path) {
        StringBuilder stringBuilder = new StringBuilder("");
        try {
            FileReader fileReader = new FileReader(path);
            int c = fileReader.read();
            while (c != -1) {
                stringBuilder.append((char) c);
                c = fileReader.read();
            }
            fileReader.close();
        } catch (IOException e) {
            System.out.print("Read error");
        }
        return stringBuilder.toString();
    }

    public String getResult(String code, String input, String output) {
        createAndSave(partPath + getCodeFile(), code);
        createAndSave(partPath + "a.in", input);
        try {
            Runtime.getRuntime().exec("cmd /c start " + partPath + getStartFile());
            Thread.sleep(4000);
        } catch (IOException | InterruptedException e) {
            System.out.print("Script execution error");
        }
        return (normalise(readOutput(partPath + "b.out")).equals(output)) ? "Y" : "N";
    }
}
