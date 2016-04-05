package garputala.irfananda.dompetku;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//get data account from host to account class
public class AccountParser {
    private static final String HOST="http://www.dompetku.cingkleung.com/getAccount.php";

    public static List<Account> getAccount(){
        //penampung list Account
        List<Account> accounts= new ArrayList<>();

        JSONParser jsonParser = new JSONParser();

        JSONObject jsonObject = jsonParser.getJSONFromUrl(HOST);
        try{
            JSONArray jsonArray = jsonObject.getJSONArray("Account");
            for(int i = 0; i < jsonArray.length();i++){
                //get jsonarray from jsonobject
                JSONObject obj = jsonArray.getJSONObject(i);
                //get data from jsonarray into class account
                Account account= new Account();
                account.setId_ac(obj.getInt("id_ac"));
                account.setAccount_name(obj.getString("account_name"));
                account.setValue(obj.getInt("value"));
                account.setId_ac_category(obj.getInt("id_ac_category"));
                account.setNama_category(obj.getString("nama_category"));
                account.setTanggal_input(obj.getString("tanggal_input"));
                accounts.add(account);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return accounts;
    }

}
