package cn.gh.fms.model;

public class CalMonthResult {

    //给钱人用户名
    private String spendUserName;
    //收钱人用户名
    private String incomeUserName;
    //金额
    private String price;

    public String getSpendUserName() {
        return spendUserName;
    }

    public void setSpendUserName(String spendUserName) {
        this.spendUserName = spendUserName;
    }

    public String getIncomeUserName() {
        return incomeUserName;
    }

    public void setIncomeUserName(String incomeUserName) {
        this.incomeUserName = incomeUserName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
