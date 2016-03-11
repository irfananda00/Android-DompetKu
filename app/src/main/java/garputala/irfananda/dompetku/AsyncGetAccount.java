package garputala.irfananda.dompetku;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

//background task for retrieving account
public class AsyncGetAccount extends AsyncTask<Void,Void,List<Account>>{

    private Context context;
    private Dialog loadingDialog;

    public AsyncGetAccount(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        loadingDialog = ProgressDialog.show(context, "Retrieving data", "Loading...");
    }

    @Override
    protected List<Account> doInBackground(Void... params) {
        return AccountParser.getAccount();
    }

    @Override
    protected void onPostExecute(List<Account> accounts) {
        super.onPostExecute(accounts);
        if(loadingDialog.isShowing()){
            loadingDialog.dismiss();
        }
    }
}
