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

//background task for updating account
public class AsyncEditAccount extends AsyncTask<Void,Void,String >{

    private static final String HOST="http://www.dompetku.cingkleung.com/updateAccount.php";
    private Dialog loadingDialog;
    private Context context;
    private String account_name;
    private String value;
    private String id_ac_category;
    private String nama_category;

    public AsyncEditAccount(Context context, String account_name, String value, String id_ac_category, String nama_category) {
        this.context = context;
        this.account_name = account_name;
        this.value = value;
        this.id_ac_category=id_ac_category;
        this.nama_category=nama_category;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        loadingDialog = ProgressDialog.show(context, "Connecting to server", "Loading...");
    }

    @Override
    protected String doInBackground(Void... params) {
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("account_name", account_name));
        nameValuePairs.add(new BasicNameValuePair("value",value));
        nameValuePairs.add(new BasicNameValuePair("id_ac_category",id_ac_category));
        nameValuePairs.add(new BasicNameValuePair("nama_category",nama_category));
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
            Toast.makeText(context, "Data Updated", Toast.LENGTH_LONG).show();
        }
    }
}
