<template>
	<Modal
        v-model="visible"
        @on-ok="visible=false"
        @on-cancel="visible=false" 
        :styles="{background: '#FFFFFF'}"
        :width="500">
        <template slot="header">
        	<template v-if="curTab == 1">
        		<div style="width: 18px;height: 18px;" class="assets-icon assets-icon-modal-default"></div>
        		物流查询
        	</template>
        	<template v-if="curTab == 2">
        		<div style="width: 18px;height: 18px;" class="assets-icon assets-icon-modal-logistics"></div>
        		物流信息
        	</template>
        </template>
        <div>
        	<Row>
        		<Col span="24" style="padding-bottom: 50px;">
        			<template v-if="curTab == 1">
        				<Row>
        					<Col span="24">
        						<Input 
        							placeholder="请输入病人ID、样本ID查询"
        							v-model="queryId" size="small" ></Input>
        					</Col>
        				</Row>
        				
        			</template>
        			<template v-if="curTab == 2">
        				<template v-if="resultData.applyList">
        					<Table :columns="columns" :data="resultData.applyList"></Table>
        				</template>
        				<template v-else-if="resultData.applyDetail">
        					<Row>
	        					<Col span="8">姓名：{{resultData.applyDetail.patientInfo.name}}</Col>
	        					<Col span="8">性别：{{resultData.applyDetail.patientInfo.sex}}</Col>
	        					<Col span="8">年龄：{{resultData.applyDetail.patientInfo.age}}</Col>
	        					<Col span="8">职业：{{resultData.applyDetail.patientInfo.occupation}}</Col>
	        					<Col span="8">婚姻状况：{{resultData.applyDetail.patientInfo.marriageStatus}}</Col>
	        					<Col span="8">籍贯：{{resultData.applyDetail.patientInfo.birthPlace}}</Col>
	        					<Col span="24">地址：{{resultData.applyDetail.patientInfo.address}}</Col>
	        					<Col span="24">送检医院：{{resultData.applyDetail.patientInfo.inspectionHospital}}</Col>
	        					<Col span="8">科别：{{resultData.applyDetail.patientInfo.department}}</Col>
	        					<Col span="8">门诊号：{{resultData.applyDetail.patientInfo.outpatientNo}}</Col>
	        					<Col span="8">住院号：{{resultData.applyDetail.patientInfo.hospitalNo}}</Col>
	        					<Col span="8">病房：{{resultData.applyDetail.patientInfo.sickroom}}</Col>
	        					<Col span="8">床号：{{resultData.applyDetail.patientInfo.bedNo}}</Col>
	        				</Row>
        				</template>
        				
        				<template v-else-if="resultData.sample">
	        				<div style="border-bottom: 1px solid #F2F2F2;padding: 10px;">
	        					<!--<div style="width: 18px;height: 18px;" class="assets-icon assets-icon-sample-usable"></div>
	        					转运箱完成-->
	        					{{resultData.sample.sampleNo}}
	        				</div>
	        				<div style="padding: 10px;">
		        				<Row><Col span="4">是否可用:</Col><Col span="20">{{resultData.sample.availableStatusDesc}}</Col></Row>
		        				<Row><Col span="4">采集部位:</Col><Col span="20">{{resultData.sample.collectionLocationDesc}}</Col></Row>
		        				<Row><Col span="4">固定状态:</Col><Col span="20">{{resultData.sample.fixedStatusDesc}}</Col></Row>
		        				<Row><Col span="4">固定时间:</Col><Col span="20">{{resultData.sample.fixedTime}}</Col></Row>
		        				<Row><Col span="4">物流状态:</Col><Col span="20">{{resultData.sample.logisticsStatusDesc}}</Col></Row>
		        				<Row><Col span="4">打印状态:</Col><Col span="20">{{resultData.sample.photoStatusDesc}}</Col></Row>
		        				<Row><Col span="4">离体状态:</Col><Col span="20">{{resultData.sample.separationStatusDesc}}</Col></Row>
		        				<Row><Col span="4">样本类型:</Col><Col span="20">{{resultData.sample.typeDesc}}</Col></Row>
		        				<Row><Col span="4">物流信息:</Col>
		        					<Col span="20" style="padding: 10px 0;">
		        						<Timeline>
									        <TimelineItem style="padding: 0;" v-for="log in resultData.sample.logisticsList" :key="log.time">
									            <p style="font-size: 12px;color: #333333;">{{log.desc}}</p>
									        </TimelineItem>
									    </Timeline>
		        					</Col>
		        				</Row>
	        				</div>
        				</template>
        				
        			</template>
        		</Col>
        	</Row>
        </div>
        <template slot="footer">
        	<template v-if="curTab == '1'">
        		<Button type="primary" @click="visible=false">取消</Button>
        		<Button type="primary" @click="query">查询</Button>
        	</template>
        	<template v-else>
        		<Button type="primary" @click="curTab=1">重新查询</Button>
        		<Button type="primary" @click="visible=false">确认</Button>
        	</template>
        </template>
    </Modal>
</template>

<script>
import {applyLogisticsQuery} from '@/api/sampleApi'

export default {
  name: 'LogisticsInfo',
  components: {},
  data () {
    return {
    	visible:false,
    	curTab:1,
    	queryId:'',
    	resultData:null,
    	columns: [
            {
                title: '病人ID',
                key: 'patientNo'
            },
            {
                title: '申请单号',
                key: 'applyNo'
            },
            {
                title: '状态',
                key: 'statusDesc'
            }
        ],
    }
  },
  computed: {
  },
  methods: {
  	show(sampleNo){
  		this.visible = true;
  		this.curTab = 1;
  		this.queryId = ''
  	},
  	query(){
  		if(this.queryId){
  			applyLogisticsQuery({
  				id:this.queryId
  			}).then(res=>{
  				if(res.data){
  					this.resultData = res.data;
  					if(res.data.applyList || res.data.applyDetail || res.data.sample){
  						this.curTab = 2;
  					} else {
  						this.$Message.info('未查询到相关物流信息');
  					}
  				} else {
  					this.$Message.info('未查询到相关物流信息');
  				}
  				console.log(res)
  			})
  		} else {
  			this.$Message.info('请输入查询ID');
  		}
  	},
  	tabChange(name){
  		this.curTab = name;
  	},
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