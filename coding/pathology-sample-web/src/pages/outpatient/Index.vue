<template>
	<div class="h100pct">
		<div class="head">
    		<Row>
    			<Col span="8"><strong>门诊站点</strong></Col>
    			<Col span="16" class="ta-r">
    				<Button class="mr10x" type="primary" @click="logisticsQuery">物流查询</Button>
    				<Button :disabled="applyNo==''" type="primary" icon="ios-paper-plane-outline" @click="sentApply"> 发送申请单</Button>
    			</Col>
    		</Row>
    	</div>
    	<div class="com-content-body">
			<Row :gutter="16" class="h100pct">
		        <Col span="9" class="h100pct">
		        	<div style="height: 60%;padding-bottom: 20px;">
		            	<section class="h100pct sys-mian-panel">
		            		<div class="title">
		            			<Row type="flex" justify="center" align="middle">
		            				<Col span="12">
		            					<div style="width: 18px;height: 18px;" class="assets-icon assets-icon-modal-patient"></div>
		            					病人信息
		            				</Col>
		            				<Col class="fs14x ta-r" span="12">手术室编号：{{siteId}}</Col>
		            			</Row>
		            		</div>
		            		<div class="content">
		            			<Row :gutter="10" style="padding: 5px 0;">
		            				<Col span="12">
		            					ID:
		            					<Input id="outPatientPagePatientNoInput" v-model="patientNo" size="small" ><Button @click="getPatientInfo" slot="append">读取</Button></Input>
		            				</Col>
		            				<!--<Col span="12">病理号：
		            					<Input size="small" ><Button slot="append">生成</Button></Input>
		            				</Col>-->
		            			</Row>
		            			
		            			<div class="main">
		            				<vuescroll ref="vuescroll" :opt="{bar:{background:'#ffffff'},scrollPanel:{scrollingX: false,}}">
			            				<Row :gutter="10">
				            				<Col span="6">患者姓名:<Input v-model="patientInfo.name" size="small" :disabled="inputDisabled" ></Input></Col>
				            				<Col span="6">性别:<Input v-model="patientInfo.sex" size="small" :disabled="inputDisabled" ></Input></Col>
				            				<Col span="6">年龄:<Input v-model="patientInfo.age" size="small" :disabled="inputDisabled" ></Input></Col>
				            				<Col span="6">婚姻状况:<Input v-model="patientInfo.marriageStatus" size="small" :disabled="inputDisabled" ></Input></Col>
				            			</Row>
				            			<Row :gutter="10">
				            				<Col span="6">籍贯:<Input v-model="patientInfo.birthPlace" size="small" :disabled="inputDisabled" ></Input></Col>
				            				<Col span="6">职业:<Input v-model="patientInfo.occupation" size="small" :disabled="inputDisabled" ></Input></Col>
				            				<Col span="12">地址:<Input v-model="patientInfo.address" size="small" :disabled="inputDisabled" ></Input></Col>
				            			</Row>
				            			<Row :gutter="10">
				            				<Col span="12">送检医院:<Input v-model="patientInfo.inspectionHospital" size="small" :disabled="inputDisabled" ></Input></Col>
				            				<Col span="6">科别:<Input v-model="patientInfo.department" size="small" :disabled="inputDisabled" ></Input></Col>
				            				<Col span="6">门诊号:<Input v-model="patientInfo.outpatientNo" size="small" :disabled="inputDisabled" ></Input></Col>
				            			</Row>
				            			<!--<Row :gutter="10" style="padding: 5px 0;">
				            				<Col span="6">病房:<Input v-model="patientInfo.sickroom" size="small" :disabled="inputDisabled" ></Input></Col>
				            				<Col span="6">住院号:<Input v-model="patientInfo.hospitalNo" size="small" :disabled="inputDisabled" ></Input></Col>
				            				<Col span="6">床号:<Input v-model="patientInfo.bedNo" size="small" :disabled="inputDisabled" ></Input></Col>
				            			</Row>-->
			            			</vuescroll>
		            			</div>
		            		</div>
		            	</section>
		            </div>
		            <div class="sys-mian-tab-panel" style="height: 40%;">
		            	 <Tabs type="card">
			                <TabPane class="h100pct fs12x" label="临床">
			                	<Row class="h100pct" :gutter="10" style="padding: 5px 0;">
		            				<Col class="h100pct" span="12">
		            					<label>病史摘要及临床检查所见</label>
		            					<div class="main-lc">
		            						<textarea v-model="patientInfo.medicalHistorySummary" :disabled="inputDisabled"
		            						class="w100pct h100pct"
		            						 placeholder="病史摘要及临床检查所见" />
		            					</div>
		            				</Col>
		            				<Col class="h100pct" span="12">
		            					<label>手术名称及手术所见</label>
		            					<div class="main-lc">
		            						<textarea v-model="patientInfo.operationSummary" :disabled="inputDisabled"
		            						class="w100pct h100pct"
		            						 placeholder="手术名称及手术所见" />
		            					</div>
		            				</Col>
		            			</Row>
			                </TabPane>
			                <TabPane label="妇科">
			                	<div class="main-fk">
		            				<Row :gutter="10" style="padding: 5px 0;">
			            				<Col span="6">妊次:<Input v-model="patientInfo.pregnancy" size="small" :disabled="inputDisabled" ></Input></Col>
			            				<Col span="6">产次:<Input v-model="patientInfo.parity" size="small" :disabled="inputDisabled" ></Input></Col>
			            				<Col span="6">末次妊娠:<Input v-model="patientInfo.lastPregnancy" size="small" :disabled="inputDisabled" ></Input></Col>
			            				<Col span="6">月经史:<Input v-model="patientInfo.menstrualHistory" size="small" :disabled="inputDisabled" ></Input></Col>
			            			</Row>
			            			<Row :gutter="10" style="padding: 5px 0;">
			            				<Col span="6">初经:<Input v-model="patientInfo.firstMenstruation" size="small" :disabled="inputDisabled" ></Input></Col>
			            				<Col span="6">周期:<Input v-model="patientInfo.period" size="small" :disabled="inputDisabled" ></Input></Col>
			            				<Col span="6">前次月经:<Input v-model="patientInfo.preMenstrual" size="small" :disabled="inputDisabled" ></Input></Col>
			            				<Col span="6">末次月经:<Input v-model="patientInfo.lastPregnancy" size="small" :disabled="inputDisabled" ></Input></Col>
			            			</Row>
			            			<Row :gutter="10" style="padding: 5px 0;">
			            				<Col span="6"><div class="word-line">是否内分泌治疗:</div><Input v-model="patientInfo.bedNo" size="small" :disabled="inputDisabled" ></Input></Col>
			            				<Col span="6">治疗日期:
			            					<!--<Input v-model="patientInfo.treatmentDate" size="small" :disabled="inputDisabled" ></Input>-->
			            					<DatePicker :disabled="inputDisabled" :transfer="Boolean(true)" type="date" v-model="patientInfo.treatmentDate" show-week-numbers size="small" style="width: 100%"></DatePicker>
			            				</Col>
			            				<Col span="6">剂量:<Input v-model="patientInfo.dose" size="small" :disabled="inputDisabled" ></Input></Col>
			            				<Col span="6">
			            					<div class="word-line">刮宫或采样日期:</div>
			            					<!--<Input v-model="patientInfo.dcOrSamplingDate" size="small" :disabled="inputDisabled" ></Input>-->
			            					<DatePicker :disabled="inputDisabled" :transfer="Boolean(true)" type="date" v-model="patientInfo.dcOrSamplingDate" show-week-numbers size="small" style="width: 100%"></DatePicker>
			            				</Col>
			            			</Row>
		            			</div>
			                </TabPane>
			                <TabPane label="肿瘤">
			                	<div class="main-zl">
		            				<Row :gutter="10" style="padding: 5px 0;">
			            				<Col span="8">肿瘤部位:<Input v-model="patientInfo.tumorSite" size="small" :disabled="inputDisabled" ></Input></Col>
			            				<Col span="8">肿瘤大小形状:<Input v-model="patientInfo.tumorSizeAndShape" size="small" :disabled="inputDisabled" ></Input></Col>
			            				<Col span="8">活动度:<Input v-model="patientInfo.activityDegree" size="small" :disabled="inputDisabled" ></Input></Col>
			            			</Row>
			            			<Row :gutter="10" style="padding: 5px 0;">
			            				<Col span="8">肿瘤生长速度:<Input v-model="patientInfo.tumorGrowthRate" size="small" :disabled="inputDisabled" ></Input></Col>
			            				<Col span="8">肿瘤坚度:<Input v-model="patientInfo.firmness" size="small" :disabled="inputDisabled" ></Input></Col>
			            				<Col span="8">肿瘤发现日期:
			            					<!--<Input v-model="patientInfo.tumorDiscoveryDate" size="small" :disabled="inputDisabled" ></Input>-->
			            					<DatePicker :disabled="inputDisabled" :transfer="Boolean(true)" type="date" v-model="patientInfo.tumorDiscoveryDate" show-week-numbers size="small" style="width: 100%"></DatePicker>
			            				</Col>
			            			</Row>
			            			<Row :gutter="10" style="padding: 5px 0;">
			            				<Col span="24"><div class="word-line">转移位置:</div><Input v-model="patientInfo.transferLocation" size="small" :disabled="inputDisabled" ></Input></Col>
			            			</Row>
		            			</div>
			                </TabPane>
			            </Tabs>
		            </div>
		        </Col>
		        <Col span="15" class="h100pct">
		            <div class="h100pct sys-mian-panel">
		            	<div class="title">
	            			<Row type="flex" justify="center" align="middle">
	            				<Col span="12">
	            					<div style="width: 18px;height: 18px;" class="assets-icon assets-icon-sample_manage"></div>
	            					样本管理
	            				</Col>
	            				<Col class="fs14x ta-r" span="12">
	            					<Button type="primary" icon="ios-print-outline" @click="printTag" class="ml10x" :loading="printTagFlag">打印</Button>
	            					<Button type="primary" icon="ios-remove-circle-outline" @click="sampleDeleteShow" class="ml10x">作废</Button>
	            				</Col>
	            			</Row>
	            		</div>
	            		<div class="content">
	            			<div class="h100pct" >
	            				<vuescroll>
			            			<Row :gutter="10" type="flex">
			            				<Col span="6" v-for="sample in sampleList">
			            					<div class="sample w100pct posR" style="border: 1px solid #CBCBCB;border-radius: 2px;margin-bottom: 10px;">
			            						<div class="w100pct posR" style="padding-bottom: 75%;" :class="[sample.fixedStatus==0?'bg-status-unfixed':'bg-status-fixed']">
			            							<div class="sample-head">
			            								<span class="dpIB ta-c" style="width: 20px;line-height: 20px;color: #fff;">{{sample.sampleNo}}</span>
			            								<Checkbox :value="selectSample.sampleNo == sample.sampleNo" @on-change="sampleSelect(sample)" class="fr"></Checkbox>
			            							</div>
			            						</div>
			            						<div style="padding: 10px;">
			            							<Row :gutter="10" style="padding-bottom: 10px;" class="vaM">
			            								<Col  class="ta-c" span="6">
			            									<div class="status-icon " :class="[sample.availableStatus==0?'status-icon-usable':'status-icon-disable']"></div>
			            								</Col>
			            								<Col  class="ta-c" span="6">
			            									<div class="status-icon" :class="[sample.isFrozen?'status-icon-frozen':'status-icon-normal']"></div>
			            								</Col>
			            								<Col  class="ta-c" span="6">
			            									<div class="status-icon" :class="[sample.fixedStatus==0?'status-icon-unfixed':'status-icon-fixed']"></div>
			            								</Col>
			            								<Col  class="ta-c" span="6">
			            									<div class="status-icon " :class="[sample.tagStatus==0?'status-icon-no-print':'status-icon-printed']"></div>
			            								</Col>
			            							</Row>
			            							<p><Button @click="sampleDetailShow(sample)" style="width: 100%;" type="primary">详情</Button></p>
			            						</div>
			            					</div>
			            				</Col>
			            				<Col span="6" style="margin-bottom: 10px;">
			            					<div class="add-sample" @click="addSampleShow" v-if="applyNo">
			            						<div style="width: 50px;height: 50px;" class="assets-icon assets-icon-default_img"></div>
			            						<p>添加样本</p>
			            					</div>
			            				</Col>
			            			</Row>
			            		</vuescroll>
	            			</div>
		            		<div class="foot">
			        			<!--<Page :total="100" show-elevator />-->
		            		</div>
	            		</div>
		            </div>
		        </Col>
		    </Row>
    	</div>
		
		<Modal
	        v-model="chooseApply"
	        :mask-closable="false"
	        :closable=false
	        @on-ok="chooseApply=false"
	        @on-cancel="chooseApply=false"
	        :width="300">
	        <template slot="header">
	        	<div style="width: 18px;height: 18px;" class="assets-icon assets-icon-modal-default"></div>
	        	选择申请单
	        </template>
	        <div style="font-size: 16px;color: #505050;text-align: center;padding: 40px 0;">
	        	<Select v-model="applyNo" style="width:200px">
			        <Option v-for="item in applyList" :value="item.applyNo" :key="item.applyNo">{{ item.applyNo }}</Option>
			    </Select>
	        </div>
	        <template slot="footer">
	        	<Button @click="chooseApply=false;initApplyInfo();">初始化申请单</Button>
	        	<Button type="primary" @click="chooseApply=false;getSampleList();"  :disabled="applyNo?false:true">确认</Button>
	        </template>
	    </Modal>
	    
		<SampleDetail :siteId="siteId" :applyNo="applyNo" @refresh="getSampleList" @cancel="detailAction" @print="detailAction" ref="sampleDetail" />
		<AddSample ref="AddSample" @refresh="getSampleList" />
		<Printing ref="Printing" />
		<SampleDelete @refresh="getSampleList" ref="SampleDelete" />
		<LogisticsInfo :siteId="siteId" @applyNoChange="applyNoChange" ref="logisticsInfo"></LogisticsInfo>
		<print-tag ref="printTag" :siteId="siteId" :sample="selectSample"></print-tag>
	</div>
