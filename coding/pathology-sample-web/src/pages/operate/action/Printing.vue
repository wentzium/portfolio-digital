<template>
	<div>
		<Modal
	        v-model="visible"
	        @on-ok="visible=false"
	        @on-cancel="visible=false">
	        <template slot="header">
	        	<div style="width: 18px;height: 18px;" class="assets-icon assets-icon-modal-print"></div>
	        	打印并发送申请单
	        </template>
	        <div style="font-size: 16px;color: #505050;text-align: center;padding: 40px 0;">
	        	申请单发送中，正在打印申请单……
	        </div>
	        <template slot="footer">
	        	<Button  @click="visible=false" type="primary">确认</Button>
	        </template>
	    </Modal>
	</div>
</template>

<script>

export default {
  name: 'Printing',
  components: {},
  props:{
  	selectSample:{
  		type:Object,
  		default:{}
  	}
  },
  data () {
    return {
    	visible:false
    }
  },
  computed: {
  },
  methods: {
  	show(){
  		this.visible = true;
//		this.$refs.print.print();
  	},
  	hide(){
  		this.visible = false;
  	}
  }
}
</script>

<style>
</style>