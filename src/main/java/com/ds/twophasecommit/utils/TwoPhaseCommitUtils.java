package com.ds.twophasecommit.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.ds.twophasecommit.constants.TwoPhaseCommitConstants.N1_SERVER_PORT;
import static com.ds.twophasecommit.constants.TwoPhaseCommitConstants.N2_SERVER_PORT;

public class TwoPhaseCommitUtils {
    private static final Map<String, Integer> nodes = new ConcurrentHashMap<>();

    public static Map<String, Integer> getConnectedClients() {
        nodes.put("processOne", N1_SERVER_PORT);
        nodes.put("processTwo", N2_SERVER_PORT);
        return nodes;
    }
}
