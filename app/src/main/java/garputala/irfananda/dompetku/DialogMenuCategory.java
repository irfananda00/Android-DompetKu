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

public class DialogMenuCategory extends DialogFragment {
    private AccountCategory accountCategory;
    private TransactionCategory transactionCategory;
    private Context context;

    public DialogMenuCategory(Context context, AccountCategory accountCategory) {
        this.context = context;
        this.accountCategory = accountCategory;
    }

    public DialogMenuCategory(Context context, TransactionCategory transactionCategory) {
        this.context = context;
        this.transactionCategory = transactionCategory;
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
        final View rootView = inflater.inflate(R.layout.activity_dialog_menu2,null);

        TextView txt_delete = (TextView)rootView.findViewById(R.id.txt_delete);
        txt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
                if (accountCategory!=null){
                    AsyncDeleteAccountCategory post = new AsyncDeleteAccountCategory(context,String.valueOf(accountCategory.getId_ac_category()));
                    post.execute();
                    Intent intent = new Intent(context,SettingsActivity.class);
                    startActivity(intent);
                }else if (transactionCategory!=null){
                    AsyncDeleteTransactionCategory post = new AsyncDeleteTransactionCategory(context,String.valueOf(transactionCategory.getId_tr_category()));
                    post.execute();
                    Intent intent = new Intent(context,SettingsActivity.class);
                    startActivity(intent);
                }
            }
        });

        return rootView;
    }


}
