package garputala.irfananda.dompetku;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

//set data account to list_account
public class AccountAdapter extends BaseAdapter{
    private Context context;
    private List<Account> accounts;

    public AccountAdapter(FragmentAccount context, List<Account> accounts) {
        this.context = context.getActivity();
        this.accounts = accounts;
    }

    @Override
    public int getCount() {
        return accounts.size();
    }

    @Override
    public Object getItem(int position) {
        return accounts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.list_account,parent,false);

        ImageView img_icon = (ImageView)convertView.findViewById(R.id.img_icon);
        TextView txt_account_name=(TextView)convertView.findViewById(R.id.txt_account_name);
        TextView txt_account_category=(TextView)convertView.findViewById(R.id.txt_account_category);
        TextView txtValue=(TextView)convertView.findViewById(R.id.txtValue);

        Account account;
        account = accounts.get(position);

        txt_account_name.setText(account.getAccount_name());
        txt_account_category.setText(account.getNama_category());
        txtValue.setText("Rp. "+account.getValue());

        if(account.getNama_category().equals("Cash"))
            img_icon.setImageDrawable(convertView.getResources().getDrawable(R.drawable.coins4));
        else if(account.getNama_category().equals("Debit Card"))
            img_icon.setImageDrawable(convertView.getResources().getDrawable(R.drawable.credit69));
        else if(account.getNama_category().equals("Savings"))
            img_icon.setImageDrawable(convertView.getResources().getDrawable(R.drawable.tool9));
        else if(account.getNama_category().equals("Credit Card"))
            img_icon.setImageDrawable(convertView.getResources().getDrawable(R.drawable.payment3));
        else
            img_icon.setImageDrawable(convertView.getResources().getDrawable(R.drawable.dollar135));

        return convertView;
    }

}

