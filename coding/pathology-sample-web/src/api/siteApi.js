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
 * 样本作废
 */
export function sampleCancel(params) {
	return request({
		url: `/api/sample-service/sample/cancel`,
		method: 'post',
		params
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



