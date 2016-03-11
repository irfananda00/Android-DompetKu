package garputala.irfananda.dompetku;

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
import android.widget.EditText;
import android.widget.Toast;

//activity Editing account
public class EditAccountActivity extends AppCompatActivity{

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

        Account account = (Account) getIntent().getSerializableExtra("Account");

        final EditText edt_account_name = (EditText)findViewById(R.id.edt_account_name);
        final EditText edt_value = (EditText)findViewById(R.id.edt_value);
        btn_select_category = (Button)findViewById(R.id.btn_select_category);
        btn_select_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isInternetAvailable()) {
                    FragmentManager fragmentManager = getFragmentManager();
                    DialogListAccountCategory dialogListAccountCategory= new DialogListAccountCategory(EditAccountActivity.this);
                    dialogListAccountCategory.show(fragmentManager, "Select Category");
                }else{
                    Toast.makeText(EditAccountActivity.this, "Error retrieving data. Check your internet connection", Toast.LENGTH_LONG).show();
                }
            }
        });

        edt_account_name.setText(account.getAccount_name());
        edt_value.setText(String.valueOf(account.getValue()));
        btn_select_category.setText(account.getNama_category());

        Button btn_add = (Button)findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isInternetAvailable()) {
                    String account_name = edt_account_name.getText().toString();
                    String value = edt_value.getText().toString();
                    String category = btn_select_category.getText().toString();
                    AsyncEditAccount post = new AsyncEditAccount(EditAccountActivity.this, account_name, value, String.valueOf(id_ac_category), category);
                    post.execute();

                    DataContainer dataContainer = new DataContainer();
                    dataContainer.setAppsFirstStart(true);
                    Intent intent = new Intent(EditAccountActivity.this,MainActivity.class);
                    intent.putExtra("DataContainer", dataContainer);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(EditAccountActivity.this, "Error retrieving data. Check your internet connection", Toast.LENGTH_LONG).show();
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
        Intent intent = new Intent(EditAccountActivity.this,MainActivity.class);
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
        Intent intent = new Intent(EditAccountActivity.this,MainActivity.class);
        intent.putExtra("DataContainer", dataContainer);
        startActivity(intent);
        this.finish();
    }

    private boolean isInternetAvailable() {
        return isConnectedMobile()||isConnectedWifi()||isNetworkAvailable();
    }

    // Check if there is any connectivity for a Wifi network
    public boolean isConnectedWifi(){
        NetworkInfo info = Connectivity.getNetworkInfo(EditAccountActivity.this);

        return info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI;
    }

    // Check if there is any connectivity for a mobile network
    public boolean isConnectedMobile(){
        NetworkInfo info = Connectivity.getNetworkInfo(EditAccountActivity.this);
        return info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_MOBILE;
    }

    // Check all connectivities whether available or not
    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(EditAccountActivity.this.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        // if no network is available networkInfo will be null
        // otherwise check if we are connected
        return networkInfo != null && networkInfo.isConnected();
    }

}
