package io.hua.blockchainexplorer1.dao;

import io.hua.blockchainexplorer1.po.TransactionDetail;
import io.hua.blockchainexplorer1.po.TransactionDetailKey;

import java.util.List;

public interface TransactionDetailMapper {
    int deleteByPrimaryKey(TransactionDetailKey key);

    int insert(TransactionDetail record);

    int insertSelective(TransactionDetail record);

    TransactionDetail selectByPrimaryKey(TransactionDetailKey key);

    int updateByPrimaryKeySelective(TransactionDetail record);

    int updateByPrimaryKey(TransactionDetail record);

    void truncate();

    List<TransactionDetail> selectByAddress(String address);
}