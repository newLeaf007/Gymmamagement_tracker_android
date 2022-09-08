package com.example.gymmamagement.modals;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Payment {
    @SerializedName("id")
    private Integer id;

    @SerializedName("amount")
    private Integer amount;

    @SerializedName("month")
    private String month;

    @SerializedName("type")
    private String type;

    @SerializedName("year")
    private String year;

    @SerializedName("addedOn")
    private Date addedOn;

    public Payment() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Date getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(Date addedOn) {
        this.addedOn = addedOn;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", amount=" + amount +
                ", month='" + month + '\'' +
                ", type='" + type + '\'' +
                ", year='" + year + '\'' +
                ", addedOn=" + addedOn +
                '}';
    }
}
