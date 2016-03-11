package garputala.irfananda.dompetku;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ListCategoryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_list_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView listCategory = (ListView)findViewById(R.id.listCategory);
        final String category = (String) getIntent().getSerializableExtra("Category");
        switch (category) {
            case "account":
                try{
                    final List<AccountCategory> accountCategories = new AsyncGetAccountCategory(this).execute().get();
                    final AccountCategoryAdapter accountCategoryAdapter= new AccountCategoryAdapter(this,accountCategories);
                    listCategory.setAdapter(accountCategoryAdapter);
                    listCategory.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            AccountCategory accountCategory = accountCategories.get(position);
                            DialogMenuCategory dialogMenuCategory= new DialogMenuCategory(ListCategoryActivity.this, accountCategory);
                            dialogMenuCategory.show(fragmentManager, "Menu Category Account");
                            return false;
                        }
                    });

                }catch (InterruptedException e) {
                    Log.i("infoirfan","interrupted exception");
                } catch (ExecutionException e) {
                    Log.i("infoirfan", "execution exception");
                }
                break;
            case "transaction":
                try{
                    final List<TransactionCategory> transactionCategories= new AsyncGetTransactionCategory(this).execute().get();
                    final TransactionCategoryAdapter transactionCategoryAdapter= new TransactionCategoryAdapter(this,transactionCategories);
                    listCategory.setAdapter(transactionCategoryAdapter);
                    listCategory.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            TransactionCategory transactionCategory = transactionCategories.get(position);
                            DialogMenuCategory dialogMenuCategory= new DialogMenuCategory(ListCategoryActivity.this, transactionCategory);
                            dialogMenuCategory.show(fragmentManager, "Menu Category Transaction");
                            return false;
                        }
                    });

                }catch (InterruptedException e) {
                    Log.i("infoirfan","interrupted exception");
                } catch (ExecutionException e) {
                    Log.i("infoirfan", "execution exception");
                }
                break;
        }

        Button btn_add = (Button)findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListCategoryActivity.this,InputCategoryActivity.class);
                intent.putExtra("Category",category);
                startActivity(intent);
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
        Intent intent = new Intent(ListCategoryActivity.this,SettingsActivity.class);
        startActivity(intent);
        this.finish();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ListCategoryActivity.this,SettingsActivity.class);
        startActivity(intent);
        this.finish();
    }
}
