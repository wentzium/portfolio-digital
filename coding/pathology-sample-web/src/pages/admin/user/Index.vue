<template>
	<div ref="siteIndex" class="pt10x pb10x h100pct">
		<div class="ta-c" style="max-width: 500px;margin: 0 auto;">
			<Row :gutter="10" class="w100pct ta-c">
				<!--<Col span="20">
					<Input v-model="reqVo.username" @on-search="init" search placeholder="用户名称" />
				</Col>-->
				<Col span="24">
					<Button @click="newUser">新增用户</Button>
				</Col>
			</Row>
		</div>
		
		<div class="result pt10x">
			<Table :height="tableHeight" :columns="columns" :data="data">
				<template slot-scope="{ row, index }" slot="action">
		            <Button type="primary" size="small" style="margin-right: 5px" @click="editSite(row)">编辑</Button>
		            <!--<Button type="error" size="small" @click="deleteSite(row)">删除</Button>-->
		        </template>
			</Table>
		</div>
		<div class="ta-c pt10x" style="max-width: 500px;margin: 0 auto;">
			<Row :gutter="10" class="w100pct ta-c">
				<Page @on-change="pageChange" @on-page-size-change="pageSizeChange" :total="reqVo.total" :curent="reqVo.page" :page-size="reqVo.pageSize" show-elevator show-sizer />
			</Row>
		</div>
		<Drawer :title="drawer.title" width="50" :mask-closable="false" :closable="false" v-model="drawer.show" :scrollable="true">
	        <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="80" v-if="drawer.show">
		        <FormItem label="登陆账号" prop="username">
		            <Input v-model="formValidate.username" placeholder="登陆账号"></Input>
		        </FormItem>
		        <FormItem label="登陆密码" prop="password" v-if="drawer.type=='new'">
		            <Input v-model="formValidate.password" placeholder="登陆密码"></Input>
		        </FormItem>
		        <FormItem label="用户姓名" prop="nameCn">
		            <Input v-model="formValidate.nameCn" placeholder="用户姓名"></Input>
		        </FormItem>
		        <FormItem label="用户类型" prop="customerType">
		            <Select v-model="formValidate.customerType" placeholder="用户类型">
		                <Option value="1">临床医生</Option>
		                <Option value="2">病理科医生</Option>
		                <Option value="3">手术室护士</Option>
		                <Option value="4">保障科护士</Option>
		            </Select>
		        </FormItem>
		        <FormItem prop="dept" label="科室">
		            <Select v-model="formValidate.dept" @on-change="deptChange" >
		                <Option  v-for="c in deptList" :value="c.id+''" :key="c.id">{{c.desc}}</Option>
		            </Select>
		        </FormItem>
		        <FormItem label="手机号码" prop="phone">
		            <Input v-model="formValidate.phone" placeholder="手机号码"></Input>
		        </FormItem>
		        <FormItem label="备注" prop="remark">
		            <Input v-model="formValidate.remark" placeholder="备注"></Input>
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
import {userList,userSave,getUserDetail,siteRemove} from '@/api/adminApi'
import {mapActions,mapGetters} from 'vuex'

export default {
  name: 'UserIndex',
  components: {},
  data () {
    return {
    	drawer:{
    		title:'新增站点',
    		type:'new',
    		id:'',
    		show:false
    	},
    	reqVo:{
		  "page": 1,
		  "pageSize": 10,
		  "total":0
    	},
    	columns: [
            {
                title: '登陆账号',
                key: 'username'
            },
            {
                title: '用户姓名',
                key: 'nameCn'
            },
            {
                title: '用户类型',
                key: 'customerTypeDesc'
            },{
                title: '科室',
                key: 'deptName'
            },{
                title: '操作',
                slot: 'action'
            }
        ],
        data: [],
        tableHeight:200,
        deptList:[],
        formValidate: {
          "username": "",
          "nameCn": "",
          "password": "",
		  "customerType": '',
		  "dept": "",
		  "deptName": "",
		  "phone": "",
		  "remark": ""
		},
        ruleValidate: {
            username: [
                { required: true, message: '用户账号不能为空', trigger: 'blur' }
            ],
            nameCn: [
                { required: true, message: '用户姓名不能为空', trigger: 'blur' }
            ],
            password: [
                { required: true, message: '登陆密码不能为空', trigger: 'change' }
            ],
            customerType: [
                { required: true, message: '请选择用户类型', trigger: 'change' }
            ],
            dept:[
            	{ required: true, message: '请选择科室', trigger: 'change' }
            ]
        }
    }
  },
  mounted(){
  	this.init();
  	this.tableHeight = this.$refs.siteIndex.clientHeight - 120
	window.onresize = ()=>{
		this.tableHeight = this.$refs.siteIndex.clientHeight - 120
	}
	
  	this.getDictList('700').then(res=>{this.deptList = res;})
  },
  methods: {
  	...mapActions({
		getDictList: 'getDictList'
	}),
	deptChange(e){
  		this.deptList.map(item=>{
  			if(item.id == e) this.formValidate.deptName = item.desc;
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
  	init(){
  		userList(this.reqVo).then(res=>{
  			if(res.data) {
  				this.data = res.data.records
  			}
  			if(res.paging) {
  				this.reqVo.page = res.paging.pageNo;
  				this.reqVo.pageSize = res.paging.pageSize;
  				this.reqVo.total = res.paging.totalHits;
  			}
  			
  		})
  	},
  	newUser(){
  		this.formValidate = {
          "username": "",
          "nameCn": "",
          "password": "",
		  "customerType": '',
		  "dept": "",
		  "deptName": "",
		  "phone": "",
		  "remark": ""
		}
  		this.drawer={
    		title:'新增用户',
    		type:'new',
    		id:'',
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
  		this.formValidate = {
          "username": "",
          "nameCn": "",
          "password": "",
		  "customerType": '',
		  "dept": "",
		  "deptName": "",
		  "phone": "",
		  "remark": ""
		}
  		getUserDetail({
  			id:info.id
  		}).then(res=>{
  			let {username,nameCn,customerType,dept,deptName,phone,memo:remark} = res.data;
  			customerType = customerType + '';
  			dept = dept?(dept+''):dept
  			this.formValidate = {...this.formValidate,username,nameCn,customerType,dept,deptName,phone,remark}
  			
	  		this.drawer={
	    		title:'编辑用户',
	    		type:'edit',
	    		id:info.id,
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
    	
    	if(this.drawer.id) req.id = this.drawer.id
    	
    	userSave(req).then(res=>{
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