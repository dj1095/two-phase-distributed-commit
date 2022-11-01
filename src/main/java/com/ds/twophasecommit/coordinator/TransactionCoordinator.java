package com.ds.twophasecommit.coordinator;

import com.ds.twophasecommit.shared.Process;
import com.ds.twophasecommit.shared.TransactionRef;
import com.ds.twophasecommit.utils.TwoPhaseCommitUtils;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static com.ds.twophasecommit.constants.TwoPhaseCommitConstants.LOCAL_HOST;
import static com.ds.twophasecommit.constants.TwoPhaseCommitConstants.TC_DOWN_TIME;
import static com.ds.twophasecommit.constants.TwoPhaseCommitConstants.TC_TIME_OUT;

public class TransactionCoordinator {
    //private static  final ExecutorService executor = Executors.newSingleThreadExecutor();
    private static final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    public static void main(String[] args) {
        boolean isPrepPhaseSuccess = sendPrepareMessage();
        Map<String, Integer> nodes = TwoPhaseCommitUtils.getConnectedClients();
        if(isPrepPhaseSuccess){
            //send global commit
            System.out.println("All clients replied yes. Sending Global Commit");
            sendMessageToClients(nodes,"Commit");
            System.out.println("Transaction Committed");
        }else{
            //send global abort
            System.out.println("Some of the clients replied no. Sending Global Abort");
            sendMessageToClients(nodes,"Abort");
            System.out.println("Transaction Aborted");
        }

    }

    private static void sendMessageToClients(Map<String, Integer> nodes, String message)  {
        for (Map.Entry<String, Integer> node : nodes.entrySet()) {
            Registry registry = null;
            try {
                registry = LocateRegistry.getRegistry(LOCAL_HOST, node.getValue());
                Process process = (Process) registry.lookup(node.getKey());
                process.sendMessage(message);
            } catch (RemoteException | NotBoundException e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean sendPrepareMessage() {
        Map<String, Integer> nodes = TwoPhaseCommitUtils.getConnectedClients();
        try {
            for (Map.Entry<String, Integer> node : nodes.entrySet()) {
                Registry registry = LocateRegistry.getRegistry(LOCAL_HOST, node.getValue());
                Process process = (Process) registry.lookup(node.getKey());
                TransactionRef transactionRef = new TransactionRef();
                process.beginTransaction(transactionRef.getTransactionID());
                tcDowntime(TC_DOWN_TIME);
                final Future<String> handler = executor.submit(new Callable() {
                    @Override
                    public String call() throws Exception {
                        System.out.println("Sending Prepare Message to :" + node.getKey());
                        return process.prepare(transactionRef);
                    }
                });
                String ack = "no";
                try {
                    ack = handler.get(TC_TIME_OUT, TimeUnit.MILLISECONDS);
                } catch (TimeoutException e) {
                    System.out.println("Transaction Timed Out");
                    handler.cancel(true);
                   // transactionRef.getTransactionStatus().setTimedOut(true);
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Acknowledgment from " + node.getKey() +":" + ack +"\n");
                if(ack.equalsIgnoreCase("no")){
                    // global abort
                    return false;
                }else if(ack.equalsIgnoreCase("yes")){
                    transactionRef.getTransactionStatus().setPrepareSuccess(true);
                }

            }
        } catch (RemoteException | NotBoundException e) {
            System.out.println("An Exception occurred while server init.");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static void tcDowntime(long downTime) {
        try {
            executor.schedule(() -> {},downTime,TimeUnit.MILLISECONDS).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }


}
