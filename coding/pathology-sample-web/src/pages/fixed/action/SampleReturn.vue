<template>
	<div class="sample-return">
		
		<Row class="item"><Col span="4">样本编号:</Col><Col span="20">32242322</Col></Row>
		<Row class="item"><Col span="4">离体时间:</Col><Col span="20">32242322</Col></Row>
		<Row class="item"><Col span="4">采取部位:</Col><Col span="20">32242322</Col></Row>
		<Row class="item"><Col span="4">组织块数:</Col><Col span="20">32242322</Col></Row>
		<Row class="item"><Col span="4">固定状态:</Col><Col span="20">32242322</Col></Row>
		<Row class="item"><Col span="4">物流状态:</Col><Col span="20">32242322</Col></Row>
		<Row class="item"><Col span="4">标签状态:</Col><Col span="20">32242322</Col></Row>
		
		
		
		<div class="foot">
				<Button class="mr10x" type="primary" @click="visible=true">确定退样</Button>
		</div>
		
		<Modal
	        v-model="visible"
	        @on-ok="visible=false"
	        @on-cancel="visible=false">
	        <template slot="header">
	        	<div style="width: 18px;height: 18px;" class="assets-icon assets-icon-modal-default"></div>
	        	是否退养
	        </template>
	        <div style="font-size: 16px;color: #505050;text-align: center;padding: 40px 0;">
	        	是否放弃已选退样样本？
	        </div>
	        <template slot="footer">
	        	<Button>取消</Button>
	        	<Button type="primary">确认</Button>
	        </template>
	    </Modal>
    
	</div>
</template>

<script>

export default {
  name: 'FixedSampleReturn',
  components: {},
  data () {
    return {
    	visible:false,
    }
  },
  computed: {
  },
  methods: {
  	inputSampleWeightShow(){
  		this.$refs.InputSampleWeight.show()
  	},
  	showSuggest(){
  		this.$refs.suggest.show()
  	},
  	sampleDeleteShow(){
  		this.$refs.SampleDelete.show()
  	}
  }
}
</script>

<style lang="less" scoped="scoped">
.sample-return{
	.item{
		background: #F2F2F2;
		border-radius: 4px;
		line-height: 32px;
		margin-bottom: 10px;
		/*font-size: 12px;
		color: #333333;
		line-height: 19px;*/
	}
}
</style>