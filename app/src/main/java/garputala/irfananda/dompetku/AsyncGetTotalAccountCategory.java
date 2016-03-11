package garputala.irfananda.dompetku;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class AsyncGetTotalAccountCategory extends AsyncTask<Void,Void,Account> {
    private Context context;
    private Dialog loadingDialog;

    public AsyncGetTotalAccountCategory(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        loadingDialog = ProgressDialog.show(context, "Retrieving data", "Loading...");
    }

    @Override
    protected Account doInBackground(Void... params) {
        return TotalAccountCategoryParser.getTotalAccountCategory();
    }

    @Override
    protected void onPostExecute(Account accounts) {
        super.onPostExecute(accounts);
        if(loadingDialog.isShowing()){
            loadingDialog.dismiss();
        }
    }
}
