package com.isl.outland_horizon.level.capa.data;


import com.isl.outland_horizon.Utils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;


import java.util.HashMap;
import java.util.Map;

public class OhAttributes implements OhInterFace {//能力相关逻辑

   HashMap<String, OhAttribute.ScapeApi> attributeHashMap=new HashMap<>();

   public OhAttributes() {
       //注册几个能力添加几个不限制类型,但是尽量避免列表类
       add("mana",0.0d,Double.MAX_VALUE,0.0d,true);//这里的isSync需要你从网络包里去处理比如杀死实体的时候,将此值设为true,然后设置相关能力的计算后改为false

   }

    @Override
    public CompoundTag saveNbtData(CompoundTag tag) {//将数据存储下来
        if (this.attributeHashMap != null) {
            ListTag tagList = new ListTag();
            this.attributeHashMap.values().forEach(attribute -> {
                CompoundTag dataTag = new CompoundTag();
                dataTag.putString("id", attribute.getId());
                dataTag.putString("min", Utils.ToJson(attribute.getMin()) );
                dataTag.putString("max",Utils.ToJson(attribute.getMax()) );
                dataTag.putString("value",Utils.ToJson(attribute.getValue()) );
                dataTag.putBoolean("isSync",attribute.getSync());
                dataTag.putBoolean("isC2S",attribute.getC2S());
                tagList.add(dataTag);
            });
            tag.put(Utils.SetKey("attribute"), tagList);
        }
        return tag;
    }

    @Override
    public void loadNbtData(CompoundTag tag) {//将数据加载出来
        if (this.attributeHashMap==null){
            this.attributeHashMap=new HashMap<>();
        }
        if (tag.contains(Utils.SetKey("attribute"), 9)) {
                ListTag dataList = tag.getList(Utils.SetKey("attribute"), 10);
                dataList.forEach(dataTag -> {
                    if (dataTag instanceof CompoundTag compoundData) {
                        String id = compoundData.getString("id");
                        Object min = Utils.ToObject(compoundData.getString("min"));
                        Object max = Utils.ToObject(compoundData.getString("max"));
                        Object value = Utils.ToObject(compoundData.getString("value"));
                        boolean isSync=compoundData.getBoolean("isSync");
                        boolean isC2S = compoundData.getBoolean("isC2S");
                        this.attributeHashMap.put(id,create(id,min,max,value,isSync,isC2S));
                    }
                });
        }
    }

    @Override
    public void copy(OhInterFace cap) {//内部方法勿动
        Map<String, OhAttribute.ScapeApi> other = cap.getProfession();
        this.attributeHashMap.putAll(other);
    }

    @Override
    public Map<String, OhAttribute.ScapeApi> getProfession() {
        return this.attributeHashMap;
    }//获取当前存在的能力返回是个HashMap

    public OhAttribute.ScapeApi create(String id, Object min, Object max, Object value, Boolean isSync, Boolean isC2S){
       return new OhAttribute.ScapeApi(id,min,max,value,isSync,isC2S);//辅助方法
    }
    public void add(String id, Object min, Object max, Object value, Boolean isSync, Boolean isC2S){
        attributeHashMap.put(id,create(id,min,max,value,isSync,isC2S));//辅助方法
    }
    public void add(String id, Object min, Object max, Object value){//辅助方法
        attributeHashMap.put(id,create(id,min,max,value,false,false));
    }
    public void add(String id, Object min, Object max, Object value,Boolean isSync){//辅助方法
        attributeHashMap.put(id,create(id,min,max,value,isSync,false));
    }

    public int getSize(){
       return attributeHashMap.size();
    }//取初始化的能力数量

    public OhAttribute.ScapeApi get(String ID){
      return attributeHashMap.get(ID);
    }//获取能力


}
