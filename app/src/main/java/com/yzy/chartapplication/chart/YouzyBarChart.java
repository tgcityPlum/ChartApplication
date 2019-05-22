package com.yzy.chartapplication.chart;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.yzy.chartapplication.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 柱形图BarChart类
 */
public class YouzyBarChart extends BarChart {
    //柱形图的x轴宽度
    private float mBarWidth = 0.3f;
    //图例窗体的大小
    private float mFormSize = 13f;
    //图例文字的大小
    private float mFormTextSize = 16f;
    //x轴数据的字体大小
    private float mXAxisTextSize = 15f;
    //y轴数据的字体大小
    private float mYAxisTextSize = 10f;
    //距视图窗口底部的偏移，类似与paddingBottom
    private float mExtraBottomOffset = 10f;
    //距视图窗口顶部的偏移，类似与paddingTop
    private float mExtraTopOffset = 30f;
    //数据显示动画，从左往右依次显示
    private int mAnimateDurationMillis = 1500;
    //单个柱形图的背景色
    private int mSingleBarChartColor = R.color.barchart1;
    //第二个柱形图的背景色
    private int mTwoBarChartColor = R.color.barchart2;
    //第三个柱形图的背景色
    private int mThreeBarChartColor = R.color.barchart3;

    public YouzyBarChart(Context context) {
        super(context);
    }

    public YouzyBarChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public YouzyBarChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 单数据集。设置柱状图样式，X轴为字符串，Y轴为数值
     *
     * @param xAxisValue x轴文案描述信息集合
     * @param yAxisValue y轴显示的高度集合
     * @param title      图例文字
     */
    public void setBarChart(List<String> xAxisValue, List<Float> yAxisValue, String title) {
        //设置自定义的点击的气泡弹框 markerView
//        MPChartMarkerView markerView = new MPChartMarkerView(getContext(), R.layout.custom_marker_view);
//        setMarker(markerView);
        //x坐标轴设置
        setBarChartXAxis(xAxisValue, false);
        //y轴设置
        //设置坐标轴最大最小值
        setBarChartYAxis(Double.valueOf((Collections.min(yAxisValue)) * 0.1).floatValue(), Double.valueOf((Collections.max(yAxisValue)) * 1.1).floatValue());
        //图例设置
        setBarChartLegend();
        //设置柱状图数据
        setSingleBarChartData(yAxisValue, title);
        //设置barChart属性
        setBarChartAttributes();
    }

    /**
     * 设置柱图
     *
     * @param yAxisValue y轴显示的高度集合
     * @param title      图例文字
     */
    private void setSingleBarChartData(List<Float> yAxisValue, String title) {
        //将柱状图的高度集合填充到BarEntry中，最后设置到BarDataSet上
        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0, n = yAxisValue.size(); i < n; ++i) {
            entries.add(new BarEntry(i, yAxisValue.get(i)));
        }

