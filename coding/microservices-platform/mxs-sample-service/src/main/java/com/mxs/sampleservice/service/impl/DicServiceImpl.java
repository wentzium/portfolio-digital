package com.mxs.sampleservice.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mxs.common.util.IsDeleteEnum;
import com.mxs.common.util.StreamUtil;
import com.mxs.sampleservice.bo.DicKeyBO;
import com.mxs.sampleservice.bo.DicValueBO;
import com.mxs.sampleservice.bo.admin.AdminDicValueBO;
import com.mxs.sampleservice.entity.Dic;
import com.mxs.sampleservice.entity.DicItem;
import com.mxs.sampleservice.mapper.DicMapper;
import com.mxs.sampleservice.service.DicItemService;
import com.mxs.sampleservice.service.DicService;
import com.mxs.sampleservice.service.SequenceService;
import com.mxs.sampleservice.util.BizException;
import com.mxs.sampleservice.web.vo.admin.ListDicItemReqVO;
import com.mxs.sampleservice.web.vo.admin.ListDicReqVO;
import com.mxs.sampleservice.web.vo.admin.SaveDicItemReqVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhangyingxuan
 * @since 2019-09-02
 */
@Service
public class DicServiceImpl extends ServiceImpl<DicMapper, Dic> implements DicService {
    @Autowired
    private DicItemService dicItemService;
    @Autowired
    private SequenceService sequenceService;

    @Override
    public List<DicKeyBO> list() {
        List<DicKeyBO> resultList = new ArrayList<>();
        List<Dic> dicList = this.baseMapper.selectList(new EntityWrapper<>());
        StreamUtil.getStream(dicList).forEach(
                item -> {
                    DicKeyBO bo = new DicKeyBO();
                    bo.setCode(item.getItemCode());
                    bo.setDesc(item.getItemName());
                    bo.setValueList(new ArrayList<>());

                    List<DicItem> itemList = dicItemService.selectList(new EntityWrapper<DicItem>().eq("dic_id", item.getId()).eq("is_deleted", IsDeleteEnum.NO.getCode()));
                    bo.getValueList().addAll(StreamUtil.getStream(itemList).map(vItem -> {
                        DicValueBO valueBO = new DicValueBO();
                        valueBO.setId(vItem.getId());
                        valueBO.setCode(vItem.getItemCode());
                        valueBO.setDesc(vItem.getItemName());
                        valueBO.setDefaultShow(vItem.getIsDefault() == 1);
                        return valueBO;
                    }).collect(Collectors.toList()));

                    resultList.add(bo);
                }
        );
        return resultList;
    }

    /**
     * 保存字典值
     *
     * @param reqVO
     */
    @Transactional
    @Override
    public long save(SaveDicItemReqVO reqVO) {
        if (null == reqVO.getDicId()) {
            throw new BizException("字典ID不能为空");
        }
        if (null == reqVO.getIsDefault()) {
            reqVO.setIsDefault(false);
        }
        Dic dicEntity = this.selectById(reqVO.getDicId());

        int dicItemNum = dicItemService.selectCount(new EntityWrapper<DicItem>().eq("dic_id", dicEntity.getId()));

        DicItem dicItem;
        if (null == reqVO.getId()) {
            // 新增
            dicItem = new DicItem();
            dicItem.setDicId(reqVO.getDicId());
            dicItem.setItemName(reqVO.getItemName());
            dicItem.setItemCode(String.format("%s%s", dicEntity.getItemCode(), this.addedCharAndLength(dicItemNum + 1L, 6, '0')));
            dicItem.setIsDefault(reqVO.getIsDefault() ? 1 : 0);

            // 如果为默认值，则更新改字典下的其它项为非默认值
            if (reqVO.getIsDefault() && dicItemNum > 0) {
                DicItem updateItem = new DicItem();
                updateItem.setIsDefault(0);
                dicItemService.update(updateItem, new EntityWrapper<DicItem>().eq("dic_id", dicEntity.getId()));
            }

            dicItemService.insert(dicItem);
            return dicItem.getId();
        } else {
            dicItem = dicItemService.selectById(reqVO.getId());
            if (null == dicItem) {
                throw new BizException("字典值不存在");
            }
            dicItem.setItemName(reqVO.getItemName());
            dicItem.setIsDefault(reqVO.getIsDefault() ? 1 : 0);

            // 如果为默认值，则更新改字典下的其它项为非默认值 排除自己
            if (reqVO.getIsDefault() && dicItemNum > 1) {
                DicItem updateItem = new DicItem();
                updateItem.setIsDefault(0);
                dicItemService.update(updateItem, new EntityWrapper<DicItem>().eq("dic_id", dicEntity.getId()));
            }

            dicItemService.updateById(dicItem);
            return dicItem.getId();
        }
    }

