<template>
	<div class="h100pct">
		<div class="head">
    		<Row>
    			<Col span="8"><strong>固定站点</strong></Col>
    			<Col span="16" class="ta-r">
    				<Button class="mr10x" type="primary" @click="logisticsQuery">物流查询</Button>
    				<!--<Button class="mr10x fixed-action-btn" type="primary" @click="changeTheatre=true">
    					<div style="width: 10px;height: 10px;margin-right: 5px;vertical-align: -1px;" class="assets-icon assets-icon-btn-theater"></div>
    					选择手术室
    				</Button>-->
    				<!--<Button class="mr10x fixed-action-btn" type="primary" :class="{'fixed-cur-btn':curShow=='package'}" @click="curShow='package'">
    					<div style="width: 10px;height: 10px;margin-right: 5px;vertical-align: -1px;" class="assets-icon assets-icon-btn-package"></div>打包</Button>-->
    				<!--<Button class="mr10x fixed-action-btn" type="primary" :class="{'fixed-cur-btn':curShow=='return'}" @click="curShow='return'">
    					<div style="width: 10px;height: 10px;margin-right: 5px;vertical-align: -1px;" class="assets-icon assets-icon-btn-return"></div>退样</Button>-->
    				<Button class="mr10x" type="primary" style="background:#ED1B2F;" @click="exit">
    					<div style="width: 10px;height: 10px;margin-right: 5px;vertical-align: -1px;" class="assets-icon assets-icon-btn-quit"></div>退出</Button>
    			</Col>
    		</Row>
    	</div>
    	<div class="com-content-body">
			<Row :gutter="16" class="h100pct">
		        <Col span="15" class="h100pct">
		        	<div class="h100pct sys-mian-panel">
		            	<div class="title">
	            			<Row type="flex" justify="center" align="middle">
	            				<Col span="9">
	            					<div style="width: 18px;height: 18px;" class="assets-icon assets-icon-list"></div>
	            					样本列表
	            				</Col>
	            				<Col span="15" class="ta-r" style="font-size: 12px;">
	            					<span>共{{needFixedNum}}个样本，已固定{{needFixedNum-queryInfo.total}}个样本</span>
	            					<!--<span class="ml10x"><Checkbox >自动打印标签</Checkbox></span>-->
	            				</Col>
	            			</Row>
	            		</div>
	            		<div class="content">
	            			<div class="h100pct" style="padding-bottom: 42px;">
	            				<vuescroll>
	            					<Table class="h100pct" :columns="columns" :data="data"></Table>
	            				</vuescroll>
	            			</div>
		            		<div class="foot">
			        			<Page @on-change="pageChange" @on-page-size-change="pageSizeChange" :total="queryInfo.total" :curent="queryInfo.page" :page-size="queryInfo.pageSize" show-elevator show-sizer />
		            		</div>
	            		</div>
		            </div>
		        </Col>
		        <Col span="9" class="h100pct">
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
	            			<div class="h100pct" style="padding-bottom: 42px;">
	            				
	            				<SampleReturn v-if="curShow=='return'"></SampleReturn>
	            				<SamplePackage v-else-if="curShow=='package'"></SamplePackage>
	            				<SampleDetail ref="sampleDetail" :siteId="siteId" @refresh="init" v-else></SampleDetail>
	            				
	            			</div>
	            		</div>
		            </div>
		        </Col>
		    </Row>
    	</div>
		
		<Modal
	        v-model="exitVisible"
	        @on-ok="exitVisible=false"
	        @on-cancel="exitVisible=false">
	        <template slot="header">
	        	<div style="width: 18px;height: 18px;" class="assets-icon assets-icon-modal-default"></div>
	        	是否退出
	        </template>
	        <div style="font-size: 16px;color: #505050;text-align: center;padding: 40px 0;">
	        	共 {{needFixedNum}}个 样本，已固定 {{needFixedNum-queryInfo.total}}个 样本，还有 {{queryInfo.total}}个 样本没有固定！
	        </div>
	        <template slot="footer">
	        	<Button @click="exitVisible=false">取消</Button>
	        	<Button type="primary" @click="exitVisible=false;needFixedNum=null;$router.go(-1)">确认</Button>
	        </template>
	    </Modal>
	    
	    <Modal
	        v-model="changeTheatre"
	        @on-ok="changeTheatre=false"
	        @on-cancel="changeTheatre=false"
	        :width="300"
	        >
	        <template slot="header">
	        	<div style="width: 18px;height: 18px;" class="assets-icon assets-icon-modal-default"></div>
	        	选择手术室
	        </template>
	        <div style="font-size: 16px;color: #505050;text-align: center;padding: 10px 0;">
	        	<Row>
	        		<Col span="12" style="margin-bottom: 10px;">
	        			<Button class="mr10x fixed-action-btn" :class="{'fixed-cur-btn':selectOpreate=='1'}" type="primary" @click="selectOpreate='1'" >
	    					<div style="width: 10px;height: 10px;margin-right: 5px;vertical-align: -1px;" class="assets-icon assets-icon-btn-theater"></div>
	    					手术室一
	    				</Button>
	        		</Col>
	        		<Col span="12" style="margin-bottom: 10px;">
	        			<Button class="mr10x fixed-action-btn" :class="{'fixed-cur-btn':selectOpreate=='2'}" type="primary" @click="selectOpreate='2'"  >
	    					<div style="width: 10px;height: 10px;margin-right: 5px;vertical-align: -1px;" class="assets-icon assets-icon-btn-theater"></div>
	    					手术室二
	    				</Button>
	        		</Col>
	        		<Col span="12" style="margin-bottom: 10px;">
	        			<Button class="mr10x fixed-action-btn" :class="{'fixed-cur-btn':selectOpreate=='3'}" type="primary" @click="selectOpreate='3'"  >
	    					<div style="width: 10px;height: 10px;margin-right: 5px;vertical-align: -1px;" class="assets-icon assets-icon-btn-theater"></div>
	    					手术室二
	    				</Button>
	        		</Col>
	        		<Col span="12" style="margin-bottom: 10px;">
	        			<Button class="mr10x fixed-action-btn" :class="{'fixed-cur-btn':selectOpreate=='4'}" type="primary" @click="selectOpreate='4'"  >
	    					<div style="width: 10px;height: 10px;margin-right: 5px;vertical-align: -1px;" class="assets-icon assets-icon-btn-theater"></div>
	    					手术室二
	    				</Button>
	        		</Col>
	        	</Row>
	        </div>
	        <template slot="footer">
	        	<Button @click="exitVisible=false">取消</Button>
	        	<Button type="primary" @click="changeTheatre=false">确认</Button>
	        </template>
	    </Modal>
		
		<LogisticsInfo :siteId="siteId" ref="logisticsInfo"></LogisticsInfo>
	</div>
