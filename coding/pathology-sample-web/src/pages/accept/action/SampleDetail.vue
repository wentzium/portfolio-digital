<template>
	<div class="h100pct">
		<div class="h80pct sys-mian-panel">
        	<div class="title">
    			<Row type="flex" justify="center" align="middle">
    				<Col span="24">
    					<div style="width: 18px;height: 18px;" class="assets-icon assets-icon-sample_manage"></div>
    					样本详细信息
    				</Col>
    			</Row>
    		</div>
    		<div class="content">
    			<Row class="pt10x">
					<Col span="24">
						<Form :label-width="80">
							<FormItem label="样本编号:">
					            <Input v-model="sampleNo" @on-change="sampleNoInput"><Button @click="acceptSample" slot="append">读取</Button></Input>
					        </FormItem>
						</Form>
					</Col>
				</Row>
				<template v-if="sampleInfo.sampleNo">
	    			<Carousel
	    				v-if="sampleInfo.imgList && sampleInfo.imgList.length"
				        :autoplay="setting.autoplay"
				        :autoplay-speed="setting.autoplaySpeed"
				        :dots="setting.dots"
				        :radius-dot="setting.radiusDot"
				        :trigger="setting.trigger"
				        :arrow="setting.arrow">
				        <CarouselItem v-for="img in sampleInfo.imgList">
				            <div class="demo-carousel ta-c"><img style="width: 100%;height: 130px;margin-right: 10px;" :src="img" /></div>
				        </CarouselItem>
				    </Carousel>
					
					<div class="infos">
						<Row class="pb10x"><Col span="4">固定时间:</Col><Col span="20">{{sampleInfo.fixedTime}}</Col></Row>
						<Row class="pb10x"><Col span="4">组织块数:</Col><Col span="20">{{sampleInfo.num}}</Col></Row>
					</div>
				</template>
    		</div>
        </div>
        <div class="h20pct pt16x">
        	<div class="h100pct sys-mian-panel">
				<!--<div class="pt16x pb10x ta-l"><Checkbox >自动保存</Checkbox></div>-->
				<div class="pt16x">
					
					<Row :gutter="5" v-if="sampleInfo.sampleNo">
						<Col span="8"><Button class="mr10x w100pct" type="primary" disabled="disabled" @click="getPathologyNo" >获取病理号</Button></Col>
						<Col span="8"><Button class="mr10x w100pct" type="primary"  disabled="disabled" >打印标签</Button></Col>
						<Col span="8"><Button class="mr10x w100pct" type="primary" @click="reject=true">退单</Button></Col>
					</Row>
				</div>
				
        	</div>
        </div>
        
        <Modal
	        v-model="reject"
	        @on-ok="reject=false"
	        @on-cancel="reject=false"
	        :width="300"
	        >
	        <template slot="header">
	        	退单原因
	        </template>
	        <div style="font-size: 16px;color: #505050;text-align: center;padding: 10px 0;">
	        	<div class="refuse">
					<Row :gutter="16">
						<Col span="24">
							<Select  class="w100pct" v-model="reason">
						        <Option  v-for="c in rejectReasonList" :value="c.id" :key="c.id">{{c.desc}}</Option>
						    </Select>
						</Col>
					</Row>
				</div>
	        </div>
	        <template slot="footer">
	        	<Button @click="reject=false">取消</Button>
	        	<Button type="primary" @click="rejectSample">确认</Button>
	        </template>
	    </Modal>
	</div>
</template>


<script>
import {getSampleInfo,receiveSample,rejectSample,getPathologyNo} from '@/api/sampleApi'

import InputSampleWeight from "./InputSampleWeight"
import Suggest from "./Suggest"
import SampleDelete from "./SampleDelete"
import {mapActions,mapGetters} from 'vuex'

export default {
  name: 'TransportSampleDetail',
  components: {InputSampleWeight,Suggest,SampleDelete},
  props:{
  	siteId:[String,Number]
  },
  data () {
    return {
    	reject:false,
    	reason:'',
    	rejectReasonList:[],
        setting: {
            autoplay: false,
            autoplaySpeed: 2000,
            dots: 'none',
            radiusDot: true,
            trigger: 'click',
            arrow: 'hover'
        },
        sampleNo:'',
    	sampleInfo:{},
    }
  },
  mounted(){
  	//退单原因
  	this.getDictList('500').then(res=>{this.rejectReasonList = res;})
  },
  methods: {
  	...mapActions({
		getDictList: 'getDictList'
	}),
  	init(){
  		this.sampleNo = '';
  		this.sampleInfo={};
  	},
  	parentInit(sample){
  		this.sampleNo = sample.sampleNo;
  		this.sampleInfo=sample;
  	},
  	sampleNoInput(){
  		if(this.sampleNo.length > 4){
  			let l_3 = this.sampleNo.substr(this.sampleNo.length - 3);
  			let l_2 = this.sampleNo.substr(this.sampleNo.length - 2);
  			if(l_3.startsWith('_') && !isNaN(l_2)){
  				this.acceptSample();
  			}
  		}
  	},
  	acceptSample(){
  		receiveSample({sampleNo:this.sampleNo,siteId:this.siteId}).then(fRes=>{
  			this.$Message.info(fRes.msg);
  			getSampleInfo({sampleNo:this.sampleNo}).then(res=>{
  				if(res && res.data) {
  					this.sampleInfo = res.data;
  					this.$emit('refresh',res.data)
  				}
  			})
  		})
  	},
  	getPathologyNo(){//获取病理号
  		getPathologyNo({
  			sampleNo:this.sampleNo
  		}).then(res=>{
  			this.$Message.info(res.msg);
  		})
  	},
  	rejectSample(){
  		rejectSample({
  			"reason": this.reason,
		    "sampleNo": this.sampleNo,
		    "siteId": this.siteId
  		}).then(res=>{
  			this.reject = false;
  			this.$Message.info(res.msg);
  			this.$emit('rejectSuccess',this.sampleNo)
  		})
  	}
  }
}
</script>

<style lang="less" scoped="">
.infos{
	.item{
		background: #F2F2F2;
		border-radius: 4px;
		line-height: 32px;
		margin-bottom: 10px;
		padding-left: 10px;
	}
}
</style>>