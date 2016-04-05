package garputala.irfananda.dompetku;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//get data Expense & Income from host1 & host2 to class Expense & Income
public class ExpenseIncomeParser {

    private static final String HOST1="http://www.dompetku.cingkleung.com/Expense.php";
    private static final String HOST2="http://www.dompetku.cingkleung.com/Income.php";

    public static Expense getExpense(String year,String month,String day){

        Expense expense= new Expense();

        JSONParser jsonParser = new JSONParser();

        JSONObject jsonObject = jsonParser.getJSONFromUrl(HOST1+"?year="+year+"&month="+month+"&day="+day);
        try{
            JSONArray jsonArray = jsonObject.getJSONArray("Expense");

                JSONObject obj = jsonArray.getJSONObject(0);

                expense.setTotal(obj.getInt("total"));
                expense.setSum(obj.getInt("sum"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return expense;
    }

    public static Income getIncome(String year,String month,String day){

        Income income= new Income();

        JSONParser jsonParser = new JSONParser();

        JSONObject jsonObject = jsonParser.getJSONFromUrl(HOST2+"?year="+year+"&month="+month+"&day="+day);
        try{
            JSONArray jsonArray = jsonObject.getJSONArray("Income");

            JSONObject obj = jsonArray.getJSONObject(0);

            income.setTotal(obj.getInt("total"));
            income.setSum(obj.getInt("sum"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return income;
    }
}
