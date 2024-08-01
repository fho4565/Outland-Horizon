package com.isl.outland_horizon.level.capa.data;

import com.isl.outland_horizon.Utils;
import net.minecraft.network.FriendlyByteBuf;

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



        public static OhAttribute.ScapeApi deserialize(FriendlyByteBuf buffer) {//序列化
            String ID = buffer.readUtf();
            OhAttribute.TAPI Json = Utils.ToObject(buffer.readUtf());
            return new ScapeApi(ID,Json.min,Json.max,Json.value,buffer.readBoolean(),buffer.readBoolean());
        }
        public void serialize(FriendlyByteBuf buf) {//反序列化
            buf.writeUtf(this.id);
            OhAttribute.TAPI API=new OhAttribute.TAPI();
            API.min=this.min;
            API.max=this.max;
            API.value=this.value;
            buf.writeUtf(Utils.ToJson(API));
            buf.writeBoolean(this.isSync);
            buf.writeBoolean(this.isC2S);
        }


        public void copyFrom(OhAttribute.ScapeApi attribute) {
            if (this.id.equals(attribute.id)) {
                this.min = attribute.min;
                this.max = attribute.max;
                this.value = attribute.value;
                this.isSync = attribute.isSync;
                this.isC2S = attribute.isC2S;
            }
        }

    }








    public static class TAPI{
        public Object min;
        public Object max;
        public Object value;
    }






}
