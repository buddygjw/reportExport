package com.gjw.export.test;

import com.gjw.export.Cell;

import java.io.Serializable;

/**
 * Created by Administrator on 14-5-6.
 */
public class Student implements Serializable {

    @Cell(title="学号")
    private int id;
    @Cell(title="姓名")
    private String name;
    @Cell(title="地址")
    private String address;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;

    }

    public Student(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public Student() {
        // TODO Auto-generated constructor stub
    }

}
