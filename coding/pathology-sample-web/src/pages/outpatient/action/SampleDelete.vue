<template>
	<Modal
        v-model="visible"
        @on-ok="visible=false"
        @on-cancel="visible=false" :width="840">
        <template slot="header">
        	<div style="width: 18px;height: 18px;" class="assets-icon assets-icon-modal-print"></div>
        	打印样本详情
        </template>
	        <p style="font-size: 12px;color: #666666;margin-bottom: 16px;">是否批量作废这些样本，请写明作废理由</p>
	        <p style="margin-bottom: 16px;">
	        	<span class="tag"  @click="reqVo.reason='理由理由理由理由理由理由'" ><label class="tag-text" >理由理由理由理由理由理由</label></span>
	        	<span class="tag"  @click="reqVo.reason='理由理由理由理由理由理由1'"><label class="tag-text" >理由理由理由理由理由理由1</label></span>
	        	<span class="tag"  @click="reqVo.reason='理由理由理由理由理由理由2'"><label class="tag-text" >理由理由理由理由理由理由2</label></span>
	        </p>
	        <Input v-model="reqVo.reason" type="textarea" :rows="4" placeholder="请填写作废理由" />
        <template slot="footer">
        	<Button @click="visible=false">取消</Button>
        	<Button @click="ok" type="primary">确认</Button>
        </template>
    </Modal>
</template>

<script>
import {sampleCancel} from '@/api/sampleApi'

export default {
  name: 'SampleDelete',
  components: {},
  data () {
    return {
    	visible:false,
    	sn:'',//样本号
		reqVo:{
		  "reason": "",
		  "sampleNoList": [],
		  "siteId": 0
		}
    }
  },
  computed: {
  },
  methods: {
  	show(sn,siteId){
  		this.visible = true;
  		this.reqVo.sampleNoList =[sn.sampleNo];
  		this.reqVo.siteId = siteId;
  	},
  	ok(){
  		sampleCancel(this.reqVo).then(res=>{
  			this.$emit('refresh')
  			this.$Message.info(res.msg);
  			this.visible = false;
  		},err=>{console.log(err)})
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