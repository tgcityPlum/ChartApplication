package com.yzy.chartapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.data.PieEntry;
import com.yzy.chartapplication.chart.YouzyBarChart;
import com.yzy.chartapplication.chart.YouzyPieChart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        YouzyBarChart barChart = findViewById(R.id.chart1);
        YouzyPieChart pieChart = findViewById(R.id.chart2);

        /*java.util.List<String> xAxisValue = new ArrayList<>();
        List<Float> yAxisValue = new ArrayList<>();

        xAxisValue.add("武汉");
        xAxisValue.add("深圳");
        xAxisValue.add("广州");
        xAxisValue.add("上海");
        xAxisValue.add("北京");

        yAxisValue.add(21f);
        yAxisValue.add(18f);
        yAxisValue.add(16f);
        yAxisValue.add(14f);
        yAxisValue.add(10f);
        barChart.setBarChart(xAxisValue,yAxisValue,"就业地区");*/

        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(0.4f, "武汉"));
        entries.add(new PieEntry(0.28f, "深圳"));
        entries.add(new PieEntry(0.12f, "广州"));
        entries.add(new PieEntry(0.1f, "上海"));
        entries.add(new PieEntry(0.05f, "北京"));
        entries.add(new PieEntry(0.05f, "其它"));
        pieChart.setData(entries, false, 1,true);

    }

}
