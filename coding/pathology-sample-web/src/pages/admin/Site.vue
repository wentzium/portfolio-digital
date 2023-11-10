<template>
	<div class="home-content ">
		<Row :gutter="50" class="w100pct ta-c">
			<Col span="3">
				<div class="item"><span @click="$router.push('/leica/appointment')" >预约</span></div>
			</Col>
			<Col span="3">
				<div class="item word-line"><span @click="$router.push('/appointment/list')">预约通知</span></div>
			</Col>
			<Col span="3">
				<div class="item word-line"><span @click="$router.push('/leica/outpatient')">门诊</span></div>
			</Col>
			<Col span="3">
				<div class="item"><span @click="$router.push('/leica/operate')">手术</span></div>
			</Col>
			<Col span="3">
				<div class="item"><span @click="$router.push('/leica/fixed')">固定</span></div>
			</Col>
			<Col span="3">
				<div class="item"><span @click="$router.push('/leica/transport')">运送</span></div>
			</Col>
			<Col span="3">
				<div class="item"><span @click="$router.push('/leica/multi-transport')">二级转运</span></div>
			</Col>
			<Col span="3">
				<div class="item"><span @click="$router.push('/leica/accept')">接收</span></div>
			</Col>
		</Row>
	</div>
</template>

<script>

export default {
  name: 'Home',
  components: {},
  data () {
    return {
    }
  },
  computed: {
  },
  methods: {
  }
}
</script>

<style lang="less" scoped>
@import "../../index.less";
.home-content{
	height: 100%;
	display: flex;
	flex-direction: row;
	justify-content: center;
	align-items: center;
	.item{
		width: 100%;
		padding-bottom:100%;
		font-size: 15px;
		font-weight: bold;
		line-height: 100px;
		background: @primary-color;
		color: #FFFFFF;
		border-radius: 5px;
		position: relative;
		span{
			display: flex;
		    flex-direction: column;
		    justify-content: center;
		    align-items: center;
		    position: absolute;
		    width: 100%;
		    height: 100%;
			cursor: pointer;
		}
	}
}
</style>