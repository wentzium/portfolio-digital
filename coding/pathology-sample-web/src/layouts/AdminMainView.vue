<template>
  <global-layout >
  		<keep-alive >
		  	<router-view />
      </keep-alive>
  </global-layout>
</template>

<script>
import {mapActions} from 'vuex'
import GlobalLayout from "@/layouts/AdminGlobalLayout"

export default {
  name: 'MainView',
  components: {GlobalLayout},
  data () {
    return {
    }
  },
  computed: {
  },
  mounted(){
  },
  methods: {
  },
  beforeCreate () {
  }
}
</script>

<style lang="less" scoped>
</style>
