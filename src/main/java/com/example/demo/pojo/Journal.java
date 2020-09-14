package com.example.demo.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;

import javax.persistence.*;

@Entity
@Table(name = "journal_info")
@ExcelTarget(value = "JournalId")
public class Journal {

    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @javax.persistence.Id
    @Column(name = "journal_id")
    private long journalId;  //创建的日志ID

    @Column(name = "journal_message", length = 1000)
    @Excel(name = "日志信息", width = 120)
    private String journalMessage;  //日志信息

    @Column(name = "journal_time")
    @Excel(name = "记录时间", databaseFormat = "yyyy-MM-dd", width = 80)
    private String journalTime;  //日志生成时间

    public long getJournalId() {
        return journalId;
    }

    public void setJournalId(long journalId) {
        this.journalId = journalId;
    }

    public String getJournalMessage() {
        return journalMessage;
    }

    public void setJournalMessage(String journalMessage) {
        this.journalMessage = journalMessage;
    }

    public String getJournalTime() {
        return journalTime;
    }

    public void setJournalTime(String journalTime) {
        this.journalTime = journalTime;
    }
}
