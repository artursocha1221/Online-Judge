package com.example.Online.Judge;

import org.springframework.beans.factory.annotation.Value;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

public class TestRunner {
    private static final String partPath;
    private static final Map<String, String> codeFile = new HashMap<>();
    private static final Map<String, String> startFile = new HashMap<>();

    static {
        partPath = "C:\\Users\\Artur\\Desktop\\enviroment\\";
        codeFile.put("cpp", "code.cpp");
        codeFile.put("java", "Main.java");
        startFile.put("cpp", "cppstart.bat");
        startFile.put("java", "javastart.bat");
    }

    private static boolean isWhite(char c) {
        return !(c >= '!' && c <= '~');
    }

    public static String normalise(String text) {
        int firstNonWhite = 0;
        while (isWhite(text.charAt(firstNonWhite)))
            ++firstNonWhite;
        int lastNonWhite = text.length() - 1;
        while (isWhite(text.charAt(lastNonWhite)))
            --lastNonWhite;
        StringBuilder normalised = new StringBuilder("");
        for (int i = firstNonWhite; i <= lastNonWhite; ++i) {
            char c = text.charAt(i);
            if (isWhite(c)) {
                if (!isWhite(normalised.charAt(normalised.length() - 1)))
                    normalised.append(' ');
            }
            else
                normalised.append(c);
        }
        return normalised.toString();
    }

    private static void createAndSave(String path, String content) {
        try {
            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException e) {
            System.out.print("Write error");
        }
    }

    private static String readOutput(String path) {
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

    public static String result(String code, String input, String output, String language) {
        createAndSave(partPath + codeFile.get(language), code);
        createAndSave(partPath + "a.in", input);
        try {
            Runtime.getRuntime().exec("cmd /c start " + partPath + startFile.get(language));
            Thread.sleep(4000);
        } catch (IOException | InterruptedException e) {
            System.out.print("Script execution error");
        }
        return (normalise(readOutput(partPath + "b.out")).equals(normalise(output))) ? "Y" : "N";
    }
}
