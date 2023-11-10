<template>
	<div class="h100pct sys-mian-panel">
    	<div class="title">
			<Row type="flex" justify="center" align="middle">
				<Col span="24">
					<div style="width: 18px;height: 18px;" class="assets-icon assets-icon-sample_manage"></div>
					申请单详细信息
				</Col>
			</Row>
		</div>
		<div class="content">
			<div class="sample-return h100pct" style="padding-bottom: 42px;">
				<vuescroll>
					<Table class="h100pct" :columns="columns" :data="data">
						<template slot-scope="{ row }" slot="hasPrinted">
				            <strong>{{ row.hasPrinted?'是':'否' }}</strong>
				        </template>
						<template slot-scope="{ row }" slot="action">
				            <Button type="primary" size="small" style="margin-right: 5px" @click="show(row)">查看</Button>
				        </template>
					</Table>
				</vuescroll>
			</div>
		</div>
	</div>
	
</template>

<script>
import {applyDetailList} from '@/api/sampleApi'
import vuescroll from 'vuescroll';
export default {
  name: 'RightContent',
  components: {vuescroll},
  props:{
  	siteId:[String,Number]
  },
  data () {
    return {
    	columns: [
            {title: '申请单ID',key: 'applyNo'},
//          {title: '部位',key: 'patientName'},
            {title: '创建时间',key: 'createTime'},
            {title: '是否打印',key: 'hasPrinted',slot:"hasPrinted"},
            {title: ' ',key: 'action',slot:"action"},
        ],
        data: [],
    }
  },
  methods: {
  	showApplyList(patientNo){
  		applyDetailList({
  			patientNo:patientNo,
  			siteId:this.siteId,
  		}).then(res=>{
  			if(res.data) this.data = res.data;
  		})
  	},
  	show(row){
		console.log(row)
		this.$emit('viewApplyNo',row.applyNo)
	},
  }
}
</script>

<style lang="less" scoped="scoped">
.sample-return{
	padding-top: 20px;
	.item{
		border-radius: 4px;
		line-height: 32px;
		margin-bottom: 10px;
		padding-left: 10px;
		/*font-size: 12px;
		color: #333333;
		line-height: 19px;*/
	}
}
</style>