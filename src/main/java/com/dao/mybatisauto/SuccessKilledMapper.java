package com.dao.mybatisauto;

import com.pojo.mybatisauto.SuccessKilled;
import com.pojo.mybatisauto.SuccessKilledExample;
import com.pojo.mybatisauto.SuccessKilledKey;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface SuccessKilledMapper {
    int countByExample(SuccessKilledExample example);

    int deleteByExample(SuccessKilledExample example);

    int deleteByPrimaryKey(SuccessKilledKey key);

    int insert(SuccessKilled record);

    int insertSelective(SuccessKilled record);

    List<SuccessKilled> selectByExample(SuccessKilledExample example);

    SuccessKilled selectByPrimaryKey(SuccessKilledKey key);

    int updateByExampleSelective(@Param("record") SuccessKilled record, @Param("example") SuccessKilledExample example);

    int updateByExample(@Param("record") SuccessKilled record, @Param("example") SuccessKilledExample example);

    int updateByPrimaryKeySelective(SuccessKilled record);

    int updateByPrimaryKey(SuccessKilled record);
    
    /**
     * 多对一查询出秒杀成功记录以及对应的秒杀商品信息
     * @param key
     * @return
     */
    SuccessKilled selectBySeckillIdWithSeckill(SuccessKilledKey key);
    
    /**
     * 执行秒杀存储过程
     */
    void seckillByProcedure(Map<String,Object> params);
}