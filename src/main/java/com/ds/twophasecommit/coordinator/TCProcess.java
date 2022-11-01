package com.ds.twophasecommit.coordinator;

import com.ds.twophasecommit.shared.Process;
import com.ds.twophasecommit.shared.TransactionRef;

import java.rmi.RemoteException;
import java.util.UUID;

public class TCProcess implements Process {


    @Override
    public String prepare(TransactionRef transactionRef) {
        return "";
    }

    @Override
    public void beginTransaction(UUID transactionID) throws RemoteException {

    }

    @Override
    public void sendMessage(String message) throws RemoteException {

    }

}
