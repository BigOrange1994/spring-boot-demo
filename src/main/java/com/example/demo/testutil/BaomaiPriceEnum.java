package com.example.demo.testutil;

import java.util.HashMap;
import java.util.Map;

/**
 * 保卖价枚举类
 */
public enum BaomaiPriceEnum {

    // 手机保卖价
    PRICE_MOBILE_BAOMAI_NORMAL("PRICE_MOBILE_BAOMAI_NORMAL", 0),

    // 数码-耳机保卖价
    PRICE_SHUMA_EARPHONE_BAOMAI_NORMAL("PRICE_SHUMA_EARPHONE_BAOMAI_NORMAL", 1),

    // 平板保卖
    PRICE_PAD_BAOMAI_NORMAL("PRICE_PAD_BAOMAI_NORMAL", 2);

    private Integer method;

    private String name;

    BaomaiPriceEnum(String name, Integer method) {
        this.name = name;
        this.method = method;
    }

    public Integer getMethod() {
        return method;
    }

    public String getName() {
        return name;
    }


    private static  Integer getTest(BaomaiPriceEnum cus, Map<BaomaiPriceEnum , Integer> testMap){
        System.out.println(cus.hashCode());
        return testMap.get(cus);
    }

    public static void main(String[] args) {
        Map<BaomaiPriceEnum , Integer> testMap = new HashMap<>();
        testMap.put(BaomaiPriceEnum.PRICE_MOBILE_BAOMAI_NORMAL, 1);
        testMap.put(BaomaiPriceEnum.PRICE_PAD_BAOMAI_NORMAL, 2);
        System.out.println(getTest(BaomaiPriceEnum.PRICE_PAD_BAOMAI_NORMAL, testMap));

    }



}
