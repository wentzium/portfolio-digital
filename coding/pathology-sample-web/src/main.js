import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import iView from 'iview';
import './index.less'

import '@/utils/filter'

Vue.config.productionTip = false

Vue.use(iView);

Vue.prototype.$vuescrollConfig = {
	bar: {
	    background: '#7f7f7f'
	}
};

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
