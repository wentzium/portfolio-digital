package com.mxs.sampleservice.web;


import com.baomidou.mybatisplus.plugins.Page;
import com.mxs.common.util.Paging;
import com.mxs.common.util.ResponseCode;
import com.mxs.common.util.ResponseData;
import com.mxs.sampleservice.bo.admin.AdminDicValueBO;
import com.mxs.sampleservice.bo.admin.DicKeyBO;
import com.mxs.sampleservice.service.DicService;
import com.mxs.sampleservice.util.BizException;
import com.mxs.sampleservice.web.vo.admin.ListDicItemReqVO;
import com.mxs.sampleservice.web.vo.admin.ListDicReqVO;
import com.mxs.sampleservice.web.vo.admin.SaveDicItemReqVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhangyingxuan
 * @since 2019-08-02
 */

@Slf4j
@RestController
@RequestMapping("/admin/dicItem")
public class AdminDicItemController {
    @Autowired
    private DicService dicService;

    @ApiOperation("字典值列表")
    @PostMapping("/list")
    public ResponseData list(@RequestBody ListDicItemReqVO reqVO) {
        try {
            Page<AdminDicValueBO> result = dicService.listDicItem(reqVO);
            return ResponseData.createSuccessResult(result.getRecords(), Paging.builder().pageNo(result.getCurrent()).pageSize(result.getSize()).totalHits(result.getTotal()).hasMore(result.hasNext()).build());
        } catch (BizException e) {
            log.error("AdminDicController->list 业务异常", e);
            return ResponseData.createFailedResult(e.getMessage());
        } catch (Exception e) {
            log.error("AdminDicController->list 异常", e);
            return ResponseData.createFailedResult("查询失败");
        }
    }

    @ApiOperation("保存/新增字典值")
    @PostMapping("/save")
    public ResponseData save(@RequestBody SaveDicItemReqVO reqVO) {
        try {
            dicService.save(reqVO);
            return ResponseData.builder().msg("保存字典值成功").code(ResponseCode.SUCCESS.code).build();
        } catch (BizException e) {
            log.error("AdminDicController->save 业务异常", e);
            return ResponseData.createFailedResult(e.getMessage());
        } catch (Exception e) {
            log.error("AdminDicController->save 异常", e);
            return ResponseData.createFailedResult("保存字典值失败");
        }
    }

    @ApiOperation("字典值详情")
    @GetMapping("/getDetail")
    public ResponseData getDetail(@RequestParam("dicItemId") Long dicItemId) {
        try {
            return ResponseData.builder().msg("查询字典值成功").data(dicService.getDetail(dicItemId)).code(ResponseCode.SUCCESS.code).build();
        } catch (BizException e) {
            log.error("AdminDicController->getDetail 业务异常", e);
            return ResponseData.createFailedResult(e.getMessage());
        } catch (Exception e) {
            log.error("AdminDicController->getDetail 异常", e);
            return ResponseData.createFailedResult("查询字典值失败");
        }
    }

    @ApiOperation("删除字典值")
    @PostMapping("/remove")
    public ResponseData remove(@RequestBody Long dicItemId) {
        try {
            dicService.remove(dicItemId);
            return ResponseData.builder().msg("删除字典值成功").code(ResponseCode.SUCCESS.code).build();
        } catch (BizException e) {
            log.error("AdminDicController->remove 业务异常", e);
            return ResponseData.createFailedResult(e.getMessage());
        } catch (Exception e) {
            log.error("AdminDicController->remove 异常", e);
            return ResponseData.createFailedResult("删除字典值失败");
        }
    }

    @ApiOperation("字典查询")
    @PostMapping("/searchDicList")
    public ResponseData searchDicList(@RequestBody ListDicReqVO reqVO) {
        try {
            Page<DicKeyBO> result = dicService.searchAdminDicList(reqVO);
            return ResponseData.builder().msg("查询字典列表成功").code(ResponseCode.SUCCESS.code)
                    .data(result.getRecords())
                    .paging( Paging.builder().pageNo(result.getCurrent()).pageSize(result.getSize()).totalHits(result.getTotal()).hasMore(result.hasNext()).build()).build();
        } catch (BizException e) {
            log.error("AdminDicController->searchDicList 业务异常", e);
            return ResponseData.createFailedResult(e.getMessage());
        } catch (Exception e) {
            log.error("AdminDicController->searchDicList 异常", e);
            return ResponseData.createFailedResult("查询字典列表失败");
        }
    }
}

