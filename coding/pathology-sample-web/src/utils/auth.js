import Cookies from 'js-cookie'

export function clearUserData(){
	localStorage.removeItem("directPath");
	localStorage.removeItem("rolePermissions");
	localStorage.removeItem("buttonPermissions");
}

//md5(32) pathology-sample-username : 264401e1d9bda46ad25eb7b1d650b192
export function setUsername(username) {
	return Cookies.set('264401e1d9bda46ad25eb7b1d650b192', username)
}
export function getUsername() {
	return Cookies.get('264401e1d9bda46ad25eb7b1d650b192')
}

//md5(32) solarman-home-token:9ac11337b7141049af7c75b907e61cc5 
export function getToken() {
	return Cookies.get('9ac11337b7141049af7c75b907e61cc5')
}

export function setToken(token) {
	return Cookies.set('9ac11337b7141049af7c75b907e61cc5', token)
}

export function setTokenWith15Days(token) {
	return Cookies.set('9ac11337b7141049af7c75b907e61cc5', token,{ expires: 15 })
}

export function removeToken() {
	Cookies.remove('access_token')
	return Cookies.remove('9ac11337b7141049af7c75b907e61cc5')
}

//md5(32) solarman-home-refresh-token:7cf012d25878614523054a695ab82538
export function getRefreshToken() {
	return Cookies.get('7cf012d25878614523054a695ab82538')
}

export function setRefreshToken(refreshToken) {
	return Cookies.set('7cf012d25878614523054a695ab82538', refreshToken)
}

export function removeRefreshToken() {
	return Cookies.remove('7cf012d25878614523054a695ab82538')
}

export function setUserInfo(userInfo) {
	Cookies.set('userInfo', userInfo)
}
export function getUserInfo(){
	return Cookies.get('userInfo')
}

export function removeUserInfo() {
	return Cookies.remove('userInfo')
}
