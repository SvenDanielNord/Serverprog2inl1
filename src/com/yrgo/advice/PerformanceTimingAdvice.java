package com.yrgo.advice;


import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.reflect.Method;

public class PerformanceTimingAdvice {

    public Object preformTimingMeasurement(ProceedingJoinPoint method) throws Throwable {

        //before
        long startTime = System.nanoTime();

        try {
            //proceed to target
            Object value = method.proceed();
            return value;
        } finally {
            //after
            long endTime = System.nanoTime();
            long timeTaken = endTime - startTime;
            System.out.println("Time taken for the method " + method.getSignature().getName() + " in the class" );
            System.out.println(method.getTarget().getClass().getName() + " took " + timeTaken
                    / 1000000 + " milliseconds");

        }
    }
}