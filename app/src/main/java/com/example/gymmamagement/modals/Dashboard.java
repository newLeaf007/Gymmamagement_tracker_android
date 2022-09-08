package com.example.gymmamagement.modals;

import com.google.gson.annotations.SerializedName;

public class Dashboard {

    @SerializedName("totlaMmember")
    private Integer totalMmember;

    @SerializedName("activeTotalMember")
    private Integer activeTotalMember;

    @SerializedName("totalPayment")
    private Integer totalPayment;

    public Dashboard() {
    }

    public Integer getTotalMmember() {
        return totalMmember;
    }

    public void setTotalMmember(Integer totalMmember) {
        this.totalMmember = totalMmember;
    }

    public Integer getActiveTotalMember() {
        return activeTotalMember;
    }

    public void setActiveTotalMember(Integer activeTotalMember) {
        this.activeTotalMember = activeTotalMember;
    }

    public Integer getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(Integer totalPayment) {
        this.totalPayment = totalPayment;
    }

    @Override
    public String toString() {
        return "Dashboard{" +
                "totalMmember=" + totalMmember +
                ", activeTotalMember=" + activeTotalMember +
                ", totalPayment=" + totalPayment +
                '}';
    }
}
