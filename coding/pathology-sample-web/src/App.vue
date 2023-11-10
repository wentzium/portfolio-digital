<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
import { mapMutations } from 'vuex'

export default {
  name: 'App',
  data(){
  	return {
  		updateTime:moment().valueOf(),
  		scanInput:''
  	}
  },
  mounted(){
  	moment.locale('zh-cn');
//		document.onkeypress = e=> {
//			this.update(e.key);
//  }
  },
  methods:{
  	moment,
  	update(k){
			if(moment().valueOf()-this.updateTime > 300) {
				this.updateTime = moment().valueOf();
				this.scanInput = k;
				setTimeout(()=>{
					this.$store.commit('SET_SCAN_INPUT',this.scanInput)
				},300)
			} else {
				this.scanInput += k;
			}
  	}
  }
}
</script>

<style lang="less">
.size{
  height: 100%;
}
html,body{
  .size;
  overflow: hidden;
  margin: 0;
  padding: 0;
}
#app {
  .size;
}
</style>
