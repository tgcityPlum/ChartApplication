package com.yzy.chartapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yzy.chartapplication.chart.YouzyBarChart;
import com.yzy.chartapplication.chart.YouzyPieChart;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        YouzyBarChart barChart = findViewById(R.id.chart1);
        YouzyPieChart pieChart = findViewById(R.id.chart2);

//        barChart.setBarChart(null,null,"");

//        pieChart.setData(null);
    }

}
