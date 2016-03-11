package garputala.irfananda.dompetku;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.ExecutionException;

//fragment account that can be implemented in mainActivity
public class FragmentAccount extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_fragment_account,container,false);

        ListView listViewAccount = (ListView) rootView.findViewById(R.id.listView);
        TextView txt_totalCash = (TextView)rootView.findViewById(R.id.txt_totalCash);

        try{
            final List<Account> accountList = new AsyncGetAccount(this.getActivity()).execute().get();
            final Account account = new AsyncGetTotalCash(this.getActivity()).execute().get();
            final AccountAdapter accountAdapter = new AccountAdapter(this,accountList);
            listViewAccount.setAdapter(accountAdapter);

            listViewAccount.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    FragmentManager fragmentManager = getFragmentManager();
                    Account account = accountList.get(position);
                    DialogMenuList dialogMenuListAccount = new DialogMenuList(rootView.getContext(),account);
                    dialogMenuListAccount.show(fragmentManager,"Menu Account");
                    return false;
                }
            });
            txt_totalCash.setText("Rp. "+account.getTotalCash());

        }catch (InterruptedException e) {
            Log.i("infoirfan","interrupted exception");
        } catch (ExecutionException e) {
            Log.i("infoirfan", "execution exception");
        }

        FloatingActionButton addAccount = (FloatingActionButton)rootView.findViewById(R.id.addAccount);
        addAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(rootView.getContext(), InputAccountActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }
}