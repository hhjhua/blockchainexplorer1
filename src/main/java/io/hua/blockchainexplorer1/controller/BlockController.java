package io.hua.blockchainexplorer1.controller;

import io.hua.blockchainexplorer1.api.BitcoinApi;
import io.hua.blockchainexplorer1.api.BitcoinJsonRpcClient;
import io.hua.blockchainexplorer1.dao.BlockMapper;
import io.hua.blockchainexplorer1.dto.BlockDetailDTO;
import io.hua.blockchainexplorer1.dto.BlockListDTO;
import io.hua.blockchainexplorer1.po.Block;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/block")
@CrossOrigin
public class BlockController {
    @Autowired
    private BitcoinApi bitcoinApi;

    @Autowired
    private BitcoinJsonRpcClient bitcoinJsonRpcClient;

    @Autowired
    private BlockMapper mapper;

    @Value("${blockchain.recentCount}")
    private Integer recentCount;

    @GetMapping("/getRecentBlocks")
    public List<BlockListDTO> getRecentBlocks() throws Throwable {

//        String bestBlockhash = bitcoinJsonRpcClient.getBestBlockhash();
//        String tempBlockhash = bestBlockhash;
//
//        List<BlockListDTO> blockListDTOS = new LinkedList<>();
//
//        for (int i = 0; i < 5; i++) {
//            JSONObject block = bitcoinApi.getNoTxBlock(tempBlockhash);
//            BlockListDTO blockListDTO = new BlockListDTO();
//            blockListDTO.setHeight(block.getInteger("height"));
//            Long time = block.getLong("time");
//            Date date = new Date(time * 1000);
//            blockListDTO.setTime(date);
//            blockListDTO.setTxSize(block.getJSONArray("tx").size());
//            blockListDTO.setSizeOnDisk(block.getLong("size"));
//            blockListDTOS.add(blockListDTO);
//            tempBlockhash = block.getString("previousblockhash");
//        }

//        JSONObject chainInfo = bitcoinApi.getChainInfo();
//        Integer height = chainInfo.getInteger("blocks");
//        height -= 5;
//        String blockHashByHeight = bitcoinJsonRpcClient.getBlockHashByHeight(height);
//        JSONArray blockHeaders = bitcoinApi.getBlockHeaders(5, blockHashByHeight);
//
//        LinkedList<BlockListDTO> blockListDTOS = new LinkedList<>();
//        for (int i = 4; i > -1; i--) {
//            JSONObject block = blockHeaders.getJSONObject(i);
//            BlockListDTO blockListDTO = new BlockListDTO();
//            blockListDTO.setHeight(block.getInteger("height"));
//            Long time = block.getLong("time");
//            Date date = new Date(time * 1000);
//            blockListDTO.setTime(date);
//            //todo add size on disk
//            blockListDTO.setTxSize(block.getInteger("nTx"));
//            blockListDTOS.add(blockListDTO);
//        }

        List<Block> blocks = mapper.selectRecent();
        List<BlockListDTO> blockListDTOS = blocks.stream().map(block -> {
            BlockListDTO blockListDTO = new BlockListDTO();
            blockListDTO.setHeight(block.getHeight());
            blockListDTO.setTime(block.getTime().getTime());
            blockListDTO.setTxSize(block.getTxSize());
            blockListDTO.setSizeOnDisk(block.getSizeOnDisk());
            return blockListDTO;
        }).collect(Collectors.toList());

        return blockListDTOS;


//        bitcoinApi.getBlockHeaders(recentCount,bestBlockhash)
    }

    @GetMapping("/getRecentBlocksByNameType")
    public List<BlockListDTO> getRecentBlocksByNameType(@RequestParam String name,
                                                        @RequestParam String type){
        return null;
    }

    @GetMapping("/getBlockDetailByHash")
    public BlockDetailDTO getBlockDetailByHash(@RequestParam String blockhash){
        return null;
    }

    @GetMapping("/getBlockDetailByHeight")
    public BlockDetailDTO getBlockDetailByHeight(@RequestParam Integer blockheight){
        return null;
    }
}
