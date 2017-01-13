package me.zhouzhuo.zzweatherview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * ZzWeatherView
 *
 * @author Zz
 * 2016/12/8 9:29
 */
public class ZzWeatherView extends HorizontalScrollView {

    private List<WeatherModel> list;
    private Paint dayPaint;
    private Paint nightPaint;

    protected Path pathDay;
    protected Path pathNight;

    public static final int LINE_TYPE_CURVE = 1; //曲线
    public static final int LINE_TYPE_DISCOUNT = 2; //折线

    private int lineType = LINE_TYPE_CURVE;
    private float lineWidth = 6f;

    private OnWeatherItemClickListener weatherItemClickListener;

    public ZzWeatherView(Context context) {
        this(context, null);
    }

    public ZzWeatherView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ZzWeatherView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        dayPaint = new Paint();
        dayPaint.setColor(0xff78ad23);
        dayPaint.setAntiAlias(true);
        dayPaint.setStrokeWidth(lineWidth);
        dayPaint.setStyle(Paint.Style.STROKE);

        nightPaint = new Paint();
        nightPaint.setColor(0xff23acb3);
        nightPaint.setAntiAlias(true);
        nightPaint.setStrokeWidth(lineWidth);
        nightPaint.setStyle(Paint.Style.STROKE);

