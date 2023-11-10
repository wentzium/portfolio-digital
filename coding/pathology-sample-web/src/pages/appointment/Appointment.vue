<template>
	<Row class="h100pct" type="flex" justify="center" align="middle">
		<section class="appointment-frozen">
			<h1>预约冰冻</h1>
			<div class="sys-mian-panel">
				<div class="title">
    				<div span="24">
    					<img class="fl" width="30" height="30" src="@/assets/modal/appointment.png" />
    					<span class="ml10x" style="font-size: 20px;color: #333333;">预约详情</span>
    				</div>
        		</div>
        		<div class="content">
        			<Form :model="applyInfo" ref="appointmentApplyForm" :rules="rules" @keydown.enter.native="handleSubmit">
        				<Row :gutter="10">
	        				<Col span="12">
						        <FormItem prop="operationRoomName" class="appointment-frozen-form-item" >
						        	<template slot="label">手术室名称 <span class="form-require">*</span></template>
						            <Input v-model="applyInfo.operationRoomName" type="text"  placeholder="请输入"></Input>
						        </FormItem>
	        				</Col>
	        				<Col span="12">
	        					<FormItem prop="operationRoomNo" label="手术室号码" class="appointment-frozen-form-item"  >
						            <Input v-model="applyInfo.operationRoomNo"  type="text"  placeholder="请输入"></Input>
						        </FormItem>
	        				</Col>
	        			</Row>
	        			<Row :gutter="10">
	        				<Col span="12">
						        <FormItem prop="operationRoomTel" label="手术室电话" class="appointment-frozen-form-item">
						            <Input v-model="applyInfo.operationRoomTel"  type="text"  placeholder="请输入"></Input>
						        </FormItem>
	        				</Col>
	        				<Col span="12">
	        					<FormItem prop="operationType" label="手术类型" class="appointment-frozen-form-item">
						            <Select v-model="applyInfo.operationType" @on-change="operationTypeChange" >
						                <Option  v-for="c in operationTypeList" :value="c.id" :key="c.id">{{c.desc}}</Option>
						            </Select>
						        </FormItem>
	        				</Col>
	        			</Row>
	        			<Row :gutter="10">
	        				<Col span="12">
						        <FormItem prop="operationDoctor" label="手术医生" class="appointment-frozen-form-item">
						            <Input v-model="applyInfo.operationDoctor" type="text"  placeholder="请输入"></Input>
						        </FormItem>
	        				</Col>
	        				<Col span="12">
	        					<FormItem prop="patientName" label="病人姓名" class="appointment-frozen-form-item" >
						            <Input v-model="applyInfo.patientName" type="text"  placeholder="请输入"></Input>
						        </FormItem>
	        				</Col>
	        			</Row>
	        			<Row :gutter="10">
	        				<Col span="12">
						        <FormItem prop="operationTime" class="appointment-frozen-form-item" >
						        	<template slot="label">手术日期 <span class="form-require">*</span></template>
						            <DatePicker v-model="applyInfo.operationTime" class="ivu-leica-date" type="date" placeholder="请选择" style="width: 100%"></DatePicker>
						        </FormItem>
	        				</Col>
	        				<Col span="12">
	        					<FormItem prop="expectDeliverySampleTime" class="appointment-frozen-form-item">
	        						<template slot="label">预计送样时间 <span class="form-require">*</span></template>
						            <DatePicker v-model="applyInfo.expectDeliverySampleTime" class="ivu-leica-datetime" type="datetime" placeholder="请选择" style="width: 100%"></DatePicker>
						        </FormItem>
	        				</Col>
	        			</Row>
	        			<div style="padding: 20px 0;">
				            <Button @click="handleSubmit" style="width: 100%;" type="primary">发送申请单</Button>
				        </div>
				    </Form>
        		</div>
			</div>
		</section>
	</Row>
</template>

<script>
import {appointmentApply} from '@/api/sampleApi'
import {mapActions} from 'vuex'

export default {
  name: 'Appointment',
  components: {},
  data () {
    return {
    	applyInfo:{
		  "applyNo": "",
		  "expectDeliverySampleTime": "",
		  "operationDoctor": "",
		  "operationRoomName": "",
		  "operationRoomNo": "",
		  "operationRoomTel": "",
		  "operationTime": "",
		  "operationType": 0,
		  "operationTypeDesc":"",
		  "patientName": "",
		  "siteId": 0
    	},
    	operationTypeList:[]
	}
  },
  mounted(){
  	this.getSiteIdByType(2).then(res=>{this.applyInfo.siteId = res});
  	//手术类型
  	this.getDictList('600').then(res=>{this.operationTypeList = res;})
  },
  computed: {
    rules () {
      return {
        operationRoomName: [
          { required: true, message: '手术室名称不能为空', trigger: 'blur' }
        ],
        operationTime: [
          { required: true,type: 'date', message: '手术时间不能为空', trigger: 'blur' }
        ],
        expectDeliverySampleTime: [
          { required: true,type: 'date', message: '预计送样时间不能为空', trigger: 'blur' }
        ],
      }
    }
  },
  methods: {
  	...mapActions({
		getSiteIdByType: 'account/getSiteIdByType',
		getDictList: 'getDictList'
	}),
	operationTypeChange(e){
		console.log(e)
  		this.operationTypeList.map(item=>{
  			if(item.id == e) this.applyInfo.operationTypeDesc = item.desc;
  		})
  	},
  	handleSubmit () {
      this.$refs.appointmentApplyForm.validate((valid) => {
        if (valid) {
        	let req = _.cloneDeep(this.applyInfo);
        	req.operationTime = moment(req.operationTime).format("YYYY-MM-DD HH:mm:ss");
        	req.expectDeliverySampleTime = moment(req.expectDeliverySampleTime).format("YYYY-MM-DD HH:mm:ss");
          appointmentApply(req).then(res=>{
          	this.$Message.success(res.msg);
          	this.$router.push('/')
          },err=>{
          	console.log(err)
          })
        }
      })
    }
  }
}
</script>

<style type="less" scoped>
.appointment-frozen{
	width: 490px;
}
</style>