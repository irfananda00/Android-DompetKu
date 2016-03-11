package garputala.irfananda.dompetku;

import java.io.Serializable;
import java.util.List;

//DataContainer for containing any data that needed in mainactivity
//this data should be gathered while splashscreenactivity is intact
public class DataContainer implements Serializable {
    private List<Transaction> transactions;
    private List<Account> accounts;
    private Expense expense;
    private Income income;
    private boolean appsFirstStart;

    public boolean isAppsFirstStart() {
        return appsFirstStart;
    }

    public void setAppsFirstStart(boolean appsFirstStart) {
        this.appsFirstStart = appsFirstStart;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public Expense getExpense() {
        return expense;
    }

    public void setExpense(Expense expense) {
        this.expense = expense;
    }

    public Income getIncome() {
        return income;
    }

    public void setIncome(Income income) {
        this.income = income;
    }
}
