package io.hua.blockchainexplorer1.service;

import io.hua.blockchainexplorer1.po.Block;

import java.util.List;

public interface BlockService {

    List<Block> selectRecent();

}
