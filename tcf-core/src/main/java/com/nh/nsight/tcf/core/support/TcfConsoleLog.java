package com.nh.nsight.tcf.core.support;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public final class TcfConsoleLog {
    private static final PrintStream OUT = new PrintStream(System.out, true, StandardCharsets.UTF_8);

    private TcfConsoleLog() {
    }

    public static void println(String message) {
        OUT.println(message);
    }
}
