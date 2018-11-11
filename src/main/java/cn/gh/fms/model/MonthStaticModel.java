package cn.gh.fms.model;

import java.util.List;

public class MonthStaticModel {

    private String curMonth;
    private List<StaticUserPay> staticUserPays;
    private String totalPayPrice;
    private String avgPrice;
    private List<CalMonthResult> calResults;

    public String getCurMonth() {
        return curMonth;
    }

    public void setCurMonth(String curMonth) {
        this.curMonth = curMonth;
    }

    public List<StaticUserPay> getStaticUserPays() {
        return staticUserPays;
    }

    public void setStaticUserPays(List<StaticUserPay> staticUserPays) {
        this.staticUserPays = staticUserPays;
    }

    public String getTotalPayPrice() {
        return totalPayPrice;
    }

    public void setTotalPayPrice(String totalPayPrice) {
        this.totalPayPrice = totalPayPrice;
    }

    public String getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(String avgPrice) {
        this.avgPrice = avgPrice;
    }

    public List<CalMonthResult> getCalResults() {
        return calResults;
    }

    public void setCalResults(List<CalMonthResult> calResults) {
        this.calResults = calResults;
    }
}
