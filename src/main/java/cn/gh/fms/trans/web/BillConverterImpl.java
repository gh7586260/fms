package cn.gh.fms.trans.web;

import cn.gh.fms.BO.PayBill;
import cn.gh.fms.BO.User;
import cn.gh.fms.model.BillModel;
import cn.gh.fms.model.CalMonthResult;
import cn.gh.fms.model.MonthStaticModel;
import cn.gh.fms.model.StaticUserPay;
import cn.gh.fms.server.UserService;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class BillConverterImpl implements BillConverter {

    @Autowired
    private UserService userService;

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

    @Override
    public MonthStaticModel convertMonthStaticModel(String month, List<PayBill> monthPayBills) throws ParseException {
        MonthStaticModel staticModel = new MonthStaticModel();
        Date date = new SimpleDateFormat("yyyy-MM").parse(month);
        staticModel.setCurMonth(new SimpleDateFormat("yyyy年MM月").format(date));
        //用户月统计列表,key:用户ID  value：用户月账单列表
        Map<Long, List<PayBill>> userStaticMap = new HashMap<>();
        List<PayBill> userBills;
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (PayBill payBill : monthPayBills) {
            //计算总值
            totalAmount = totalAmount.add(payBill.getPayPrice());
            //按用户拆分
            if (userStaticMap.containsKey(payBill.getUserId())) {
                userStaticMap.get(payBill.getUserId()).add(payBill);
            } else {
                userBills = new ArrayList<>();
                userBills.add(payBill);
                userStaticMap.put(payBill.getUserId(), userBills);
            }
        }
        List<User> allUsers = this.userService.queryAllUsers();
        int userNum = allUsers.size(); //用户数量
        staticModel.setTotalPayPrice(totalAmount.stripTrailingZeros().toPlainString());
        //用户平均支出
        BigDecimal avgPayPrice = totalAmount.divide(BigDecimal.valueOf(userNum),2, BigDecimal.ROUND_HALF_UP);
        staticModel.setAvgPrice(avgPayPrice.stripTrailingZeros().toPlainString());
        //用户的月支出情况列表
        List<StaticUserPay> staticUserPays = this.calUserPay(allUsers, userStaticMap);
        staticModel.setStaticUserPays(staticUserPays);
        staticModel.setCalResults(this.calGiveMoneyResult(avgPayPrice, staticUserPays));
        return staticModel;
    }

    private List<CalMonthResult> calGiveMoneyResult(BigDecimal avgPrice, List<StaticUserPay> userPays) {
        // 先拆分给钱和收钱的人
        List<TempCalUserPay> giverList = new ArrayList<>();
        List<TempCalUserPay> receiverList = new ArrayList<>();
        TempCalUserPay tempCalUserPay;
        for (StaticUserPay staticUserPay : userPays) {
            int compareValue = avgPrice.compareTo(staticUserPay.getTotalPay());
            if (compareValue > 0) { //支出小于平均值，是该给钱的人
                tempCalUserPay = new TempCalUserPay(staticUserPay.getName(), avgPrice.subtract(staticUserPay.getTotalPay()));
                giverList.add(tempCalUserPay);
            } else if (compareValue < 0) {//支出大于平均值，是该收钱的人
                tempCalUserPay = new TempCalUserPay(staticUserPay.getName(), staticUserPay.getTotalPay().subtract(avgPrice));
                receiverList.add(tempCalUserPay);
            }
            //等于平均值,不给也不出，不管
        }
        List<CalMonthResult> monthResults = new ArrayList<>();
        //执行给钱,给钱的人都给完了结束
        Pair<Integer, BigDecimal> giveMoneyPair;
        for (TempCalUserPay giver : giverList) {
            for (TempCalUserPay receiver : receiverList) {
                if (receiver.getPrice().equals(BigDecimal.ZERO)) {
                    continue;   //收钱的人，收完了，换下个收钱
                }
                giveMoneyPair = this.doGiveMoney(monthResults, giver, receiver);
                if (giveMoneyPair.getKey() > 0) {
                    //收完钱了，计算给钱人还剩多少钱，换下一个收钱
                    giver.setPrice(giver.getPrice().subtract(giveMoneyPair.getValue()));
                    receiver.setPrice(BigDecimal.ZERO);
                    continue;
                }
                break; //给钱的人，给完了，换下一个给钱
            }
        }
        return monthResults;
    }

    //执行一次给钱操作
    private Pair<Integer, BigDecimal> doGiveMoney(List<CalMonthResult> monthResults, TempCalUserPay giver, TempCalUserPay receiver) {
        CalMonthResult calMonthResult = new CalMonthResult();
        calMonthResult.setSpendUserName(giver.getName());
        calMonthResult.setIncomeUserName(receiver.getName());
        int compareValue = giver.getPrice().compareTo(receiver.getPrice());
        BigDecimal realGivePrice = compareValue > 0 ? receiver.getPrice() : giver.getPrice();
        calMonthResult.setPrice(realGivePrice.stripTrailingZeros().toPlainString());
        monthResults.add(calMonthResult);
        return new Pair<>(compareValue, realGivePrice);
    }

    //用户花费列表统计
    private List<StaticUserPay> calUserPay(List<User> allUsers, Map<Long, List<PayBill>> userStaticMap) {
        List<StaticUserPay> staticUserPays = new ArrayList<>();
        StaticUserPay tempUserPay = null;
        for (User user : allUsers) {
            long tempUserId = user.getUserId();
            tempUserPay = new StaticUserPay();
            tempUserPay.setUserId(tempUserId);
            tempUserPay.setName(user.getUserName());
            if (!userStaticMap.containsKey(tempUserId)) {
                tempUserPay.setTotalPay(BigDecimal.ZERO);
            } else {
                tempUserPay.setTotalPay(userStaticMap.get(tempUserId).stream().map(PayBill::getPayPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add));
            }
            staticUserPays.add(tempUserPay);
        }
        return staticUserPays;
    }

    //中间计算的用户给钱（收钱）对象
    private class TempCalUserPay {
        private String name;
        private BigDecimal price;

        public TempCalUserPay(String name, BigDecimal price) {
            this.name = name;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }
    }
}
