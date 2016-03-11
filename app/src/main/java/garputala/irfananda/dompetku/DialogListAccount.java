package garputala.irfananda.dompetku;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

//dialogfragment for show list account
public class DialogListAccount extends DialogFragment{

    private InputTransactionActivity inputTransactionActivity;
    private EditTransactionActivity editTransactionActivity;

    public DialogListAccount(InputTransactionActivity inputTransactionActivity) {
        this.inputTransactionActivity = inputTransactionActivity;
    }

    public DialogListAccount(EditTransactionActivity editTransactionActivity) {
        this.editTransactionActivity = editTransactionActivity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle("Select Account");
        View rootView = inflater.inflate(R.layout.activity_dialog_list,null);

        try{
            final List<Account> accountList = new AsyncGetAccount(rootView.getContext()).execute().get();
            List<String> accountName = new ArrayList<>();
            for(Account account:accountList){
                accountName.add(account.getAccount_name());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(rootView.getContext(),android.R.layout.simple_list_item_1,accountName);
            final ListView listViewAccount = (ListView)rootView.findViewById(R.id.listView);
            listViewAccount.setAdapter(adapter);

            listViewAccount.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Account account = accountList.get(position);
                    if(inputTransactionActivity!=null)
                        ((InputTransactionActivity) getActivity()).setAccountName(account.getAccount_name(),account.getId_ac());
                    else if(editTransactionActivity!=null)
                        ((EditTransactionActivity) getActivity()).setAccountName(account.getAccount_name(), account.getId_ac());
                    getDialog().dismiss();
                }
            });

        }catch (InterruptedException e) {
            Log.i("infoirfan","interrupted exception");
        } catch (ExecutionException e) {
            Log.i("infoirfan", "execution exception");
        }

        return rootView;
    }
}
