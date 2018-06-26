package rmiServer;

public interface TransactionManagerInterface {

    public float makeDeposit(float amount);
    public float makeWithdrawal(float amount);
    public boolean send(float amount, int accNum);
    public float[][] getTransactionHistory();

}
