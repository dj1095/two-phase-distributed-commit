package com.ds.twophasecommit.coordinator;

import com.ds.twophasecommit.shared.Process;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import static com.ds.twophasecommit.constants.TwoPhaseCommitConstants.LOCAL_HOST;
import static com.ds.twophasecommit.constants.TwoPhaseCommitConstants.TC_SERVER_PORT;

public class TCServer {

    public static void main(String[] args) {
        try {
            initServer();
        } catch (RemoteException e) {
            System.out.println("An Exception occurred while TCServer init.");
            e.printStackTrace();
        }
    }

    private static void initServer() throws RemoteException {
        System.out.println("Transaction Coordinator Listening on port..." + TC_SERVER_PORT);
        System.setProperty("java.rmi.server.hostname", LOCAL_HOST);
        Process transactionCoordinator = new TCProcess();
        Process tcStub = (Process) UnicastRemoteObject.exportObject(transactionCoordinator, 0);
        Registry registry = LocateRegistry.createRegistry(TC_SERVER_PORT);
        registry.rebind("TC", tcStub);
    }
}
