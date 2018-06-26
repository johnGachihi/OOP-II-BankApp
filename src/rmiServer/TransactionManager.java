package rmiServer;

import Tools.DBHelper;
import pojos.Account;

public class TransactionManager {

    private Account account;

    public TransactionManager(Account acc) {
        account = acc;
        account.setAccNumber(DBHelper.getAccountNumber(
                account.getEncPin(), account.getSalt()));
    }

    public float makeDeposit(float amount){
        return DBHelper.deposit(amount, account.getAccNumber());
    }

    public float makeWithdrawal(float amount) {
        return DBHelper.withdraw(amount, account.getAccNumber());
    }

    public float send(float amount, int receiverAccNo){
        return DBHelper.send(account.getAccNumber(), amount, receiverAccNo);
    }

    public int getAccount(){
        return account.getAccNumber();
    }
}
