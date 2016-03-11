package garputala.irfananda.dompetku;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.Nullable;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

//fragment overview that can be implemented in mainActivity
public class FragmentOverview extends Fragment{

    private Button btn_date;
    private ListView listViewTransaction;
    private List<Transaction> transactionList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_fragment_overview, container, false);

        btn_date = (Button) rootView.findViewById(R.id.btn_date);

        //date now
        final Calendar myCalendar = Calendar.getInstance();
        myCalendar.get(Calendar.YEAR);
        myCalendar.get(Calendar.MONTH);
        myCalendar.get(Calendar.DAY_OF_MONTH);
        updateLabel(myCalendar,rootView);

        listViewTransaction = (ListView) rootView.findViewById(R.id.listTransaction);
        listViewTransaction.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fragmentManager = getFragmentManager();
                Transaction transaction = transactionList.get(position);
                DialogMenuList dialogMenuList = new DialogMenuList(rootView.getContext(), transaction);
                dialogMenuList.show(fragmentManager, "Menu Transaction");
                return false;
                }
            });

        FloatingActionButton addTransaction = (FloatingActionButton)rootView.findViewById(R.id.addTransaction);
        addTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(rootView.getContext(), InputTransactionActivity.class);
                startActivity(intent);
            }
        });

        //datepicker
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(myCalendar,rootView);
            }

        };

        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(rootView.getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        return rootView;
    }

    private List<Transaction> getTransactionList(String tanggal_input) {
        try {
            return new AsyncGetTransaction(this.getActivity()).execute(tanggal_input).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void updateLabel(Calendar myCalendar, View rootView) {

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        btn_date = (Button) rootView.findViewById(R.id.btn_date);

        btn_date.setText(sdf.format(myCalendar.getTime()));

        String tanggal_input = btn_date.getText().toString();

        transactionList = getTransactionList(tanggal_input);

        final TransactionAdapter transactionAdapter = new TransactionAdapter(this,transactionList);
        listViewTransaction = (ListView) rootView.findViewById(R.id.listTransaction);
        listViewTransaction.setAdapter(transactionAdapter);
    }

}
