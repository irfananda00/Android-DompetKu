package garputala.irfananda.dompetku;

import java.io.Serializable;

public class TransactionCategory implements Serializable {
    private int id_tr_category;
    private String category;

    public int getId_tr_category() {
        return id_tr_category;
    }

    public void setId_tr_category(int id_tr_category) {
        this.id_tr_category = id_tr_category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
