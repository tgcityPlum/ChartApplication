package com.yzy.chartapplication.chart;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.List;

/**
 * Created by Charlie on 2016/9/23.
 * 对字符串类型的坐标轴标记进行格式化
 */
public class YouzyIAxisValueFormatter implements IAxisValueFormatter {

    //区域值
    private List<String> mStrs;

    /**
     * 对字符串类型的坐标轴标记进行格式化
     *
     * @param strs
     */
    public YouzyIAxisValueFormatter(List<String> strs) {
        this.mStrs = strs;
    }

    @Override
    public String getFormattedValue(float v, AxisBase axisBase) {
        int positon;
        if (v < 0) {
            positon = 0;
        } else if (v < mStrs.size()) {
            positon = (int) v;
        } else {
            positon = 0;
        }
        return mStrs.get(positon);
    }
}
