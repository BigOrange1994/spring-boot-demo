package com.example.demo.testutil;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class SaleMethodVersionConfigDTO implements Serializable {

    private Map<String, SaleMethodConfig> configInfo;

    @Data
    public static class SaleMethodConfig{

        // 支持的版本
        private String supportVersion;

        // ab比例
        private String abPercent;
    }

}
