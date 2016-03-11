package garputala.irfananda.dompetku;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

//background task for retrieving income
public class AsyncGetIncome extends AsyncTask<Void,Void,Income> {

    private Context context;
    private Dialog loadingDialog;
    private String year;
    private String month;
    private String day;

    public AsyncGetIncome(Context context, String year, String month, String day) {
        this.context = context;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        loadingDialog = ProgressDialog.show(context, "Retrieving data", "Loading...");
    }

    @Override
    protected Income doInBackground(Void... params) {
        return ExpenseIncomeParser.getIncome(year,month,day);
    }

    @Override
    protected void onPostExecute(Income income) {
        super.onPostExecute(income);
        if(loadingDialog.isShowing()){
            loadingDialog.dismiss();
        }
    }

}
