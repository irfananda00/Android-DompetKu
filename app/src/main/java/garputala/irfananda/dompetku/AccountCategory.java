package garputala.irfananda.dompetku;


import java.io.Serializable;

public class AccountCategory implements Serializable {
    private int id_ac_category;
    private String category;

    public int getId_ac_category() {
        return id_ac_category;
    }

    public void setId_ac_category(int id_ac_category) {
        this.id_ac_category = id_ac_category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
