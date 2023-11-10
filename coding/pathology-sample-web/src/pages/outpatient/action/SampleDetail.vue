<template>
	<Modal
        v-model="visible"
        @on-ok="visible=false"
        @on-cancel="visible=false" 
        :styles="{background: '#FFFFFF'}"
        :width="840">
        <template slot="header">
        	<div class="modal-head-tabcard">
	        	<Tabs type="card" @on-click="tabChange">
	        		<TabPane label="样本详情" name="1">
	                </TabPane>
	                <TabPane label="物流状态" name="2">
	                </TabPane>
	    		</Tabs>
        	</div>
        	<template v-if="curTab == 1">
        		<div style="width: 18px;height: 18px;" class="assets-icon assets-icon-modal-default"></div>
        		样本信息
        	</template>
        	<template v-if="curTab == 2">
        		<div style="width: 18px;height: 18px;" class="assets-icon assets-icon-modal-logistics"></div>
        		物流信息
        	</template>
        </template>
        <div style="max-height: 300px;overflow-y: auto;">
        	<Row>
        		<Col span="10" style="padding-bottom: 50px;">
        			<template v-if="curTab == 1">
        				<Row><Col span="4">样本编号:</Col><Col span="20">{{sampleInfo.sampleNo}}</Col></Row>
        				离体时间
	        			<DatePicker v-model="sampleInfo.separationTime" class="ivu-leica-date" type="datetime" placeholder="请选择" style="width: 100%"></DatePicker>
	        			采取部位
	        			<Select v-model="sampleInfo.collectionLocationDicId" @on-change="collectionLocationChange" style="width:100%">
					        <Option  v-for="c in collectionLocation" :value="c.id" :key="c.value">{{c.id}}</Option>
				        </Select>
	        			样本类型
	        			<Select v-model="sampleInfo.typeDicId" @on-change="sampleTypeChange" style="width:100%">
					        <Option  v-for="c in sampleTypeList" :value="c.id" :key="c.id">{{c.desc}}</Option>
					    </Select>
	        			组织块数
	        			<Select v-model="sampleInfo.num" style="width:100%">
					        <Option value="1">1</Option>
					        <Option value="2">2</Option>
					        <Option value="3">3</Option>
					    </Select>
					    固定时间
					    <DatePicker :transfer="Boolean(true)" type="datetime" v-model="sampleInfo.fixedTime" show-week-numbers size="small" style="width: 100%"></DatePicker>
						固定液类型
					   	<Select v-model="sampleInfo.fixativeType" @on-change="fixedLiquidTypeChange" style="width:100%">
					        <Option  v-for="c in fixedLiquidTypeList" :value="c.id" :key="c.id">{{c.desc}}</Option>
					    </Select>
					           样本重量
					    <Input v-model="sampleInfo.sampleWeight" ><span slot="append">g</span></Input>
					    固定液体积
					    <Input v-model="sampleInfo.fixativeVolume" ><span slot="append">ml</span></Input>
					    转运容量
					    <Input v-model="sampleInfo.transferContainer" ><span slot="append">ml</span></Input>
	        			样本说明
	        			<Input v-model="sampleInfo.desc" type="textarea" :rows="4" placeholder="样本说明" />
        				<!--<Row><Col span="4">离体时间:</Col><Col span="20">{{sampleInfo.separationTime}}</Col></Row>
        				<Row><Col span="4">采取部位:</Col><Col span="20">{{sampleInfo.collectionLocationDicId}}</Col></Row>
        				<Row><Col span="4">组织块数:</Col><Col span="20">{{sampleInfo.num}}</Col></Row>
        				<Row><Col span="4">固定状态:</Col><Col span="20">{{sampleInfo.fixedStatusDesc}}</Col></Row>
        				<Row><Col span="4">物流状态:</Col><Col span="20">{{sampleInfo.logisticsStatusDesc}}</Col></Row>
        				<Row><Col span="4">标签状态:</Col><Col span="20">{{sampleInfo.tagStatusDesc}}</Col></Row>
        				<Row><Col span="4">样本说明:</Col><Col span="20">{{sampleInfo.desc}}</Col></Row>-->
        			</template>
        			<template v-if="curTab == 2">
        				<div style="border-bottom: 1px solid #F2F2F2;padding: 10px;">
        					<div style="width: 18px;height: 18px;" class="assets-icon assets-icon-sample-usable"></div>
        					物流信息
        				</div>
        				<div style="padding: 10px;">
	        				<!--<Row><Col span="4">运转箱ID:</Col><Col span="20">32242322</Col></Row>
	        				<Row><Col span="4">样本ID:</Col><Col span="20">32242322</Col></Row>
	        				<Row><Col span="4">样本快数:</Col><Col span="20">32242322</Col></Row>-->
	        				<Row><Col span="4">物流信息:</Col>
	        					<Col span="20" style="padding: 10px 0;">
	        						<Timeline>
								        <TimelineItem style="padding: 0;" v-for="k in logisticsInfo" :key="k.time">
								            <p style="font-size: 12px;color: #333333;">{{k.time}} {{k.desc}}</p>
								        </TimelineItem>
								    </Timeline>
	        					</Col>
	        				</Row>
        				</div>
        			</template>
        		</Col>
        		<Col span="14" style="padding: 10px;">
        			<div style="width: 100%;height: 200px;position: relative;">
        				<img style="top: 25%;left: 25%;position: absolute;" width="50%" height="50%" src="@/assets/default_img.png" />
        				<!--<img width="100%" height="100%" src="@/assets/eg.png" />-->
        				<!--<video id="video" class="w100pct h100pct" autoplay playsinline></video>
        				<canvas style="display: none;" id="canvas" width="240" height="160"></canvas>-->
        			</div>
        			<div style="margin: 10px 0;background: #454545;color: #fff;line-height: 30px;">
        				<Row class="ta-c" >
        					<!--<Col span="7">补光</Col>
        					<Col span="1"><Divider type="vertical" /></Col>-->
        					<!--<Col span="7" ><span style="cursor: pointer;" @click="getPhoto">拍摄</span></Col>-->
        					<Col span="11" ><span style="cursor: pointer;">拍摄</span></Col>
						    <Col span="2"><Divider type="vertical" /></Col>
        					<Col span="11">全屏</Col>
        				</Row>
        			</div>
        			<div class="word-line" style="overflow: auto;">
        				<img v-for="img in sampleInfo.imgList" style="width: 130px;height: 130px;margin-right: 10px;" :src="img" />
        			</div>
        		</Col>
        	</Row>
        </div>
        <template slot="footer">
        	<Button type="primary" @click="sampleAction('print')">打印</Button>
        	<Button type="primary" @click="save">确认</Button>
        </template>
    </Modal>
