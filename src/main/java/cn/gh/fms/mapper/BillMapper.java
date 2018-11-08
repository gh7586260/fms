package cn.gh.fms.mapper;

import cn.gh.fms.DO.PayBillDO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface BillMapper {

    /**
     * 插入一条消费记录
     *
     * @param payBillDO
     * @return
     */
    boolean insert(PayBillDO payBillDO);

    /**
     * 根据ID获得消费单
     *
     * @param id
     * @return
     */
    PayBillDO getById(@Param("id") long id);

    /**
     * 获取时间范围内的消费单列表
     *
     * @param queryMonth  '2014-04'  月份
     * @param userId    为null时查询所有用户
     * @return
     */
    List<PayBillDO> queryMonthBills(@Param("queryMonth") String queryMonth, @Param("userId") Long userId);

    /**
     * 更新消费单
     *
     * @param detail
     * @param payPrice
     * @param payTime
     * @return
     */
    boolean update(@Param("id") long id, @Param("detail") String detail, @Param("payPrice") BigDecimal payPrice, @Param("payTime") Date payTime);

    /**
     * 删除消费单
     *
     * @param id
     * @return
     */
    boolean delete(@Param("id") long id);
}
