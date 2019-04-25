package io.hua.blockchainexplorer1.service.impl;

import io.hua.blockchainexplorer1.dao.BlockMapper;
import io.hua.blockchainexplorer1.po.Block;
import io.hua.blockchainexplorer1.service.BlockService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BlockServiceImpl implements BlockService {

    @Autowired
    private BlockMapper mapper;

    @Override
    public List<Block> selectRecent() {
        List<Block> blocks=mapper.selectRecent();
        return blocks;
    }
}
