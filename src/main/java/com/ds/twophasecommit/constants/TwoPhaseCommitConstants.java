package com.ds.twophasecommit.constants;

import java.time.Duration;

public class TwoPhaseCommitConstants {

    public static final int TC_SERVER_PORT = 9000;
    public static final int N1_SERVER_PORT = 9001;
    public static final int N2_SERVER_PORT = 9002;
    public static final String LOCAL_HOST = "127.0.0.1";

    public static final long TC_TIME_OUT = Duration.ofSeconds(8).toMillis();
    public static final long TC_DOWN_TIME = Duration.ofSeconds(0).toMillis();

    public static final long N1_TIME_OUT =  Duration.ofSeconds(15).toMillis();
    public static final long N1_DOWN_TIME = Duration.ofSeconds(10).toMillis();

    public static final long N2_TIME_OUT =  Duration.ofSeconds(5).toMillis();
    public static final long N2_DOWN_TIME = Duration.ofSeconds(0).toMillis();



}
