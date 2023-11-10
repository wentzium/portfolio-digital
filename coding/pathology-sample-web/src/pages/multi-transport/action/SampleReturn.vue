<template>
	<div class="h100pct sys-mian-panel">
    	<div class="title">
			<Row type="flex" justify="center" align="middle">
				<Col span="24">
					<div style="width: 18px;height: 18px;" class="assets-icon assets-icon-sample_manage"></div>
					样本详细信息
				</Col>
			</Row>
		</div>
		<div class="content">
			<div class="sample-return h100pct" style="padding-bottom: 42px;">
				
				<Row class="item">
					<Col span="4">样本:</Col>
					<Col span="20">
						<Input @on-change="addSample" v-model="sampleNo" type="text"  placeholder="请输入">
							<template slot="suffix">
								<div class="dpIB" style="width: 32px;height: 32px;padding: 15%;">
									<div class="w100pct h100pct assets-icon assets-icon-input-scan"></div>
								</div>
							</template>
						</Input>
					</Col>
				</Row>
				
				<Row class="item"><Col span="24">
					<Tag v-for="item in sampleNoList" :key="item" :name="item" closable @on-close="handleClose">样本Id:{{item}}</Tag>
				</Col></Row>
				
				<div class="foot">
						<Button class="mr10x" type="primary" @click="returnSampleConfrim">确定退样</Button>
				</div>
				
				<Modal
			        v-model="visible"
			        @on-ok="visible=false"
			        @on-cancel="visible=false">
			        <template slot="header">
			        	<div style="width: 18px;height: 18px;" class="assets-icon assets-icon-modal-default"></div>
			        	是否退样
			        </template>
			        <div style="font-size: 16px;color: #505050;text-align: center;padding: 40px 0;">
			        	是退回已选样本？
			        </div>
			        <template slot="footer">
			        	<Button @click="visible=false">取消</Button>
			        	<Button type="primary" @click="returnSample">确认</Button>
			        </template>
			    </Modal>
		    
			</div>
		</div>
	</div>
	
</template>

<script>

import {returnSample} from '@/api/sampleApi'

export default {
  name: 'FixedSampleReturn',
  components: {},
  props:{
  	siteId:[String,Number]
  },
  data () {
    return {
    	visible:false,
    	sampleNoList: [],
    	sampleNo:''
    }
  },
  methods: {
  	returnSampleConfrim(){
  		if(this.sampleNoList.length){
  			this.visible = true;
  		} else {
  			this.$Message.info('请扫描样本Id后再进行退样');
  		}
  	},
  	handleClose (event, name) {
        const index = this.sampleNoList.indexOf(name);
        this.sampleNoList.splice(index, 1);
    },
    addSample(e){
    	if(!this.sampleNo) return false;
    	let sn = this.sampleNo;
    	if(!this.sampleNoList.length) {
    		this.sampleNoList.push(sn);
    	} else if(this.sampleNoList.indexOf(sn) == -1) {
    		this.sampleNoList.push(sn);
    	}
    },
    returnSample(){
  		returnSample({
		  "sampleNoList": this.sampleNoList,
		  "siteId": this.siteId
		}).then(res=>{
			this.visible = false;
  			this.$Message.info(res.msg);
  		})
  		
    },
  	inputSampleWeightShow(){
  		this.$refs.InputSampleWeight.show()
  	},
  	showSuggest(){
  		this.$refs.suggest.show()
  	},
  	sampleDeleteShow(){
  		this.$refs.SampleDelete.show()
  	}
  }
}
</script>

<style lang="less" scoped="scoped">
.sample-return{
	padding-top: 20px;
	.item{
		border-radius: 4px;
		line-height: 32px;
		margin-bottom: 10px;
		padding-left: 10px;
		/*font-size: 12px;
		color: #333333;
		line-height: 19px;*/
	}
}
</style>