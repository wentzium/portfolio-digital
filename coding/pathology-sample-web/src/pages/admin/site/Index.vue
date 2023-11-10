<template>
	<div ref="siteIndex" class="pt10x pb10x h100pct">
		<div class="ta-c" style="max-width: 500px;margin: 0 auto;">
			<Row :gutter="10" class="w100pct ta-c">
				<Col span="20">
					<Input v-model="reqVo.siteName" @on-search="init" search placeholder="站点名称" />
				</Col>
				<Col span="4">
					<Button @click="newSite">新增站点</Button>
				</Col>
			</Row>
		</div>
		
		<div class="result pt10x">
			<Table :height="tableHeight" :columns="columns" :data="data">
				<template slot-scope="{ row, index }" slot="action">
		            <Button type="primary" size="small" style="margin-right: 5px" @click="editSite(row)">编辑</Button>
		            <Button type="error" size="small" @click="deleteSite(row)">删除</Button>
		        </template>
			</Table>
		</div>
		
		<Drawer :title="drawer.title" width="50" :mask-closable="false" :closable="false" v-model="drawer.show" :scrollable="true">
	        <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="80">
		        <FormItem label="站点名称" prop="siteName">
		            <Input v-model="formValidate.siteName" placeholder="站点名称"></Input>
		        </FormItem>
		        <FormItem label="站点编号" prop="siteNo">
		            <Input v-model="formValidate.siteNo" placeholder="站点编号"></Input>
		        </FormItem>
		        <FormItem label="通配IP" prop="ip">
		            <Input v-model="formValidate.ip" placeholder="通配IP"></Input>
		        </FormItem>
		        <FormItem label="绑定IP" prop="mapSiteIdList">
		        	<Input v-model="formValidate.mapSiteIdList[0].ip" placeholder="绑定IP" >
		        		<Button @click="addMapSiteId" slot="append" icon="md-add"></Button>
		        	</Input>
		        	<Input class="mt10x" v-if="index>0" v-for="(item,index) in formValidate.mapSiteIdList" v-model="item.ip" placeholder="绑定IP">
		        		<Button @click="removeMapSiteId(index)" slot="append" icon="md-remove"></Button>
		        	</Input>
		        </FormItem>
		        <FormItem label="站点类型" prop="siteType">
		            <Select v-model="formValidate.siteType" placeholder="选择站点类型">
		                <Option value="1">手术站点</Option>
		                <Option value="2">预约站点</Option>
		                <Option value="3">门诊站点</Option>
		                <Option value="4">固定站点</Option>
		                <Option value="5">运送站点</Option>
		                <Option value="6">接收站点</Option>
		                <Option value="7">运送站点（二级）</Option>
		            </Select>
		        </FormItem>
		        <FormItem label="备注" prop="remark">
		            <Input v-model="formValidate.remark" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="备注"></Input>
		        </FormItem>
		        <FormItem>
		            <Button type="primary" @click="handleSubmit('formValidate')">保存</Button>
		            <Button @click="drawer.show=false" style="margin-left: 8px">关闭</Button>
		        </FormItem>
		    </Form>
	    </Drawer>
	</div>
	
</template>

<script>
import {siteList,siteSave,getSiteDetail,siteRemove} from '@/api/adminApi'

export default {
  name: 'SiteIndex',
  components: {},
  data () {
    return {
    	drawer:{
    		title:'新增站点',
    		show:false
    	},
    	reqVo:{
		  "ip": "",
		  "siteName": "",
		  "siteNo": "",
		  "siteType": ""
    	},
    	columns: [
            {
                title: '站点名称',
                key: 'siteName'
            },
            {
                title: '站点类型',
                key: 'siteDesc'
            },
            {
                title: 'ip',
                key: 'ip'
            },{
                title: '操作',
                slot: 'action'
            }
        ],
        data: [],
        tableHeight:200,
        
        formValidate: {
            siteName: '',
            siteNo: '',
            siteType: '',
            mapSiteIdList: [{ip:''}],
            ip: '',
            remark: ''
        },
        ruleValidate: {
            siteName: [
                { required: true, message: '站点名称不能为空', trigger: 'blur' }
            ],
            siteNo: [
                { required: true, message: '站点编号不能为空', trigger: 'blur' }
            ],
            siteType: [
                { required: true, message: '请选择站点类型', trigger: 'change' }
            ],
            ip: [
                { required: true, message: '通配IP不能为空', trigger: 'change' }
            ],
            mapSiteIdList: [
                { required: true,type:"array",min:1,  message: 'IP不能为空' , trigger: 'change' },
            ],
        }
    }
  },
  mounted(){
  	this.init();
  	this.tableHeight = this.$refs.siteIndex.clientHeight - 80
	window.onresize = ()=>{
		this.tableHeight = this.$refs.siteIndex.clientHeight - 80
	}
  },
  methods: {
  	init(){
  		siteList(this.reqVo).then(res=>{
  			this.data = res.data
  		})
  	},
  	newSite(){
  		this.drawer={
    		title:'新增站点',
    		show:true
    	}
  	},
  	deleteSite(info){
  		this.$Modal.confirm({
            title: '删除站点',
            content: '<p>是否确认删除站点<'+info.siteName+'></p>',
            onOk: () => {
                siteRemove({
                	siteId:info.id
                }).then(res=>{
                	this.$Message.success(res.msg);
                	this.init()
                })
            },
            onCancel: () => {}
        });
  	},
  	editSite(info){
  		getSiteDetail({
  			siteId:info.id
  		}).then(res=>{
  			if(res.data.mapSiteIdList.length) {res.data.mapSiteIdList = res.data.mapSiteIdList.map(item=>{return {ip:item}});}
  			else res.data.mapSiteIdList = [{ip:""}]
  			res.data.siteType +='';
  			res.data.remark = res.data.siteDesc;
  			this.formValidate = res.data; 
	  		this.drawer={
	    		title:'编辑站点',
	    		show:true
	    	}
  		})
  	},
  	addMapSiteId(){
  		this.formValidate.mapSiteIdList.push({ip:''})
  	},
  	removeMapSiteId(i){
  		this.formValidate.mapSiteIdList.splice(i,1)
  	},
  	handleSubmit (name) {
        this.$refs[name].validate((valid) => {
            if (valid) {
            	this.save()
            }
        })
    },
    save(){
    	let req = {...this.formValidate};
    	req.mapSiteIdList = req.mapSiteIdList.map(item=>item.ip)
    	siteSave(req).then(res=>{
    		this.$Message.success(res.msg);
    		this.drawer.show = false;
    		this.init();
    	}).catch(err=>{this.$Message.error(err.msg);})
    }
  }
}
</script>

<style lang="less" scoped>
</style>