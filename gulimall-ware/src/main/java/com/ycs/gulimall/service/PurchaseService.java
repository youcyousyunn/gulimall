package com.ycs.gulimall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycs.gulimall.entity.PurchaseEntity;
import com.ycs.gulimall.utils.PageUtils;
import com.ycs.gulimall.vo.MergeVo;
import com.ycs.gulimall.vo.PurchaseDoneVo;

import java.util.List;
import java.util.Map;

public interface PurchaseService extends IService<PurchaseEntity> {
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 查询未领取的采购单
     * @param params
     * @return
     */
    PageUtils queryPageUnreceive(Map<String, Object> params);

    /**
     * 合并采购需求
     * @param mergeVo
     */
    void mergePurchase(MergeVo mergeVo);

    /**
     * 领取采购单
     * @param ids
     */
    void received(List<Long> ids);

    /**
     * 完成采购单
     * @param doneVo
     */
    void done(PurchaseDoneVo doneVo);
}

