package garputala.irfananda.dompetku;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class NavHeaderAdapter extends BaseAdapter{

    private Context context;
    private Expense expense;
    private Income income;

    public NavHeaderAdapter(FragmentOverview context, Expense expense, Income income) {
        this.context = context.getActivity();
        this.expense = expense;
        this.income = income;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
