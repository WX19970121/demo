package com.example.demo.pojo;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "charts_title")
public class ChartsTitle {

    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @javax.persistence.Id
    @Column(name = "charts_title_id")
    private long chartsTitleId; //Id

    @Column(name = "title_name")
    private String titleName;

    @OneToMany(mappedBy="chartsTitle", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    private List<ChartsValue> chartsValueList;

    public long getChartsTitleId() {
        return chartsTitleId;
    }

    public void setChartsTitleId(long chartsTitleId) {
        this.chartsTitleId = chartsTitleId;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public List<ChartsValue> getChartsValueList() {
        return chartsValueList;
    }

    public void setChartsValueList(List<ChartsValue> chartsValueList) {
        this.chartsValueList = chartsValueList;
    }
}
