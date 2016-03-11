package garputala.irfananda.dompetku;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

//background task for retrieving expense
public class AsyncGetExpense extends AsyncTask<Void,Void,Expense> {
    private Context context;
    private Dialog loadingDialog;
    private String year;
    private String month;
    private String day;

    public AsyncGetExpense(Context context, String year, String month, String day) {
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
    protected Expense doInBackground(Void... params) {
        return ExpenseIncomeParser.getExpense(year,month,day);
    }

    @Override
    protected void onPostExecute(Expense expense) {
        super.onPostExecute(expense);
        if(loadingDialog.isShowing()){
            loadingDialog.dismiss();
        }
    }
}
