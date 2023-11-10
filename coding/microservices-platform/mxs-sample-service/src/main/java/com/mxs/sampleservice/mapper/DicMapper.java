package com.mxs.sampleservice.mapper;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.mxs.sampleservice.bo.admin.AdminDicValueBO;
import com.mxs.sampleservice.entity.Dic;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mxs.sampleservice.web.vo.admin.ListDicItemReqVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhangyingxuan
 * @since 2019-08-14
 */
public interface DicMapper extends BaseMapper<Dic> {
    List<AdminDicValueBO> selectByCondition(Pagination pagination, @Param("condition") ListDicItemReqVO condition);
}