</template>

<script>
import {mapActions,mapGetters} from 'vuex'
import {getPatientInfoApi,getSampleListByApplyNo,saveApplyInfo,initApply,getApplyDetailInfo} from '@/api/sampleApi'

import vuescroll from 'vuescroll';
import GlobalLayout from "@/layouts/GlobalLayout"
import AddSample from "./action/AddSample"
import SampleDetail from "./action/SampleDetail"
import Printing from "./action/Printing"
import SampleDelete from "./action/SampleDelete"
import LogisticsInfo from "@/pages/logistics/LogisticsInfo"
import PrintTag from "@/components/PrintTag"

export default {
  name: 'OpreateIndex',
  components: {GlobalLayout,AddSample,SampleDetail,Printing,SampleDelete,vuescroll,LogisticsInfo,PrintTag},
  data () {
    return {
    	printTagFlag:false,
    	addSampleModel:false,
    	inputDisabled:false,
    	patientNo:'',
    	sampleList:[],
    	patientInfo:{},
    	chooseApply:false,
    	applyList:[],
    	applyNo:'',
    	selectSample:{},
    	siteId:''
    }
  },
  beforeRouteLeave(to, from, next) {
  	this.patientNo = '';
  	this.patientInfo = {};
  	this.sampleList = [];
  	this.applyList = [];
  	this.addSampleModel = false;
  	this.chooseApply=false;
  	this.selectSample = {};
	next()
  },
  mounted(){
  	this.getSiteIdByType(1).then(res=>{this.siteId = res});
  },
  watch:{
  	sacnCount(v){
  		this.patientNo = this.scanInput;
  		let ht = document.getElementById('outPatientPagePatientNoInput');
  		for(let i =0;i<ht.children.length;i++){
  			if(ht.children[i] == document.activeElement) {
  				this.getPatientInfo()
  			}
  		}
  	}
  },
  computed: {
  // 使用对象展开运算符将 getter 混入 computed 对象中
    ...mapGetters([
      'scanInput',
      "sacnCount"
    ])
  },
  methods: {
  	...mapActions({
		getSiteIdByType: 'account/getSiteIdByType',
	}),
	sentApply(){
		if(Object.keys.call(null,this.patientInfo).length) {
			this.patientInfo.applyNo = this.applyNo
			saveApplyInfo(this.patientInfo).then(res=>{
				this.$Message.info('申请单发送成功');
			})
		} else {
			this.$Message.info('请完善病人信息');
		}
	},
  	getPatientInfo(){
  		this.inputDisabled = false;
  		getPatientInfoApi({
  			patientNo:this.patientNo,
  			siteId:this.siteId,
  		}).then(res=>{
  			if(res.data) {
  				if(res.data.patientInfo.dcOrSamplingDate) res.data.patientInfo.dcOrSamplingDate= moment(res.data.patientInfo.dcOrSamplingDate).format('YYYY-MM-DD')
  				if(res.data.patientInfo.treatmentDate) res.data.patientInfo.treatmentDate= moment(res.data.patientInfo.treatmentDate).format('YYYY-MM-DD')
  				if(res.data.patientInfo.tumorDiscoveryDate) res.data.patientInfo.tumorDiscoveryDate= moment(res.data.patientInfo.tumorDiscoveryDate).format('YYYY-MM-DD')
  				this.patientInfo = res.data.patientInfo;
  				if(res.data.applyList && res.data.applyList.length){
  					this.applyList = res.data.applyList;
  					this.chooseApply = true;
  				} else if(res.data.applyNo) {
  					this.applyNo = res.data.applyNo;
  				} else {
  					this.initApplyInfo()
  				}
//				this.applyNo = res.data.applyNo || res.data.applyList[0].applyNo;
//				this.getSampleList()
  			}
  		},err=>{console.log(err)})
  	},
  	initApplyInfo(){//初始化申请单
  		initApply({
		  patientNo:this.patientNo,
  		  siteId:this.siteId,
  		}).then(res=>{
  			this.applyNo = res.data;
  			this.getSampleList()
  		})
  	},
  	sampleSelect(sn){
		if(this.selectSample && this.selectSample.sampleNo == sn.sampleNo) this.selectSample = {};
		else this.selectSample = sn;
  	},
  	getSampleList(){
  		getSampleListByApplyNo({
  			applyNo:this.applyNo
  		}).then(res=>{
  			if(res.data) this.sampleList = res.data
  		},err=>{console.log(err)})
  	},
  	logisticsQuery(){
  		this.$refs.logisticsInfo.show();
  	},
  	addSampleShow(){
  		this.$refs.AddSample.show(this.applyNo,this.siteId)
  	},
  	sampleDetailShow(sn){
	  	this.selectSample = sn
  		this.$refs.sampleDetail.show(sn.sampleNo)
  	},
  	printTag(){
  		if(!Object.keys.call(null,this.selectSample).length) {this.$Message.info('请先选择需要打印的样本');return ;}
  		this.printTagFlag = true;
  		this.$refs.printTag.print().then(res=>{
  			this.printTagFlag = false
  			this.$Message.success(res.msg)
  			this.applyNoChange('1',this.applyNo)
  		}).catch(err=>{this.printTagFlag = false;this.$Message.success(err.msg)});
  	},
  	printingShow(){
  		if(!Object.keys.call(null,this.selectSample).length) {this.$Message.info('请先选择需要打印的样本');return ;}
  		this.$refs.Printing.show()
  	},
  	sampleDeleteShow(){
  		if(!Object.keys.call(null,this.selectSample).length) {this.$Message.info('请先选择需要作废的样本');return ;}
  		this.$refs.SampleDelete.show(this.selectSample,this.siteId)
  	},
  	detailAction(action){
  		if(action.type == 'print') {
  			this.$refs.printTag.print().then(res=>{
	  			this.printTagFlag = false
	  			this.$Message.success(res.msg);
	  			this.applyNoChange('1',this.applyNo)
	  		}).catch(err=>{this.printTagFlag = false;this.$Message.success(err.msg)});
  		}
  		if(action.type == 'cancel') this.$refs.SampleDelete.show(action.sampleNo)
  	},
  	applyNoChange(t,data){
  		this.inputDisabled = true;
  		if(t == 1){//查询的是病人id
  			getApplyDetailInfo({
  				applyNo:data
  			}).then(res=>{
  				if(res.data) {
  					if(res.data.patientInfo.dcOrSamplingDate) res.data.patientInfo.dcOrSamplingDate= moment(res.data.patientInfo.dcOrSamplingDate).format('YYYY-MM-DD')
	  				if(res.data.patientInfo.treatmentDate) res.data.patientInfo.treatmentDate= moment(res.data.patientInfo.treatmentDate).format('YYYY-MM-DD')
	  				if(res.data.patientInfo.tumorDiscoveryDate) res.data.patientInfo.tumorDiscoveryDate= moment(res.data.patientInfo.tumorDiscoveryDate).format('YYYY-MM-DD')
  				
  					this.patientInfo = res.data.patientInfo;
  					this.patientNo = res.data.patientInfo.patientNo;
  					this.applyNo = data;
  					this.sampleList = res.data.sampleList;
  				}
  			})
  		} else if(t == 2) {//查询的是申请单
  			if(data.detail.patientInfo.dcOrSamplingDate) data.detail.patientInfo.dcOrSamplingDate= moment(data.detail.patientInfo.dcOrSamplingDate).format('YYYY-MM-DD')
			if(data.detail.patientInfo.treatmentDate) data.detail.patientInfo.treatmentDate= moment(data.detail.patientInfo.treatmentDate).format('YYYY-MM-DD')
			if(data.detail.patientInfo.tumorDiscoveryDate) data.detail.patientInfo.tumorDiscoveryDate= moment(data.detail.patientInfo.tumorDiscoveryDate).format('YYYY-MM-DD')
  			
  			this.patientInfo = data.detail.patientInfo;
			this.patientNo = data.detail.patientInfo.patientNo;
			this.applyNo = data.applyNo;
			this.sampleList = data.detail.sampleList;
  		}
  	}
  }
}
</script>

