package cn.gh.fms.server;

import cn.gh.fms.BO.PayBill;
import cn.gh.fms.result.Result;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface BillService {

    //新增
    Result<Void> addBill(long userId, String detail, BigDecimal payPrice, Date payDate);

    //编辑
    Result<Void> editBill(long billId, String detail, BigDecimal payPrice, Date payDate);

    //根据ID获取账单信息
    Result<PayBill> getById(long billId);

    /**
     * 查询消费账单列表
     *
     * @param queryMonth '2014-04' 查询月份
     * @param userId     为0表示查询全部
     * @return
     */
    List<PayBill> queryPayBills(String queryMonth, long userId);
}
