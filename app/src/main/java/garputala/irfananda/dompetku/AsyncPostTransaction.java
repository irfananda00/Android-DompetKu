package garputala.irfananda.dompetku;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
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

//background task for inserting transaction
public class AsyncPostTransaction extends AsyncTask<Void, Void, String> {
    private static final String HOST="http://garputala-chalange.site88.net/setTransaction.php";
    private Dialog loadingDialog;
    private Context context;
    private String id_ac;
    private String account_name;
    private String jenis_transaksi;
    private String routine;
    private String tanggal_input;
    private String id_tr_category;
    private String nama_category;
    private String value;
    private String nama;

    public AsyncPostTransaction(AppCompatActivity appCompatActivity, String id_ac,String account_name,String jenis_transaksi, String routine, String tanggal_input, String id_tr_category, String nama_category, String value, String nama) {
        this.context = appCompatActivity;
        this.id_ac=id_ac;
        this.account_name=account_name;
        this.jenis_transaksi=jenis_transaksi;
        this.routine=routine;
        this.tanggal_input=tanggal_input;
        this.id_tr_category=id_tr_category;
        this.nama_category=nama_category;
        this.value=value;
        this.nama=nama;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        loadingDialog = ProgressDialog.show(context, "Uploading to server", "Loading...");
    }

    @Override
    protected String doInBackground(Void... params) {
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("id_ac", id_ac));
        nameValuePairs.add(new BasicNameValuePair("nama_ac", account_name));
        nameValuePairs.add(new BasicNameValuePair("jenis_transaksi", jenis_transaksi));
        nameValuePairs.add(new BasicNameValuePair("routine", routine));
        nameValuePairs.add(new BasicNameValuePair("tanggal_input", tanggal_input));
        nameValuePairs.add(new BasicNameValuePair("id_tr_category", id_tr_category));
        nameValuePairs.add(new BasicNameValuePair("nama_category", nama_category));
        nameValuePairs.add(new BasicNameValuePair("value",value));
        nameValuePairs.add(new BasicNameValuePair("nama",nama));
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
        if(loadingDialog.isShowing()){
            loadingDialog.dismiss();
        }
        if(s.equalsIgnoreCase("success")){
            Toast.makeText(context, "Data Upload Success", Toast.LENGTH_LONG).show();
        }
    }
}
