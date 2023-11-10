<template>
	<Modal
        v-model="visible"
        @on-ok="visible=false"
        @on-cancel="visible=false">
        <template slot="header">
        	<div style="width: 18px;height: 18px;" class="assets-icon assets-icon-modal-print"></div>
        	打印样本详情
        </template>
        <div style="font-size: 16px;color: #505050;text-align: center;padding: 40px 0;">
        	申请单发送成功，正在打印申请单……
        </div>
        <template slot="footer">
        	<Button @click="visible=false" type="primary">确认</Button>
        </template>
    </Modal>
</template>

<script>
export default {
  name: 'Printing',
  components: {},
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
  	}
  }
}
</script>

<style>
</style>