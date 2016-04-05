package garputala.irfananda.dompetku;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TotalTransactionCategoryParser {
    private static final String HOST="http://www.dompetku.cingkleung.com/getTotalTransactionCategory.php";

    public static Transaction getTotalTransactionCategory(){
        //penampung Account
        Transaction transaction= new Transaction();

        JSONParser jsonParser = new JSONParser();

        JSONObject jsonObject = jsonParser.getJSONFromUrl(HOST);
        try{
            JSONArray jsonArray = jsonObject.getJSONArray("CategoryTransaction");
            //get jsonarray from jsonobject
            JSONObject obj = jsonArray.getJSONObject(0);
            //get data from jsonarray into class account
            transaction.setTransport(obj.getInt("Transport"));
            transaction.setEating(obj.getInt("Eating Out"));
            transaction.setEducation(obj.getInt("Education"));
            transaction.setGroceries(obj.getInt("Groceries"));
            transaction.setInsurance(obj.getInt("Insurance"));
            transaction.setMedical(obj.getInt("Medical"));
            transaction.setUtilities(obj.getInt("Utilities"));
            transaction.setSalary(obj.getInt("Salary"));
            transaction.setCash(obj.getInt("Cash"));
            transaction.setOthers(obj.getInt("Others"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return transaction;
    }
}
