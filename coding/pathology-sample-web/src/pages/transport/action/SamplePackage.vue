<template>
	<div class="sample-return">
		
		<p>已选择 0 个打包样本</p>
		<p>转运箱编号</p>
		<Input >
			<template slot="suffix">
				<div class="dpIB" style="width: 32px;height: 32px;padding: 15%;">
					<div class="w100pct h100pct assets-icon assets-icon-input-scan"></div>
				</div>
			</template>
		</Input>
		
		<div class="foot">
				<Button class="mr10x" type="primary" @click="visible=true">确定打包</Button>
		</div>
		
		<Modal
	        v-model="visible"
	        @on-ok="visible=false"
	        @on-cancel="visible=false">
	        <template slot="header">
	        	<div style="width: 18px;height: 18px;" class="assets-icon assets-icon-modal-default"></div>
	        	是否打包
	        </template>
	        <div style="font-size: 16px;color: #505050;text-align: center;padding: 40px 0;">
	        	打包完成，正在打印标签……
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
  name: 'FixedSamplePackage',
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