package me.zhouzhuo.zzweatherview;

/**
 * Model of weather
 *
 * @author Zz
 * 2016/12/8 9:42
 */
public class WeatherModel {
    
    /**
     * 白天温度
     */
    private int dayTemp;
    /**
     * 晚上温度
     */
    private int nightTemp;
    /**
     * 白天天气
     */
    private String dayWeather;
    /**
     * 晚上天气
     */
    private String nightWeather;
    /**
     * 日期
     */
    private String date;
    /**
     * 星期
     */
    private String week;
    /**
     * 是否今天
     */
    private boolean isToday;
    
    /**
     * 白天天气图片
     */
    private int dayPic;
    
    /**
     * 晚上天气图片
     */
    private int nightPic;
    
    /**
     * 风向
     */
    private String windOrientation;
    /**
     * 风级
     */
    private String windLevel;
    /**
     * 空气级别
     */
    private AirLevel airLevel;
    
    public int getDayTemp() {
        return dayTemp;
    }
    
    public void setDayTemp(int dayTemp) {
        this.dayTemp = dayTemp;
    }
    
    public int getNightTemp() {
        return nightTemp;
    }
    
    public void setNightTemp(int nightTemp) {
        this.nightTemp = nightTemp;
    }
    
    public String getDayWeather() {
        return dayWeather;
    }
    
    public void setDayWeather(String dayWeather) {
        this.dayWeather = dayWeather;
    }
    
    public String getNightWeather() {
        return nightWeather;
    }
    
    public void setNightWeather(String nightWeather) {
        this.nightWeather = nightWeather;
    }
    
    public String getDate() {
        return date;
    }
    
    public void setDate(String date) {
        this.date = date;
    }
    
    public String getWeek() {
        return week;
    }
    
    public void setWeek(String week) {
        this.week = week;
    }
    
    public boolean isToday() {
        return isToday;
    }
    
    public void setToday(boolean today) {
        isToday = today;
    }
    
    public int getDayPic() {
        return dayPic;
    }
    
    public void setDayPic(int dayPic) {
        this.dayPic = dayPic;
    }
    
    public int getNightPic() {
        return nightPic;
    }
    
    public void setNightPic(int nightPic) {
        this.nightPic = nightPic;
    }
    
    public String getWindOrientation() {
        return windOrientation;
    }
    
    public void setWindOrientation(String windOrientation) {
        this.windOrientation = windOrientation;
    }
    
    public String getWindLevel() {
        return windLevel;
    }
    
    public void setWindLevel(String windLevel) {
        this.windLevel = windLevel;
    }
    
    public AirLevel getAirLevel() {
        return airLevel;
    }
    
    public void setAirLevel(AirLevel airLevel) {
        this.airLevel = airLevel;
    }
}
