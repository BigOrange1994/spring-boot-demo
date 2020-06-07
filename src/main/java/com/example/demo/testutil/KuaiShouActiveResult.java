package com.example.demo.testutil;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Data
public class KuaiShouActiveResult {

    private Integer result_type;

    private String id;

    private String did_md5;

}
