<template>
  <appointment-layout>
  	  <keep-alive >
		  	<router-view />
      </keep-alive>
  </appointment-layout>
</template>

<script>
import {mapActions} from 'vuex'
import AppointmentLayout from "@/layouts/AppointmentLayout"

export default {
  name: 'AppointmentView',
  components: {AppointmentLayout},
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
