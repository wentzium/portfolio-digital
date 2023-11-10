<template>
	<div class="h100pct">
		<div class="head">
    		<Row>
    			<Col span="8"><strong>运送站点</strong></Col>
    			<Col span="16" class="ta-r">
    				<Button class="mr10x" type="primary" @click="logisticsQuery">物流查询</Button>
    				<Button class="mr10x fixed-action-btn" type="primary" :class="{'fixed-cur-btn':curShow=='transport'}" @click="curShow='transport'">
    					<div style="width: 10px;height: 10px;margin-right: 5px;vertical-align: -1px;" class="assets-icon assets-icon-btn-return"></div>转运</Button>
    				<!--<Button class="mr10x fixed-action-btn" type="primary" :class="{'fixed-cur-btn':curShow=='detail'}" @click="curShow='detail'">
    					<div style="width: 10px;height: 10px;margin-right: 5px;vertical-align: -1px;" class="assets-icon assets-icon-btn-return"></div>样本详情</Button>
    				-->
    				<Button class="mr10x fixed-action-btn" type="primary" :class="{'fixed-cur-btn':curShow=='return'}" @click="curShow='return'">
    					<div style="width: 10px;height: 10px;margin-right: 5px;vertical-align: -1px;" class="assets-icon assets-icon-btn-return"></div>退样</Button>
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
	            					<!--<span>共50个样本，已固定0个样本</span>-->
	            					<!--<span class="ml10x"><Checkbox >自动打印标签</Checkbox></span>-->
	            				</Col>
	            			</Row>
	            		</div>
	            		<div class="content">
	            			<div class="h100pct" style="padding-bottom: 42px;">
	            				<Table :columns="columns" :data="data"></Table>
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
		        	
		        	<SampleReturn :siteId="siteId" @refresh="init" v-if="curShow=='return'"></SampleReturn>
        			<SampleTransport :siteId="siteId" @refresh="init" v-else-if="curShow=='transport'"></SampleTransport>
    				<SampleDetail :siteId="siteId" @refresh="init" v-else></SampleDetail>
		            <!--<div class="h80pct sys-mian-panel">
		            	<div class="title">
	            			<Row type="flex" justify="center" align="middle">
	            				<Col span="24">
	            					<div style="width: 18px;height: 18px;" class="assets-icon assets-icon-sample_manage"></div>
	            					样本详细信息
	            				</Col>
	            			</Row>
	            		</div>
	            		<div class="content">
	            		</div>
		            </div>
		            <div class="h20pct pt16x">
		            	<div class="h100pct sys-mian-panel">
							<div class="pt16x pb10x ta-l"><Checkbox >自动保存</Checkbox></div>
							<Button class="mr10x w100pct" type="primary" >保存</Button>
		            	</div>
		            </div>-->
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
	        	共  {{queryInfo.total}}个 样本，已转运 {{exitInfo.dealNum}}个 样本，还有 {{queryInfo.total - exitInfo.dealNum}}个 样本没有转运！
	        </div>
	        <template slot="footer">
	        	<Button @click="exitVisible=false">取消</Button>
	        	<Button type="primary" @click="exitVisible=false;$router.go(-1)">确认</Button>
	        </template>
	    </Modal>
	    
		<LogisticsInfo ref="logisticsInfo"></LogisticsInfo>
	</div>
</template>

<script>
import SampleDetail from "./action/SampleDetail"
import SampleReturn from "./action/SampleReturn"
import SampleTransport from "./action/SampleTransport"
import SamplePackage from "./action/SamplePackage"
import LogisticsInfo from "./action/LogisticsInfo"
//import Printing from "./action/Printing"
//import SampleDelete from "./action/SampleDelete"
import {mapActions} from 'vuex'
import {searchDeliveryList,exitPrompt} from '@/api/sampleApi'

export default {
  name: 'TransportIndex',
  components: {SampleDetail,SampleReturn,SamplePackage,SampleTransport,LogisticsInfo},
  data () {
    return {
    	value3: 0,
        setting: {
            autoplay: false,
            autoplaySpeed: 2000,
            dots: 'none',
            radiusDot: true,
            trigger: 'click',
            arrow: 'hover'
        },
    	addSampleModel:false,
    	curShow:'transport',//index:默认信息
    	exitVisible:false,
    	siteId:'',
    	exitInfo:{},
    	columns: [
            {title: '手术室ID/门诊ID',key: 'operateUserId'},
            {title: '病人ID',key: 'patientNo'},
            {title: '姓名',key: 'patientName'},
            {title: '病理号',key: 'patientNo'},
            {title: '样本id',key: 'sampleNo'},
            {title: '组织块数',key: 'num'},
            {title: '离体时间',key: 'separationTime'},
            {title: '固定时间',key: 'fixedTime'},
            {title: '护士',key: 'operateUserNameCn'},
            {title: '取件护士',key: 'fixUserNameCn'},
        ],
        data: [],
        queryInfo:{
		  "deliverySiteId": 0,
		  "page": 1,
		  "pageSize": 10,
		  "total":0,
		}
    }
  },
  mounted(){
  	this.getSiteIdByType(5).then(res=>{
  		this.siteId = res
  		this.$nextTick(()=>{
  			this.init()
  		})
  	});
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
  		this.queryInfo.deliverySiteId = this.siteId;
  		searchDeliveryList(this.queryInfo).then(res=>{
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