package garputala.irfananda.dompetku;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

//fragment reports that can be implemented in mainActivity
public class FragmentReports extends Fragment {

    private PieChart pieChart;
    private BarChart barChart;
    private HorizontalBarChart horizontalbarchart;
    private Spinner spn_viewby;
    private int year,month,day;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_fragment_reports,container,false);

        //date now
        final Calendar myCalendar = Calendar.getInstance();
        year = myCalendar.get(Calendar.YEAR);
        month = myCalendar.get(Calendar.MONTH);
        day = myCalendar.get(Calendar.DAY_OF_MONTH);

        spn_viewby = (Spinner)rootView.findViewById(R.id.spn_viewby);
        ImageButton btn_search = (ImageButton)rootView.findViewById(R.id.btn_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String viewby = String.valueOf(spn_viewby.getSelectedItem());
                switch (viewby) {
                    case "This Day":
                        //this day
                        createChart(year, month+1, day);
                        break;
                    case "This Month":
                        //this month
                        createChart(year, month+1, 0);
                        break;
                    case "This Year":
                        //this month
                        createChart(year, 0, 0);
                        break;
                    default:
                        //any time
                        createChart(0, 0, 0);
                        break;
                }
            }
        });

        pieChart = (PieChart)rootView.findViewById(R.id.piechart);

        barChart = (BarChart)rootView.findViewById(R.id.barchart);

        horizontalbarchart = (HorizontalBarChart)rootView.findViewById(R.id.horizontalbarchart);

        createChart(year, month+1, day);

        //set chart value selected listener
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry entry, int i, Highlight highlight) {
                if (entry == null)
                    return;
                if (entry.getXIndex() == 0)
                    Toast.makeText(FragmentReports.this.getActivity(), "Total Expense : Rp. " + (int) entry.getVal(), Toast.LENGTH_LONG).show();
                else if (entry.getXIndex() == 1)
                    Toast.makeText(FragmentReports.this.getActivity(), "Total Income : Rp. " + (int) entry.getVal(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });

        return rootView;
    }

    private void createChart(int year, int month, int day) {
        //get expense income
        Expense expense = getExpense(year, month, day);
        Income income = getIncome(year, month, day);
        Transaction transactionCategory = getTransactionCategory();
        //create pie chart
        createPieChart(expense, income);
        //create bar chart
        createBarChart(expense, income);
        //create horizontal bar chart
        createHorizontalBarChart(transactionCategory);
    }

    private Transaction getTransactionCategory() {
        try {
            final Transaction transaction= new AsyncGetTotalTransactionCategory(this.getActivity()).execute().get();
            if(transaction==null){
                Toast.makeText(this.getActivity(),"Error retrieving Income data. Check your internet connection",Toast.LENGTH_SHORT).show();
            }
            return transaction;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Income getIncome(int year,int month,int day) {
        try {
            final Income income = new AsyncGetIncome(this.getActivity(),String.valueOf(year),String.valueOf(month),String.valueOf(day)).execute().get();
            if(income==null){
                Toast.makeText(this.getActivity(),"Error retrieving Income data. Check your internet connection",Toast.LENGTH_SHORT).show();
            }
            return income;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Expense getExpense(int year,int month,int day) {
        try {
            final Expense expense = new AsyncGetExpense(this.getActivity(),String.valueOf(year),String.valueOf(month),String.valueOf(day)).execute().get();
            if(expense==null){
                Toast.makeText(this.getActivity(),"Error retrieving Expense data. Check your internet connection",Toast.LENGTH_SHORT).show();
            }
            return expense;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void createPieChart(Expense expense, Income income){
        //configure pie chart
        pieChart.setUsePercentValues(true);
        pieChart.setDescription("");
        pieChart.setDragDecelerationFrictionCoef(0.95f);

        //enable hole and configure
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColorTransparent(true);
        pieChart.setHoleRadius(7);
        pieChart.setTransparentCircleRadius(10);

        //enable rotation of chart touch
        pieChart.setRotationAngle(0);
        pieChart.setRotationEnabled(true);

        //add data chart

            ArrayList<Entry> Value = new ArrayList<>();
            Value.add(new Entry(expense.getSum(), 0));
            Value.add(new Entry(income.getSum(), 1));

            ArrayList<String> Name = new ArrayList<>();
            Name.add("Expense");
            Name.add("Income");

            //create pie data set
            PieDataSet pieDataSet = new PieDataSet(Value,"");
            pieDataSet.setSliceSpace(3);
            pieDataSet.setSelectionShift(5);

            //add colors
            ArrayList<Integer> colors = new ArrayList<>();

            for(int c: ColorTemplate.COLORFUL_COLORS)
                colors.add(c);

            for(int c: ColorTemplate.JOYFUL_COLORS)
                colors.add(c);

            colors.add(ColorTemplate.getHoloBlue());
            pieDataSet.setColors(colors);

            //instance pie data set now
            PieData data = new PieData(Name,pieDataSet);
            data.setValueFormatter(new PercentFormatter());
            data.setValueTextSize(11f);
            data.setValueTextColor(Color.WHITE);

            pieChart.setData(data);

            pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

            //undo all highlights
            pieChart.highlightValues(null);

            //update piechart
            pieChart.invalidate();

        //customize legends
        Legend legend = pieChart.getLegend();
        legend.setPosition(Legend.LegendPosition.ABOVE_CHART_LEFT);
        legend.setForm(Legend.LegendForm.SQUARE);
        legend.setFormSize(9f);
        legend.setTextSize(11f);
        legend.setXEntrySpace(7);
        legend.setYEntrySpace(5);

    }

    private void createBarChart(Expense expense, Income income) {
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);

        barChart.setDescription("");

        //if entries more than 100 , no values will be drawn
        barChart.setMaxVisibleValueCount(100);

        //scaling can now only be done on x- and y-axis separately
        barChart.setPinchZoom(false);

        barChart.setDrawGridBackground(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(2);

        YAxisValueFormatter custom = new MyYAxisValueFormatter();

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setLabelCount(8, false);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);

        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setLabelCount(8, false);
        rightAxis.setValueFormatter(custom);
        rightAxis.setSpaceTop(15f);

        Legend legend = barChart.getLegend();
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        legend.setForm(Legend.LegendForm.SQUARE);
        legend.setFormSize(9f);
        legend.setTextSize(11f);
        legend.setXEntrySpace(4f);

        ArrayList<String> xVals = new ArrayList<>();
        xVals.add("Expense");
        xVals.add("Income");

        ArrayList<BarEntry> yVals = new ArrayList<>();
        yVals.add(new BarEntry(expense.getTotal(),0));
        yVals.add(new BarEntry(income.getTotal(), 1));

        BarDataSet barDataSet = new BarDataSet(yVals,"Total of Transaction");
        barDataSet.setBarSpacePercent(35f);

        ArrayList<BarDataSet> dataSets = new ArrayList<>();
        dataSets.add(barDataSet);

        BarData data = new BarData(xVals,dataSets);
        data.setValueTextSize(10f);

        barChart.setData(data);

        barChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
    }

    private void createHorizontalBarChart(Transaction transactionCategory) {
//        horizontalbarchart.setOnChartValueSelectedListener();
        horizontalbarchart.setDrawBarShadow(false);
        horizontalbarchart.setDrawValueAboveBar(true);
        horizontalbarchart.setDescription("");

        horizontalbarchart.setMaxVisibleValueCount(100);
        horizontalbarchart.setPinchZoom(false);

        horizontalbarchart.setDrawGridBackground(false);

        XAxis x1 = horizontalbarchart.getXAxis();
        x1.setPosition(XAxis.XAxisPosition.BOTTOM);
        x1.setDrawAxisLine(true);
        x1.setDrawGridLines(true);
        x1.setGridLineWidth(0.3f);

        YAxis yl = horizontalbarchart.getAxisLeft();
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(true);
        yl.setGridLineWidth(0.3f);

        YAxis yr = horizontalbarchart.getAxisRight();
        yr.setDrawAxisLine(true);
        yr.setDrawGridLines(false);

        //setData
        ArrayList<BarEntry> yVals1 = new ArrayList<>();
        ArrayList<String> xVals = new ArrayList<>();

        xVals.add(0,"Transport");
        xVals.add(1,"Eating Out");
        xVals.add(2,"Education");
        xVals.add(3,"Groceries");
        xVals.add(4,"Insurance");
        xVals.add(5,"Medical");
        xVals.add(6,"Utilities");
        xVals.add(7,"Salary");
        xVals.add(8,"Cash");
        xVals.add(9,"Others");

        yVals1.add(new BarEntry(transactionCategory.getTransport(),0));
        yVals1.add(new BarEntry(transactionCategory.getEating(),1));
        yVals1.add(new BarEntry(transactionCategory.getEducation(),2));
        yVals1.add(new BarEntry(transactionCategory.getGroceries(),3));
        yVals1.add(new BarEntry(transactionCategory.getInsurance(),4));
        yVals1.add(new BarEntry(transactionCategory.getMedical(),5));
        yVals1.add(new BarEntry(transactionCategory.getUtilities(),6));
        yVals1.add(new BarEntry(transactionCategory.getSalary(),7));
        yVals1.add(new BarEntry(transactionCategory.getCash(), 8));
        yVals1.add(new BarEntry(transactionCategory.getOthers(), 9));

        BarDataSet set = new BarDataSet(yVals1,"Transaction Category");

        ArrayList<BarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set);

        BarData data = new BarData(xVals,dataSets);
        data.setValueTextSize(10f);

        horizontalbarchart.setData(data);
        //

        horizontalbarchart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        Legend l = horizontalbarchart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setFormSize(8f);
        l.setXEntrySpace(4f);


    }
}
