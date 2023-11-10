<template>
  <global-layout 
  	v-if="$store.getters['account/siteList'].length && dicts"
  	>
		  	<router-view />
  </global-layout>
</template>

<script>
import {mapActions} from 'vuex'
import GlobalLayout from "@/layouts/GlobalLayout"

export default {
  name: 'MainView',
  components: {GlobalLayout},
  data () {
    return {
    	dicts:null
    }
  },
  computed: {
  },
  mounted(){
		this.getSiteList({ip:'127.0.0.1'}).then(res=>{
			this.getDictList('100').then(res=>{
				this.dicts = res;
			})
		},err=>{});
  },
  methods: {
  	...mapActions({
			getSiteList: 'account/getSiteList',
			getDictList:'getDictList'
		}),
  },
  beforeCreate () {
  }
}
</script>

<style lang="less" scoped>
</style>
