package garputala.irfananda.dompetku;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

public class AsyncGetAccountCategory extends AsyncTask<Void,Void,List<AccountCategory>> {
    private Context context;
    private Dialog loadingDialog;

    public AsyncGetAccountCategory(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        loadingDialog = ProgressDialog.show(context, "Retrieving data", "Loading...");
    }

    @Override
    protected List<AccountCategory> doInBackground(Void... params) {
        return AccountCategoryParser.getAccountCategory();
    }

    @Override
    protected void onPostExecute(List<AccountCategory> accountCategories) {
        super.onPostExecute(accountCategories);
        if(loadingDialog.isShowing()){
            loadingDialog.dismiss();
        }
    }
}
