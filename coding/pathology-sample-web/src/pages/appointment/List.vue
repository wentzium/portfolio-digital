<template>
	<div class="h100pct pb10x posR" @click="getAppointmentApplyList">
		<Table :columns="columns" class="pt10x appointment-list-tablehead"></Table>
		<div class="w100pct h100pct" style="position: absolute;top: 0;left: 0;padding-top: 50px;padding-bottom: 20px;">
			<vuescroll ref="vuescroll" :opt="{bar:{size: '0px',background:'#ffffff'},scrollPanel:{scrollingX: false,}}">
				<Table v-if="data" :columns="columns" :data="data" :show-header="false"></Table>
			</vuescroll>
		</div>
	</div>
</template>

<script>
import {appointmentApplyList} from '@/api/sampleApi'
import vuescroll from 'vuescroll';

export default {
  name: 'Appointment',
  components: {vuescroll},
  data () {
    return {
    	query:{
    		page:1,
    		pageSize:20
    	},
    	columns: [
            {title: '手术室名称',key: 'operationRoomName'},
            {title: '手术类型',key: 'operationTypeDesc'},
            {title: '手术医生',key: 'operationDoctor'},
            {title: '病人姓名',key: 'patientName'},
            {title: '手术日期',key: 'operationTime'},
            {title: '预计送样时间',key: 'expectDeliverySampleTime'},
        ],
        data: [],
        scrollInterval:null,
        timeout:null,
	}
  },
  mounted(){
  	this.getAppointmentApplyList()
  },
  methods: {
  	getAppointmentApplyList(){
  		appointmentApplyList(this.query).then(res=>{
  			if(res.data){
  				this.data = res.data;
  				this.initScroll()
  			}
  		},err=>{console.log(err)})
  	},
  	initScroll(){
  		this.$nextTick(()=>{
  			if(this.$refs['vuescroll']){
		  		this.$refs['vuescroll'].scrollTo(
				  {
				    y: '100%'
				  },
				  10000,
				  'easeInQuad'
				);
  			}
  		})
  		if(this.timeout) clearTimeout(this.timeout)
  		this.timeout = setTimeout(()=>{
  			this.data = []
  			this.getAppointmentApplyList()
  		},10000)
  	}
  },
	beforeRouteLeave(to, from, next){
		if(this.timeout) clearTimeout(this.timeout);
		this.timeout = null;
		next()
	}
}
</script>

<style>
	.appointment-list-tablehead .ivu-table-tip{
		display: none;
	}
	.__vuescroll .__bar-is-vertical{display: none;}
</style>
<style type="less" scoped>
.appointment-frozen{
	width: 490px;
}
.ivu-table-tip{display: none;}
</style>