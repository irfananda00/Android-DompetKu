package garputala.irfananda.dompetku;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

public class AsyncGetTransactionCategory extends AsyncTask<Void,Void,List<TransactionCategory>> {
    private Context context;
    private Dialog loadingDialog;

    public AsyncGetTransactionCategory(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        loadingDialog = ProgressDialog.show(context, "Retrieving data", "Loading...");
    }

    @Override
    protected List<TransactionCategory> doInBackground(Void... params) {
        return TransactionCategoryParser.getTransactionCategory();
    }

    @Override
    protected void onPostExecute(List<TransactionCategory> transactionCategories) {
        super.onPostExecute(transactionCategories);
        if(loadingDialog.isShowing()){
            loadingDialog.dismiss();
        }
    }
}