        BarDataSet barDataSet;
        //针对BarDataSet的复用
        if (getData() != null && getData().getDataSetCount() > 0) {
            barDataSet = (BarDataSet) getData().getDataSetByIndex(0);
            barDataSet.setValues(entries);
            getData().notifyDataChanged();
            notifyDataSetChanged();
        } else {
            barDataSet = new BarDataSet(entries, title);
            //设置柱形图的填充颜色
            barDataSet.setColor(ContextCompat.getColor(getContext(), getSingleBarChartColor()));//设置set1的柱的颜色

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(barDataSet);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(mYAxisTextSize);//设置柱形图的高度
            data.setBarWidth(mBarWidth);//设置单个柱形图的宽度
            data.setValueFormatter(new YouzyIValueFormatter());

            setData(data);
        }
    }

    /**
     * 设置双柱状图样式
     *
     * @param xAxisValue
     * @param yAxisValue1
     * @param yAxisValue2
     * @param bartilte1
     * @param bartitle2
     */
    public void setBarChart(List<String> xAxisValue, List<Float> yAxisValue1, List<Float> yAxisValue2, String bartilte1, String bartitle2) {

        /*MPChartMarkerView markerView = new MPChartMarkerView(getContext(), R.layout.custom_marker_view);
        setMarker(markerView);*/

        //x坐标轴设置
        setBarChartXAxis(xAxisValue, true);

        //y轴设置
        //设置坐标轴最大最小值
        Float yMin1 = Collections.min(yAxisValue1);
        Float yMin2 = Collections.min(yAxisValue2);
        Float yMax1 = Collections.max(yAxisValue1);
        Float yMax2 = Collections.max(yAxisValue2);
        Float yMin = Double.valueOf((yMin1 < yMin2 ? yMin1 : yMin2) * 0.1).floatValue();
        Float yMax = Double.valueOf((yMax1 > yMax2 ? yMax1 : yMax2) * 1.1).floatValue();

        setBarChartYAxis(yMin, yMax);

        //图例设置
        setBarChartLegend();

        //设置柱状图数据
        setTwoBarChartData(xAxisValue, yAxisValue1, yAxisValue2, bartilte1, bartitle2);

        setBarChartAttributes();

    }

    /**
     * 设置柱状图数据源
     */
    private void setTwoBarChartData(List<String> xAxisValue, List<Float> yAxisValue1, List<Float> yAxisValue2, String bartilte1, String bartitle2) {
        // (0.45 + 0.03) * 2 + 0.04 = 1，即一个间隔为一组，包含两个柱图 -> interval per "group"
        float barSpace = 0.08f;//组内距
        float groupSpace = 1 - 2 * (mBarWidth + barSpace);//组间距

        ArrayList<BarEntry> entries1 = new ArrayList<>();
        ArrayList<BarEntry> entries2 = new ArrayList<>();

        for (int i = 0, n = yAxisValue1.size(); i < n; ++i) {
            entries1.add(new BarEntry(i, yAxisValue1.get(i)));
            entries2.add(new BarEntry(i, yAxisValue2.get(i)));
        }

        BarDataSet dataset1, dataset2;

        if (getData() != null && getData().getDataSetCount() > 0) {
            dataset1 = (BarDataSet) getData().getDataSetByIndex(0);
            dataset2 = (BarDataSet) getData().getDataSetByIndex(1);
            dataset1.setValues(entries1);
            dataset2.setValues(entries2);
            getData().notifyDataChanged();
            notifyDataSetChanged();
        } else {
            dataset1 = new BarDataSet(entries1, bartilte1);
            dataset2 = new BarDataSet(entries2, bartitle2);
            //设置柱形图的颜色
            dataset1.setColor(ContextCompat.getColor(getContext(), getSingleBarChartColor()));
            dataset2.setColor(ContextCompat.getColor(getContext(), getTwoBarChartColor()));

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(dataset1);
            dataSets.add(dataset2);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(mBarWidth);
            data.setValueFormatter(new IValueFormatter() {
                @Override
                public String getFormattedValue(float value, Entry entry, int i, ViewPortHandler viewPortHandler) {
                    return StringUtils.double2String(value, 2);
                }
            });

            setData(data);
        }

        getBarData().setBarWidth(mBarWidth);
//        getXAxis().setAxisMinimum(xAxisValue.get(0));
        getXAxis().setAxisMinimum(0);
        // barData.getGroupWith(...) is a helper that calculates the width each group needs based on the provided parameters
//        getXAxis().setAxisMaximum(getBarData().getGroupWidth(groupSpace, barSpace) * xAxisValue.size() + xAxisValue.get(0));
        getXAxis().setAxisMaximum(getBarData().getGroupWidth(groupSpace, barSpace) * xAxisValue.size() + 0);
        groupBars(0, groupSpace, barSpace);
//        groupBars(xAxisValue.get(0), groupSpace, barSpace);
    }

    /**
     * 设置三柱状图样式
     *
     * @param xAxisValue
     * @param yAxisValue1
     * @param yAxisValue2
     * @param yAxisValue3
     * @param bartilte1
     * @param bartitle2
     * @param bartitle3
     */
    public void setBarChart(List<String> xAxisValue, List<Float> yAxisValue1, List<Float> yAxisValue2, List<Float> yAxisValue3, String bartilte1, String bartitle2, String bartitle3) {
        //x坐标轴设置
        setBarChartXAxis(xAxisValue, true);

        //y轴设置
        Float yMin1 = Collections.min(yAxisValue1);
        Float yMin2 = Collections.min(yAxisValue2);
        Float yMin3 = Collections.min(yAxisValue3);
        Float yMax1 = Collections.max(yAxisValue1);
        Float yMax2 = Collections.max(yAxisValue2);
        Float yMax3 = Collections.max(yAxisValue3);
        Float yMinTemp = yMin1 < yMin2 ? yMin1 : yMin2;
        Float yMin = yMinTemp < yMin3 ? yMinTemp : yMin3;
        Float yMaxTemp = yMax1 > yMax2 ? yMax1 : yMax2;
        Float yMax = yMaxTemp > yMax3 ? yMaxTemp : yMax3;

        setBarChartYAxis(Double.valueOf(yMin * 0.9).floatValue(), Double.valueOf(yMax * 1.1).floatValue());

        //图例设置
        setBarChartLegend();

        //设置柱状图数据
        setThreeBarChartData(xAxisValue, yAxisValue1, yAxisValue2, yAxisValue3, bartilte1, bartitle2, bartitle3);

        setBarChartAttributes();
    }

    /**
     * 设置三柱图数据源
     *
     * @param xAxisValue
     * @param yAxisValue1
     * @param yAxisValue2
     * @param yAxisValue3
     * @param bartilte1
     * @param bartitle2
     * @param bartitle3
     */
    private void setThreeBarChartData(List<String> xAxisValue, List<Float> yAxisValue1, List<Float> yAxisValue2, List<Float> yAxisValue3, String bartilte1, String bartitle2, String bartitle3) {
        // (0.45 + 0.03) * 2 + 0.04 = 1，即一个间隔为一组，包含三个柱图 -> interval per "group"
        float barSpace = 0.08f;//组内距
        float groupSpace = 1 - 2 * (mBarWidth + barSpace);//组间距

        ArrayList<BarEntry> first_entries = new ArrayList<>();
        ArrayList<BarEntry> second_entries = new ArrayList<>();
        ArrayList<BarEntry> third_entries = new ArrayList<>();

        for (int i = 0, n = xAxisValue.size(); i < n; ++i) {
            first_entries.add(new BarEntry(i, yAxisValue1.get(i)));
            second_entries.add(new BarEntry(i, yAxisValue2.get(i)));
            third_entries.add(new BarEntry(i, yAxisValue3.get(i)));
        }

        BarDataSet first_set, second_set, third_set;

        if (getData() != null && getData().getDataSetCount() > 0) {
            first_set = (BarDataSet) getData().getDataSetByIndex(0);
            second_set = (BarDataSet) getData().getDataSetByIndex(1);
            third_set = (BarDataSet) getData().getDataSetByIndex(2);
            first_set.setValues(first_entries);
            second_set.setValues(second_entries);
            third_set.setValues(third_entries);
            getData().notifyDataChanged();
            notifyDataSetChanged();
        } else {
            first_set = new BarDataSet(first_entries, bartilte1);
            second_set = new BarDataSet(second_entries, bartitle2);
            third_set = new BarDataSet(third_entries, bartitle3);

            first_set.setColor(ContextCompat.getColor(getContext(), getSingleBarChartColor()));
            second_set.setColor(ContextCompat.getColor(getContext(), getTwoBarChartColor()));
            third_set.setColor(ContextCompat.getColor(getContext(), getThreeBarChartColor()));

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(first_set);
            dataSets.add(second_set);
            dataSets.add(third_set);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(mBarWidth);
            data.setValueFormatter(new IValueFormatter() {
                @Override
                public String getFormattedValue(float value, Entry entry, int i, ViewPortHandler viewPortHandler) {
                    return StringUtils.double2String(value, 2);
                }
            });

            setData(data);
        }

        getBarData().setBarWidth(mBarWidth);
        getXAxis().setAxisMinimum(0);
        getXAxis().setAxisMaximum(getBarData().getGroupWidth(groupSpace, barSpace) * xAxisValue.size() + 0);
        groupBars(0, groupSpace, barSpace);
    }

    /**
     * x坐标轴设置
     */
    private void setBarChartXAxis(List<String> xAxisValue, boolean isCenterAxisLabels) {
        //x坐标轴设置
        IAxisValueFormatter xAxisFormatter = new YouzyIAxisValueFormatter(xAxisValue);//设置自定义的x轴值格式化器
        XAxis xAxis = getXAxis();//获取x轴
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置X轴标签显示位置
        xAxis.setDrawGridLines(false);//不绘制格网线
        xAxis.setGranularity(1f);//设置最小间隔，防止当放大时，出现重复标签。
        xAxis.setValueFormatter(xAxisFormatter);
        xAxis.setTextSize(mXAxisTextSize);//设置标签字体大小
        xAxis.setLabelCount(xAxisValue.size());//设置标签显示的个数
        xAxis.setCenterAxisLabels(isCenterAxisLabels);//设置标签居中
    }

    /**
     * y轴设置
     */
    private void setBarChartYAxis(float axisMinimum, float axisMaximum) {
        //y轴设置
        YAxis leftAxis = getAxisLeft();//获取左侧y轴
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);//设置y轴标签显示在外侧
        leftAxis.setAxisMinimum(axisMinimum);//设置Y轴最小值
        leftAxis.setAxisMaximum(axisMaximum);//设置Y轴最小值
        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawLabels(false);//禁止绘制y轴标签
        leftAxis.setDrawAxisLine(false);//禁止绘制y轴

        getAxisRight().setEnabled(false);//禁用右侧y轴
    }

    /**
     * 图例设置
     */
    private void setBarChartLegend() {
        Legend legend = getLegend();
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);//图例水平居中
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);//图例在图表上方
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);//图例的方向为水平
        legend.setDrawInside(false);//绘制在chart的外侧
        legend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);//图例中的文字方向

        legend.setForm(Legend.LegendForm.CIRCLE);//图例窗体的形状
        legend.setFormSize(mFormSize);//图例窗体的大小
        legend.setTextSize(mFormTextSize);//图例文字的大小
        //legend.setYOffset(-2f);
    }

    /**
     * 设置barChart属性
     */
    private void setBarChartAttributes() {
        getDescription().setEnabled(false);//设置描述
        setPinchZoom(true);//设置按比例放缩柱状图
        setExtraBottomOffset(mExtraBottomOffset);//距视图窗口底部的偏移，类似与paddingbottom
        setExtraTopOffset(mExtraTopOffset);//距视图窗口顶部的偏移，类似与paddingtop
        setFitBars(true);//使两侧的柱图完全显示
        animateX(mAnimateDurationMillis);//数据显示动画，从左往右依次显示
        invalidate();
    }

    /**
     * 获取单个柱形图的背景色
     */
    private int getSingleBarChartColor() {
        return mSingleBarChartColor;
    }

    public int getTwoBarChartColor() {
        return mTwoBarChartColor;
    }

    public int getThreeBarChartColor() {
        return mThreeBarChartColor;
    }

    /**
     * 提供外部修改第一个柱形图背景颜色的方法
     */
    public YouzyBarChart setSingleBarChartColor(int color){
        mSingleBarChartColor = color;

        return this;
    }

    /**
     * 提供外部修改第二个柱形图背景颜色的方法
     */
    public void setTwoBarChartColor(int mTwoBarChartColor) {
        this.mTwoBarChartColor = mTwoBarChartColor;
    }

    /**
     * 提供外部修改第三个柱形图背景颜色的方法
     */
    public void setThreeBarChartColor(int mThreeBarChartColor) {
        this.mThreeBarChartColor = mThreeBarChartColor;
    }
}
