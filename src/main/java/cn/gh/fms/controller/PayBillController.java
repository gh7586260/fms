package cn.gh.fms.controller;

import cn.gh.fms.BO.PayBill;
import cn.gh.fms.constant.ErrorCode;
import cn.gh.fms.model.BillModel;
import cn.gh.fms.model.MonthStaticModel;
import cn.gh.fms.result.ListResult;
import cn.gh.fms.result.Result;
import cn.gh.fms.result.ResultUtils;
import cn.gh.fms.server.BillService;
import cn.gh.fms.trans.web.BillConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class PayBillController extends BaseController {

    @Autowired
    private BillService billService;
    @Autowired
    private BillConverter billConverter;

    //打开账单列表页面
    @RequestMapping(value = "/bill/open", method = RequestMethod.GET)
    public String openBill() {
        return "bill/payBill";
    }

    //打开单个账单页面
    @RequestMapping(value = "/open/one/bill", method = RequestMethod.GET)
    public String openOneBill(@RequestParam(value = "curBillId", required = false) Long curBillId, Model model) {
        model.addAttribute("curBillId", curBillId);
        return "bill/oneBill";
    }

    //添加账单
    @RequestMapping(value = "/add/bill", method = RequestMethod.GET)
    public String addBill(@RequestParam("detail") String detail, @RequestParam("payPrice") BigDecimal payPrice,
                          @RequestParam("payTime") String payTime, Model model) throws ParseException {
        if (StringUtils.isEmpty(detail) || StringUtils.isEmpty(payTime) || payPrice.compareTo(BigDecimal.ZERO) <= 0) {
            model.addAttribute("errorMsg", ErrorCode.PARAM_ERROR.getErrorMsg());
            return "systemError";
        }
        Date payDate = new SimpleDateFormat("yyyy-MM-dd").parse(payTime);
        Result<Void> addBillResult = this.billService.addBill(super.getLoginUser(), detail, payPrice, payDate);
        if (!addBillResult.isSuccess()) {
            model.addAttribute("errorMsg", addBillResult.getErrorMsg());
            return "systemError";
        }
        return "redirect:/bill/open";
    }

    //编辑账单
    @RequestMapping(value = "/edit/bill", method = RequestMethod.GET)
    public String addBill(@RequestParam("billId") long billId, @RequestParam("detail") String detail, @RequestParam("payPrice") BigDecimal payPrice,
                          @RequestParam("payTime") String payTime, Model model) throws ParseException {
        Date payDate = new SimpleDateFormat("yyyy-MM-dd").parse(payTime);
        Result<Void> editResult = this.billService.editBill(billId, detail, payPrice, payDate);
        if (!editResult.isSuccess()) {
            model.addAttribute("errorMsg", editResult.getErrorMsg());
            return "systemError";
        }
        return "redirect:/bill/open";
    }

    //删除账单
    @RequestMapping(value = "/delete/bill", method = RequestMethod.GET)
    public String deleteBill(@RequestParam("billId") long billId, Model model) {
        Result<Void> deleteResult = this.billService.delete(billId);
        if (!deleteResult.isSuccess()) {
            model.addAttribute("errorMsg", deleteResult.getErrorMsg());
            return "systemError";
        }
        return "redirect:/bill/open";
    }

    /**
     * 获取单个账单详情
     *
     * @return
     */
    @RequestMapping(value = "/get/bill/info", method = RequestMethod.GET)
    @ResponseBody
    public Result<BillModel> getBillInfo(@RequestParam("billId") long billId) {
        PayBill payBill = this.billService.getById(billId).getResult();
        return ResultUtils.success(this.billConverter.convertOneBill(payBill));
    }

    /**
     * 获取账单列表
     *
     * @param curMonth 当前月份
     * @param userId   为0表示查询全部
     * @return
     */
    @RequestMapping(value = "/query/bills", method = RequestMethod.GET)
    @ResponseBody
    public ListResult<BillModel> queryBills(@RequestParam("curMonth") String curMonth, @RequestParam("userId") long userId) throws ParseException {
        ListResult<BillModel> listResult = new ListResult<>();
        List<PayBill> payBills = this.billService.queryPayBills(curMonth, userId);
        List<BillModel> billModels = this.billConverter.convertBillModels(payBills);
        listResult.setList(billModels);
        return listResult;
    }

    //打开月资金统计页面
    @RequestMapping(value = "/open/month/static", method = RequestMethod.GET)
    public String openMonthStatic(@RequestParam("curMonth") String curMonth, Model model) {
        model.addAttribute("curMonth", curMonth);
        return "bill/monthStatic";
    }

    //获取用户月账单统计信息
    @RequestMapping(value = "/month/bill/static/info", method = RequestMethod.GET)
    @ResponseBody
    public Result<MonthStaticModel> getMonthBillStaticInfo(@RequestParam("month") String month) throws ParseException {
        List<PayBill> monthPayBills = this.billService.queryPayBills(month, 0);
        MonthStaticModel monthStaticModel = this.billConverter.convertMonthStaticModel(month, monthPayBills);
        return ResultUtils.success(monthStaticModel);
    }
}
