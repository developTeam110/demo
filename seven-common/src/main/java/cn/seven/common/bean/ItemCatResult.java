package cn.seven.common.bean;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ItemCatResult {

//    @JsonProperty("data")//指定对象序列化json的名称
    private List<ItemCatData> itemCats = new ArrayList<ItemCatData>();
    
    @JsonProperty("data")
    public List<ItemCatData> getItemCats() {
        return itemCats;
    }
    public void setItemCats(List<ItemCatData> itemCats) {
        this.itemCats = itemCats;
    }

}
