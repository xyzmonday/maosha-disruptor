package com.yff.maosha.mapper;

import com.yff.maosha.entity.ItemOrder;
import com.yff.maosha.entity.ItemOrderExample;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface ItemOrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ItemOrder record);

    int insertSelective(ItemOrder record);

    List<ItemOrder> selectByExample(ItemOrderExample example);

    ItemOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ItemOrder record);

    int updateByPrimaryKey(ItemOrder record);

    void batchInsert(List<ItemOrder> list);
}