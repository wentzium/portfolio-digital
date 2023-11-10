import Vue from 'vue'
import Vuex from 'vuex'
import account from './modules/account'
import setting from './modules/setting'
import {getDictList} from '@/api/sampleApi';

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    account,
    setting
  },
  state: {
  	dictList:[],
  	sacnCount:0,
		scanInput:''
  },
  getters:{
		scanInput: state => state.scanInput,
		sacnCount: state => state.sacnCount,
		dictList:state => state.dictList
	},
  mutations: {
		SET_SCAN_INPUT: (state, info) => {
			state.scanInput = info,
			state.sacnCount++;
		},
		SET_DICTLIST:(state,dlist)=>{
			state.dictList = dlist;
		}
  },
  actions: {
		getDictList({commit, state,dispatch},dictCode){
			if(state.dictList.length>0){
				return new Promise((resolve, reject) => {
					let dict = state.dictList.filter(item=>item.code==dictCode)
					resolve(dict[0].valueList)
				})
			} else {
				return new Promise((resolve, reject) => {
					getDictList().then(response => {
						commit('SET_DICTLIST', response.data)
						let dict = response.data.filter(item=>item.code==dictCode)
						resolve(dict[0].valueList)
					}, error => {
						reject(error)
					})
				})
				
			}
		}
  }
})
