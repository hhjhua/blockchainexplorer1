package io.hua.blockchainexplorer1.dao;

import io.hua.blockchainexplorer1.po.TransactionDetail;
import io.hua.blockchainexplorer1.po.TransactionDetailKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TransactionDetailMapper {
    int deleteByPrimaryKey(TransactionDetailKey key);

    int insert(TransactionDetail record);

    int insertSelective(TransactionDetail record);

    TransactionDetail selectByPrimaryKey(TransactionDetailKey key);

    int updateByPrimaryKeySelective(TransactionDetail record);

    int updateByPrimaryKey(TransactionDetail record);

    int  truncate();

    List<TransactionDetail> selectByAddress(@Param("address") String address);
}