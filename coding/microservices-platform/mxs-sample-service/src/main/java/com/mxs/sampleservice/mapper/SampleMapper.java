package com.mxs.sampleservice.mapper;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.mxs.sampleservice.bo.ExitPromptNumBO;
import com.mxs.sampleservice.bo.SampleListForDeliverySiteBO;
import com.mxs.sampleservice.bo.SampleListForFixSiteBO;
import com.mxs.sampleservice.bo.SampleListForReceiveSiteBO;
import com.mxs.sampleservice.entity.Sample;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mxs.sampleservice.web.vo.SearchDeliveryListReqVO;
import com.mxs.sampleservice.web.vo.SearchFixListReqVO;
import com.mxs.sampleservice.web.vo.SearchMultiDeliveryListReqVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 样本信息 Mapper 接口
 * </p>
 *
 * @author zhangyingxuan
 * @since 2019-08-14
 */
public interface SampleMapper extends BaseMapper<Sample> {
    List<SampleListForFixSiteBO> selectListForFixSite(Pagination pagination, @Param("condition") SearchFixListReqVO reqVO);

    List<SampleListForDeliverySiteBO> selectListForDeliverySite(Pagination pagination, @Param("condition") SearchDeliveryListReqVO reqVO);

    List<SampleListForDeliverySiteBO> selectListForMultiDeliverySite(Pagination pagination, @Param("sampleNoList") List<String> sampleNoList);

    List<SampleListForReceiveSiteBO> selectListForReceiveSite(Pagination pagination, @Param("siteIdList") List<Long> siteIdList);

    ExitPromptNumBO selectNumForFixSite(@Param("condition") SearchFixListReqVO reqVO);
    ExitPromptNumBO selectNumForDeliverySite(@Param("condition") SearchDeliveryListReqVO reqVO);
    ExitPromptNumBO selectNumForReceiveSite(@Param("siteIdList") List<Long> siteIdList);

}
