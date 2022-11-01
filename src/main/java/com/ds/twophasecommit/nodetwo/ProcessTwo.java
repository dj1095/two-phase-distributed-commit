package com.ds.twophasecommit.nodetwo;

import com.ds.twophasecommit.shared.Process;
import com.ds.twophasecommit.shared.TransactionRef;

import java.rmi.RemoteException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static com.ds.twophasecommit.constants.TwoPhaseCommitConstants.N1_TIME_OUT;
import static com.ds.twophasecommit.constants.TwoPhaseCommitConstants.N2_DOWN_TIME;
import static com.ds.twophasecommit.constants.TwoPhaseCommitConstants.N2_TIME_OUT;
import static com.ds.twophasecommit.constants.TwoPhaseCommitConstants.TC_DOWN_TIME;

public class ProcessTwo implements Process {
    //private static final ExecutorService executor = Executors.newFixedThreadPool(2);
    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
    private static final Map<UUID, Boolean> transactionStatusMap = new ConcurrentHashMap<>();
    final Future<String> handler = executor.submit(new Callable<String>() {
        @Override
        public String call() throws Exception {
            Thread.sleep(N2_DOWN_TIME);
            return "";
        }
    });

    @Override
    public String prepare(TransactionRef transactionRef) throws RemoteException {
        System.out.println("N2: Prepare Message received From TC");
       /* try {
            executor.schedule(() -> {},N2_DOWN_TIME,TimeUnit.MILLISECONDS).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }*/
        n2Downtime(N2_DOWN_TIME);
        if(TC_DOWN_TIME > N2_TIME_OUT){
            transactionStatusMap.put(transactionRef.getTransactionID(),true);
        }
        if (transactionStatusMap.getOrDefault(transactionRef.getTransactionID(),false)) {
            System.out.println("N2: Transaction Timed Out. Returning reply as no");
            return "no";
        }
        System.out.println("N2: Returning reply as yes");
        return "yes";
    }

    private void n2Downtime(long n2DownTime) {
        try {
            executor.schedule(() -> {},n2DownTime,TimeUnit.MILLISECONDS).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void beginTransaction(UUID transactionID) throws RemoteException {
        System.out.println("N2: Waiting for prepare msg from TC");

        try {
            System.out.println("N2: Timer Started for the Transaction");
            handler.get(N2_TIME_OUT, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            System.out.println("Transaction Timed Out");
            handler.cancel(true);
            transactionStatusMap.put(transactionID, true);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessage(String message) throws RemoteException {
        handler.cancel(true);
        System.out.println("N2: Transaction" + message);
    }
}
