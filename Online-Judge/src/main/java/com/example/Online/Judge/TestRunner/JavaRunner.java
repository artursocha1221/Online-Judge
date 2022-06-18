package com.example.Online.Judge.TestRunner;

import org.springframework.stereotype.Component;

@Component
public class JavaRunner extends LanguageRunner{
    private final String codeFile = "Main.java";
    private final String startFile = "javastart.bat";

    @Override
    String getCodeFile() {
        return codeFile;
    }

    @Override
    String getStartFile() {
        return startFile;
    }
}
