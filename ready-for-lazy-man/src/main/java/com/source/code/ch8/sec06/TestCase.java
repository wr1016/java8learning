package com.source.code.ch8.sec06;

import java.lang.annotation.*;

@Repeatable(TestCases.class)
public @interface TestCase {
   String params();
   String expected();   
}
