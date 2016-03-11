package garputala.irfananda.dompetku;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

//set data transaction to list_transaction
public class TransactionAdapter extends BaseAdapter{

    private Context context;
    private List<Transaction> transactions;

    public TransactionAdapter(FragmentOverview context, List<Transaction> transactions) {
        this.context = context.getActivity();
        this.transactions = transactions;
    }

    @Override
    public int getCount() {
        return transactions.size();
    }

    @Override
    public Object getItem(int position) {
        return transactions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.list_transaction,parent,false);

        ImageView img_icon = (ImageView)convertView.findViewById(R.id.img_icon);
        TextView txt_transaction_name=(TextView)convertView.findViewById(R.id.txt_transaction_name);
        TextView txt_account_name  = (TextView)convertView.findViewById(R.id.txt_account_name);
        TextView txt_value= (TextView)convertView.findViewById(R.id.txt_value);

        Transaction transaction = transactions.get(position);

        txt_transaction_name.setText(transaction.getNama());
        txt_account_name.setText(transaction.getAccount_name());
        txt_value.setText("Rp. "+transaction.getValue());

        if(transaction.getJenis_transaksi().equals("Expense"))
            txt_value.setTextColor(convertView.getResources().getColorStateList(R.color.colorExpensevalue));
        else if(transaction.getJenis_transaksi().equals("Income"))
            txt_value.setTextColor(convertView.getResources().getColorStateList(R.color.colorIncomevalue));

        if(transaction.getNama_category().equals("Transport"))
            img_icon.setImageDrawable(convertView.getResources().getDrawable(R.drawable.taxi14));
        else if(transaction.getNama_category().equals("Eating Out"))
            img_icon.setImageDrawable(convertView.getResources().getDrawable(R.drawable.plate24));
        else if(transaction.getNama_category().equals("Education"))
            img_icon.setImageDrawable(convertView.getResources().getDrawable(R.drawable.graduate6));
        else if(transaction.getNama_category().equals("Groceries"))
            img_icon.setImageDrawable(convertView.getResources().getDrawable(R.drawable.cart10));
        else if(transaction.getNama_category().equals("Insurance"))
            img_icon.setImageDrawable(convertView.getResources().getDrawable(R.drawable.family11));
        else if(transaction.getNama_category().equals("Medical"))
            img_icon.setImageDrawable(convertView.getResources().getDrawable(R.drawable.surgeon1));
        else if(transaction.getNama_category().equals("Utilities"))
            img_icon.setImageDrawable(convertView.getResources().getDrawable(R.drawable.security50));
        else if(transaction.getNama_category().equals("Salary"))
            img_icon.setImageDrawable(convertView.getResources().getDrawable(R.drawable.dollars27));
        else if(transaction.getNama_category().equals("Cash"))
            img_icon.setImageDrawable(convertView.getResources().getDrawable(R.drawable.dollar135));
        else
            img_icon.setImageDrawable(convertView.getResources().getDrawable(R.drawable.cart10));

        return convertView;
    }
}
