package cn.gh.fms.model;

import java.math.BigDecimal;

/**
 * 统计用户支出
 */
public class StaticUserPay {

    private long userId;
    private String name;
    private BigDecimal totalPay;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getTotalPay() {
        return totalPay;
    }

    public void setTotalPay(BigDecimal totalPay) {
        this.totalPay = totalPay;
    }
}