</template>

<script>
import {getSampleInfo,getSampleLogistics,saveSampleInfo} from '@/api/sampleApi'

export default {
  name: 'SampleDetail',
  components: {},
  props:{
  	siteId:[String,Number],
  	applyNo:[String,Number]
  },
  data () {
    return {
    	visible:false,
    	collectionLocation:[],
    	sampleTypeList:[],
    	fixedLiquidTypeList:[],
    	curTab:1,
    	sampleNo:'',
    	sampleInfo:{},
    	logisticsInfo:[],
    }
  },
  mounted(){
  	//采集部位
  	this.getDictList('100').then(res=>{this.collectionLocation = res;})
  	//样本类型
  	this.getDictList('200').then(res=>{this.sampleTypeList = res;})
  	//固定液类型
  	this.getDictList('300').then(res=>{this.fixedLiquidTypeList = res;})
  },
  methods: {
  	...mapActions({
		getDictList: 'getDictList'
	}),
  	collectionLocationChange(e){
  		this.collectionLocation.map(item=>{
  			if(item.id == e) this.sampleInfo.collectionLocationDesc = item.desc;
  		})
  	},
  	sampleTypeChange(e){
  		this.sampleTypeList.map(item=>{
  			if(item.id == e) this.sampleInfo.typeDesc = item.desc;
  		})
  	},
  	fixedLiquidTypeChange(e){
  		this.fixedLiquidTypeList.map(item=>{
  			if(item.id == e) this.sampleInfo.fixativeTypeDesc = item.desc;
  		})
  	},
  	show(sampleNo){
  		this.visible = true;
  		this.curTab = 1;
  		this.sampleNo = sampleNo;
  		getSampleInfo({sampleNo}).then(res=>{
  			if(res.data) {
  				res.data.num += ''
  				this.sampleInfo = res.data;
  			}
  		},err=>{console.log(err)});
  		getSampleLogistics({sampleNo}).then(res=>{
  			this.logisticsInfo = res.data;
  		},err=>{console.log(err)})
  	},
  	save(){
  		this.visible=false;
  	    let {
  		  applyNo,
		  collectionLocationDesc,
		  collectionLocationDicId,
		  desc,
		  imgList,
		  num,
		  separationTime,
		  typeDesc,
		  typeDicId,
		  fixativeType,
		  fixedTime,
		  sampleWeight,
		  fixativeVolume,
		  transferContainer
  		} = this.sampleInfo;
  		
  		if(separationTime) separationTime = moment(separationTime).format("YYYY-MM-DD HH:mm:ss");
  		if(fixedTime) fixedTime = moment(fixedTime).format("YYYY-MM-DD HH:mm:ss");
  		saveSampleInfo(
		{
		  collectionLocationDesc,
		  collectionLocationDicId,
		  desc,
		  imgList,
		  num,
		  separationTime,
		  typeDesc,
		  typeDicId,
		  fixativeType,
		  fixedTime,
		  sampleWeight,
		  fixativeVolume,
		  transferContainer,
		  siteId:this.siteId,
		  sampleNo:this.sampleNo,
		  applyNo:this.applyNo
  		}
  		).then(res=>{
  			this.$emit('refresh')
  			if(res.msg) this.$Message.success(res.msg)
  			this.visible = false;
  		},err=>{console.log(err)})
  	},
  	tabChange(name){
  		this.curTab = name;
  	},
  	sampleAction(actionType){
  		this.visible = false;
  		this.$emit(actionType,{
  			type:actionType,
  			sampleNo:this.sampleNo
  		})
  	}
  }
}
</script>

