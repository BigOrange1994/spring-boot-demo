package com.example.demo.testutil;


import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProtoBufResult {


    public static void main(String[] args) {
       /* KuaiShouActiveResult test = new KuaiShouActiveResult(1, "23", "aaijaijai");
        byte[] bytes = ProtoBufUtil.serializer(test);
        for (byte b : bytes) {
            System.out.print(b);
        }
        System.out.println();
        System.out.println(bytes);
        System.out.println();
        System.out.println("反序列化");
        KuaiShouActiveResult d = ProtoBufUtil.deserializer(bytes, KuaiShouActiveResult.class);
        System.out.println(d);
        Integer a = new Integer (100);
        Integer b = 100;
        System.out.println(a.equals(b));

        LocalDateTime localDateTime2 = LocalDateTime.ofInstant(Instant.ofEpochMilli(1583391851118L),ZoneOffset.ofHours(8));
        System.out.println(localDateTime2.getYear());*/

        String a1 = "{\"COUPON_RECYCLE_NORMAL\":{\"supportVersion\":\"7.7\",\"abPercent\":\"50\"},\"MOBILE_BAOMAI_NORMAL\":{\"supportVersion\":\"\",\"abPercent\":\"\"},\"SHUMA_EARPHONE_BAOMAI_NORMAL\":{\"supportVersion\":\"\",\"abPercent\":\"\"},\"BOOK_RECYCLE_NORMAL\":{\"supportVersion\":\"\",\"abPercent\":\"\"},\"WENWAN\":{\"supportVersion\":\"\",\"abPercent\":\"\"},\"GAME\":{\"supportVersion\":\"\",\"abPercent\":\"\"},\"PAD_BAOMAI_NORMAL\":{\"supportVersion\":\"\",\"abPercent\":\"\"},\"SHUMA_LAPTOP_BAOMAI_NORMAL\":{\"supportVersion\":\"\",\"abPercent\":\"\"},\"SELF\":{\"supportVersion\":\"\",\"abPercent\":\"\"}}";
        SaleMethodVersionConfigDTO saleMethodVersionConfig = null;
        try {
            //saleMethodVersionConfig = JSONObject.parseObject(a1, SaleMethodVersionConfigDTO.class);
            Map<String, SaleMethodVersionConfigDTO.SaleMethodConfig> saleMethodVersionConfig1 = JSONObject.parseObject(a1, Map.class);
            System.out.println(saleMethodVersionConfig1.values());

        } catch (Exception e) {


        }
        System.out.println(a1.endsWith(""));

        String str = String.format("%04d", 0);
        System.out.println(str);

        String title = "";
        String content = "测试1111";
        // 标题处理
        if(StringUtils.isBlank(title) && StringUtils.isNotBlank(content)){
            Integer length = content.length() >= 6? 6 : content.length();
            title = content.substring(0, length);
            System.out.println(title);
        }

        String bb = "1234";
        System.out.println(bb.substring(0, bb.length() -3));

        String result = "";
        String[] picsArr = result.split("\\|", -1);
        List<String> picList = new ArrayList<>();
        picList.addAll(Arrays.asList(picsArr));
        if(picList.contains("22")){
            picList.remove("22");
        }
        result = picList.stream().collect(Collectors.joining("|"));
        System.out.println(result);

        System.out.println(JSONObject.toJSONString(null));

        /*StringBuilder s1 = new StringBuilder();
        s1.deleteCharAt(s1.length() - 1);
        System.out.println(s1);*/

        StringBuilder s11 = new StringBuilder();
        s11.append("&");
        System.out.println(s11.substring(0, s11.length()) + "*");

        Integer aaaa = null;
        aaaa=2;
        System.out.println(aaaa);


    }

}
