package garputala.irfananda.dompetku;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TransactionCategoryParser {
    private static final String HOST="http://garputala-chalange.site88.net/getTransactionCategory.php";

    public static List<TransactionCategory> getTransactionCategory(){
        //penampung list AccountCategory
        List<TransactionCategory> transactionCategories= new ArrayList<>();

        JSONParser jsonParser = new JSONParser();

        JSONObject jsonObject = jsonParser.getJSONFromUrl(HOST);
        try{
            JSONArray jsonArray = jsonObject.getJSONArray("TransactionCategory");
            for(int i = 0; i < jsonArray.length();i++){
                //get jsonarray from jsonobject
                JSONObject obj = jsonArray.getJSONObject(i);
                //get data from jsonarray into class account
                TransactionCategory transactionCategory= new TransactionCategory();
                transactionCategory.setId_tr_category(obj.getInt("id_tr_category"));
                transactionCategory.setCategory(obj.getString("category"));
                transactionCategories.add(transactionCategory);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return transactionCategories;
    }
}
