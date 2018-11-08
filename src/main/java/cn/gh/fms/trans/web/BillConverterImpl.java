package cn.gh.fms.trans.web;

import cn.gh.fms.BO.PayBill;
import cn.gh.fms.model.BillModel;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BillConverterImpl implements BillConverter {

    @Override
    public List<BillModel> convertBillModels(List<PayBill> payBills) {
        if (payBills == null) {
            return null;
        }
        return payBills.stream().map(payBill -> this.convertOneBill(payBill)).collect(Collectors.toList());
    }

    @Override
    public BillModel convertOneBill(PayBill payBill) {
        BillModel billModel = new BillModel();
        billModel.setBillId(payBill.getId());
        billModel.setUserId(payBill.getUserId());
        billModel.setUserName(payBill.getUserName());
        billModel.setDetail(payBill.getDetail());
        billModel.setPayPrice(payBill.getPayPrice().stripTrailingZeros().toPlainString());
        billModel.setPayTime(new SimpleDateFormat("yyyy-MM-dd").format(payBill.getPayTime()));
        return billModel;
    }
}
