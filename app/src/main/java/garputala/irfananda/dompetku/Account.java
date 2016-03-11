package garputala.irfananda.dompetku;

import java.io.Serializable;

public class Account implements Serializable{
    private int id_ac;
    private String account_name;
    private int value;
    private int id_ac_category;
    private String nama_category;
    private String tanggal_input;
    private int totalCash;
    private int Cash;
    private int Debit;
    private int Savings;
    private int Credit;
    private int Others;

    public int getCash() {
        return Cash;
    }

    public void setCash(int cash) {
        Cash = cash;
    }

    public int getDebit() {
        return Debit;
    }

    public void setDebit(int debit) {
        Debit = debit;
    }

    public int getSavings() {
        return Savings;
    }

    public void setSavings(int savings) {
        Savings = savings;
    }

    public int getCredit() {
        return Credit;
    }

    public void setCredit(int credit) {
        Credit = credit;
    }

    public int getOthers() {
        return Others;
    }

    public void setOthers(int others) {
        Others = others;
    }

    public int getTotalCash() {
        return totalCash;
    }

    public void setTotalCash(int totalCash) {
        this.totalCash = totalCash;
    }

    public String getTanggal_input() {
        return tanggal_input;
    }

    public void setTanggal_input(String tanggal_input) {
        this.tanggal_input = tanggal_input;
    }

    public int getId_ac() {
        return id_ac;
    }

    public void setId_ac(int id_ac) {
        this.id_ac = id_ac;
    }

    public int getId_ac_category() {
        return id_ac_category;
    }

    public void setId_ac_category(int id_ac_category) {
        this.id_ac_category = id_ac_category;
    }

    public String getNama_category() {
        return nama_category;
    }

    public void setNama_category(String nama_category) {
        this.nama_category = nama_category;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
