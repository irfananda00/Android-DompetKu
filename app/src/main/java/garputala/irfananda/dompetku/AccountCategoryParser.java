package garputala.irfananda.dompetku;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AccountCategoryParser {
    private static final String HOST="http://garputala-chalange.site88.net/getAccountCategory.php";

    public static List<AccountCategory> getAccountCategory(){
        //penampung list AccountCategory
        List<AccountCategory> accountCategories= new ArrayList<>();

        JSONParser jsonParser = new JSONParser();

        JSONObject jsonObject = jsonParser.getJSONFromUrl(HOST);
        try{
            JSONArray jsonArray = jsonObject.getJSONArray("AccountCategory");
            for(int i = 0; i < jsonArray.length();i++){
                //get jsonarray from jsonobject
                JSONObject obj = jsonArray.getJSONObject(i);
                //get data from jsonarray into class account
                AccountCategory accountCategory= new AccountCategory();
                accountCategory.setId_ac_category(obj.getInt("id_ac_category"));
                accountCategory.setCategory(obj.getString("category"));
                accountCategories.add(accountCategory);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return accountCategories;
    }

}
