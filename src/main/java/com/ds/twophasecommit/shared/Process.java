package com.ds.twophasecommit.shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;

public interface Process extends Remote {
    String prepare(TransactionRef transactionRef) throws RemoteException;
    void beginTransaction(UUID transactionID) throws RemoteException;
    void sendMessage(String message) throws RemoteException;
}
