package garputala.irfananda.dompetku;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//get data transaction from host to transaction class
public class TransactionParser {
    private static final String HOST="http://www.dompetku.cingkleung.com/";

    public static List<Transaction> getTransaction(String tanggal_input){
        //penampung list Transaction
        List<Transaction> transactions = new ArrayList<>();

        JSONParser jsonParser = new JSONParser();

        JSONObject jsonObject = jsonParser.getJSONFromUrl(HOST+"getTransaction.php?tanggal_input="+tanggal_input);
        try{
            JSONArray jsonArray = jsonObject.getJSONArray("Transaction");
            for(int i = 0; i < jsonArray.length();i++){
                //get jsonarray from jsonobject
                JSONObject obj = jsonArray.getJSONObject(i);
                //get data from jsonarray into class transaction
                Transaction transaction = new Transaction();
                transaction.setId_tr(obj.getInt("id_tr"));
                transaction.setId_ac(obj.getInt("id_ac"));
                transaction.setAccount_name(obj.getString("nama_ac"));
                transaction.setJenis_transaksi(obj.getString("jenis_transaksi"));
                transaction.setRoutine(obj.getInt("routine"));
                transaction.setTanggal_input(obj.getString("tanggal_input"));
                transaction.setId_tr_category(obj.getInt("id_tr_category"));
                transaction.setNama_category(obj.getString("nama_category"));
                transaction.setValue(obj.getInt("value"));
                transaction.setNama(obj.getString("nama"));
                //set value of class transaction into element list transactions
                transactions.add(transaction);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return transactions;
    }
}
