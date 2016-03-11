package garputala.irfananda.dompetku;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TotalAccountCategoryParser {
    private static final String HOST="http://garputala-chalange.site88.net/getTotalAccountCategory.php";

    public static Account getTotalAccountCategory(){
        //penampung Account
        Account account = new Account();

        JSONParser jsonParser = new JSONParser();

        JSONObject jsonObject = jsonParser.getJSONFromUrl(HOST);
        try{
            JSONArray jsonArray = jsonObject.getJSONArray("CategoryAccount");
            //get jsonarray from jsonobject
            JSONObject obj = jsonArray.getJSONObject(0);
            //get data from jsonarray into class account
            account.setCash(obj.getInt("Cash"));
            account.setDebit(obj.getInt("Debit Card"));
            account.setSavings(obj.getInt("Savings"));
            account.setCredit(obj.getInt("Credit Card"));
            account.setOthers(obj.getInt("Others"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return account;
    }
}
