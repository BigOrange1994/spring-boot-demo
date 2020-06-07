package com.example.demo.testutil;


public class KuaiShouActiveResult11 {

    public KuaiShouActiveResult11(Integer result_type, String id1) {
        this.result_type = result_type;
        this.id1 = id1;
    }

    private Integer result_type;

    public Integer getResult_type() {
        return result_type;
    }

    public void setResult_type(Integer result_type) {
        this.result_type = result_type;
    }

    public String getId() {
        return id1;
    }

    public void setId(String id1) {
        this.id1 = id1;
    }

    private String id1;


    @Override
    public String toString() {
        return "KuaiShouActiveResult{" +
                "result_type=" + result_type +
                ", id1='" + id1 + '\'' +
                '}';
    }

}
