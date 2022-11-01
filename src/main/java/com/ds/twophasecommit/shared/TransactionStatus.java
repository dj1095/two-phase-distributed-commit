package com.ds.twophasecommit.shared;

import java.io.Serializable;

public class TransactionStatus implements Serializable {

    private String processName;
    private boolean timedOut;
    private boolean prepareSuccess;

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public boolean isPrepareSuccess() {
        return prepareSuccess;
    }

    public void setPrepareSuccess(boolean prepareSuccess) {
        this.prepareSuccess = prepareSuccess;
    }

    public boolean isTimedOut() {
        return timedOut;
    }

    public void setTimedOut(boolean timedOut) {
        this.timedOut = timedOut;
    }
}
