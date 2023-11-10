import axios from 'axios'
import router from '@/router'
import {Message} from 'iview';
import * as authUtils from '@/utils/auth'
//import * as Util from '@/util/util'

Message.config({
  duration: 2,
  maxCount: 1,
});

//创建axios实例
const query = axios.create({
    withCredentials: true,
    timeout: 30000,
    headers: {
        'Content-Type': 'application/json;charset=UTF-8'
    }
})

query.interceptors.request.use(config => {
	if(config.method === 'formdata'){
		config.method = 'post';
		config.headers['Content-Type'] = 'application/x-www-form-urlencoded';
	}
	let token = authUtils.getToken()
	if (token != null && !config.url.includes('/user/login')) {
		config.headers['Authorization'] = `bearer ${token}`
	}
   	return config;
   }, error => {
   	return Promise.reject(error)
});


//response拦截器
query.interceptors.response.use(
    response => {
    		let {data} = response;
    		if(data.code == 200) {
    			return Promise.resolve(data)
    		} else {
    			if(data.msg) Message.warning(data.msg);
    			return Promise.reject(data)
    		}
    },
    error => {
        //网络链接错误
        if(error.message === 'Network Error'){
            Message.warning('网络错误');
            return Promise.reject('网络错误，请稍后再试');
        }

        //请求超时
        let originalRequest = error.config;
        if(error.code == 'ECONNABORTED' && error.message.indexOf('timeout')!=-1 && !originalRequest._retry){
            Message.warning('请求超时，请稍后再试');
            return Promise.reject('请求超时，请稍后再试');
        }
        
        
			let { status,data } = error.response;
				//第一次是access_token 调取
			if (status === 401) {
				router.push({path: '/login'})
			} else if (status === 403) {
				Message.warning("资源不可用");
				return Promise.reject("资源不可用")
			} else if(status==301){
				// this.$router.push("/login");
				router.push({path: '/login'})
			} else if(status==502){
				Message.warning('网络错误，请稍后再试');
	    	return Promise.reject('请求超时，请稍后再试');
			} else {
				// 业务层 412 错误逻辑  国际化{"code":"xx","param":xx}
				if(data && data.message) {
					Message.warning(data.message);
				}
				return Promise.reject('error');
			}
	
    }
)

export default query