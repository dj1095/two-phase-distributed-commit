package com.ds.twophasecommit.nodetwo;

import com.ds.twophasecommit.shared.Process;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import static com.ds.twophasecommit.constants.TwoPhaseCommitConstants.LOCAL_HOST;
import static com.ds.twophasecommit.constants.TwoPhaseCommitConstants.N2_SERVER_PORT;

public class P2Server {
    public static void main(String[] args) {
        try {
            initServer();
        } catch (RemoteException e) {
            System.out.println("An Exception occurred while server init.");
            e.printStackTrace();
        }
    }

    private static void initServer() throws RemoteException {
        System.out.println("Node Two Listening on port..." + N2_SERVER_PORT);
        System.setProperty("java.rmi.server.hostname", LOCAL_HOST);
        Process processTwo = new ProcessTwo();
        Process processTwoStub = (Process) UnicastRemoteObject.exportObject(processTwo, 0);
        Registry registry = LocateRegistry.createRegistry(N2_SERVER_PORT);
        registry.rebind("processTwo", processTwoStub);
    }
}
