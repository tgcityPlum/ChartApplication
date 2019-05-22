package com.yzy.chartapplication.chart;

/**
 * 柱状图的点击事件接口
 */
public interface OnPieChartItemClick {
    //点击响应 方法
    void onPieChartItemClick(int position);
    //什么的没点 方法
    void onNothingSelected();
}
