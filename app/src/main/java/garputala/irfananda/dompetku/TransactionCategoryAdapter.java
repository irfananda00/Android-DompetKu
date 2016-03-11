package garputala.irfananda.dompetku;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class TransactionCategoryAdapter extends BaseAdapter {
    private Context context;
    private List<TransactionCategory> transactionCategories;

    public TransactionCategoryAdapter(Context context, List<TransactionCategory> transactionCategories) {
        this.context = context;
        this.transactionCategories = transactionCategories;
    }

    @Override
    public int getCount() {
        return transactionCategories.size();
    }

    @Override
    public Object getItem(int position) {
        return transactionCategories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.list_category,parent,false);

        TextView txtId=(TextView)convertView.findViewById(R.id.txtId);
        TextView txtNama=(TextView)convertView.findViewById(R.id.txtNama);

        TransactionCategory transactionCategory;
        transactionCategory = transactionCategories.get(position);

        txtId.setText(String.valueOf(transactionCategory.getId_tr_category()));
        txtNama.setText(transactionCategory.getCategory());

        return convertView;
    }
}
