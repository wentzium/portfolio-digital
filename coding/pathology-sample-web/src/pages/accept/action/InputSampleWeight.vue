<template>
	<Modal
        v-model="visible"
        :footer-hide="Boolean(true)"
        :width="270"
        >
        <div style="font-size: 16px;color: #505050;text-align: center;padding-top: 40px;">
        	<Input v-model="weight" disabled="">
        		<div slot='suffix' style="line-height: 30px;">g</div>
        	</Input>
    		<Row :gutter="10" class="pt10x">
    			<Col span="6"><span class="min-btn" @click="weight+='1'">1</span></Col>
    			<Col span="6"><span class="min-btn" @click="weight+='2'">2</span></Col>
    			<Col span="6"><span class="min-btn" @click="weight+='3'">3</span></Col>
    			<Col span="6"><span class="min-btn" @click="weight=''">Cler</span></Col>
    			<Col span="6"><span class="min-btn" @click="weight+='4'">4</span></Col>
    			<Col span="6"><span class="min-btn" @click="weight+='5'">5</span></Col>
    			<Col span="6"><span class="min-btn" @click="weight+='6'">6</span></Col>
    			<Col span="6"><span class="min-btn" @click="weight+='.'">.</span></Col>
    			<Col span="6"><span class="min-btn" @click="weight+='7'">7</span></Col>
    			<Col span="6"><span class="min-btn" @click="weight+='8'">8</span></Col>
    			<Col span="6"><span class="min-btn" @click="weight+='9'">9</span></Col>
    			<Col span="6"><span class="min-btn" @click="weight+='0'">0</span></Col>
    		</Row>
    		<div></div>
    		<Button type="primary" style="width: 100%;" @click="visible=false;$emit('weight',weight)">确认</Button>
        </div>
    </Modal>
</template>

<script>
export default {
  name: 'InputSampleWeight',
  components: {},
  data () {
    return {
    	visible:false,
    	weight:'',
    }
  },
  computed: {
  },
  methods: {
  	show(){
  		this.visible = true;
  	}
  }
}
</script>

<style lang="less" scoped="scoped">
.min-btn{display: inline-block;
width: 50px;height: 50px;font-size: 14px;text-align: center;line-height: 50px;color: #FFFFFF;
background: #696973;cursor: pointer;
border-radius: 2px;margin-bottom: 10px;}
</style>