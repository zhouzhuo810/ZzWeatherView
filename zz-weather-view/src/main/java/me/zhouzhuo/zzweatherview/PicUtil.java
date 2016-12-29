package me.zhouzhuo.zzweatherview;

import android.widget.ImageView;

/**
 * Created by zz on 2016/12/28.
 */

public class PicUtil {

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
}
