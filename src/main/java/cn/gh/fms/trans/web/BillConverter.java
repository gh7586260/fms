package cn.gh.fms.trans.web;

import cn.gh.fms.BO.PayBill;
import cn.gh.fms.model.BillModel;
import cn.gh.fms.model.MonthStaticModel;

import java.text.ParseException;
import java.util.List;

public interface BillConverter {

    /**
     * 装换消费账单模型
     *
     * @param payBills
     * @return
     */
    List<BillModel> convertBillModels(List<PayBill> payBills);

    /**
     * 转换单个账单模型
     *
     * @param payBill
     * @return
     */
    BillModel convertOneBill(PayBill payBill);

    /**
     * 转换月账单统计模型
     *
     * @param month
     * @param monthPayBills
     * @return
     */
    MonthStaticModel convertMonthStaticModel(String month, List<PayBill> monthPayBills) throws ParseException;
}