<style lang="less">
@import "../../../index.less";

.modal-head-tabcard{
	position:absolute;
	width:100%;
	left:0;
	top:-32px;
	height:34px;
    .ivu-tabs-card{
        height: 100%;
        .ivu-tabs-content-animated{display: none;}
        .ivu-tabs-bar{
        	border-bottom: none;
            .ivu-tabs-tab{background: @primary-color;color: #fff;border:none}
            .ivu-tabs-tab-active {
                position: relative;
                color: @primary-color;
                background: #fff;
                border: none;
            }
            .ivu-tabs-tab-active:before{
                content: " ";
                display: inline-block;
                position: absolute;
                width: 30px;
                height: 2px;
                background: dodgerblue;
                top: 0px;
                transform: translateX(50%);
            }
        }
        .ivu-tabs-content {
            box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);
            height: calc(~'100% - 32px');
            margin-top: -16px;
            .ivu-tabs-tabpane {
                background: #fff;
                padding: 16px;
                .main-lc{
                    height: 95%;
                    background: #f2f2f2;
                    border: 1px solid #cbcbcb;
                    border-radius: 2px;
                }
                .main-fk{
                    height: 100%;
                    padding: 5px;
                    background: #f9f9f9;
                }
                .main-zl{
                    height: 100%;
                    padding: 5px;
                    background: #f9f9f9;
                }
            }
        }
    }
}
</style>