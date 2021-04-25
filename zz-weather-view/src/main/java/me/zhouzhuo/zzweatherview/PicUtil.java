package me.zhouzhuo.zzweatherview;

import android.content.Context;

/**
 * @author zz
 * @date 2016/12/28
 */

public class PicUtil {
    
    
    /**
     * 根据白天天气名称解析白天天气图片
     *
     * @param weatherName 天气名称
     * @return 天气图片资源
     */
    public static int getDayWeatherPic(String weatherName) {
        switch (weatherName) {
            case "晴":
                return R.drawable.w0;
            case "多云":
                return R.drawable.w1;
            case "阴":
                return R.drawable.w2;
            case "雷阵雨":
                return R.drawable.w4;
            case "雨夹雪":
                return R.drawable.w6;
            case "小雨":
                return R.drawable.w7;
            case "中雨":
                return R.drawable.w8;
            case "大雨":
                return R.drawable.w9;
            case "暴雨":
                return R.drawable.w10;
            case "大雪":
                return R.drawable.w17;
            case "中雪":
                return R.drawable.w16;
            case "冰雹":
                return R.drawable.w15;
        }
        return R.drawable.w0;
    }
    
    /**
     * 根据夜间天气名称解析夜间天气图片
     *
     * @param weatherName 天气名称
     * @return 天气图片资源
     */
    public static int getNightWeatherPic(String weatherName) {
        switch (weatherName) {
            case "晴":
                return R.drawable.w30;
            case "多云":
                return R.drawable.w31;
            case "阴":
                return R.drawable.w2;
            case "雷阵雨":
                return R.drawable.w4;
            case "雨夹雪":
                return R.drawable.w6;
            case "小雨":
                return R.drawable.w7;
            case "中雨":
                return R.drawable.w8;
            case "大雨":
                return R.drawable.w9;
            case "暴雨":
                return R.drawable.w10;
            case "大雪":
                return R.drawable.w17;
            case "中雪":
                return R.drawable.w16;
            case "冰雹":
                return R.drawable.w15;
        }
        return R.drawable.w30;
    }
    
    
    /**
     * convert dp to its equivalent px
     * <p>
     * 将dp转换为与之相等的px
     */
    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
