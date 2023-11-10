<template>
	<div ref="siteIndex" class="pt10x pb10x h100pct">
		<div class="result">
			<div class="ta-c pb10x">
				<Input style="margin-right: 10px;width: 30%;" v-model="reqVo.keyName" @on-search="searchByKey" search placeholder="Key名称" />
				<Button type="primary" size="small" @click="newDict">新增</Button>
			</div>
			<Table :height="tableHeight" :columns="columns" :data="data">
				<template slot-scope="{ row, index }" slot="defaultShow">
					{{row.defaultShow?'是':"否"}}
		        </template>
				<template slot-scope="{ row, index }" slot="action">
		            <Button type="primary" size="small" style="margin-right: 5px" @click="editSite(row)">编辑</Button>
		            <Button type="error" size="small" @click="deleteDict(row)">删除</Button>
		        </template>
			</Table>
		</div>
		<div class="ta-c pt10x" style="max-width: 500px;margin: 0 auto;">
			<Row :gutter="10" class="w100pct ta-c">
				<Page @on-change="pageChange" @on-page-size-change="pageSizeChange" :total="reqVo.total" :curent="reqVo.page" :page-size="reqVo.pageSize" show-elevator show-sizer />
			</Row>
		</div>
		<Drawer :title="drawer.title" width="50" :mask-closable="false" :closable="false" v-model="drawer.show" :scrollable="true">
	        <Form v-if="drawer.show" ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="80">
		        <FormItem label="字典类型" prop="dicId">
		            <Select v-model="formValidate.dicId" placeholder="请选择">
		                <Option v-for="dict in defaultDictList" :value="dict.id+''">{{dict.desc}}</Option>
		            </Select>
		        </FormItem>
		        <FormItem label="字典名称" prop="itemName">
		            <Input v-model="formValidate.itemName" placeholder="字典名称"></Input>
		        </FormItem>
		        <FormItem label="默认" prop="isDefault">
		        	<Select v-model="formValidate.isDefault" >
		        		<Option value="0">否</Option>
		        		<Option value="1">是</Option>
				    </Select>
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
import {dictList,dictSave,getDetail,dictRemove,searchDicList} from '@/api/adminApi'

export default {
  name: 'SiteIndex',
  components: {},
  data () {
    return {
    	drawer:{
    		title:'新增字典',
    		show:false
    	},
    	reqVo:{
    	  "keyName":"",
		  "page": 1,
		  "pageSize": 10,
		  "total":0
    	},
    	columns: [
    		{
                title: '所属key',
                key: 'dicCode'
            },
            {
                title: 'key名称',
                key: 'dicDesc'
            },
            {
                title: '编码',
                key: 'code'
            },
            {
                title: '名称',
                key: 'desc'
            },
            {
                title: '默认',
                slot: 'defaultShow'
            },{
            	title: '操作',
                slot: 'action'
            }
        ],
        data: [],
        tableHeight:200,
        formValidate: {
            dicId: '',
            itemName: '',
            isDefault: '0'
        },
        ruleValidate: {
            dicId: [
                { required: true, message: '字典编号不能为空', trigger: 'change' }
            ],
            itemName: [
                { required: true, message: '字典名称不能为空', trigger: 'blur' }
            ]
        },
        defaultDictList:[]
    }
  },
  mounted(){
  	this.init();
  	this.getDicList();
  	this.tableHeight = this.$refs.siteIndex.clientHeight - 120
	window.onresize = ()=>{
		this.tableHeight = this.$refs.siteIndex.clientHeight - 120
	}
  },
  methods: {
  	getDicList(){
  		searchDicList({
  		  "keyName":this.reqVo.keyName,
		  "page": 1,
		  "pageSize": 100
		}).then(res=>{
			console.log(res)
			if(res.data) this.defaultDictList = res.data;
		})
  	},
  	pageChange(p){
		this.reqVo.page  = p;
		this.init()
	},
	pageSizeChange(ps){
		this.reqVo.pageSize  = ps;
		this.init()
	},
	searchByKey(){
		this.reqVo.page= 1;
		this.init();
	},
  	init(){
		dictList(this.reqVo).then(res=>{
			this.data = res.data
			if(res.paging) {
  				this.reqVo.page = res.paging.pageNo;
  				this.reqVo.pageSize = res.paging.pageSize;
  				this.reqVo.total = res.paging.totalHits;
  			}
		})
  	},
  	newDict(){
  		this.formValidate={
            dicId: '',
            itemName: '',
            isDefault: '0'
        }
  		this.drawer={
    		title:'新增字典',
    		show:true
    	}
  	},
  	deleteDict(info){
  		this.$Modal.confirm({
            title: '删除字典',
            content: '<p>是否确认删除字典<'+info.siteName+'></p>',
            onOk: () => {
                dictRemove(info.id).then(res=>{
                	this.$Message.success(res.msg);
                	this.init()
                })
            },
            onCancel: () => {}
        });
  	},
  	editSite(info){
  		getDetail({
  			dicItemId:info.id
  		}).then(res=>{
  			res.data.isDefault = res.data.isDefault?'1':'0';
  			if(res.data) {
  				let {dicId,desc:itemName,defaultShow:isDefault,id} = res.data;
  				dicId = dicId?(dicId+''):''
  				isDefault = isDefault?'1':'0';
  				this.formValidate = {
  					dicId,itemName,isDefault,id
  				};
  			}
	  		this.drawer={
	    		title:'编辑字典',
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
    	if(req.isDefault == '0'){req.isDefault=false} else {req.isDefault=true};
    	dictSave(req).then(res=>{
    		this.$Message.success(res.msg);
    		this.drawer.show = false;
    		this.init();
    	}).catch(err=>{})
    }
  }
}
</script>

<style lang="less" scoped>
</style>