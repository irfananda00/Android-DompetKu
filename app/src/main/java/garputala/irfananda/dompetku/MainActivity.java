package garputala.irfananda.dompetku;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Expense expense;
    private Income income;
    private View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("infoirfan", "create");

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("infoirfan", "start");
        if(isInternetAvailable()){
            Fragment fragment = new FragmentOverview();
            String StringFragment = "Transactions";

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            rootView = getLayoutInflater().inflate(R.layout.nav_header_main, navigationView, false);
            DataContainer dataContainer= (DataContainer) getIntent().getSerializableExtra("DataContainer");
            Log.i("infoirfan","app = "+dataContainer.isAppsFirstStart());
            if(dataContainer.isAppsFirstStart()){
                navigationView.addHeaderView(rootView);
                dataContainer.setAppsFirstStart(false);
            }
            changeFragment(fragment, StringFragment, rootView);
        }else{
            Toast.makeText(this,"Error retrieving data. Check your internet connection",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this,SettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        String StringFragment = null;

        if (id == R.id.nav_overview) {
            fragment = new FragmentOverview();
            StringFragment = "Transactions";
        } else if (id == R.id.nav_accounts) {
            fragment = new FragmentAccount();
            StringFragment = "Accounts";
        } else if (id == R.id.nav_about) {
            fragment = new FragmentAbout();
            StringFragment = "About";
        } else if (id == R.id.nav_reports) {
            fragment = new FragmentReports();
            StringFragment = "Reports";
        }

        if(isInternetAvailable()) {
            changeFragment(fragment,StringFragment, rootView);
        }else{
            Toast.makeText(this,"Error retrieving data. Check your internet connection",Toast.LENGTH_LONG).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void changeFragment(Fragment fragment, String StringFragment, View rootView){
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainFrame, fragment);
            ft.commit();
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            setHeaderNavigation(StringFragment,rootView);
        }
    }

    private void setHeaderNavigation(String StringFragment, View rootView) {

        TextView txt_title = (TextView) rootView.findViewById(R.id.txt_title);
        TextView txt_1 = (TextView)rootView.findViewById(R.id.txt_1);
        TextView txt_2 = (TextView)rootView.findViewById(R.id.txt_2);
        TextView txt_3 = (TextView)rootView.findViewById(R.id.txt_3);
        TextView txt_4 = (TextView)rootView.findViewById(R.id.txt_4);
        TextView txt_5 = (TextView)rootView.findViewById(R.id.txt_5);
        ImageView img_about = (ImageView)rootView.findViewById(R.id.img_about);
        ImageView img_cornerright = (ImageView)rootView.findViewById(R.id.img_cornerright);
        ImageView img_cornerleft = (ImageView)rootView.findViewById(R.id.img_cornerleft);
        switch (StringFragment) {
            case "Transactions": {
                txt_title.setText("Today");
                img_cornerleft.setImageDrawable(getResources().getDrawable(R.drawable.leftcorner));
                img_cornerright.setImageDrawable(getResources().getDrawable(R.drawable.rightcorner));
                img_about.setImageDrawable(null);
                txt_1.setText(null);
                txt_2.setText(null);
                txt_5.setText(null);
                //date now
                final Calendar myCalendar = Calendar.getInstance();
                int year = myCalendar.get(Calendar.YEAR);
                int month = myCalendar.get(Calendar.MONTH);
                int day = myCalendar.get(Calendar.DAY_OF_MONTH);
                if (isInternetAvailable()) {
                    expense = getExpense(year, month + 1, day);
                    income = getIncome(year, month + 1, day);
                } else {
                    Toast.makeText(this, "Error retrieving data. Check your internet connection", Toast.LENGTH_LONG).show();
                }
                txt_3.setText("Expense : Rp. " + expense.getSum());
                txt_4.setText("Income   : Rp. " + income.getSum());
                break;
            }
            case "Accounts":
                txt_title.setText("Total Category");
                img_cornerleft.setImageDrawable(getResources().getDrawable(R.drawable.leftcorner));
                img_cornerright.setImageDrawable(getResources().getDrawable(R.drawable.rightcorner));
                img_about.setImageDrawable(null);
                Account account = new Account();
                if (isInternetAvailable()) {
                    try {
                        account = new AsyncGetTotalAccountCategory(this).execute().get();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(this, "Error retrieving data. Check your internet connection", Toast.LENGTH_LONG).show();
                }
                txt_1.setText("Cash : " + account.getCash());
                txt_2.setText("Debit : " + account.getDebit());
                txt_3.setText("Savings : " + account.getSavings());
                txt_4.setText("Credit : " + account.getCredit());
                txt_5.setText("Others : " + account.getOthers());
                break;
            case "Reports": {
                txt_title.setText("Today");
                img_cornerleft.setImageDrawable(getResources().getDrawable(R.drawable.leftcorner));
                img_cornerright.setImageDrawable(getResources().getDrawable(R.drawable.rightcorner));
                img_about.setImageDrawable(null);
                txt_1.setText(null);
                txt_2.setText(null);
                txt_5.setText(null);
                //date now
                final Calendar myCalendar = Calendar.getInstance();
                int year = myCalendar.get(Calendar.YEAR);
                int month = myCalendar.get(Calendar.MONTH);
                int day = myCalendar.get(Calendar.DAY_OF_MONTH);
                if (isInternetAvailable()) {
                    expense = getExpense(year, month + 1, day);
                    income = getIncome(year, month + 1, day);
                } else {
                    Toast.makeText(this, "Error retrieving data. Check your internet connection", Toast.LENGTH_LONG).show();
                }
                txt_3.setText("Expense : " + expense.getTotal());
                txt_4.setText("Income   : " + income.getTotal());
                break;
            }
            case "About":
                img_cornerleft.setImageDrawable(null);
                img_cornerright.setImageDrawable(null);
                txt_1.setText(null);
                txt_2.setText(null);
                txt_3.setText(null);
                txt_4.setText(null);
                txt_5.setText(null);
                txt_title.setText(null);
                img_about.setImageDrawable(getResources().getDrawable(R.drawable.icondompetku));
                break;
        }

    }

    private Income getIncome(int year,int month,int day) {
        try {
            final Income income = new AsyncGetIncome(this,String.valueOf(year),String.valueOf(month),String.valueOf(day)).execute().get();
            if(income==null){
                Toast.makeText(this, "Error retrieving Income data. Check your internet connection", Toast.LENGTH_SHORT).show();
            }
            return income;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Expense getExpense(int year,int month,int day) {
        try {
            final Expense expense = new AsyncGetExpense(this,String.valueOf(year),String.valueOf(month),String.valueOf(day)).execute().get();
            if(expense==null){
                Toast.makeText(this,"Error retrieving Expense data. Check your internet connection",Toast.LENGTH_SHORT).show();
            }
            return expense;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean isInternetAvailable() {
        return isConnectedMobile()||isConnectedWifi()||isNetworkAvailable();
    }

    // Check if there is any connectivity for a Wifi network
    public boolean isConnectedWifi(){
        NetworkInfo info = Connectivity.getNetworkInfo(MainActivity.this);

        return info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI;
    }

    // Check if there is any connectivity for a mobile network
    public boolean isConnectedMobile(){
        NetworkInfo info = Connectivity.getNetworkInfo(MainActivity.this);
        return info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_MOBILE;
    }

    // Check all connectivities whether available or not
    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(MainActivity.this.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        // if no network is available networkInfo will be null
        // otherwise check if we are connected
        return networkInfo != null && networkInfo.isConnected();
    }

}
