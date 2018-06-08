package com.module1.comms;

import java.util.HashMap;
import java.util.Map;

public class R {
    private Map<String,Object> map = null;
    public R() {
        map = new HashMap<>();
    }
    public R putOk(Object value,String msg) {
        map.put("data",value);
        map.put("status","200");
        if(msg != null)
            map.put("msg",msg);
        return this;
    }
    public R putErr(Object value,String msg){
        map.put("data",value);
        map.put("status","400");
        if(msg != null)
            map.put("msg",msg);
        return this;
    }
    public Map getMap(){
        return this.map;
    }
}
