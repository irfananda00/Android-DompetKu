package garputala.irfananda.dompetku;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button btn_category_account = (Button) findViewById(R.id.btn_category_account);
        btn_category_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInternetAvailable()) {
                    Intent intent = new Intent(SettingsActivity.this, ListCategoryActivity.class);
                    intent.putExtra("Category", "account");
                    startActivity(intent);
                }
            }
        });

        Button btn_category_transaction = (Button) findViewById(R.id.btn_category_transaction);
        btn_category_transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isInternetAvailable()){
                    Intent intent = new Intent(SettingsActivity.this,ListCategoryActivity.class);
                    intent.putExtra("Category","transaction");
                    startActivity(intent);
                }
            }
        });

        Button btn_clear_data = (Button) findViewById(R.id.btn_clear_data);
        btn_clear_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                if(isInternetAvailable()){
                                    AsyncDeleteAllData post = new AsyncDeleteAllData(SettingsActivity.this);
                                    post.execute();
                                }
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:

                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                builder.setMessage("Are you sure? This will delete all accounts and transactions data").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
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
        Intent intent = new Intent(SettingsActivity.this,MainActivity.class);
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
        Intent intent = new Intent(SettingsActivity.this,MainActivity.class);
        intent.putExtra("DataContainer", dataContainer);
        startActivity(intent);
        this.finish();
    }

    private boolean isInternetAvailable() {
        return isConnectedMobile()||isConnectedWifi()||isNetworkAvailable();
    }

    // Check if there is any connectivity for a Wifi network
    public boolean isConnectedWifi(){
        NetworkInfo info = Connectivity.getNetworkInfo(SettingsActivity.this);

        return info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI;
    }

    // Check if there is any connectivity for a mobile network
    public boolean isConnectedMobile(){
        NetworkInfo info = Connectivity.getNetworkInfo(SettingsActivity.this);
        return info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_MOBILE;
    }

    // Check all connectivities whether available or not
    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(SettingsActivity.this.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        // if no network is available networkInfo will be null
        // otherwise check if we are connected
        return networkInfo != null && networkInfo.isConnected();
    }

}
