# ECalendar
日历控件
## ScreenShot
<img src="https://github.com/Edison0716/ECalendar/blob/master/Snapshot.png" width="600" height="2090" alt="范围选择"/><br/>


```
allprojects {
		repositories {
			...
			maven { url 'https://www.jitpack.io' }
		}
	}
  
  dependencies {
	        implementation 'com.github.Edison0716:ECalendar:1.0'
	}
```


### 使用方式
```
public class EHiRangeBaseCalendarView extends BaseCommonCalendarView<RangeCalendarEntity> {
    @Override
    public void drawDayText(Canvas canvas, RangeCalendarEntity item) {
    // 绘制文字
    }
     @Override
    public void drawDayBackground(Canvas canvas, RangeCalendarEntity item) {
    // 绘制背景
    }
     @Override
    public void createCalendarStrategy(List<List<RangeCalendarEntity>> calendarDates) {
    // 创建策略类 比如 范围两点策略  范围三点策略
    }
}
```
### 策略设计模式 自行添加继承
```
public interface ICalendarStrategy<T extends BaseCalendarEntity> {
    /**
     * 点击策略
     * @param clickEntity 点击的日期
     */
    void handleClick(T clickEntity);

    /**
     * 返回选中的那个日期
     * @return 选中得数据
     */
    List<T> getCheckedDates();

    /**
     * 重置
     */
    void reset();

    /**
     * 设置整个外部列表集合
     * @param dateList 外部列表集合
     */
    void setListData(List<List<T>> dateList);
}
```
