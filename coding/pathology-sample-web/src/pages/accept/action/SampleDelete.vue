<template>
	<Modal
        v-model="visible"
        @on-ok="visible=false"
        @on-cancel="visible=false" >
        <template slot="header">
        	<div style="width: 18px;height: 18px;" class="assets-icon assets-icon-modal-default"></div>
        	样本作废
        </template>
	        <p style="font-size: 12px;color: #666666;margin-bottom: 16px;">是否批量作废这些样本，请写明作废理由</p>
	        <Input type="textarea" :rows="4" placeholder="请填写作废理由" />
        <template slot="footer">
        	<Button>取消</Button>
        	<Button type="primary">确认</Button>
        </template>
    </Modal>
</template>

<script>
export default {
  name: 'FixedSampleDelete',
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

<style lang="less" scoped="">
.tag{
	display: inline-block;
	background: #D6EDF8;
	border-radius: 2px;
	padding: 5px;
	margin-right: 10px;
}
.tag-text{
	font-size: 11px;
	color: #32A3DB;
}
</style>