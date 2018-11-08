package cn.gh.fms.trans.dao;

import cn.gh.fms.BO.PayBill;
import cn.gh.fms.DO.PayBillDO;

import java.util.List;
import java.util.stream.Collectors;

public class PayBillTrans {

    public final static PayBill toBO(PayBillDO payBillDO){
        PayBill payBill = new PayBill();
        payBill.setId(payBillDO.getId());
        payBill.setUserId(payBillDO.getUserId());
        payBill.setUserName(payBillDO.getUserName());
        payBill.setDetail(payBillDO.getDetail());
        payBill.setPayPrice(payBillDO.getPayPrice());
        payBill.setPayTime(payBillDO.getPayTime());
        payBill.setGmtCreated(payBillDO.getGmtCreated());
        payBill.setGmtLastModified(payBillDO.getGmtLastModified());
        return payBill;
    }

    public final static List<PayBill> toBOList(List<PayBillDO> payBillDOS){
        if(payBillDOS==null){
            return null;
        }
        return payBillDOS.stream().map(payBillDO -> toBO(payBillDO)).collect(Collectors.toList());
    }

    public final static PayBillDO toDO(PayBill payBill){
        PayBillDO payBillDO = new PayBillDO();
        payBillDO.setId(payBill.getId());
        payBillDO.setUserId(payBill.getUserId());
        payBillDO.setUserName(payBill.getUserName());
        payBillDO.setDetail(payBill.getDetail());
        payBillDO.setPayPrice(payBill.getPayPrice());
        payBillDO.setPayTime(payBill.getPayTime());
        payBillDO.setGmtCreated(payBill.getGmtCreated());
        payBillDO.setGmtLastModified(payBill.getGmtLastModified());
        return payBillDO;
    }
}
