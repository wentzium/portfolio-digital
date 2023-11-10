<template>
	<div class="h100pct sys-mian-panel">
    	<div class="title">
			<Row type="flex" justify="center" align="middle">
				<Col span="24">
					<div style="width: 18px;height: 18px;" class="assets-icon assets-icon-sample_manage"></div>
					样本转运
				</Col>
			</Row>
		</div>
		<div class="content">
			<div class="sample-return h100pct" style="padding-bottom: 42px;">
				
				<Row class="item">
					<Col span="4">转运箱:</Col>
					<Col span="20">
						<Input @on-change="transport" v-model="transportBoxNo" type="text"  placeholder="请输入">
							<template slot="suffix">
								<div class="dpIB" style="width: 32px;height: 32px;padding: 15%;">
									<div class="w100pct h100pct assets-icon assets-icon-input-scan"></div>
								</div>
							</template>
						</Input>
					</Col>
				</Row>
				<Row class="item">
					<Col span="4">样本:</Col>
					<Col span="20">
						<Input @on-change="transport" v-model="sampleNo" type="text"  placeholder="请输入">
							<template slot="suffix">
								<div class="dpIB" style="width: 32px;height: 32px;padding: 15%;">
									<div class="w100pct h100pct assets-icon assets-icon-input-scan"></div>
								</div>
							</template>
						</Input>
					</Col>
				</Row>
				
			</div>
		</div>
	</div>
	
</template>

<script>
import {transferSample} from '@/api/sampleApi'

export default {
  name: 'TransportSampleTransport',
  components: {},
  props:{
  	siteId:[String,Number]
  },
  data () {
    return {
    	transportBoxNo:'',
    	sampleNo:''
    }
  },
  methods: {
  	transport(){
  		if(this.transportBoxNo && this.sampleNo){
  			transferSample({
			  "sampleNo": this.sampleNo,
			  "siteId": this.siteId,
			  "transferNo": this.transportBoxNo
  			}).then(res=>{
  				this.$Message.info(res.msg);
  				this.$emit('transerSuccess')
  			})
  		}
  	}
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