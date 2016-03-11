package garputala.irfananda.dompetku;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

//background task for retrieving transaction
public class AsyncGetTransaction extends AsyncTask<String,Void,List<Transaction> > {

    private Context context;
    private Dialog loadingDialog;

    public AsyncGetTransaction(Activity context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        loadingDialog = ProgressDialog.show(context, "Retrieving data", "Loading...");
    }

    @Override
    protected List<Transaction> doInBackground(String... params) {
        return TransactionParser.getTransaction(params[0]);
    }

    @Override
    protected void onPostExecute(List<Transaction> transactions) {
        super.onPostExecute(transactions);
        if(loadingDialog.isShowing()){
            loadingDialog.dismiss();
        }
    }

}
