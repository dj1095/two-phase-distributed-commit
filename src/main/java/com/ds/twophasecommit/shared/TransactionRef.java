package com.ds.twophasecommit.shared;

import java.io.Serializable;
import java.util.UUID;

public class TransactionRef implements Serializable {
    private UUID transactionID;
    private TransactionStatus transactionStatus;

    public TransactionRef() {
        this.transactionID = UUID.randomUUID();
        this.transactionStatus = new TransactionStatus();
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public UUID getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(UUID transactionID) {
        this.transactionID = transactionID;
    }

}
