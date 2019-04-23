package io.hua.blockchainexplorer1.dao;

import io.hua.blockchainexplorer1.po.Transaction;

public interface TransactionMapper {
    int deleteByPrimaryKey(String txid);

    int insert(Transaction record);

    int insertSelective(Transaction record);

    Transaction selectByPrimaryKey(String txid);

    int updateByPrimaryKeySelective(Transaction record);

    int updateByPrimaryKey(Transaction record);

    void truncate();
}