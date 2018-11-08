package cn.gh.fms.server;

import cn.gh.fms.BO.PayBill;
import cn.gh.fms.BO.User;
import cn.gh.fms.DO.PayBillDO;
import cn.gh.fms.constant.ErrorCode;
import cn.gh.fms.mapper.BillMapper;
import cn.gh.fms.result.Result;
import cn.gh.fms.result.ResultUtils;
import cn.gh.fms.trans.dao.PayBillTrans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class BillServiceImpl implements BillService {

    @Autowired
    private UserService userService;
    @Autowired
    private BillMapper billMapper;

    @Override
    public Result<Void> addBill(long userId, String detail, BigDecimal payPrice, Date payDate) {
        User user = this.userService.getById(userId).getResult();
        if (user == null) {
            return ResultUtils.error(ErrorCode.USER_NOT_EXIST);
        }
        PayBill payBill = new PayBill();
        payBill.setUserId(userId);
        payBill.setUserName(user.getUserName());
        payBill.setDetail(detail);
        payBill.setPayPrice(payPrice);
        payBill.setPayTime(payDate);
        this.billMapper.insert(PayBillTrans.toDO(payBill));
        return ResultUtils.success();
    }

    @Override
    public Result<Void> editBill(long billId, String detail, BigDecimal payPrice, Date payDate) {
        this.billMapper.update(billId, detail, payPrice, payDate);
        return ResultUtils.success();
    }

    @Override
    public Result<PayBill> getById(long billId) {
        PayBillDO payBillDO = this.billMapper.getById(billId);
        return ResultUtils.success(PayBillTrans.toBO(payBillDO));
    }

    @Override
    public List<PayBill> queryPayBills(String queryMonth, long userId) {
        List<PayBillDO> payBillDOS = this.billMapper.queryMonthBills(queryMonth, userId == 0 ? null : userId);
        return PayBillTrans.toBOList(payBillDOS);
    }
}

