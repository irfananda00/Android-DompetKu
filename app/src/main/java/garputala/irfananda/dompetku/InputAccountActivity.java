package garputala.irfananda.dompetku;

import android.app.FragmentManager;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

//activity for input account
public class InputAccountActivity extends AppCompatActivity {

    private Button btn_select_category;
    private int id_ac_category;

    public void setCategoryName(String categoryName, int id_ac_category) {
        btn_select_category= (Button)findViewById(R.id.btn_select_category);
        btn_select_category.setText(categoryName);
        this.id_ac_category = id_ac_category;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_input_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final EditText edt_account_name = (EditText)findViewById(R.id.edt_account_name);
        final EditText edt_value = (EditText)findViewById(R.id.edt_value);

        btn_select_category = (Button)findViewById(R.id.btn_select_category);
        btn_select_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInternetAvailable()) {
                    FragmentManager fragmentManager = getFragmentManager();
                    DialogListAccountCategory dialogListAccountCategory = new DialogListAccountCategory(InputAccountActivity.this);
                    dialogListAccountCategory.show(fragmentManager, "Select Category");
                } else {
                    Toast.makeText(InputAccountActivity.this, "Error retrieving data. Check your internet connection", Toast.LENGTH_LONG).show();
                }
            }
        });

        Button btn_add = (Button)findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isInternetAvailable()) {
                    String account_name = edt_account_name.getText().toString();
                    String value = edt_value.getText().toString();
                    String category = btn_select_category.getText().toString();
                    //date now
                    final Calendar myCalendar = Calendar.getInstance();
                    myCalendar.get(Calendar.YEAR);
                    myCalendar.get(Calendar.MONTH);
                    myCalendar.get(Calendar.DAY_OF_MONTH);
                    String myFormat = "yyyy-MM-dd"; //In which you need put here
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                    String tanggal_input = sdf.format(myCalendar.getTime());
                    Log.i("infoirfan", tanggal_input);
                    AsyncPostAccount post = new AsyncPostAccount(InputAccountActivity.this, account_name, value, String.valueOf(id_ac_category), category, tanggal_input);
                    post.execute();

                    DataContainer dataContainer = new DataContainer();
                    dataContainer.setAppsFirstStart(true);
                    Intent intent = new Intent(InputAccountActivity.this,MainActivity.class);
                    intent.putExtra("DataContainer", dataContainer);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(InputAccountActivity.this, "Error retrieving data. Check your internet connection", Toast.LENGTH_LONG).show();
                }
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
        Intent intent = new Intent(InputAccountActivity.this,MainActivity.class);
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
        Intent intent = new Intent(InputAccountActivity.this,MainActivity.class);
        intent.putExtra("DataContainer", dataContainer);
        startActivity(intent);
        this.finish();
    }

    private boolean isInternetAvailable() {
        return isConnectedMobile()||isConnectedWifi()||isNetworkAvailable();
    }

    // Check if there is any connectivity for a Wifi network
    public boolean isConnectedWifi(){
        NetworkInfo info = Connectivity.getNetworkInfo(InputAccountActivity.this);

        return info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI;
    }

    // Check if there is any connectivity for a mobile network
    public boolean isConnectedMobile(){
        NetworkInfo info = Connectivity.getNetworkInfo(InputAccountActivity.this);
        return info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_MOBILE;
    }

    // Check all connectivities whether available or not
    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(InputAccountActivity.this.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        // if no network is available networkInfo will be null
        // otherwise check if we are connected
        return networkInfo != null && networkInfo.isConnected();
    }
}
