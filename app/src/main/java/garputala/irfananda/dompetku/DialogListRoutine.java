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

public class DialogListRoutine extends DialogFragment {

    private InputTransactionActivity inputTransactionActivity;
    private EditTransactionActivity editTransactionActivity;

    public DialogListRoutine(InputTransactionActivity inputTransactionActivity) {
        this.inputTransactionActivity = inputTransactionActivity;
    }

    public DialogListRoutine(EditTransactionActivity editTransactionActivity) {
        this.editTransactionActivity = editTransactionActivity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle("Select Iteration");
        View rootView = inflater.inflate(R.layout.activity_dialog_list, null);

        final List<String> routineName = new ArrayList<>();
        routineName.add(0,"Once");
        routineName.add(1,"Daily");
        routineName.add(2,"Weekly");
        routineName.add(3,"Monthly");
        routineName.add(4,"Every Year");

            ArrayAdapter<String> adapter = new ArrayAdapter<>(rootView.getContext(),android.R.layout.simple_list_item_1,routineName);
            final ListView listViewAccount = (ListView)rootView.findViewById(R.id.listView);
            listViewAccount.setAdapter(adapter);

            listViewAccount.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String s= routineName.get(position);
                    if(inputTransactionActivity!=null)
                        ((InputTransactionActivity) getActivity()).setRoutineName(s);
                    else if(editTransactionActivity!=null)
                        ((EditTransactionActivity) getActivity()).setRoutineName(s);
                    getDialog().dismiss();
                }
            });

        return rootView;
    }
}