</template>

<script>
import {mapActions,mapGetters} from 'vuex'
import {searchFixSampleList,exitPrompt} from '@/api/sampleApi'

import vuescroll from 'vuescroll';
import SampleDetail from "./action/SampleDetail"
import SampleReturn from "./action/SampleReturn"
import SamplePackage from "./action/SamplePackage"
import LogisticsInfo from "@/pages/logistics/LogisticsInfo"
//import Printing from "./action/Printing"
//import SampleDelete from "./action/SampleDelete"

export default {
  name: 'OpreateIndex',
  components: {vuescroll,SampleDetail,SampleReturn,SamplePackage,LogisticsInfo},
  data () {
    return {
    	addSampleModel:false,
    	curShow:'index',//index:默认信息
    	exitVisible:false,
    	changeTheatre:false,//选择手术室
    	selectOpreate:'',
    	columns: [
            {title: '手术室ID',key: 'operateUserId'},
            {title: '病人ID',key: 'patientNo'},
            {title: '姓名',key: 'patientName'},
            {title: '样本id',key: 'sampleNo'},
            {title: '组织块数',key: 'num'},
            {title: '离体时间',key: 'separationTime'},
            {title: '固定时间',key: 'fixedTime'},
            {title: '护士',key: 'operateUserNameCn'},
            {title: '状态',key: 'fixedStatusDesc'},
        ],
        siteId:'',
        exitInfo:{},
        needFixedNum:null,//待固定总数。
        queryInfo:{
        	"applyNo": "",
        	"fixSiteId":'',
        	"mapSiteIdList":[],
			"page": 1,
			"pageSize": 10,
			"total":0,
        },
        data: []
    }
  },
  mounted(){
  	this.getSiteIdByType(4).then(res=>{
  		this.siteId = res
  		this.$nextTick(()=>{
  			this.init()
  		})
  	});
  },
  watch:{
  	sacnCount(v){
  		this.$refs.sampleDetail.scanInput(this.scanInput)
  	}
  },
  computed: {
    ...mapGetters([
      'scanInput',
      "sacnCount"
    ])
  },
  beforeRouteLeave(to, from, next){
  	this.$refs.sampleDetail.init()
	this.data=[];
	this.queryInfo = {
    	"applyNo": "",
    	"fixSiteId":'',
    	"mapSiteIdList":[],
		"page": 1,
		"pageSize": 10,
		"total":0,
    };
    this.needFixedNum = null;
  	next()
  },
  methods: {
  	...mapActions({
		getSiteIdByType: 'account/getSiteIdByType',
	}),
	pageChange(p){
		this.queryInfo.page  = p;
		this.init()
	},
	pageSizeChange(ps){
		this.queryInfo.pageSize  = ps;
		this.init()
	},
  	init(){
  		this.queryInfo.fixSiteId = this.siteId
  		searchFixSampleList(this.queryInfo).then(res=>{
  			if(res.data) this.data = res.data;
  			if(res.paging) {
  				if(!this.needFixedNum) this.needFixedNum = res.paging.totalHits;
  				this.queryInfo.page = res.paging.pageNo;
  				this.queryInfo.pageSize = res.paging.pageSize;
  				this.queryInfo.total = res.paging.totalHits;
  			}
  		})
  	},
  	inputSampleWeightShow(){
  		this.$refs.InputSampleWeight.show()
  	},
  	logisticsQuery(){
  		this.$refs.logisticsInfo.show();
  	},
  	exit(){
  		exitPrompt({
  			siteId:this.siteId
  		}).then(res=>{
  			if(res.data) {this.exitInfo = res.data;this.exitVisible = true;}
  			
  		})
  	}
  }
}
</script>

<style lang="less" scoped>
@import "../../index.less";

.fixed-action-btn{border: none;}
.fixed-cur-btn{background: #32A3DB;}
.fixed-cur-btn:hover{background: #32A3DB;}
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