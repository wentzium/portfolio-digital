<template>
	<div>
		<Row>
			<Col span="24">
				<Form :label-width="80">
					<FormItem label="样本编号:">
			            <Input v-model="sampleNo" ><Button @click="fixedSample" slot="append">读取</Button></Input>
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
		    <div style="padding: 0 20px;">
				<Row class="pb10x"><Col span="4">固定时间:</Col><Col span="20">{{sampleInfo.fixedTime}}</Col></Row>
				<!--<Row class="pb10x"><Col span="4">组织块数:</Col><Col span="20">{{sampleInfo.num}}</Col></Row>-->
				
				<Row class="pb10x">
					<Col span="4">固定液类型:</Col>
					<Col span="20">
						<Select v-model="sampleInfo.fixativeType" style="width:200px">
					        <Option  v-for="c in fixedLiquidTypeList" :value="c.id" :key="c.id">{{c.desc}}</Option>
					    </Select>
					</Col>
				</Row>
				<Row class="pb10x"><Col span="4">样本重量:</Col><Col span="20"><Input v-model="sampleInfo.sampleWeight" ><span slot="append">g</span></Input></Col></Row>
				<Row class="pb10x"><Col span="4">固定液体积:</Col><Col span="20"><Input v-model="sampleInfo.fixativeVolume" ><span slot="append">ml</span></Input></Col></Row>
				<Row class="pb10x"><Col span="4">转运容量:</Col><Col span="20"><Input v-model="sampleInfo.transferContainer" ><span slot="append">ml</span></Input></Col></Row>
		    </div>
			
		</template>
		<div class="foot" v-if="sampleInfo.sampleNo">
			<!--<div class="pb10x ta-l"><Checkbox >自动保存</Checkbox></div>-->
			<div>
				<Button class="mr10x" icon="ios-print-outline" type="primary" @click="printTag" :loading="printTagFlag">打印</Button>
		            					
    			<Button class="mr10x" type="primary" @click="sampleDeleteShow">退样</Button>
    			<Button class="mr10x" type="primary" @click="inputSampleWeightShow">保存</Button>
			</div>
		</div>
		
		<InputSampleWeight ref="InputSampleWeight" @weight="showSuggest"></InputSampleWeight>
		<Suggest ref="suggest"</Suggest>
		<SampleDelete ref="SampleDelete"></SampleDelete>
		<print-tag ref="printTag" :siteId="siteId" :sample="sampleInfo"></print-tag>
	</div>
</template>


<script>
import {getSampleInfo,fixSample,updateFixedInfo,returnSample} from '@/api/sampleApi'
import {mapActions,mapGetters} from 'vuex'

import vuescroll from 'vuescroll';
import InputSampleWeight from "./InputSampleWeight"
import Suggest from "./Suggest"
import SampleDelete from "./SampleDelete"
import PrintTag from "@/components/PrintTag"

export default {
  name: 'FixedSampleDetail',
  components: {vuescroll,InputSampleWeight,Suggest,SampleDelete,PrintTag},
  props:{
  	siteId:[String,Number]
  },
  data () {
    return {
    	printTagFlag:false,//打印标签
    	sampleNo:'',
    	sampleInfo:{},
    	fixedLiquidTypeList:[],
    	defaultFixedLiquidType:{
    		feml:{
    			name:'福尔马林',
    			id:''
    		},
    		jj:{
    			name:'酒精',
    			id:''
    		}
    	},
        setting: {
            autoplay: false,
            autoplaySpeed: 2000,
            dots: 'none',
            radiusDot: true,
            trigger: 'click',
            arrow: 'hover'
        }
    }
  },
  mounted(){
  	//固定液类型
  	this.getDictList('300').then(res=>{
  		this.fixedLiquidTypeList = res;
  		this.fixedLiquidTypeList.map(item=>{
  			if(item.desc == '福尔马林') this.defaultFixedLiquidType.feml.id = item.id;
  			if(item.desc == '酒精') this.defaultFixedLiquidType.jj.id = item.id;
  		})
  	
  	})
  },
  methods: {
  	...mapActions({
		getDictList: 'getDictList'
	}),
  	scanInput(sampleNo){
  		this.sampleNo = sampleNo;
  		this.fixedSample();
  	},
  	init(){
  		this.sampleInfo = {};
  		this.sampleNo = ''
  	},
  	fixedSample(){
  		getSampleInfo({sampleNo:this.sampleNo}).then(res=>{
  			if(res && res.data) {
  				fixSample({sampleNo:this.sampleNo,siteId:this.siteId}).then(fRes=>{
  					this.sampleInfo = fRes.data;
  					if(this.sampleInfo.typeDesc == '常规样品') {
  						if(!this.sampleInfo.fixativeType || this.sampleInfo.fixativeType == ''){
  							this.sampleInfo.fixativeType = this.defaultFixedLiquidType.feml.id;
  							this.sampleInfo.fixativeTypeDesc = '福尔马林'
  						}
  					} else if (this.sampleInfo.typeDesc == '细胞学样品'){
  						if(!this.sampleInfo.fixativeType || this.sampleInfo.fixativeType == ''){
  							this.sampleInfo.fixativeType = this.defaultFixedLiquidType.jj.id;
  							this.sampleInfo.fixativeTypeDesc = '酒精'
  						}
  					}
  					this.$Message.info(fRes.msg);
  					this.$emit('refresh')
  				})
  			}
  		})
  	},
  	inputSampleWeightShow(){
  		let {fixativeType,fixativeTypeDesc,fixativeVolume,sampleNo,sampleWeight,transferContainer} = this.sampleInfo
  		
  		updateFixedInfo({
  			fixativeType,
  			fixativeTypeDesc,
  			fixativeVolume,
  			sampleNo,
  			sampleWeight,
  			transferContainer,
  			siteId:this.siteId
  		}).then(res=>{
  			this.$Message.info(res.msg);
  		})
  		
//		this.$refs.InputSampleWeight.show()
  	},
  	showSuggest(){
  		this.$refs.suggest.show()
  	},
  	sampleDeleteShow(){
  		let {sampleNo} = this.sampleInfo
  		returnSample({
		  "sampleNoList": [sampleNo],
		  "siteId": this.siteId
		}).then(res=>{
			this.sampleNo = '';
			this.sampleInfo = {};
  			this.$Message.info(res.msg);
  		})
  		
//		this.$refs.SampleDelete.show()
  	},
  	printTag(){
  		this.printTagFlag = true;
  		this.$refs.printTag.print().then(res=>{
  			this.printTagFlag = false
  			this.$Message.success(res.msg)
  		}).catch(err=>{this.printTagFlag = false;this.$Message.success(err.msg)});
  	},
  }
}
</script>

<style lang="less" scoped="">
</style>>