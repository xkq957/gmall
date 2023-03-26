package com.xkq.gmall.search.service;

import com.xkq.common.to.es.SkuEsModel;

import java.io.IOException;
import java.util.List;

/**
 * @author: xkq
 * @date: 2023/3/26 0:52
 * @description:
 */
public interface ProductSaveService {
    boolean saveProductAsIndices(List<SkuEsModel> skuEsModels) throws IOException;
}
