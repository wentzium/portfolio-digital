<template>
	<div class="h100pct">
		<div class="head">
    		<Row>
    			<Col span="8"><strong>手术站点</strong></Col>
    			<Col span="16" class="ta-r">
    				<Button v-if="step==2" class="mr10x" type="primary" @click="step=1;isNew=false;curRowInfo={};init()">返回</Button>
    				<Button class="mr10x" type="primary" @click="logisticsQuery">物流查询</Button>
    				<Button v-if="step==1" type="primary" icon="ios-add" @click="isNew=true;step2Init()"> 创建申请单</Button>
    				<template v-else>
    					<Button :disabled="applyNo==''" class="mr10x" type="primary" @click="saveApply"> 保存</Button>
    					<Button :disabled="applyNo==''" type="primary" @click="sendApply"> 发送</Button>
    				</template>
    			</Col>
    		</Row>
    	</div>
    	<div class="com-content-body">
    		<template v-if="step==2">
				<Row :gutter="16" class="h100pct">
			        <Col span="9" class="h100pct">
			        	<div style="height: 45%;padding-bottom: 10px;">
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
			            			<Row :gutter="10" >
			            				<Col span="12">
			            					ID:
			            					<Input v-model="patientNo" size="small" :disabled="inputDisabled" >
			            						<Button v-if="isNew" @click="newGetPatientInfo();" :disabled="canGetPatientInfo" slot="append">读取</Button>
			            					</Input>
			            				</Col>
			            				<Col span="12">送检医院：
			            					<Input placeholder="本院病人此项留空" v-model="patientInfo.inspectionHospital" size="small" ></Input>
			            				</Col>
			            				<Col span="24">送检项目：
			            					<Input v-model="checkItemDisplay" search enter-button="更改" @on-search="chooseSendCheckItem=true" readonly />
			            				</Col>
			            			</Row>
			            			
			            			<div class="main-100" >
			            				<vuescroll ref="vuescroll" :opt="{bar:{background:'#ffffff'},scrollPanel:{scrollingX: Boolean(false)}}">
				            				<Row :gutter="10" >
					            				<Col span="6">患者姓名:<Input v-model="patientInfo.name" size="small" :disabled="true" ></Input></Col>
					            				<Col span="6">性别:<Input v-model="patientInfo.sex" size="small" :disabled="true" ></Input></Col>
					            				<Col span="6">年龄:<Input v-model="patientInfo.age" size="small" :disabled="true" ></Input></Col>
					            				<Col span="6">婚姻状况:<Input v-model="patientInfo.marriageStatus" size="small" :disabled="true" ></Input></Col>
					            			</Row>
					            			<Row :gutter="10" >
					            				<!--<Col span="6">籍贯:<Input v-model="patientInfo.birthPlace" size="small" :disabled="true" ></Input></Col>-->
					            				<Col span="6">预约号:<Input v-model="applyNo" size="small" :disabled="true" ></Input></Col>
					            				<Col span="6">职业:<Input v-model="patientInfo.occupation" size="small" :disabled="true" ></Input></Col>
					            				<Col span="12">地址:<Input v-model="patientInfo.address" size="small" :disabled="true" ></Input></Col>
					            			</Row>
					            			<Row :gutter="10" >
					            				<!--<Col span="12">送检医院:<Input v-model="patientInfo.inspectionHospital" size="small" :disabled="true" ></Input></Col>-->
					            				<!--<Col span="6">科别:<Input v-model="patientInfo.department" size="small" :disabled="true" ></Input></Col>-->
					            				<!--<Col span="6">门诊号:<Input v-model="patientInfo.outpatientNo" size="small" :disabled="true" ></Input></Col>-->
					            				<Col span="6">病房:<Input v-model="patientInfo.sickroom" size="small" :disabled="true" ></Input></Col>
					            				<Col span="6">住院号:<Input v-model="patientInfo.hospitalNo" size="small" :disabled="true" ></Input></Col>
					            				<Col span="6">床号:<Input v-model="patientInfo.bedNo" size="small" :disabled="true" ></Input></Col>
					            				<Col span="6">科别:<Input v-model="patientInfo.department" size="small" :disabled="true" ></Input></Col>
					            			</Row>
					            			<Row :gutter="10" >
					            				<Col span="6">门诊号:<Input v-model="patientInfo.outpatientNo" size="small" :disabled="true" ></Input></Col>
					            			</Row>
				            			</vuescroll>
			            			</div>
			            		</div>
			            	</section>
			            </div>
			            <div class="sys-mian-tab-panel" style="height: 55%;">
			            	 <Tabs type="card">
				                <TabPane class="h100pct fs12x" label="临床">
				                	<!--<Row class="h50pct" :gutter="10" >
			            				<Col class="h100pct" span="12">
			            					<label>主诉</label>
			            					<div class="main-lc">
			            						<textarea v-model="patientInfo.chiefComplaint" :disabled="inputDisabled"
			            						class="w100pct h100pct"
			            						 placeholder="主诉" />
			            					</div>
			            				</Col>
			            				<Col class="h100pct" span="12">
			            					<label>专科查体</label>
			            					<div class="main-lc">
			            						<textarea v-model="patientInfo.specialPhysicalInspect" :disabled="inputDisabled"
			            						class="w100pct h100pct"
			            						 placeholder="专科查体" />
			            					</div>
			            				</Col>
			            			</Row>-->
				                	<Row class="h100pct" :gutter="10" >
			            				<Col class="h100pct" span="12">
			            					<label>是否曾接受病理检查</label>
			            					<div style="line-height: 32px;">
			            						<RadioGroup v-model="patientInfo.hasPathologyCheck">
											        <Radio :label="Number(1)">是</Radio>
											        <Radio :label="Number(0)">否</Radio>
											    </RadioGroup>
			            					</div>
			            					<!--<label>临床检查</label>
			            					<div class="main-lc h100pct">
			            						<textarea v-model="patientInfo.medicalHistorySummary" disabled="disabled"
			            						class="w100pct h100pct"
			            						placeholder="临床检查" />
			            					</div>-->
			            					<div class="h80pct pt10x">
			            						<label>临床检查</label>
			            						<div class="main-lc-sssj">
			            							<textarea v-model="patientInfo.medicalHistorySummary" disabled="disabled"
			            							class="w100pct h100pct"
			            							placeholder="临床检查" />
			            						</div>
			            					</div>
			            				</Col>
			            				<Col class="h100pct" span="12">
			            					<div>
			            						<label>手术名称</label>
			            						<Select :transfer="Boolean(true)" v-model="patientInfo.operationId" @on-change="operationNameChange" filterable>
									                <Option  v-for="c in operationNameList" :value="c.id" :key="c.id">{{c.desc}}</Option>
									                <Option  v-for="c in addOpts" :value="c.id" :key="c.id">{{c.desc}}</Option>
									                <Option value="new" style="border-top: 1px solid #C3C3C3;">其他</Option>
									            </Select>
			            						 <!--<Select v-model="patientInfo.operationTypeDicId" filterable>
									                <Option v-for="item in cityList" :value="item.value" :key="item.value">{{ item.label }}</Option>
									            </Select>
			            						<Input v-model="patientInfo.department" size="small" :disabled="true" ></Input>-->
			            					</div>
			            					<div class="h80pct pt10x">
			            						<label>手术所见</label>
			            						<div class="main-lc-sssj">
			            							<textarea v-model="patientInfo.operationSummary" 
			            							class="w100pct h100pct"
			            							 placeholder="手术所见" />
			            						</div>
			            					</div>
			            				</Col>
			            			</Row>
				                </TabPane>
				                <TabPane label="妇科">
				                	<div class="main-fk">
			            				<Row :gutter="10" >
				            				<Col span="6">妊次:<Input v-model="patientInfo.pregnancy" size="small" :disabled="inputDisabled" ></Input></Col>
				            				<Col span="6">产次:<Input v-model="patientInfo.parity" size="small" :disabled="inputDisabled" ></Input></Col>
				            				<Col span="6">末次妊娠:<Input v-model="patientInfo.lastPregnancy" size="small" :disabled="inputDisabled" ></Input></Col>
				            				<Col span="6">月经史:<Input v-model="patientInfo.menstrualHistory" size="small" :disabled="inputDisabled" ></Input></Col>
				            			</Row>
				            			<Row :gutter="10" >
				            				<Col span="6">初经:<Input v-model="patientInfo.firstMenstruation" size="small" :disabled="inputDisabled" ></Input></Col>
				            				<Col span="6">周期:<Input v-model="patientInfo.period" size="small" :disabled="inputDisabled" ></Input></Col>
				            				<Col span="6">前次月经:<Input v-model="patientInfo.preMenstrual" size="small" :disabled="inputDisabled" ></Input></Col>
				            				<Col span="6">末次月经:<Input v-model="patientInfo.lastMenstrual" size="small" :disabled="inputDisabled" ></Input></Col>
				            			</Row>
				            			<Row :gutter="10" >
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
			            				<Row :gutter="5">
				            				<Col span="8">肿瘤部位:<Input v-model="patientInfo.tumorSite" size="small" :disabled="inputDisabled" ></Input></Col>
				            				<Col span="8">肿瘤大小形状:<Input v-model="patientInfo.tumorSizeAndShape" size="small" :disabled="inputDisabled" ></Input></Col>
				            				<Col span="8">活动度:<Input v-model="patientInfo.activityDegree" size="small" :disabled="inputDisabled" ></Input></Col>
				            			</Row>
				            			<Row :gutter="5" >
				            				<Col span="8">肿瘤生长速度:<Input v-model="patientInfo.tumorGrowthRate" size="small" :disabled="inputDisabled" ></Input></Col>
				            				<Col span="8">肿瘤坚度:<Input v-model="patientInfo.firmness" size="small" :disabled="inputDisabled" ></Input></Col>
				            				<Col span="8">肿瘤发现日期:
				            					<!--<Input v-model="patientInfo.tumorDiscoveryDate" size="small" :disabled="inputDisabled" ></Input>-->
				            					<DatePicker :disabled="inputDisabled" :transfer="Boolean(true)" type="date" v-model="patientInfo.tumorDiscoveryDate" show-week-numbers size="small" style="width: 100%"></DatePicker>
				            				</Col>
				            			</Row>
				            			<Row :gutter="5" >
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
		            			<div class="h100pct">
		            				<vuescroll>
				            			<Row :gutter="10" type="flex">
				            				<Col span="6" v-for="sample in sampleList" :key="sample.sampleNo">
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
    		</template>
    		<template v-else>
    			<Row :gutter="16" class="h100pct">
			        <Col span="15" class="h100pct">
			        	<div class="h100pct sys-mian-panel">
			            	<div class="title">
		            			<Row type="flex" justify="center" align="middle">
		            				<Col span="9">
		            					<div style="width: 18px;height: 18px;" class="assets-icon assets-icon-list"></div>
		            					申请单列表
		            				</Col>
		            				<Col span="15" class="ta-r" style="font-size: 12px;">
		            					<Input v-model="queryInfo.patientNo" size="small" placeholder="搜索病人信息" style="width: 50%;">
		            						<span slot="append" @click="initByPatientNo">查询</span>
		            					</Input>
		            				</Col>
		            			</Row>
		            		</div>
		            		<div class="content">
		            			<div class="h100pct" style="padding-bottom: 42px;">
		            				<vuescroll>
		            					<Table class="h100pct" :row-class-name="rowClassName" @on-row-click="tableRowClick" :columns="columns" :data="data"></Table>
		            				</vuescroll>
		            			</div>
			            		<div class="foot">
				        			<Page 
				        				@on-change="pageChange"
				        				@on-page-size-change="pageSizeChange"
				        				:total="queryInfo.total" 
				        				:curent="queryInfo.page" 
				        				:page-size="queryInfo.pageSize" 
				        				show-elevator show-sizer />
			            		</div>
		            		</div>
			            </div>
			        </Col>
			        <Col span="9" class="h100pct">
	    				<RightContent @viewApplyNo="viewApplyNo" ref="rightContent" :siteId="siteId" ></RightContent>
			        </Col>
			    </Row>
    		</template>
    	</div>
		
		<Modal
	        v-model="addNewOption"
	        :mask-closable="false"
	        :closable=false
	        :width="300">
	        <template slot="header">
	        	<div style="width: 18px;height: 18px;" class="assets-icon assets-icon-modal-default"></div>
	        	新增手术名称
	        </template>
	        <div style="font-size: 16px;color: #505050;text-align: center;padding: 40px 0;">
	        	<Input v-model="newOpt" placeholder="请输入手术名称"/>
	        </div>
	        <template slot="footer">
	        	<Button @click="addNewOption=false;newOpt = ''">取消</Button>
	        	<Button type="primary" @click="addNewOptionOk" >确认</Button>
	        </template>
	    </Modal>
	    
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
	    
	    
	    <Modal
	        v-model="chooseSendCheckItem"
	        :mask-closable="false"
	        :closable=false
	        @on-ok="checkItemOk"
	        @on-cancel="checkItemCancel"
	        :width="500">
	        <template slot="header">
	        	<div style="width: 18px;height: 18px;" class="assets-icon assets-icon-modal-default"></div>
	        	检查项目
	        </template>
	        <div style="font-size: 16px;color: #505050;text-align: center;padding: 40px 0;height: 300px;">
	        	<!--<Select v-model="checkItemSelect" multiple :max-tag-count="2">
			        <Option  v-for="c in checkItemArray" :value="c.descriptionCode" :key="c.descriptionCode">{{c.description}}</Option>
			    </Select>-->
			    <Select v-model="checkItemSelect">
			        <Option  v-for="c in checkItemArray" :value="c.descriptionCode" :key="c.descriptionCode">{{c.description}}</Option>
			    </Select>
			    <div class="pt50x ta-l">
			    	如有相关历史检查请选择 <Button size="small" @click="getSearchCheckHis(true)">查找</Button>
			    </div>
	        </div>
	        <template slot="footer">
	        	<Button @click="checkItemCancel">取消</Button>
	        	<Button type="primary" @click="checkItemOk"  :disabled="applyNo?false:true">确认</Button>
	        </template>
	    </Modal>
	    
		<SampleDetail :siteId="siteId" :applyNo="applyNo" @refresh="getSampleList" @cancel="detailAction" @print="detailAction" ref="sampleDetail" />
		<AddSample ref="AddSample" @refresh="getSampleList" />
		<CheckHistory ref="CheckHistory" @change="getMedicalHistoryResultList" />
		<Printing :selectSample="selectSample" ref="Printing" />
		<SampleDelete @refresh="getSampleList" ref="SampleDelete" />
		<LogisticsInfo :siteId="siteId" @applyNoChange="applyNoChange" ref="logisticsInfo"></LogisticsInfo>
		<print-tag ref="printTag" :siteId="siteId" :sample="selectSample"></print-tag>
		<PrintPDF ref="printPDF"></PrintPDF>
	</div>
