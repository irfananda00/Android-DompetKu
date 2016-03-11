package garputala.irfananda.dompetku;


import java.io.Serializable;

public class Transaction implements Serializable{
    private int id_tr;
    private int id_ac;
    private String account_name;
    private String jenis_transaksi;
    private int routine;
    private String tanggal_input;
    private int id_tr_category;
    private String nama_category;
    private int value;
    private String nama;

    private int Transport;
    private int Eating;
    private int Education;
    private int Groceries;
    private int Insurance;
    private int Medical;
    private int Utilities;
    private int Salary;
    private int Cash;
    private int Others;

    public int getTransport() {
        return Transport;
    }

    public void setTransport(int transport) {
        Transport = transport;
    }

    public int getEating() {
        return Eating;
    }

    public void setEating(int eating) {
        Eating = eating;
    }

    public int getEducation() {
        return Education;
    }

    public void setEducation(int education) {
        Education = education;
    }

    public int getGroceries() {
        return Groceries;
    }

    public void setGroceries(int groceries) {
        Groceries = groceries;
    }

    public int getInsurance() {
        return Insurance;
    }

    public void setInsurance(int insurance) {
        Insurance = insurance;
    }

    public int getMedical() {
        return Medical;
    }

    public void setMedical(int medical) {
        Medical = medical;
    }

    public int getUtilities() {
        return Utilities;
    }

    public void setUtilities(int utilities) {
        Utilities = utilities;
    }

    public int getSalary() {
        return Salary;
    }

    public void setSalary(int salary) {
        Salary = salary;
    }

    public int getCash() {
        return Cash;
    }

    public void setCash(int cash) {
        Cash = cash;
    }

    public int getOthers() {
        return Others;
    }

    public void setOthers(int others) {
        Others = others;
    }

    public int getId_ac() {
        return id_ac;
    }

    public void setId_ac(int id_ac) {
        this.id_ac = id_ac;
    }

    public int getId_tr_category() {
        return id_tr_category;
    }

    public void setId_tr_category(int id_tr_category) {
        this.id_tr_category = id_tr_category;
    }

    public String getNama_category() {
        return nama_category;
    }

    public void setNama_category(String nama_category) {
        this.nama_category = nama_category;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getId_tr() {
        return id_tr;
    }

    public void setId_tr(int id_tr) {
        this.id_tr = id_tr;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getJenis_transaksi() {
        return jenis_transaksi;
    }

    public void setJenis_transaksi(String jenis_transaksi) {
        this.jenis_transaksi = jenis_transaksi;
    }

    public int getRoutine() {
        return routine;
    }

    public void setRoutine(int routine) {
        this.routine = routine;
    }

    public String getTanggal_input() {
        return tanggal_input;
    }

    public void setTanggal_input(String tanggal_input) {
        this.tanggal_input = tanggal_input;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