        pathDay = new Path();
        pathNight = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (getChildCount() > 0) {
            Log.e("xxx", ">0");
            ViewGroup root = (ViewGroup) getChildAt(0);

            if (root.getChildCount() > 0) {

                float prevDx = 0f;
                float prevDy = 0f;
                float curDx = 0f;
                float curDy = 0f;
                float prevDx1 = 0f;
                float prevDy1 = 0f;
                float curDx1 = 0f;
                float curDy1 = 0f;
                float intensity = 0.25f;

                WeatherItemView c = (WeatherItemView) root.getChildAt(0);
                int dX = c.getTempX();
                int dY = c.getTempY();
                int nX = c.getTempX();
                int nY = c.getTempY();


                TemperatureView tv = (TemperatureView) c.findViewById(R.id.ttv_day);

                tv.setRadius(10);

                int x0 = (int) (dX + tv.getxPointDay());
                int y0 = (int) (dY + tv.getyPointDay());
                int x = (int) (nX + tv.getxPointNight());
                int y = (int) (nY + tv.getyPointNight());

                pathDay.reset();
                pathNight.reset();

                pathDay.moveTo(x0, y0);
                pathNight.moveTo(x, y);

                if (lineType == LINE_TYPE_CURVE) {
                    //曲线
                    for (int i = 1; i < root.getChildCount(); i++) {
                        WeatherItemView preWI = (WeatherItemView) root.getChildAt(Math.max(i-1, 0));
                        WeatherItemView curWI = (WeatherItemView) root.getChildAt(i);
                        WeatherItemView nextWI = (WeatherItemView) root.getChildAt(Math.min(root.getChildCount()-1, i + 1));

                        int dayX0 = preWI.getTempX() + preWI.getWidth() * (i-1);
                        int dayY0 = preWI.getTempY();
                        int nightX0 = preWI.getTempX() + preWI.getWidth() * (i-1);
                        int nightY0 = preWI.getTempY();

                        int dayX = curWI.getTempX() + curWI.getWidth() * i;
                        int dayY = curWI.getTempY();
                        int nightX = curWI.getTempX() + curWI.getWidth() * i;
                        int nightY = curWI.getTempY();

                        int dayX1 = nextWI.getTempX() + nextWI.getWidth() * (i + 1);
                        int dayY1 = nextWI.getTempY();
                        int nightX1 = nextWI.getTempX() + nextWI.getWidth() * (i + 1);
                        int nightY1 = nextWI.getTempY();

                        TemperatureView tempV0 = (TemperatureView) preWI.findViewById(R.id.ttv_day);
                        TemperatureView tempV = (TemperatureView) curWI.findViewById(R.id.ttv_day);
                        TemperatureView tempV1 = (TemperatureView) nextWI.findViewById(R.id.ttv_day);

                        tempV0.setRadius(10);
                        tempV.setRadius(10);
                        tempV1.setRadius(10);

                        //day1
                        int x01 = (int) (dayX0 + tempV0.getxPointDay());
                        int y01 = (int) (dayY0 + tempV0.getyPointDay());

                        //night1
                        int x02 = (int) (nightX0 + tempV0.getxPointNight());
                        int y02 = (int) (nightY0 + tempV0.getyPointNight());

                        //day2
                        int x1 = (int) (dayX + tempV.getxPointDay());
                        int y1 = (int) (dayY + tempV.getyPointDay());
                        //night2
                        int x2 = (int) (nightX + tempV.getxPointNight());
                        int y2 = (int) (nightY + tempV.getyPointNight());

                        //day3
                        int x11 = (int) (dayX1 + tempV1.getxPointDay());
                        int y11 = (int) (dayY1 + tempV1.getyPointDay());
                        //night3
                        int x22 = (int) (nightX1 + tempV1.getxPointNight());
                        int y22 = (int) (nightY1 + tempV1.getyPointNight());
                        Log.e("xxx", "x1=" + x1 + ",y1=" + y1 + ",x11=" + x11 + ",y11=" + y11);

                        prevDx = (x1 - x01) * intensity;
                        prevDy = (y1 - y01) * intensity;
                        curDx = (x11 - x01) * intensity;
                        curDy = (y11 - y01) * intensity;

                        prevDx1 = (x2 - x02) * intensity;
                        prevDy1 = (y2 - y02) * intensity;
                        curDx1 = (x22 - x02) * intensity;
                        curDy1 = (y22 - y02) * intensity;

                        pathDay.cubicTo(
                                x01 + prevDx, y01 + prevDy,
                                x1 - curDx, y1 - curDy,
                                x1, y1);

                        pathNight.cubicTo(
                                x02 + prevDx1, y02 + prevDy1,
                                x2 - curDx1, y2 - curDy1,
                                x2, y2);


                    }

                    canvas.drawPath(pathDay, dayPaint);
                    canvas.drawPath(pathNight, nightPaint);
                } else {
                    //折线
                    for (int i = 0; i < root.getChildCount() - 1; i++) {
                        WeatherItemView child = (WeatherItemView) root.getChildAt(i);
                        WeatherItemView child1 = (WeatherItemView) root.getChildAt(i + 1);
                        int dayX = child.getTempX() + child.getWidth() * i;
                        int dayY = child.getTempY();
                        int nightX = child.getTempX() + child.getWidth() * i;
                        int nightY = child.getTempY();
                        int dayX1 = child1.getTempX() + child1.getWidth() * (i + 1);
                        int dayY1 = child1.getTempY();
                        int nightX1 = child1.getTempX() + child1.getWidth() * (i + 1);
                        int nightY1 = child1.getTempY();

                        Log.e("xxxxx", "i=" + i + ", day x=" + dayX + ", day y=" + dayY + ", night x=" + nightX + ", night y=" + nightY);
                        Log.e("xxxxx", "i=" + i + ", day x1=" + dayX1 + ", day y1=" + dayY1 + ", night x1=" + nightX1 + ", night y1=" + nightY1);

                        TemperatureView tempV = (TemperatureView) child.findViewById(R.id.ttv_day);
                        TemperatureView tempV1 = (TemperatureView) child1.findViewById(R.id.ttv_day);

                        tempV.setRadius(10);
                        tempV1.setRadius(10);

                        int x1 = (int) (dayX + tempV.getxPointDay());
                        int y1 = (int) (dayY + tempV.getyPointDay());
                        int x2 = (int) (nightX + tempV.getxPointNight());
                        int y2 = (int) (nightY + tempV.getyPointNight());

                        int x11 = (int) (dayX1 + tempV1.getxPointDay());
                        int y11 = (int) (dayY1 + tempV1.getyPointDay());
                        int x22 = (int) (nightX1 + tempV1.getxPointNight());
                        int y22 = (int) (nightY1 + tempV1.getyPointNight());
                        Log.e("xxx", "x1=" + x1 + ",y1=" + y1 + ",x11=" + x11 + ",y11=" + y11);

                        canvas.drawLine(x1, y1, x11, y11, dayPaint);
                        canvas.drawLine(x2, y2, x22, y22, nightPaint);


                    }
                }
            }

        }
    }


    public float getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(float lineWidth) {
        this.lineWidth = lineWidth;
        dayPaint.setStrokeWidth(lineWidth);
        nightPaint.setStrokeWidth(lineWidth);
        invalidate();
    }

    public List<WeatherModel> getList() {
        return list;
    }

    public void setOnWeatherItemClickListener(OnWeatherItemClickListener weatherItemClickListener) {
        this.weatherItemClickListener = weatherItemClickListener;
    }

    public void setList(final List<WeatherModel> list) {
        this.list = list;
        int screenWidth = getScreenWidth();
        int maxDay = getMaxDayTemp(list);
        int maxNight = getMaxNightTemp(list);
        int minDay = getMinDayTemp(list);
        int minNight = getMinNightTemp(list);
        int max = maxDay > maxNight ? maxDay : maxNight;
        int min = minDay < minNight ? minDay : minNight;
        removeAllViews();
        LinearLayout llRoot = new LinearLayout(getContext());
        llRoot.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        llRoot.setOrientation(LinearLayout.HORIZONTAL);
        for (int i = 0; i < list.size(); i++) {
            WeatherModel model = list.get(i);
            final WeatherItemView itemView = new WeatherItemView(getContext());
            itemView.setMaxTemp(max);
            itemView.setMinTemp(min);
            itemView.setDate(model.getDate());
            itemView.setWeek(model.getWeek());
            itemView.setDayTemp(model.getDayTemp());
            itemView.setDayWeather(model.getDayWeather());
            if (model.getDayPic() == 0) {
                if (model.getDayWeather() != null){
                    itemView.setDayImg(PicUtil.getDayWeatherPic(model.getDayWeather()));
                }
            } else {
                itemView.setDayImg(model.getDayPic());
            }
            itemView.setNightWeather(model.getNightWeather());
            itemView.setNightTemp(model.getNightTemp());
            if (model.getNightPic() == 0) {
                if (model.getNightWeather() != null){
                    itemView.setNightImg(PicUtil.getNightWeatherPic(model.getNightWeather()));
                }
            } else {
                itemView.setNightImg(model.getNightPic());
            }
            itemView.setWindOri(model.getWindOrientation());
            itemView.setWindLevel(model.getWindLevel());
            itemView.setAirLevel(model.getAirLevel());
            itemView.setLayoutParams(new LinearLayout.LayoutParams(screenWidth / 6, ViewGroup.LayoutParams.WRAP_CONTENT));
            itemView.setClickable(true);
            final int finalI = i;
            itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (weatherItemClickListener != null) {
                        weatherItemClickListener.onItemClick(itemView, finalI, list.get(finalI));
                    }
                }
            });
            llRoot.addView(itemView);
        }
        addView(llRoot);
        invalidate();
    }

    private int getScreenWidth() {
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

    private int getMinDayTemp(List<WeatherModel> list) {
        if (list != null) {
            return Collections.min(list, new DayTempComparator()).getDayTemp();
        }
        return 0;
    }

    private int getMinNightTemp(List<WeatherModel> list) {
        if (list != null) {
            return Collections.min(list, new NightTempComparator()).getNightTemp();
        }
        return 0;
    }


    private int getMaxNightTemp(List<WeatherModel> list) {
        if (list != null) {
            return Collections.max(list, new NightTempComparator()).getNightTemp();
        }
        return 0;
    }

    private int getMaxDayTemp(List<WeatherModel> list) {
        if (list != null) {
            return Collections.max(list, new DayTempComparator()).getDayTemp();
        }
        return 0;
    }

    public int getLineType() {
        return lineType;
    }

    public void setLineType(int lineType) {
        this.lineType = lineType;
        invalidate();
    }

    private static class DayTempComparator implements Comparator<WeatherModel> {

        @Override
        public int compare(WeatherModel o1, WeatherModel o2) {
            if (o1.getDayTemp() == o2.getDayTemp()) {
                return 0;
            } else if (o1.getDayTemp() > o2.getDayTemp()) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    private static class NightTempComparator implements Comparator<WeatherModel> {

        @Override
        public int compare(WeatherModel o1, WeatherModel o2) {
            if (o1.getNightTemp() == o2.getNightTemp()) {
                return 0;
            } else if (o1.getNightTemp() > o2.getNightTemp()) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    public interface OnWeatherItemClickListener {
        void onItemClick(WeatherItemView itemView, int position, WeatherModel weatherModel);
    }

}
