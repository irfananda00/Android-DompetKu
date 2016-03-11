package garputala.irfananda.dompetku;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TotalCashParser {
    private static final String HOST="http://garputala-chalange.site88.net/totalCash.php";

    public static Account getTotalCash(){
        //penampung Account
        Account account = new Account();

        JSONParser jsonParser = new JSONParser();

        JSONObject jsonObject = jsonParser.getJSONFromUrl(HOST);
        try{
            JSONArray jsonArray = jsonObject.getJSONArray("TotalCash");
                //get jsonarray from jsonobject
                JSONObject obj = jsonArray.getJSONObject(0);
                //get data from jsonarray into class account
                account.setTotalCash(obj.getInt("sum"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return account;
    }
}
