<template>
	<div class="h100pct">
		<div class="head">
    		<Row>
    			<Col span="8"><strong>接收站点</strong></Col>
    			<Col span="16" class="ta-r">
    				<Button class="mr10x" type="primary" @click="logisticsQuery">物流查询</Button>
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
	            					样本接收列表
	            				</Col>
	            				<Col span="15" class="ta-r" style="font-size: 12px;">
	            					<!--<span>共50个样本，已固定0个样本</span>-->
<!--	            					<span class="ml10x"><Checkbox >自动打印标签</Checkbox></span>-->
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
    				<SampleDetail ref="sampleDetail" :siteId="siteId" @refresh="init" ></SampleDetail>
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
	        	共  {{queryInfo.total}}个 样本，已接收 {{exitInfo.dealNum}}个 样本，还有 {{queryInfo.total - exitInfo.dealNum}}个 样本没有接收！
	        </div>
	        <template slot="footer">
	        	<Button @click="exitVisible=false">取消</Button>
	        	<Button type="primary" @click="exitVisible=false;$router.go(-1)">确认</Button>
	        </template>
	    </Modal>
	    
	    <LogisticsInfo :siteId="siteId" ref="logisticsInfo"></LogisticsInfo>
	</div>
</template>

<script>
import SampleDetail from "./action/SampleDetail"
import SampleReturn from "./action/SampleReturn"
import SamplePackage from "./action/SamplePackage"
import LogisticsInfo from "@/pages/logistics/LogisticsInfo"

import vuescroll from 'vuescroll';

import {mapActions,mapGetters} from 'vuex'
import {searchReceiveList,exitPrompt} from '@/api/sampleApi'

export default {
  name: 'OpreateIndex',
  components: {vuescroll,SampleDetail,SampleReturn,SamplePackage,LogisticsInfo},
  data () {
    return {
    	exitVisible:false,
    	columns: [
    		{title: '手术室ID/门诊ID',key: 'pathologyNo'},
            {title: '病人ID',key: 'patientNo'},
            {title: '姓名',key: 'patientName'},
            {title: '病理号',key: 'patientNo'},
            {title: '样本id',key: 'sampleNo'},
            {title: '组织块数',key: 'num'},
            {title: '接收人',key: 'receiveUserNameCn'},
        ],
        data: [],
        siteId:'',
        exitInfo:{},
        queryInfo:{
        	"siteId":'',
			"page": 1,
			"pageSize": 10,
			"total":0,
        },
    }
  },
  mounted(){
  	this.getSiteIdByType(6).then(res=>{
  		this.siteId = res;
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
  		this.queryInfo.siteId = this.siteId
  		searchReceiveList(this.queryInfo).then(res=>{
  			if(res.data) this.data = res.data;
  			if(res.paging) {
  				this.queryInfo.page = res.paging.pageNo;
  				this.queryInfo.pageSize = res.paging.pageSize;
  				this.queryInfo.total = res.paging.totalHits;
  			}
  		})
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
	
}
</style>