package com.example.Online.Judge;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class TestRunnerTest {
    @Test
    void test1() {
        Assertions.assertEquals(TestRunner.normalise("1"), "1");
    }
    @Test
    void test2() {
        Assertions.assertEquals(TestRunner.normalise(" \n1"), "1");
    }
    @Test
    void test3() {
        Assertions.assertEquals(TestRunner.normalise("1  \n\t"), "1");
    }
    @Test
    void test4() {
        Assertions.assertEquals(TestRunner.normalise(" \n 1  \t "), "1");
    }
    @Test
    void test5() {
        Assertions.assertEquals(TestRunner.normalise(" 1 a   \n c"), "1 a c");
    }
    @Test
    void test6() {
        Assertions.assertEquals(TestRunner.normalise("1\n2"), "1 2");
    }
}