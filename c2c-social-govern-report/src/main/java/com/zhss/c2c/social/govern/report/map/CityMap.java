package com.zhss.c2c.social.govern.report.map;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: LiaoLingJian
 * @Description: ${description}
 * @Date: 2021/8/19 20:30
 * @Version: 1.0
 */
public class CityMap {
    private static   Map<String,String> cityMap = new HashMap<String,String>();



    static {
        cityMap.put("1","北京");
    }


    public  static  String getCityName(String cityId){
        return  cityMap.get(cityId);
    }

}
