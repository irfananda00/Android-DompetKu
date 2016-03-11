package garputala.irfananda.dompetku;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class AccountCategoryAdapter extends BaseAdapter {
    private Context context;
    private List<AccountCategory> accountCategories;

    public AccountCategoryAdapter(Context context, List<AccountCategory> accountCategories) {
        this.context = context;
        this.accountCategories = accountCategories;
    }

    @Override
    public int getCount() {
        return accountCategories.size();
    }

    @Override
    public Object getItem(int position) {
        return accountCategories.get(position);
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

        AccountCategory accountCategory;
        accountCategory = accountCategories.get(position);

        txtId.setText(String.valueOf(accountCategory.getId_ac_category()));
        txtNama.setText(accountCategory.getCategory());

        return convertView;
    }
}
