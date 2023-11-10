<template>
	<Modal
        v-model="visible"
        @on-ok="visible=false"
        @on-cancel="visible=false">
        <template slot="header">
        	<div style="width: 18px;height: 18px;" class="assets-icon assets-icon-modal-default"></div>
        	固定液建议
        </template>
        <div style="font-size: 16px;color: #505050;text-align: center;padding: 40px 0;">
        	30g 的样本，建议使用 500ml 的固定液
        </div>
        <template slot="footer">
        	<Button>取消</Button>
        	<Button type="primary">确认</Button>
        </template>
    </Modal>
</template>

<script>
export default {
  name: 'Suggest',
  components: {},
  data () {
    return {
    	visible:false
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

<style>
</style>