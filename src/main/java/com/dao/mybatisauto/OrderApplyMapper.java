package com.dao.mybatisauto;

import com.pojo.mybatisauto.OrderApply;
import com.pojo.mybatisauto.OrderApplyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderApplyMapper {
    int countByExample(OrderApplyExample example);

    int deleteByExample(OrderApplyExample example);

    int deleteByPrimaryKey(Long applyId);

    int insert(OrderApply record);

    int insertSelective(OrderApply record);

    List<OrderApply> selectByExample(OrderApplyExample example);

    OrderApply selectByPrimaryKey(Long applyId);

    int updateByExampleSelective(@Param("record") OrderApply record, @Param("example") OrderApplyExample example);

    int updateByExample(@Param("record") OrderApply record, @Param("example") OrderApplyExample example);

    int updateByPrimaryKeySelective(OrderApply record);

    int updateByPrimaryKey(OrderApply record);
}