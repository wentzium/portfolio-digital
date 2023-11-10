import {siteListByIp} from '@/api/sampleApi';

const user = {
	namespaced: true,
	state: {
		siteList: []
	},
	getters:{
		siteList: state => state.siteList
	},
	mutations: {
		SET_SITE_LIST: (state, info) => {
			state.siteList = info
		}
	},
	actions:{
		//获取userInfo
		getSiteList({ commit},qd) {
			return new Promise((resolve, reject) => {
				siteListByIp(qd).then(response => {
					commit('SET_SITE_LIST', response.data)
					resolve(response)
				}, error => {
					reject(error)
				})
			})
		},
		getSiteIdByType({commit,state},siteType){
			return new Promise((resolve, reject) => {
				let si = ''
				state.siteList.map(item=>{
					if(item.siteType == siteType) si=item.siteId;
				})
				resolve(si)
			})
		}
		// 获取角色权限
	}
}

export default user
