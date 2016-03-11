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

public class DialogListTransactionCategory extends DialogFragment {

    private InputTransactionActivity inputTransactionActivity;
    private EditTransactionActivity editTransactionActivity;

    public DialogListTransactionCategory(InputTransactionActivity inputTransactionActivity) {
        this.inputTransactionActivity = inputTransactionActivity;
    }

    public DialogListTransactionCategory(EditTransactionActivity editTransactionActivity) {
        this.editTransactionActivity = editTransactionActivity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle("Select Category");
        View rootView = inflater.inflate(R.layout.activity_dialog_list,null);

        try{
            final List<TransactionCategory> transactionCategories= new AsyncGetTransactionCategory(rootView.getContext()).execute().get();
            List<String> categoryName = new ArrayList<>();
            for(TransactionCategory transactionCategory:transactionCategories){
                categoryName.add(transactionCategory.getCategory());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(rootView.getContext(),android.R.layout.simple_list_item_1,categoryName);
            final ListView listViewAccount = (ListView)rootView.findViewById(R.id.listView);
            listViewAccount.setAdapter(adapter);

            listViewAccount.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TransactionCategory transactionCategory= transactionCategories.get(position);
                    if(inputTransactionActivity!=null)
                        ((InputTransactionActivity) getActivity()).setCategoryName(transactionCategory.getCategory(), transactionCategory.getId_tr_category());
                    else if(editTransactionActivity!=null)
                        ((EditTransactionActivity) getActivity()).setCategoryName(transactionCategory.getCategory(), transactionCategory.getId_tr_category());
                    getDialog().dismiss();
                }
            });

        }catch (InterruptedException e) {
            Log.i("infoirfan", "interrupted exception");
        } catch (ExecutionException e) {
            Log.i("infoirfan", "execution exception");
        }

        return rootView;
    }
}
