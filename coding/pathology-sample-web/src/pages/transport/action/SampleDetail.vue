<template>
	<div class="h100pct">
		<div class="h80pct sys-mian-panel">
        	<div class="title">
    			<Row type="flex" justify="center" align="middle">
    				<Col span="24">
    					<div style="width: 18px;height: 18px;" class="assets-icon assets-icon-sample_manage"></div>
    					样本详细信息
    				</Col>
    			</Row>
    		</div>
    		<div class="content">
    			<Carousel
			        v-model="value3"
			        :autoplay="setting.autoplay"
			        :autoplay-speed="setting.autoplaySpeed"
			        :dots="setting.dots"
			        :radius-dot="setting.radiusDot"
			        :trigger="setting.trigger"
			        :arrow="setting.arrow">
			        <CarouselItem>
			            <div class="demo-carousel ta-c"><img style="width: 100%;height: 130px;margin-right: 10px;" src="@/assets/eg.png" /></div>
			        </CarouselItem>
			        <CarouselItem>
			            <div class="demo-carousel ta-c"><img style="width: 100%;height: 130px;margin-right: 10px;" src="@/assets/eg.png" /></div>
			        </CarouselItem>
			        <CarouselItem>
			            <div class="demo-carousel ta-c"><img style="width: 100%;height: 130px;margin-right: 10px;" src="@/assets/eg.png" /></div>
			        </CarouselItem>
			        <CarouselItem>
			            <div class="demo-carousel ta-c"><img style="width: 100%;height: 130px;margin-right: 10px;" src="@/assets/eg.png" /></div>
			        </CarouselItem>
			    </Carousel>
				
				<div class="infos">
					<Row class="item"><Col span="4">样本编号:</Col><Col span="20">32242322</Col></Row>
					<Row class="item"><Col span="4">离体时间:</Col><Col span="20">32242322</Col></Row>
					<Row class="item"><Col span="4">采取部位:</Col><Col span="20">32242322</Col></Row>
					<Row class="item"><Col span="4">组织块数:</Col><Col span="20">32242322</Col></Row>
					<Row class="item"><Col span="4">固定状态:</Col><Col span="20">32242322</Col></Row>
					<Row class="item"><Col span="4">物流状态:</Col><Col span="20">32242322</Col></Row>
					<Row class="item"><Col span="4">标签状态:</Col><Col span="20">32242322</Col></Row>
				</div>
    		</div>
        </div>
        <div class="h20pct pt16x">
        	<div class="h100pct sys-mian-panel pt16x">
				<!--<div class="pt16x pb10x ta-l"><Checkbox >自动保存</Checkbox></div>-->
				<div class="pt16x">
					<Row class="item" :gutter="10">
						<Col span="8"><Button class="mr10x w100pct" type="primary" >打印</Button></Col>
						<Col span="8"><Button class="mr10x w100pct" type="primary" >退样</Button></Col>
						<Col span="8"><Button class="mr10x w100pct" type="primary" >保存</Button></Col>
					</Row>
				</div>
        	</div>
        </div>
	</div>
</template>


<script>
import InputSampleWeight from "./InputSampleWeight"
import Suggest from "./Suggest"
import SampleDelete from "./SampleDelete"

export default {
  name: 'TransportSampleDetail',
  components: {InputSampleWeight,Suggest,SampleDelete},
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
        }
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

<style lang="less" scoped="">
.infos{
	.item{
		background: #F2F2F2;
		border-radius: 4px;
		line-height: 32px;
		margin-bottom: 10px;
		padding-left: 10px;
	}
}
</style>>