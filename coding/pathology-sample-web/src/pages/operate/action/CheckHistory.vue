<template>
	<Modal
        v-model="visible"
        :width="500"
        @on-ok="ok">
        <template slot="header">
        	<div style="width: 18px;height: 18px;" class="assets-icon assets-icon-modal-new"></div>
        	检查历史
        </template>
        <div v-if="visible" style="max-height: 300px;overflow-y: auto;">
        	<div class="c-h-item" v-for="item in list" @click="cur=item" :class="{'c-h-item-cur':cur.visitTime == item.visitTime}">
        		<p><span class="t">检查项目：</span>{{item.checkItem}}</p>
        		<p><span class="t">访问ID：</span>{{item.visitId}}</p>
        		<p><span class="t">检查时间：</span>{{item.visitTime}}</p>
        	</div>
        </div>
    </Modal>
</template>

<script>
import {imageUpload,saveSampleInfo} from '@/api/sampleApi'
import {mapActions,mapGetters} from 'vuex'

export default {
  name: 'CheckHistory',
  components: {},
  data () {
    return {
    	visible:false,
    	list:[],
    	cur:{}
    }
  },
  mounted(){
  },
  methods: {
  	...mapActions({
	}),
	moment,
  	show(data){
  		this.cur={};
  		this.visible = true;
  		this.list = data;
  	},
  	ok(){
  		if(!this.cur.visitTime) {
  			this.$Message.info('请选择一个检查历史');return ;
  		} else {
  			this.$emit('change',this.cur)
  			this.visible = false;
  		}
  	},
  }
}
</script>

<style>
.c-h-item{
	margin: 10px 20px;
	background: #F2F2F2;
	border-radius: 4px;
	padding: 10px;
	color: #333333;
	cursor: pointer;
	
}
.c-h-item-cur{
	border: 1px solid #1E90FF;
}
.c-h-item .t{
	font-size: 12px;
	color: #666666;
	line-height: 20px;
}
</style>