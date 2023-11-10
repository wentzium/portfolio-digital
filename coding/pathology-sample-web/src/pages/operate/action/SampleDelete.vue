<template>
	<Modal
        v-model="visible"
        @on-ok="visible=false"
        @on-cancel="visible=false" :width="300">
        <template slot="header">
        	<div style="width: 18px;height: 18px;" class="assets-icon assets-icon-modal-print"></div>
        	样本作废
        </template>
	        <p style="font-size: 12px;color: #666666;margin-bottom: 16px;">请选择作废理由</p>
	        <Select v-model="reqVo.reasonId" @on-change="reasonChange" >
                <Option  v-for="c in rejectReasonList" :value="c.id" :key="c.id">{{c.desc}}</Option>
            </Select>
	        <!--<p style="margin-bottom: 16px;">
	        	<span class="tag"  @click="reqVo.reason='理由理由理由理由理由理由'" ><label class="tag-text" >理由理由理由理由理由理由</label></span>
	        	<span class="tag"  @click="reqVo.reason='理由理由理由理由理由理由1'"><label class="tag-text" >理由理由理由理由理由理由1</label></span>
	        	<span class="tag"  @click="reqVo.reason='理由理由理由理由理由理由2'"><label class="tag-text" >理由理由理由理由理由理由2</label></span>
	        </p>
	        <Input v-model="reqVo.reason" type="textarea" :rows="4" placeholder="请填写作废理由" />-->
        <template slot="footer">
        	<Button @click="visible=false">取消</Button>
        	<Button @click="ok" type="primary">确认</Button>
        </template>
    </Modal>
</template>

<script>
import {sampleCancel} from '@/api/sampleApi'
import {mapActions,mapGetters} from 'vuex'

export default {
  name: 'SampleDelete',
  components: {},
  data () {
    return {
    	visible:false,
    	rejectReasonList:[],
    	sn:'',//样本号
		reqVo:{
		  "reasonId": "",
		  "reason": "",
		  "sampleNoList": [],
		  "siteId": 0
		}
    }
  },
  mounted() {
  },
  methods: {
  	...mapActions({
		getDictList: 'getDictList'
	}),
	reasonChange(e){
  		this.rejectReasonList.map(item=>{
  			if(item.id == e) this.reqVo.reason = item.desc;
  		})
	},
  	show(sn,siteId){
  		this.reqVo.reasonId = '';
  		this.reqVo.reason = '';
  		//作废原因
	  	this.getDictList('400').then(res=>{
	  		this.rejectReasonList = res;
	  	})
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