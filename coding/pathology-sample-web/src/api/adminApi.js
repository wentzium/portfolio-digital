import request from './request';

/**
 * 站点详情
 */
export function getSiteDetail(params) {
	return request({
		url: `/api/sample-service/admin/site/getDetail`,
		method: 'get',
		params
	})
}

/**
 * 站点详情
 */
export function siteList(data) {
	return request({
		url: `/api/sample-service/admin/site/list`,
		method: 'post',
		data
	})
}

/**
 * 删除站点
 */
export function siteRemove(data) {
	return request({
		url: `/api/sample-service/admin/site/remove`,
		method: 'post',
		data
	})
}

/**
 * 新增/保存站点信息
 */
export function siteSave(data) {
	return request({
		url: `/api/sample-service/admin/site/save`,
		method: 'post',
		data
	})
}




/**
 * 站点详情
 */
export function getDetail(params) {
	return request({
		url: `/api/sample-service/admin/dicItem/getDetail`,
		method: 'get',
		params
	})
}

/**
 * 站点详情
 */
export function dictList(data) {
	return request({
		url: `/api/sample-service/admin/dicItem/list`,
		method: 'post',
		data
	})
}

/**
 * 删除站点
 */
export function dictRemove(data) {
	return request({
		url: `/api/sample-service/admin/dicItem/remove`,
		method: 'post',
		data
	})
}

/**
 * 新增/保存站点信息
 */
export function dictSave(data) {
	return request({
		url: `/api/sample-service/admin/dicItem/save`,
		method: 'post',
		data
	})
}

/**
 * 新增/保存站点信息
 */
export function searchDicList(data) {
	return request({
		url: `/api/sample-service/admin/dicItem/searchDicList`,
		method: 'post',
		data
	})
}




/**
 * 用户
 */
export function getUserDetail(params) {
	return request({
		url: `/api/user-service/admin/user/getUserDetail`,
		method: 'get',
		params
	})
}

/**
 * 用户
 */
export function userList(data) {
	return request({
		url: `/api/user-service/admin/user/list`,
		method: 'post',
		data
	})
}


/**
 * 用户
 */
export function userSave(data) {
	return request({
		url: `/api/user-service/admin/user/save`,
		method: 'post',
		data
	})
}
