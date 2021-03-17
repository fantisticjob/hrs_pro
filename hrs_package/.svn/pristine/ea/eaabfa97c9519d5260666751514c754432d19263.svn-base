package com.hausontech.hrs.daoImpl.engine.thread;

import java.util.*;

public class Timer {

    public Timer() {
    }

    public void waitUntil (Date date) {
        waitFor (date.getTime() - System.currentTimeMillis());
    }

    public void waitUntilInterruptable (Date date) {
        waitForInterruptable (date.getTime() - System.currentTimeMillis());
    }

    public void waitForInterruptable (long t) {
        if (t <= 0)
            return;

        try {
            Thread.sleep(t);
        }
        catch (Exception e) {}
    }

    public void waitFor (long t) {
        if (t <= 0)
            return;

        long z = System.currentTimeMillis() + t;

        for (; t > 0; t = z - System.currentTimeMillis()) {
            try {
                Thread.sleep(t);
            }
            catch (Exception e) {}
        }
    }

    //
    public static void main (String[] args) {
        Timer tm = new Timer();

        tm.waitUntil (new Date((new Date().getTime() + 15000)));

        try {
            System.out.println("->");
            int i = System.in.read();
        }
        catch (Exception e) {}
    }


}