    // 补齐方法
    private static String addedCharAndLength(Long value, int addedToLength, char addedCharacter) {
        StringBuilder sb = new StringBuilder();

        // 大于数字自身长度
        if (addedToLength > 0 && addedToLength > Long.toString(value).length()) {
            int addLengthCount = addedToLength - Long.toString(value).length();
            for (int i = 0; i < addLengthCount; i++) {
                sb.append(addedCharacter);
            }
        }
        return sb.append(value).toString();
    }

    /**
     * 查询字典值详情
     *
     * @param dicItemId
     */
    @Override
    public AdminDicValueBO getDetail(Long dicItemId) {
        if (null == dicItemId) {
            throw new BizException("字典值ID不能为空");
        }
        DicItem dicItem = dicItemService.selectById(dicItemId);
        if (null == dicItem) {
            throw new BizException("字典值无效");
        }
        Dic dic = this.selectById(dicItem.getDicId());
        AdminDicValueBO bo = new AdminDicValueBO();
        bo.setDicId(dic.getId());
        bo.setDicCode(dic.getItemCode());
        bo.setDicDesc(dic.getItemName());
        bo.setCode(dicItem.getItemCode());
        bo.setDesc(dicItem.getItemName());
        bo.setId(dicItem.getId());
        bo.setDefaultShow(dicItem.getIsDefault() == 1);
        bo.setIsDeleted(dicItem.getIsDeleted() == IsDeleteEnum.YES.getCode());
        return bo;
    }

    /**
     * 删除字典值
     *
     * @param dicItemId
     */
    @Override
    public void remove(Long dicItemId) {
        if (null == dicItemId) {
            throw new BizException("字典值ID不能为空");
        }
        DicItem dicItem = dicItemService.selectById(dicItemId);
        if (null == dicItem) {
            throw new BizException("字典值无效");
        }
        dicItem.setIsDeleted(IsDeleteEnum.YES.getCode());
        dicItemService.updateById(dicItem);
    }

    @Override
    public Page<AdminDicValueBO> listDicItem(ListDicItemReqVO reqVO) {
//        Page<DicItem> page = new Page<>(reqVO.getPage() <= 0 ? 1 : reqVO.getPage(), reqVO.getPageSize() <= 0 ? 10 : reqVO.getPageSize());
//        Page<DicItem> pageResult = dicItemService.selectPage(page, new EntityWrapper<DicItem>().eq("is_deleted", IsDeleteEnum.NO.getCode()));

        if (null != reqVO.getKeyName()) {
            reqVO.setKeyName(reqVO.getKeyName().trim());
        }
        Page<AdminDicValueBO> pageResult = new Page<>(reqVO.getPage() <= 0 ? 1 : reqVO.getPage(), reqVO.getPageSize() <= 0 ? 10 : reqVO.getPageSize());
        List<AdminDicValueBO> list = this.baseMapper.selectByCondition(pageResult, reqVO);

//        Page<AdminDicValueBO> result = new Page<>();
//        BeanUtils.copyProperties(pageResult, result, "records");
//        result.getPages();

        pageResult.setRecords(list);
//        result.setRecords(StreamUtil.getStream(pageResult.getRecords()).map(item -> {
//            Dic dic = this.selectById(item.getDicId());
//            AdminDicValueBO bo = new AdminDicValueBO();
//            bo.setDicCode(dic.getItemCode());
//            bo.setDicDesc(dic.getItemName());
//            bo.setCode(item.getItemCode());
//            bo.setDesc(item.getItemName());
//            bo.setId(item.getId());
//            bo.setDefaultShow(item.getIsDefault() == 1);
//            bo.setIsDeleted(item.getIsDeleted() == IsDeleteEnum.YES.getCode());
//            return bo;
//        }).collect(Collectors.toList()));

        return pageResult;
    }

    @Override
    public Page<com.mxs.sampleservice.bo.admin.DicKeyBO> searchAdminDicList(ListDicReqVO reqVO) {
        Page<Dic> page = new Page<>(reqVO.getPage() <= 0 ? 1 : reqVO.getPage(), reqVO.getPageSize() <= 0 ? 10 : reqVO.getPageSize());
        Page<Dic> pageResult = this.selectPage(page, new EntityWrapper<>());

        Page<com.mxs.sampleservice.bo.admin.DicKeyBO> result = new Page<>();
        BeanUtils.copyProperties(pageResult, result, "records");
        result.getPages();

        result.setRecords(StreamUtil.getStream(pageResult.getRecords()).map(item -> {
            com.mxs.sampleservice.bo.admin.DicKeyBO bo = new com.mxs.sampleservice.bo.admin.DicKeyBO();
            bo.setId(item.getId());
            bo.setCode(item.getItemCode());
            bo.setDesc(item.getItemName());
            return bo;
        }).collect(Collectors.toList()));

        return result;
    }
}
