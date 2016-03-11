package garputala.irfananda.dompetku;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

//dialogfragment for menu list account
public class DialogMenuList extends DialogFragment{

    private Account account;
    private Transaction transaction;
    private Context context;

    public DialogMenuList(Context context, Account account) {
        this.account = account;
        this.context = context;
    }

    public DialogMenuList(Context context, Transaction transaction) {
        this.context = context;
        this.transaction = transaction;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_dialog_menu1,null);

        TextView txt_edit = (TextView)rootView.findViewById(R.id.txt_edit);
        txt_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
                if(account!=null){
                    Intent intent = new Intent(context, EditAccountActivity.class);
                    intent.putExtra("Account", account);
                    startActivity(intent);
                }else if(transaction!=null){
                    Intent intent = new Intent(context, EditTransactionActivity.class);
                    intent.putExtra("Transaction", transaction);
                    startActivity(intent);
                }
            }
        });

        TextView txt_delete = (TextView)rootView.findViewById(R.id.txt_delete);
        txt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
                if(account!=null){
                    AsyncDeleteAccount post = new AsyncDeleteAccount(context, account.getAccount_name());
                    post.execute();
                    getDialog().dismiss();
                }else if(transaction!=null){
                    AsyncDeleteTransaction post = new AsyncDeleteTransaction(context, String.valueOf(transaction.getId_tr()), String.valueOf(transaction.getValue()), transaction.getAccount_name(), transaction.getJenis_transaksi());
                    post.execute();
                    getDialog().dismiss();
                }
            }
        });

        return rootView;
    }


}
