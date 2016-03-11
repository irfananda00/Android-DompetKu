package garputala.irfananda.dompetku;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

//background task for deleting transaction
public class AsyncDeleteTransaction extends AsyncTask<Void,Void,String > {

    private static final String HOST="http://garputala-chalange.site88.net/deleteTransaction.php";
    private Dialog loadingDialog;
    private Context context;
    private String id_tr;
    private String value;
    private String id_ac;
    private String jenis_transaksi;

    public AsyncDeleteTransaction(Context context, String id_tr, String value, String id_ac, String jenis_transaksi) {
        this.context = context;
        this.id_tr = id_tr;
        this.value = value;
        this.id_ac = id_ac;
        this.jenis_transaksi = jenis_transaksi;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        loadingDialog = ProgressDialog.show(context, "Connecting to server", "Loading...");
    }

    @Override
    protected String doInBackground(Void... params) {
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("id_tr", id_tr));
        nameValuePairs.add(new BasicNameValuePair("value", value));
        nameValuePairs.add(new BasicNameValuePair("id_ac", id_ac));
        nameValuePairs.add(new BasicNameValuePair("jenis_transaksi", jenis_transaksi));
        InputStream is ;
        String result = null;
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(HOST);
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity entity = httpResponse.getEntity();
            is = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"),8);
            StringBuilder sb = new StringBuilder();
            String line;
            while((line = reader.readLine())!=null){
                sb.append(line).append("\n");
            }
            result = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        String s = result.trim();
        if(loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
        if(s.equals("success")) {
            Toast.makeText(context, "Data Deleted", Toast.LENGTH_LONG).show();
        }
    }
}
