import request from './request';

/**
 * 保存预约冰冻申请单
 */
export function appointmentApply(data) {
	return request({
		url: `/api/sample-service/appointmentApply/save`,
		method: 'post',
		data
	})
}

/**
 * 预约冰冻申请单list
 */
export function appointmentApplyList(data) {
	return request({
		url: `/api/sample-service/appointmentApply/list`,
		method: 'post',
		data
	})
}

/**
 * 同步病人信息
 */
export function getPatientInfoApi(data) {
	return request({
		url: `/api/sample-service/patientInfo/sync`,
		method: 'post',
		data
	})
}

/**
 * 查询病人检查历史
 */
export function searchCheckHis(data) {
	return request({
		url: `/api/sample-service/patientInfo/searchCheckHis`,
		method: 'post',
		data
	})
}

/**
 * 获取电子病历结果列表
 */
export function getMedicalHistoryResultList(data) {
	return request({
		url: `/api/sample-service/patientInfo/getMedicalHistoryResultList`,
		method: 'post',
		data
	})
}

/**
 * 样本标签打印通知
 */
export function tagPrintedNotify(data) {
	return request({
		url: `/api/sample-service/sample/tagPrintedNotify`,
		method: 'post',
		data
	})
}

/**
 * 拍照
 */
export function imageUpload(data) {
	return request({
		url: `/api/sample-service/image/upload`,
		method: 'post',
		data
	})
}

/**
 * 新增/保存样本信息
 */
export function saveSampleInfo(data) {
	return request({
		url: `/api/sample-service/sample/saveSampleInfo`,
		method: 'post',
		data
	})
}

/**
 * 查询申请单下的样本列表
 */
export function getSampleListByApplyNo(params) {
	return request({
		url: `/api/sample-service/sample/listByApplyNo`,
		method: 'get',
		params
	})
}

/**
 * 查询申请单下的样本列表
 */
export function getSampleLogistics(params) {
	return request({
		url: `/api/sample-service/sample/getLogistics`,
		method: 'get',
		params
	})
}

/**
 * 样本作废
 */
export function sampleCancel(data) {
	return request({
		url: `/api/sample-service/sample/cancel`,
		method: 'post',
		data
	})
}

/**
 * 样本作废
 */
export function getSampleInfo(params) {
	return request({
		url: `/api/sample-service/sample/getSample`,
		method: 'get',
		params
	})
}

/**
 * 查询固定站点列表查询
 */
export function searchFixSampleList(data) {
	return request({
		url: `/api/sample-service/sample/searchFixList`,
		method: 'post',
		data
	})
}

/**
 * 查询转运站点列表查询
 */
export function searchDeliveryList(data) {
	return request({
		url: `/api/sample-service/sample/searchDeliveryList`,
		method: 'post',
		data
	})
}

/**
 * 查询二级转运站点列表查询
 */
export function searchMultiDeliveryList(data) {
	return request({
		url: `/api/sample-service/sample/searchMultiDeliveryList`,
		method: 'post',
		data
	})
}

/**
 * 转运样本（一级转运站点）
 */
export function transferSample(data) {
	return request({
		url: `/api/sample-service/sample/transferSample`,
		method: 'post',
		data
	})
}

/**
 * 多级转运样本（二级及以上转运站点）
 */
export function multiTransferSample(data) {
	return request({
		url: `/api/sample-service/sample/multiTransferSample`,
		method: 'post',
		data
	})
}


/**
 * 查询固定站点列表查询
 */
export function fixSample(data) {
	return request({
		url: `/api/sample-service/sample/fixSample`,
		method: 'post',
		data
	})
}

/**
 * 查询接收站点列表查询
 */
export function searchReceiveList(data) {
	return request({
		url: `/api/sample-service/sample/searchReceiveList`,
		method: 'post',
		data
	})
}

/**
 * 获取病理号
 */
export function getPathologyNo(data) {
	return request({
		url: `/api/sample-service/sample/getPathologyNo`,
		method: 'post',
		data
	})
}

/**
 * 接收样本
 */
export function receiveSample(data) {
	return request({
		url: `/api/sample-service/sample/receiveSample`,
		method: 'post',
		data
	})
}

/**
 * 退单样本
 */
export function rejectSample(data) {
	return request({
		url: `/api/sample-service/sample/rejectSample`,
		method: 'post',
		data
	})
}

/**
 * 修改样本固定信息
 */
export function updateFixedInfo(data) {
	return request({
		url: `/api/sample-service/sample/updateFixedInfo`,
		method: 'post',
		data
	})
}

/**
 * 退样
 */
export function returnSample(data) {
	return request({
		url: `/api/sample-service/sample/returnSample`,
		method: 'post',
		data
	})
}

/**
 * 查询IP对应的站点列表
 */
export function siteListByIp(params) {
	return request({
		url: `/api/sample-service/site/listByIp`,
		method: 'get',
		params
	})
}




/*********************apply-controll********************************/
/**
 * 发送申请单-临时接口
 */
export function applyLogisticsQuery(params) {
	return request({
		url: `/api/sample-service/apply/applyLogisticsQuery`,
		method: 'get',
		params
	})
}

/**
 * 保存申请单
 */
export function saveApplyInfo(data) {
	return request({
		url: `/api/sample-service/apply/saveApplyInfo`,
		method: 'post',
		data
	})
}

/**
 * 发送申请单
 */
export function sendApplyInfo(data) {
	return request({
		url: `/api/sample-service/apply/send`,
		method: 'post',
		data
	})
}

/**
 * 初始化申请单信息
 */
export function initApply(data) {
	return request({
		url: `/api/sample-service/apply/initApply`,
		method: 'post',
		data
	})
}

/**
 * 初始化申请单信息
 */
export function exitPrompt(data) {
	return request({
		url: `/api/sample-service/sample/exitPrompt`,
		method: 'post',
		data
	})
}

/**
 * 查询申请单详情
 */
export function getApplyDetailInfo(params) {
	return request({
		url: `/api/sample-service/apply/getDetailInfo`,
		method: 'get',
		params
	})
}

/**
 * 手术站点-默认列表(登录医生在该站点录入的所有申请单列表)
 */
export function searchOperationList(data) {
	return request({
		url: `/api/sample-service/apply/searchOperationList`,
		method: 'post',
		data
	})
}

/**
 * 手术站点-申请单明细
 */
export function applyDetailList(data) {
	return request({
		url: `/api/sample-service/apply/searchOperationList/applyDetailList`,
		method: 'post',
		data
	})
}


/**
 * 手术站点-GET 查询检查项目
 */
export function searchExamRptPattern() {
	return request({
		url: `/api/sample-service/apply/searchExamRptPattern`,
		method: 'get'
	})
}


/**
 * 手术站点-申请单明细
 */
export function getDictList() {
	return request({
		url: `/api/sample-service/dic/list`,
		method: 'get'
	})
}
