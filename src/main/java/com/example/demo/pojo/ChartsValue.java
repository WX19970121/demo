package com.example.demo.pojo;

import javax.persistence.*;

@Entity
@Table(name = "charts_value")
public class ChartsValue {

    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @javax.persistence.Id
    @Column(name = "charts_value_id")
    private long chartsValueId; //Id

    @Column(name = "value_name")
    private String name; //表格的信息

    @Column(name = "value")
    private int value;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private ChartsTitle chartsTitle;

    public long getChartsValueId() {
        return chartsValueId;
    }

    public void setChartsValueId(long chartsValueId) {
        this.chartsValueId = chartsValueId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public ChartsTitle getChartsTitle() {
        return chartsTitle;
    }

    public void setChartsTitle(ChartsTitle chartsTitle) {
        this.chartsTitle = chartsTitle;
    }
}
