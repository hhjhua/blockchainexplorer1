package io.hua.blockchainexplorer1.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.hua.blockchainexplorer1.api.BitcoinApi;
import io.hua.blockchainexplorer1.api.BitcoinJsonRpcClient;
import io.hua.blockchainexplorer1.dao.BlockMapper;
import io.hua.blockchainexplorer1.dao.TransactionDetailMapper;
import io.hua.blockchainexplorer1.dao.TransactionMapper;
import io.hua.blockchainexplorer1.enumeration.TransactionDetailType;
import io.hua.blockchainexplorer1.po.Block;
import io.hua.blockchainexplorer1.po.Transaction;
import io.hua.blockchainexplorer1.po.TransactionDetail;
import io.hua.blockchainexplorer1.service.MiscService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;

@SuppressWarnings("ALL")
@Service
public class MiscServiceImpl implements MiscService {

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BitcoinApi bitcoinApi;

    @Autowired
    private BitcoinJsonRpcClient bitcoinJsonRpcClient;

    @Autowired
    private BlockMapper blockMapper;

    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private TransactionDetailMapper transactionDetailMapper;



    @Async
    @Override
    public void importFromHeight(Integer blockHeight, Boolean isClean) {

    }

    @Async
    @Override
    public void importFromHash(String blockHash, Boolean isClean) throws Throwable {
       if (isClean){
           blockMapper.truncate();
           transactionDetailMapper.truncate();
           transactionMapper.truncate();
       }
       String temphash=blockHash;
       while (temphash!=null&&!temphash.isEmpty()){
           JSONObject blockOrigin = bitcoinApi.getBlock(temphash);
           Block block=new Block();
           block.setBlockhash(blockOrigin.getString("hash"));
           block.setBlockchainId(2);
           block.setHeight(blockOrigin.getInteger("height"));
           Long time=blockOrigin.getLong("time");
           Date date=new Date(time * 10000);
           block.setTime(date);
           JSONArray txes=blockOrigin.getJSONArray("tx");
           for (int i=0;i<txes.size();i++){
               importTx(txes.getJSONObject(i),temphash,date);
               
           }
           block.setTxSize(txes.size());
           block.setSizeOnDisk(blockOrigin.getLong("size"));
           block.setDifficulty(blockOrigin.getDouble("difficulty"));
           block.setPrevBlockhash(blockOrigin.getString("previousblockhash"));
           block.setNextBlockhash(blockOrigin.getString("nextblockhash"));
           block.setMerkleRoot(blockOrigin.getString("merkleroot"));
           blockMapper.insert(block);

           temphash=blockOrigin.getString("nextblockhash");

       }

       logger.info("sync finished");
    }

    private void importTx(JSONObject tx, String blockhash, Date time) {
        Transaction transaction=new Transaction();
        String txid=tx.getString("txid");
        transaction.setTxid(txid);
        transaction.setTxhash(tx.getString("hash"));
        transaction.setBlockhash(blockhash);
        transaction.setSize(tx.getLong("size"));
        transaction.setWeight(tx.getInteger("weight"));
        transaction.setTime(time);
        transactionMapper.insert(transaction);

        JSONArray vouts = tx.getJSONArray("vout");
        for (int i = 0; i < vouts.size(); i++) {
            importVinDetail(vouts.getJSONObject(i),txid);
        }

        JSONArray vins = tx.getJSONArray("vin");

        for (int i = 1; i < vins.size(); i++) {
            importVinDetail(vins.getJSONObject(i),txid);
        }
    }

    private void importVinDetail(JSONObject vout, String txid) {
        TransactionDetail transactionDetail = new TransactionDetail();
        transactionDetail.setTxid(txid);
        JSONObject scriptPubKey = vout.getJSONObject("scriptPubKey");
        JSONArray addresses = scriptPubKey.getJSONArray("addresses");
        //todo check whether sync addresses
        if (addresses != null && !addresses.isEmpty()){
            String address = addresses.getString(0);
            transactionDetail.setAddress(address);
        }
        Double amount = vout.getDouble("value");
        transactionDetail.setAmount(amount);
        transactionDetail.setType((byte) TransactionDetailType.Receive.ordinal());

        transactionDetailMapper.insert(transactionDetail);

    }

    private void importVoutDetail(JSONObject vin, String txidOrigin) throws  Throwable{
        String txid = vin.getString("txid");
        Integer vout = vin.getInteger("vout");

        JSONObject rawTransaxtion = bitcoinJsonRpcClient.getRawTransaxtion(txid);
        JSONArray vouts = rawTransaxtion.getJSONArray("vout");
        JSONObject jsonObject = vouts.getJSONObject(vout);

        TransactionDetail transactionDetail = new TransactionDetail();
        transactionDetail.setTxid(txidOrigin);
        transactionDetail.setType((byte) TransactionDetailType.Send.ordinal());
        Double amount = jsonObject.getDouble("value");
        transactionDetail.setAmount(amount);
        JSONObject scriptPubKey = jsonObject.getJSONObject("scriptPubKey");
        JSONArray addresses = scriptPubKey.getJSONArray("addresses");
        if (addresses != null){
            String address = addresses.getString(0);
            transactionDetail.setAddress(address);
        }

        transactionDetailMapper.insert(transactionDetail);
    }

    }


