import request from './request';
import qs from 'qs'
import {removeToken, removeRefreshToken,removeUserInfo} from '@/utils/auth'

export function userLogin(data) {
	return request({
		url: `/api/user-service/user/login`,
		method: 'post',
		data
	})
}

export function userLogout() {
	return request({
		url: `/api/user-service/user/logout`,
		method: 'post'
	})
}



