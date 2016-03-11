package garputala.irfananda.dompetku;

import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

//activity for input transaction
public class InputTransactionActivity extends AppCompatActivity {

    private Button btn_date;
    private Button btn_select_account;
    private Button btn_select_category;
    private RadioButton radioJenisButton;
    private RadioGroup radioJenisTransaksi;
    //private Spinner spnRoutine;
    private Button btn_routine;
    private EditText edt_nama;
    private EditText edt_value;
    private int id_tr_category;
    private int id_ac;

    public void setAccountName(String accountName, int id_ac) {
        btn_select_account = (Button)findViewById(R.id.btn_select_account);
        btn_select_account.setText(accountName);
        this.id_ac=id_ac;
    }

    public void setCategoryName(String categoryName, int id_tr_category) {
        btn_select_category= (Button)findViewById(R.id.btn_select_category);
        btn_select_category.setText(categoryName);
        this.id_tr_category = id_tr_category;
    }

    public void setRoutineName(String routineName) {
        btn_routine= (Button)findViewById(R.id.btn_routine);
        btn_routine.setText(routineName);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_input_transaction);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        radioJenisTransaksi = (RadioGroup)findViewById(R.id.radioJenisTransaksi);
        //spnRoutine = (Spinner)findViewById(R.id.spnRoutine);
        edt_nama= (EditText)findViewById(R.id.edt_nama);
        edt_value= (EditText)findViewById(R.id.edt_value);

        Button addData = (Button) findViewById(R.id.btn_add);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isInternetAvailable()) {
                    String account_name = btn_select_account.getText().toString();
                    String category_name = btn_select_category.getText().toString();
                    int selectedJK = radioJenisTransaksi.getCheckedRadioButtonId();
                    radioJenisButton = (RadioButton) findViewById(selectedJK);
                    String jenis_transaksi = radioJenisButton.getText().toString();
                    String routine = btn_routine.getText().toString();
                    switch (routine) {
                        case "Daily":
                            routine = "1";
                            break;
                        case "Weekly":
                            routine = "7";
                            break;
                        case "Monthly":
                            routine = "30";
                            break;
                        case "Every Year":
                            routine = "365";
                            break;
                        case "Once":
                            routine = "0";
                            break;
                    }
                    String tanggal_input = btn_date.getText().toString();
                    String nama = edt_nama.getText().toString();
                    String value = edt_value.getText().toString();
                    AsyncPostTransaction post = new AsyncPostTransaction(InputTransactionActivity.this, String.valueOf(id_ac), account_name, jenis_transaksi, routine, tanggal_input, String.valueOf(id_tr_category), category_name, value, nama);
                    post.execute();

                    DataContainer dataContainer = new DataContainer();
                    dataContainer.setAppsFirstStart(true);
                    Intent intent = new Intent(InputTransactionActivity.this,MainActivity.class);
                    intent.putExtra("DataContainer", dataContainer);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(InputTransactionActivity.this, "Error retrieving data. Check your internet connection", Toast.LENGTH_LONG).show();
                }
            }
        });

        btn_select_category = (Button)findViewById(R.id.btn_select_category);
        btn_select_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isInternetAvailable()) {
                    FragmentManager fragmentManager = getFragmentManager();
                    DialogListTransactionCategory dialogListTransactionCategory= new DialogListTransactionCategory(InputTransactionActivity.this);
                    dialogListTransactionCategory.show(fragmentManager, "Select Category");
                }else{
                    Toast.makeText(InputTransactionActivity.this, "Error retrieving data. Check your internet connection", Toast.LENGTH_LONG).show();
                }
            }
        });

        btn_select_account = (Button)findViewById(R.id.btn_select_account);
        btn_select_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isInternetAvailable()) {
                    FragmentManager fragmentManager = getFragmentManager();
                    DialogListAccount dialogListAccount = new DialogListAccount(InputTransactionActivity.this);
                    dialogListAccount.show(fragmentManager, "Select Account");
                }else{
                    Toast.makeText(InputTransactionActivity.this, "Error retrieving data. Check your internet connection", Toast.LENGTH_LONG).show();
                }
            }
        });

        btn_routine = (Button)findViewById(R.id.btn_routine);
        btn_routine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                DialogListRoutine dialogListRoutine= new DialogListRoutine(InputTransactionActivity.this);
                dialogListRoutine.show(fragmentManager, "Select Iteration");
            }
        });

        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(myCalendar);
            }

        };


        btn_date = (Button)findViewById(R.id.btn_date);
        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(InputTransactionActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.close, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        DataContainer dataContainer = new DataContainer();
        dataContainer.setAppsFirstStart(true);
        Intent intent = new Intent(InputTransactionActivity.this,MainActivity.class);
        intent.putExtra("DataContainer", dataContainer);
        startActivity(intent);
        this.finish();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        DataContainer dataContainer = new DataContainer();
        dataContainer.setAppsFirstStart(true);
        Intent intent = new Intent(InputTransactionActivity.this,MainActivity.class);
        intent.putExtra("DataContainer", dataContainer);
        startActivity(intent);
        this.finish();
    }

    private void updateLabel(Calendar myCalendar) {

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        btn_date.setText(sdf.format(myCalendar.getTime()));
    }

    private boolean isInternetAvailable() {
        return isConnectedMobile()||isConnectedWifi()||isNetworkAvailable();
    }

    // Check if there is any connectivity for a Wifi network
    public boolean isConnectedWifi(){
        NetworkInfo info = Connectivity.getNetworkInfo(InputTransactionActivity.this);

        return info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI;
    }

    // Check if there is any connectivity for a mobile network
    public boolean isConnectedMobile(){
        NetworkInfo info = Connectivity.getNetworkInfo(InputTransactionActivity.this);
        return info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_MOBILE;
    }

    // Check all connectivities whether available or not
    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(InputTransactionActivity.this.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        // if no network is available networkInfo will be null
        // otherwise check if we are connected
        return networkInfo != null && networkInfo.isConnected();
    }
}
