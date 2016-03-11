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

public class DialogListAccountCategory extends DialogFragment {

    private InputAccountActivity inputAccountActivity;
    private EditAccountActivity editAccountActivity;

    public DialogListAccountCategory(InputAccountActivity inputAccountActivity) {
        this.inputAccountActivity = inputAccountActivity;
    }

    public DialogListAccountCategory(EditAccountActivity editAccountActivity) {
        this.editAccountActivity = editAccountActivity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle("Select Category");
        View rootView = inflater.inflate(R.layout.activity_dialog_list,null);

        try{
            final List<AccountCategory> accountCategories= new AsyncGetAccountCategory(rootView.getContext()).execute().get();
            List<String> categoryName = new ArrayList<>();
            for(AccountCategory accountCategory:accountCategories){
                categoryName.add(accountCategory.getCategory());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(rootView.getContext(),android.R.layout.simple_list_item_1,categoryName);
            final ListView listViewAccount = (ListView)rootView.findViewById(R.id.listView);
            listViewAccount.setAdapter(adapter);

            listViewAccount.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    AccountCategory accountCategory= accountCategories.get(position);
                    if(inputAccountActivity!=null)
                        ((InputAccountActivity) getActivity()).setCategoryName(accountCategory.getCategory(), accountCategory.getId_ac_category());
                    else if(editAccountActivity!=null)
                        ((EditAccountActivity) getActivity()).setCategoryName(accountCategory.getCategory(), accountCategory.getId_ac_category());
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
