package com.finsall.dto;

public class InsuranceCompany {
    private Integer id;
    private  String name;

    public InsuranceCompany(Integer id, String name) {
        this.id=id;
        this.name=name;
    }

    @Override
    public String toString() {
        return name;
    }
}
