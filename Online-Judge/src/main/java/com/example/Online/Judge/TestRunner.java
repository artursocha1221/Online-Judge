package com.example.Online.Judge;

import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

@Component
public class TestRunner {
    private final String partPath = "C:\\Users\\Artur\\Desktop\\enviroment\\";

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
        createAndSave(partPath + "code.cpp", code);
        createAndSave(partPath + "a.in", input);
        try {
            Runtime.getRuntime().exec("cmd /c start " + partPath + "1.bat");
            Thread.sleep(500);
            Runtime.getRuntime().exec("cmd /c start " + partPath + "2.bat");
            Thread.sleep(3000);
            Runtime.getRuntime().exec("cmd /c start " + partPath + "3.bat");
            Thread.sleep(500);
            Runtime.getRuntime().exec("cmd /c start " + partPath + "4.bat");
            Thread.sleep(500);
        } catch (IOException | InterruptedException e) {
            System.out.print("Script execution error");
        }
        return (readOutput(partPath + "b.out").equals(output)) ? "Y" : "N";
    }
}
