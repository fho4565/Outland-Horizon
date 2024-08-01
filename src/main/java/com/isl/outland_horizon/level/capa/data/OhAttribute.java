package com.isl.outland_horizon.level.capa.data;

public class OhAttribute {

    public static class ScapeApi{//提供者
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Object getMin() {
            return min;
        }

        public void setMin(Object min) {
            this.min = min;
        }

        public Object getMax() {
            return max;
        }

        public void setMax(Object max) {
            this.max = max;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public Boolean getSync() {
            return isSync;
        }

        public void setSync(Boolean sync) {
            isSync = sync;
        }

        public Boolean getC2S() {
            return isC2S;
        }

        public void setC2S(Boolean c2S) {
            isC2S = c2S;
        }

        public ScapeApi(String id, Object min, Object max, Object value, Boolean isSync, Boolean isC2S) {
            this.id = id;
            this.min = min;
            this.max = max;
            this.value = value;
            this.isSync = isSync;
            this.isC2S = isC2S;
        }
        String id;
        Object min,max,value;
        Boolean isSync,isC2S;
    }











}
