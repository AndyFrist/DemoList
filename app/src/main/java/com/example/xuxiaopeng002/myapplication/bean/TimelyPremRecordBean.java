package com.example.xuxiaopeng002.myapplication.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: ex-xuxiaopeng002
 * @CreateDate: 2019-12-17 15:39
 * @Description: java类作用描述
 */
public class TimelyPremRecordBean implements Serializable {

    /**
     * yesterday : {"min":"6","data":["352.23","18.00","8.00","236.00","534.00","558.00","254.00","226.00","423.00","123.00","324.00","352.00","571.00","246.00","426.00","363.00","256.00","45.00","214.00","214.00","654.00","543.00","453.00","352.00"],"max":"783"}
     * today : {"min":"0","data":["0.00","18.00","8.00","564.00","543.00","0.00","894.00","0.00","43.00","654.00","125.00","135.00","234.00","126.00","642.00","326.00","363.00","13.00","212.00","432.00","324.00","53.00","425.00","315.00"],"max":"1071"}
     * timelyTimes : {"title":"实时台次","value":"211"}
     * timelyPrem : {"unit":"万","title":"实时保费","value":"666.6"}
     * day : 17日
     * sevenDayAvg : {"min":"3","data":["999.99","10.00","4.00","89.00","326.00","421.00","111.00","521.00","232.00","234.00","453.00","368.00","368.00","732.00","453.00","326.00","435.00","100.00","566.00","324.00","236.00","463.00","462.00","653.00"],"max":"1197"}
     * timelyRank : {"title":"保费增速排名","value":"1"}
     */

    private YesterdayBean yesterday;
    private TodayBean today;
    private TimelyTimesBean timelyTimes;
    private TimelyPremBean timelyPrem;
    private String day;
    private SevenDayAvgBean sevenDayAvg;
    private TimelyRankBean timelyRank;
    private String deptDesc;

    public YesterdayBean getYesterday() {
        return yesterday;
    }

    public void setYesterday(YesterdayBean yesterday) {
        this.yesterday = yesterday;
    }

    public TodayBean getToday() {
        return today;
    }

    public void setToday(TodayBean today) {
        this.today = today;
    }

    public TimelyTimesBean getTimelyTimes() {
        return timelyTimes;
    }

    public void setTimelyTimes(TimelyTimesBean timelyTimes) {
        this.timelyTimes = timelyTimes;
    }

    public TimelyPremBean getTimelyPrem() {
        return timelyPrem;
    }

    public void setTimelyPrem(TimelyPremBean timelyPrem) {
        this.timelyPrem = timelyPrem;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public SevenDayAvgBean getSevenDayAvg() {
        return sevenDayAvg;
    }

    public void setSevenDayAvg(SevenDayAvgBean sevenDayAvg) {
        this.sevenDayAvg = sevenDayAvg;
    }

    public TimelyRankBean getTimelyRank() {
        return timelyRank;
    }

    public void setTimelyRank(TimelyRankBean timelyRank) {
        this.timelyRank = timelyRank;
    }

    public void setDeptDesc(String deptDesc) {
        this.deptDesc = deptDesc;
    }

    public String getDeptDesc() {
        return deptDesc;
    }

    public static class YesterdayBean {
        /**
         * min : 6
         * data : ["352.23","18.00","8.00","236.00","534.00","558.00","254.00","226.00","423.00","123.00","324.00","352.00","571.00","246.00","426.00","363.00","256.00","45.00","214.00","214.00","654.00","543.00","453.00","352.00"]
         * max : 783
         */

        private String min;
        private String max;
        private List<String> data;

        public String getMin() {
            return min;
        }

        public void setMin(String min) {
            this.min = min;
        }

        public String getMax() {
            return max;
        }

        public void setMax(String max) {
            this.max = max;
        }

        public List<String> getData() {
            return data;
        }

        public void setData(List<String> data) {
            this.data = data;
        }
    }

    public static class TodayBean {
        /**
         * min : 0
         * data : ["0.00","18.00","8.00","564.00","543.00","0.00","894.00","0.00","43.00","654.00","125.00","135.00","234.00","126.00","642.00","326.00","363.00","13.00","212.00","432.00","324.00","53.00","425.00","315.00"]
         * max : 1071
         */

        private String min;
        private String max;
        private List<String> data;

        public String getMin() {
            return min;
        }

        public void setMin(String min) {
            this.min = min;
        }

        public String getMax() {
            return max;
        }

        public void setMax(String max) {
            this.max = max;
        }

        public List<String> getData() {
            return data;
        }

        public void setData(List<String> data) {
            this.data = data;
        }
    }

    public static class TimelyTimesBean {
        /**
         * title : 实时台次
         * value : 211
         */

        private String title;
        private String value;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class TimelyPremBean {
        /**
         * unit : 万
         * title : 实时保费
         * value : 666.6
         */

        private String unit;
        private String title;
        private String value;

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class SevenDayAvgBean {
        /**
         * min : 3
         * data : ["999.99","10.00","4.00","89.00","326.00","421.00","111.00","521.00","232.00","234.00","453.00","368.00","368.00","732.00","453.00","326.00","435.00","100.00","566.00","324.00","236.00","463.00","462.00","653.00"]
         * max : 1197
         */

        private String min;
        private String max;
        private List<String> data;

        public String getMin() {
            return min;
        }

        public void setMin(String min) {
            this.min = min;
        }

        public String getMax() {
            return max;
        }

        public void setMax(String max) {
            this.max = max;
        }

        public List<String> getData() {
            return data;
        }

        public void setData(List<String> data) {
            this.data = data;
        }
    }

    public static class TimelyRankBean {
        /**
         * title : 保费增速排名
         * value : 1
         */

        private String title;
        private String value;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
