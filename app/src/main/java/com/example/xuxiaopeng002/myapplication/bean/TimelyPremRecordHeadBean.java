package com.example.xuxiaopeng002.myapplication.bean;

import java.io.Serializable;

public class TimelyPremRecordHeadBean implements Serializable {
//    "timelyTimes":{
//        "title":"实时台次",
//                "value":"0"
//    },
//            "timelyPrem":{
//        "title":"车险保费",
//                "value":"0",
//                "unit":"万"
//    },
//            "timelyRank":{//总部角色不返回这个字段
//        "title":"保费增速排名",
//                "value":"--"
//    }

    private TimelyTimesBean timelyTimes;
    private TimelyPremBean timelyPrem;
    private TimelyRankBean timelyRank;
    private String systemRoleCode;      //1前线  2后线   3总部
    private String departmentLevel;     //二级机构为2  三级机构为3

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

    public TimelyRankBean getTimelyRank() {
        return timelyRank;
    }

    public void setTimelyRank(TimelyRankBean timelyRank) {
        this.timelyRank = timelyRank;
    }

    public String getsystemRoleCode() {
        return systemRoleCode;
    }

    public String getDepartmentLevel() {
        return departmentLevel;
    }

    public void setsystemRoleCode(String systemRoleCode) {
        this.systemRoleCode = systemRoleCode;
    }

    public void setDepartmentLevel(String departmentLevel) {
        this.departmentLevel = departmentLevel;
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
