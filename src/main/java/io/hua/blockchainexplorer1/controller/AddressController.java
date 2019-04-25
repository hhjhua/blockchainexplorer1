package io.hua.blockchainexplorer1.controller;

import io.hua.blockchainexplorer1.dao.TransactionDetailMapper;

import io.hua.blockchainexplorer1.dto.AddressInfo;
import io.hua.blockchainexplorer1.po.TransactionDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SuppressWarnings("ALL")
@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private TransactionDetailMapper transactionDetailMapper;

    @GetMapping("/getAddressInfo")
    public AddressInfo getAddressInfo(@RequestParam String address){
        return null;
    }

    @GetMapping("/getAddressTransactions")
    public List<TransactionDetail> getAddressTransactions(@RequestParam String address,
                                                          @RequestParam(required = false, defaultValue = "1") Integer pageNum){
        List<TransactionDetail> transactionDetails = transactionDetailMapper.selectByAddress(address);
        return transactionDetails;

    }
}