</template>

<script>
import {mapActions,mapGetters} from 'vuex'
import {getPatientInfoApi,searchCheckHis,getSampleListByApplyNo,saveApplyInfo,sendApplyInfo,initApply,getApplyDetailInfo,searchOperationList,searchExamRptPattern} from '@/api/sampleApi'

import vuescroll from 'vuescroll';
import GlobalLayout from "@/layouts/GlobalLayout"
import AddSample from "./action/AddSample"
import CheckHistory from "./action/CheckHistory"
import SampleDetail from "./action/SampleDetail"
import Printing from "./action/Printing"
import SampleDelete from "./action/SampleDelete"
import RightContent from "./action/RightContent"
import LogisticsInfo from "@/pages/logistics/LogisticsInfo"
import PrintTag from "@/components/PrintTag"
import PrintPDF from "@/components/PrintPDF"

export default {
  name: 'OpreateIndex',
  components: {GlobalLayout,AddSample,CheckHistory,SampleDetail,Printing,SampleDelete,vuescroll,LogisticsInfo,RightContent,PrintTag,PrintPDF},
  data () {
    return {
    	printTagFlag:false,//打印标签
    	addNewOption:false,//添加手术类型
    	newOpt:'',
    	step:1,
    	canGetPatientInfo:true,//同步病人信息后禁止点击
    	isNew:false,
    	columns: [
            {title: '病人ID',key: 'patientNo'},
            {title: '姓名',key: 'patientName'},
            {title: '性别',key: 'sex'},
            {title: '年龄',key: 'age'},
            {title: '申请单数量',key: 'applyNum'},
        ],
        data: [],
        operationNameList:[],
        addOpts:[],//用户自己添加的选择项
        queryInfo:{
          "patientNo":"",
		  "siteId": 0,
		  "page": 1,
		  "pageSize": 10,
		  "total":0,
		},
		curRowInfo:{},//选中的某一行
        
    	addSampleModel:false,
    	inputDisabled:false,
    	patientNo:'',
    	sampleList:[],
    	patientInfo:{},
    	checkItemList:[],//送检项目
    	checkItemArray:[],//送检项目 选择列表
    	checkItemSelect:null,
    	checkHistory:{},//选择的检查历史
//  	checkItemSelect:[],
    	checkItemDisplay:'',
    	chooseApply:false,
    	chooseSendCheckItem:false,
    	applyList:[],
    	applyNo:'',
    	selectSample:{},
    	siteId:''
    }
  },
  beforeRouteLeave(to, from, next) {
  	this.curRowInfo = {};
  	this.queryInfo.patientNo = ""
  	
  	this.patientNo = '';
  	this.patientInfo = {};
  	this.sampleList = [];
  	this.applyList = [];
  	this.addSampleModel = false;
  	this.chooseApply=false;
  	this.selectSample = {};
  	this.step = 1;
	next()
  },
  watch:{
  	sacnCount(v){
  		if(this.step == '1') {
  			this.queryInfo.patientNo = this.scanInput;
  			this.initByPatientNo();
  		} else {
  			this.patientNo = this.scanInput;
  			this.getPatientInfo()
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
  mounted(){
//	//手术类型
//	this.getDictList('600').then(res=>{this.operationNameList = res;})
  	//手术名称
  	this.getDictList('800').then(res=>{this.operationNameList = res;})
  	
  	this.getSiteIdByType(1).then(res=>{
  		this.siteId = res;
  		this.init()
  	});
  	
  	searchExamRptPattern().then(res=>{
  		if(res.data) this.checkItemArray = res.data
  	})
  },
  methods: {
  	...mapActions({
		getSiteIdByType: 'account/getSiteIdByType',
		getDictList: 'getDictList'
	}),
	addNewOptionOk(){
		let nid = 'new_add_opt_'+this.addOpts.length
		this.addOpts.push({
			id:nid,
			desc:this.newOpt
		})
		this.patientInfo.operationId = nid;
		this.patientInfo.operationName = this.newOpt;
		this.newOpt = '';
		this.addNewOption = false;
	},
	checkItemCancel(){
		if(!this.checkItemSelect){
			this.$Message.info('检查项目不能为空');
		} else {
			this.chooseSendCheckItem=false;
	//		this.checkItemSelect = [];
	//		this.checkItemList.map(item=>{
	//			this.checkItemSelect.push(item.descriptionCode);
	//		})
			this.checkItemSelect = this.checkItemList[0].descriptionCode
		}
	},
	checkItemOk(){
		if(!this.checkItemSelect){
			this.$Message.info('检查项目不能为空');
		} else {
			this.chooseSendCheckItem=false;
			this.checkItemDisplay = '';
			this.checkItemList = [];
			this.checkItemArray.map(item=>{
				if(this.checkItemSelect.includes(item.descriptionCode)){
					this.checkItemDisplay += item.description;
					this.checkItemList.push(item)
				}
			})
		}
		
	},
	operationNameChange(e){
		if(e == 'new'){
			this.addNewOption = true;
		} else if(!isNaN(e)){//数字
			this.operationNameList.map(item=>{
				if(item.id == e) this.patientInfo.operationName = item.desc;
			})
		}
  	},
	saveApply(){
		if(this.isNew && this.checkItemDisplay == ''){
			this.$Message.info('请选择检查项目');
			return ;
		}
		if(Object.keys.call(null,this.patientInfo).length) {
			this.patientInfo.applyNo = this.applyNo
			
			let saveInfo = _.cloneDeep(this.patientInfo);
			saveInfo.applyNo = this.applyNo;
			saveInfo.checkHistory = this.checkHistory;
			saveInfo.checkItemList = this.checkItemList;
			if(saveInfo.tumorDiscoveryDate) saveInfo.tumorDiscoveryDate = moment(saveInfo.tumorDiscoveryDate).format("YYYY-MM-DD HH:mm:ss");
			if(saveInfo.dcOrSamplingDate) saveInfo.dcOrSamplingDate = moment(saveInfo.dcOrSamplingDate).format("YYYY-MM-DD HH:mm:ss");
			if(saveInfo.treatmentDate) saveInfo.treatmentDate = moment(saveInfo.treatmentDate).format("YYYY-MM-DD HH:mm:ss");
			
			if(isNaN(saveInfo.operationId)) saveInfo.operationId = '';//如果是新增的手动清空id
			saveInfo.siteId = this.siteId;
			saveApplyInfo(saveInfo).then(res=>{
				this.$Message.info('保存成功');
			})
		} else {
			this.$Message.info('请完善病人信息');
		}
	},
	sendApply(){
		if(this.isNew && this.checkItemDisplay == ''){
			this.$Message.info('请选择检查项目');
			return ;
		}
		//发送申请单时保存下，然后再发送
		if(Object.keys.call(null,this.patientInfo).length) {
			this.patientInfo.applyNo = this.applyNo
			
			let saveInfo = _.cloneDeep(this.patientInfo);
			saveInfo.checkHistory = this.checkHistory;
			saveInfo.applyNo = this.applyNo;
			saveInfo.checkItemList = this.checkItemList;
			if(saveInfo.tumorDiscoveryDate) saveInfo.tumorDiscoveryDate = moment(saveInfo.tumorDiscoveryDate).format("YYYY-MM-DD HH:mm:ss");
			if(saveInfo.dcOrSamplingDate) saveInfo.dcOrSamplingDate = moment(saveInfo.dcOrSamplingDate).format("YYYY-MM-DD HH:mm:ss");
			if(saveInfo.treatmentDate) saveInfo.treatmentDate = moment(saveInfo.treatmentDate).format("YYYY-MM-DD HH:mm:ss");
			
			if(isNaN(saveInfo.operationId)) saveInfo.operationId = '';//如果是新增的手动清空id
			//发送申请单
			this.$refs.Printing.show();
			saveInfo.siteId = this.siteId;
			saveApplyInfo(saveInfo).then(res=>{
				this.$refs.Printing.show();
				sendApplyInfo({
					applyNo:this.applyNo
				}).then(res=>{
					this.$refs.Printing.hide();
					this.$refs.printPDF.print(res.data)
				})
			})
		}
		
		
//		this.$refs.Printing.show();
//		sendApplyInfo({
//			applyNo:this.applyNo
//		}).then(res=>{
//			this.$refs.Printing.hide();
//			this.$refs.printPDF.print(res.data)
//		})
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
  				
  				let summary = "";
  				let item = this.patientInfo.checkHistory
  				if(this.patientInfo.clinicalDiagnosis) summary = "临床诊断:" + (this.patientInfo.clinicalDiagnosis || '') + '\n';
  				if(item) {
  					summary += '主诉:'+(item.clinSymp || '') + '\n';
					summary += '检查项目:'+(item.checkItem || '') + '\n';
					summary += '检查结果:'+(item.relevantDiag || '') + '\n';
  				}
  				this.patientInfo.medicalHistorySummary=summary;
	  				
  				this.checkItemList = res.data.checkItemList;
  				this.checkItemDisplay = this.checkItemList.map(item=>{return item.description}).join(',')
//				let initInfo={
//		    		chiefComplaint:'双侧乳房胀痛3天',
//		    		specialPhysicalInspect:'双乳外上象限触及多个小结节，约0.3*0.3cm，形圆',
//		    		imageInspect:'左侧有边缘模糊的等密度肿块2cm*2cm*2cm',
//		    		operationSummary:'包膜清楚，切面区白色，编织样，其中似有黄色点状病变'
//		    	};
//		    	this.patientInfo = {...res.data.patientInfo,...initInfo};
  				
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
  	newGetPatientInfo(){
  		this.inputDisabled = false;
  		getPatientInfoApi({
  			patientNo:this.patientNo,
  			siteId:this.siteId,
  		}).then(res=>{
  			if(res.data) {
  				
  				if(res.data.patientInfo.dcOrSamplingDate) res.data.patientInfo.dcOrSamplingDate= moment(res.data.patientInfo.dcOrSamplingDate).format('YYYY-MM-DD')
  				if(res.data.patientInfo.treatmentDate) res.data.patientInfo.treatmentDate= moment(res.data.patientInfo.treatmentDate).format('YYYY-MM-DD')
  				if(res.data.patientInfo.tumorDiscoveryDate) res.data.patientInfo.tumorDiscoveryDate= moment(res.data.patientInfo.tumorDiscoveryDate).format('YYYY-MM-DD')
  				
  				
  				let {name,sex,age,marriageStatus,occupation,address,inspectionHospital,outpatientNo,department,sickroom,hospitalNo,bedNo,medicalHistorySummary,clinicalDiagnosis,checkHistory} = res.data.patientInfo
  				let paInfo = {name,sex,age,marriageStatus,occupation,address,inspectionHospital,outpatientNo,department,sickroom,hospitalNo,bedNo,medicalHistorySummary,clinicalDiagnosis,checkHistory}
  				
  				this.patientInfo = {...this.patientInfo,...paInfo};
  				
  				let summary = "";
  				let item = this.patientInfo.checkHistory
  				if(this.patientInfo.clinicalDiagnosis) summary = "临床诊断:" + (this.patientInfo.clinicalDiagnosis || '') + '\n';
  				if(item) {
  					summary += '主诉:'+(item.clinSymp || '') + '\n';
					summary += '检查项目:'+(item.checkItem || '') + '\n';
					summary += '检查结果:'+(item.relevantDiag || '') + '\n';
  				}
  				this.patientInfo.medicalHistorySummary=summary;
  				
  				this.chooseSendCheckItem = true;//弹出送检项目
  				if(res.data.applyList && res.data.applyList.length){
  					this.applyList = res.data.applyList;
  					this.chooseApply = true;
  				} else if(res.data.applyNo) {
  					this.applyNo = res.data.applyNo;
  				} else {
  					this.initApplyInfo()
  				}
  				
//				this.getSearchCheckHis();
  			}
  		},err=>{console.log(err)})
  		
  	},
  	getSearchCheckHis(noDataShow = false){
  		searchCheckHis({
			patientNo:this.patientNo,
//			patientNo:259742,
  			siteId:this.siteId,
  		}).then(res=>{
  			if(res.data.length) this.$refs.CheckHistory.show(res.data)
  			else if(noDataShow) this.$Message.info('暂无检查历史')
  		})
  	},
  	getMedicalHistoryResultList(item){
  		this.checkHistory = item;
		let summary = '主诉:'+(item.clinSymp || '') + '\n';
		summary += '检查项目:'+(item.checkItem || '') + '\n';
		summary += '检查结果:'+(item.relevantDiag || '') + '\n';
		this.patientInfo.medicalHistorySummary = "临床诊断:" + (this.patientInfo.clinicalDiagnosis || '')  + '\n'+ summary;
  		
  		this.$nextTick(()=>{
  			
  			this.patientInfo.clinSymp = item.clinSymp;
  			this.patientInfo.examClass = item.examClass;
  			this.patientInfo.checkItem = item.checkItem;
  			this.patientInfo.examSubClass = item.examSubClass;
  			this.patientInfo.relevantDiag = item.relevantDiag;
  			
  			if(!this.checkHistory) this.checkHistory ={
  				clinSymp:item.clinSymp,
  				examClass:item.examClass,
  				checkItem:item.checkItem,
  				examSubClass:item.examSubClass,
  				relevantDiag:item.relevantDiag,
  			}
  		})
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
  	getSampleList(sampleNo){
  		getSampleListByApplyNo({
  			applyNo:this.applyNo
  		}).then(res=>{
  			if(res.data) {
  				this.sampleList = res.data;
  				if(sampleNo) res.data.map(item=>{
  					if(item.sampleNo == sampleNo) {
  						this.selectSample = item;
  						this.$nextTick(()=>{
							this.printTag()
  						})
  					}
  				})
  			}
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
  		
  		if(!this.selectSample.patientName)this.selectSample.patientName = this.patientInfo.name;
  		if(!this.selectSample.sex)this.selectSample.sex = this.patientInfo.sex;
  		
  		this.printTagFlag = true;
  		this.$refs.printTag.print().then(res=>{
  			this.printTagFlag = false
  			this.$Message.success(res.msg);
  			this.applyNoChange('1',this.applyNo)
  		}).catch(err=>{this.printTagFlag = false;this.$Message.success(err.msg)});
  	},
  	printingShow(){
  		if(!Object.keys.call(null,this.selectSample).length) {this.$Message.info('请先选择需要打印的样本');return ;}
  		this.$refs.Printing.show();
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
  		if(t == 1){//查询的是病人id
  			getApplyDetailInfo({
  				applyNo:data
  			}).then(res=>{
  				if(res.data) {
  					if(res.data.checkItemList.length)this.checkItemList = res.data.checkItemList;
  					this.checkItemDisplay = this.checkItemList.map(item=>{return item.description}).join(',');
  					if(this.checkItemList.length) this.checkItemSelect = this.checkItemList[0].descriptionCode
  				
  					if(res.data.patientInfo.dcOrSamplingDate) res.data.patientInfo.dcOrSamplingDate= moment(res.data.patientInfo.dcOrSamplingDate).format('YYYY-MM-DD')
	  				if(res.data.patientInfo.treatmentDate) res.data.patientInfo.treatmentDate= moment(res.data.patientInfo.treatmentDate).format('YYYY-MM-DD')
	  				if(res.data.patientInfo.tumorDiscoveryDate) res.data.patientInfo.tumorDiscoveryDate= moment(res.data.patientInfo.tumorDiscoveryDate).format('YYYY-MM-DD')
  					
  					if(this.isNew) {
						let {name,sex,age,marriageStatus,occupation,address,inspectionHospital,outpatientNo,department,sickroom,hospitalNo,bedNo} = res.data.patientInfo
		  				let paInfo = {name,sex,age,marriageStatus,occupation,address,inspectionHospital,outpatientNo,department,sickroom,hospitalNo,bedNo}
		  				
		  				this.patientInfo = {...this.patientInfo,...paInfo};
  					} else {
  						this.patientInfo = res.data.patientInfo;
  					}
  					
  					let summary = "";
  					if(res.data.patientInfo.checkHistory) this.checkHistory = res.data.patientInfo.checkHistory;
	  				let item = this.checkHistory;
	  				if(this.patientInfo.clinicalDiagnosis) summary = "临床诊断:" + (this.patientInfo.clinicalDiagnosis || '') + '\n';
	  				if(item) {
	  					summary += '主诉:'+(item.clinSymp || '') + '\n';
						summary += '检查项目:'+(item.checkItem || '') + '\n';
						summary += '检查结果:'+(item.relevantDiag || '') + '\n';
	  				}
	  				this.patientInfo.medicalHistorySummary=summary;
  				
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
  	},
  	viewApplyNo(applyNo){//
  		this.step = 2;
  		this.applyNoChange('1',applyNo)
  	},
  	initByPatientNo(){
  		this.queryInfo.page = 1;
  		this.init()
  	},
  	step2Init(){
  		this.canGetPatientInfo = false;
  		this.patientNo='';
    	this.sampleList=[];
    	this.patientInfo={medicalHistorySummary:''};
    	this.step=2;
    	this.inputDisabled=false;
  	},
  	init(){
  		this.queryInfo.siteId = this.siteId;
  		this.checkItemList=[];//送检项目
    	this.checkItemSelect=null;
    	this.checkItemDisplay='';
    	this.checkHistory = {};
  		searchOperationList(this.queryInfo).then(res=>{
  			if(res.data) this.data = res.data;
  			if(res.paging) {
  				this.queryInfo.page = res.paging.pageNo;
  				this.queryInfo.pageSize = res.paging.pageSize;
  				this.queryInfo.total = res.paging.totalHits;
  			}
  		})
  	},
  	pageChange(p){
		this.queryInfo.page  = p;
		this.init()
	},
	pageSizeChange(ps){
		this.queryInfo.pageSize  = ps;
		this.init()
	},
	tableRowClick(row){
		this.curRowInfo = row
		this.$refs.rightContent.showApplyList(row.patientNo);
	},
	rowClassName (row, index) {
        if (row.patientNo === this.curRowInfo.patientNo) {
            return 'demo-table-info-row';
        }
        return '';
    }
  }
}
</script>

<style lang="less" scoped>
@import "../../index.less";
    
.content{
	padding:10px 0 0;
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
.main-lc-sssj{
	height: calc(~"100% - 50px");
}
</style>