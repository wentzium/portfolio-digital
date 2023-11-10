package com.mxs.sampleservice.mapper;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.mxs.sampleservice.bo.operation.ListForOperationSiteBO;
import com.mxs.sampleservice.entity.Apply;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mxs.sampleservice.web.vo.SearchListForOperationSiteReqVO;
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
public interface ApplyMapper extends BaseMapper<Apply> {
    List<ListForOperationSiteBO> searchOperationList(Pagination pagination, @Param("condition") SearchListForOperationSiteReqVO reqVO);
}
