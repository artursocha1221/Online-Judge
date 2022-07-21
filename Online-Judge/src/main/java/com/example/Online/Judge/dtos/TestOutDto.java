package com.example.Online.Judge.dtos;

public class TestOutDto {
    private String input;
    private String output;

    public TestOutDto(){
    }

    public TestOutDto(String input, String output) {
        this.input = input;
        this.output = output;
    }

    public void setInput(String input) {
        this.input = input;
    }
    public void setOutput(String output) {
        this.output = output;
    }

    public String getInput() {
        return input;
    }
    public String getOutput() {
        return output;
    }
}
