package com.yzy.chartapplication.chart;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 饼状图PieChart类
 */
public class YouzyPieChart extends PieChart {
    //数据显示动画，从左往右依次显示
    private static final int mAnimateDurationMillis = 1500;
    //设置间距
    private float leftExtraOffsets = 20;
    private float topExtraOffsets = 15;
    private float rightExtraOffsets = 20;
    private float bottomExtraOffsets = 15;
    //饼状图中间文字的大小
    private float pieChartCenterTextSize = 22f;
    //饼块之间的间隔
    private float pieChartSliceSpace = 6f;
    //饼状图旋转角度
    private float pieChartRotationAngle = -90f;
    //传入的数据
    private ArrayList<PieEntry> entries = new ArrayList<>();
    //每个的比例颜色
    private int pieDrawValuesColor = Color.rgb(74, 74, 74);
    //设置图表点击Item高亮是否可用
    private boolean isHighlightPerTapEnabled = true;
    //设置图表是否显示文字可用
    private boolean isDrawCenterText = false;
    //使用的颜色类别
    private int colorsType = 0;
    //饼状图颜色
    private final int[] PIE_COLORS = {
            Color.rgb(233, 48, 45),
            Color.rgb(232, 232, 232),
            Color.rgb(232, 232, 232),
            Color.rgb(232, 232, 232),
            Color.rgb(232, 232, 232)
    };


    private final int[] PIE_COLORS_1 = {
            Color.rgb(88, 114, 239),
            Color.rgb(44, 188, 239),
            Color.rgb(100, 221, 184),
            Color.rgb(248, 207, 81),
            Color.rgb(251, 228, 148),
            Color.rgb(216, 230, 253)
    };

    public YouzyPieChart(Context context) {
        super(context);
    }

    public YouzyPieChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public YouzyPieChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 设置饼图样式
     *
     * @param pieValues 数据源
     */
    public YouzyPieChart setData(Map<String, Float> pieValues) {
        //图例设置
        setPieChartLegendEnabled(false);
        //设置饼图数据
        setPieChartData(pieValues);
        //设置饼状图属性
        setPieChartAttributes();


        return this;
    }

    /**
     * 设置饼图样式
     *
     * @param pieValues 数据源
     */
    public YouzyPieChart setData(ArrayList<PieEntry> pieValues, boolean isHighlightPerTapEnabled, int colorsType,boolean isDrawCenterText) {
        this.isHighlightPerTapEnabled = isHighlightPerTapEnabled;
        this.isDrawCenterText = isDrawCenterText;
        this.colorsType = colorsType;
        //图例设置
        setPieChartLegendEnabled(false);
        //设置饼状图属性
        setPieChartAttributes();
        //设置饼图数据
        entries.addAll(pieValues);
        pieChartInvalidate();
        return this;
    }

    /**
     * 给饼状图添加点击事件
     */
    public void addChartValueSelectedListener(final OnPieChartItemClick onPieChartItemClick) {
        //饼状图的点击事件
        setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                int position = (int) h.getX();
//                updatePieChartColor(position);

                pieChartInvalidate();
                onPieChartItemClick.onPieChartItemClick(position);
            }

            @Override
            public void onNothingSelected() {
                onPieChartItemClick.onNothingSelected();
            }
        });
    }

    /**
     * 更新饼状图颜色
     */
    private void updatePieChartColor(int position) {
        for (int i = 0; i < PIE_COLORS.length; i++) {
            if (position == i) {
                PIE_COLORS[i] = Color.rgb(233, 48, 45);
            } else {
                PIE_COLORS[i] = Color.rgb(232, 232, 232);
            }
        }
    }

    /**
     * 是否显示柱状图的样式说明
     *
     * @param showLegend 是否显示
     */
    private void setPieChartLegendEnabled(boolean showLegend) {
        Legend legend = getLegend();
        if (showLegend) {
            legend.setEnabled(true);
            legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
            legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
            legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
            legend.setDrawInside(false);
            legend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
        } else {
            legend.setEnabled(false);
        }
    }

    /**
     * 设置柱形图的属性
     */
    private void setPieChartAttributes() {
        setUsePercentValues(true);//设置使用百分比
        getDescription().setEnabled(false);//设置描述
        setExtraOffsets(leftExtraOffsets, topExtraOffsets, rightExtraOffsets, bottomExtraOffsets);
//        setCenterText(title);//设置环中的文字
//        setCenterTextSize(pieChartCenterTextSize);//设置环中文字的大小
//        setDrawCenterText(true);//设置绘制环中文字
        //是否绘制PieChart内部中心文本
        setDrawCenterText(isDrawCenterText);
        //设置是否绘制条目标签名称
        setDrawEntryLabels(isDrawCenterText);
        // 设置图表点击Item高亮是否可用
        setHighlightPerTapEnabled(isHighlightPerTapEnabled);
        //设置旋转角度
        setRotationAngle(pieChartRotationAngle);
        //设置是否启用空洞
        setDrawHoleEnabled(true);
        animateX(mAnimateDurationMillis, Easing.EasingOption.EaseInOutQuad);//数据显示动画
        // 设置pieChart图表是否可以手动旋转
        setRotationEnabled(false);
    }

    /**
     * 设置饼图数据源
     */
    private void setPieChartData(Map<String, Float> pieValues) {
        Set set = pieValues.entrySet();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            entries.add(new PieEntry(Float.valueOf(entry.getValue().toString()), entry.getKey().toString()));
        }

        pieChartInvalidate();
    }

    /**
     * 绘制饼状图
     */
    private void pieChartInvalidate() {
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setSliceSpace(pieChartSliceSpace);//设置饼块之间的间隔
        dataSet.setSelectionShift(5f);//设置饼块选中时偏离饼图中心的距离

        dataSet.setColors(colorsType == 0 ? PIE_COLORS : PIE_COLORS_1);//设置饼块的颜色
        dataSet.setValueLinePart1OffsetPercentage(80f);//数据连接线距图形片内部边界的距离，为百分数
        dataSet.setValueLinePart1Length(0.3f);
        dataSet.setValueLinePart2Length(0.4f);
        dataSet.setValueLineColor(pieDrawValuesColor);//设置连接线的颜色
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        PieData pieData = new PieData(dataSet);
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextSize(11f);
        pieData.setValueTextColor(pieDrawValuesColor);

        //是否绘制对应的数值比例
        pieData.setDrawValues(isDrawCenterText);


        setData(pieData);
        highlightValues(null);
        invalidate();
    }

}
