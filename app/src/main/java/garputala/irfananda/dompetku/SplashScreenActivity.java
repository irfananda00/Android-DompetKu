package garputala.irfananda.dompetku;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

//activity for how long the Splash Screen showing
public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_splash_screen);

            final DataContainer dataContainer= new DataContainer();
            dataContainer.setAppsFirstStart(true);

//            final Calendar cal = Calendar.getInstance();
//            int year = cal.get(Calendar.YEAR);
//            int month = cal.get(Calendar.MONTH);
//            int day = cal.get(Calendar.DAY_OF_MONTH);
//            month = month+1;
//            String tanggal_input = year+"-"+month+"-"+day;
//
////        DataContainer dataContainer = new DataContainer();
////
////        List<Transaction> transactions = new ArrayList<>();
////
////        try{
////            List<Transaction> transactionList = new AsyncGetTransaction(SplashScreenActivity.this).execute(tanggal_input).get();
////            for(int i = 0; i<transactionList.size();i++)
////                transactions.add(transactionList.get(i));
////            dataContainer.setTransactions(transactions);
////
////        }catch (InterruptedException e) {
////            Log.i("infoirfan", "interrupted exception");
////        } catch (ExecutionException e) {
////            Log.i("infoirfan", "execution exception");
////        }

        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    Intent intent = new Intent(SplashScreenActivity.this,MainActivity.class);
                    intent.putExtra("DataContainer",dataContainer);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }
}
