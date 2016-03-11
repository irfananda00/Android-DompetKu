package garputala.irfananda.dompetku;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class AsyncGetTotalTransactionCategory  extends AsyncTask<Void,Void,Transaction> {
    private Context context;
    private Dialog loadingDialog;

    public AsyncGetTotalTransactionCategory(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        loadingDialog = ProgressDialog.show(context, "Retrieving data", "Loading...");
    }
    @Override
    protected Transaction doInBackground(Void... params) {
        return TotalTransactionCategoryParser.getTotalTransactionCategory();
    }
    @Override
    protected void onPostExecute(Transaction transaction) {
        super.onPostExecute(transaction);
        if(loadingDialog.isShowing()){
            loadingDialog.dismiss();
        }
    }
}
