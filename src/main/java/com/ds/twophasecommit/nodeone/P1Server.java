package com.ds.twophasecommit.nodeone;

import com.ds.twophasecommit.shared.Process;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import static com.ds.twophasecommit.constants.TwoPhaseCommitConstants.LOCAL_HOST;
import static com.ds.twophasecommit.constants.TwoPhaseCommitConstants.N1_SERVER_PORT;

public class P1Server {
    public static void main(String[] args) {
        try {
            initServer();
        } catch (RemoteException e) {
            System.out.println("An Exception occurred while P1Server init.");
            e.printStackTrace();
        }
    }

    private static void initServer() throws RemoteException {
        System.out.println("Node One Listening on port..." + N1_SERVER_PORT);
        System.setProperty("java.rmi.server.hostname", LOCAL_HOST);
        Process processOne = new ProcessOne();
        Process processOneStub = (Process) UnicastRemoteObject.exportObject(processOne, 0);
        Registry registry = LocateRegistry.createRegistry(N1_SERVER_PORT);
        registry.rebind("processOne", processOneStub);
    }
}