<style lang="less" scoped>
@import "../../index.less";

.content{
	padding:10px 0;
	.sample-head{
		position:absolute;
		width:100%;
		left:0;
		top:0;
	}
	.add-sample{
		height: 100%;
		min-height: 100px;
		margin-bottom:10px;
		border: 1px dashed #c7c7c7;
		background: #f2f2f2;
		cursor:pointer;
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
	}
	
	.bg-status-fixed{
		background: url(../../assets/sample/status-fixed.png) 0 0 no-repeat;
		background-size: 100%;
	}
	.bg-status-unfixed{
		background: url(../../assets/sample/status-unfixed.png) 0 0 no-repeat;
		background-size: 100%;
	}
	.bg-status-no-print{
		background: url(../../assets/sample/status-no-print.png) 0 0 no-repeat;
		background-size: 100%;
	}
	
	.status-icon{
		width: 100%;
		padding-bottom: 100%;
		background-color:#f2f2f2 ;
		background-position: 0 0;
		background-repeat: no-repeat;
		background-size: 100%;
	}
	.status-icon-disable{background-image: url(../../assets/sample/disable.png);}
	.status-icon-fixed{background-image: url(../../assets/sample/fixed.png);}
	.status-icon-frozen{background-image: url(../../assets/sample/frozen.png);}
	.status-icon-no-print{background-image: url(../../assets/sample/no-print.png);}
	.status-icon-normal{background-image: url(../../assets/sample/normal.png);}
	.status-icon-printed{background-image: url(../../assets/sample/printed.png);}
	.status-icon-unfixed{background-image: url(../../assets/sample/unfixed.png);}
	.status-icon-usable{background-image: url(../../assets/sample/usable.png);}
}
</style>