package com.example.Online.Judge.TestRunner;

import org.springframework.stereotype.Component;

@Component
public class CppRunner extends LanguageRunner {
    private final String codeFile = "code.cpp";
    private final String startFile = "cppstart.bat";

    @Override
    String getCodeFile() {
        return codeFile;
    }

    @Override
    String getStartFile() {
        return startFile;
    }
}
