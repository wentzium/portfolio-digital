<template>
  <Layout class="com-layout">
    <Header class="com-header">
    	<Row>
        <Col span="16">
	    		<div class="login-user">
	        		<Avatar icon="ios-person"  />
	        		<Dropdown style="margin-left: 20px;">
	        			<span style="cursor: pointer;">{{username}}<Icon type="ios-arrow-down" style="margin-left: 10px;"></Icon></span>
				        <DropdownMenu slot="list">
				            <DropdownItem>
				            	<div @click="logout">退出登陆</div>
				            </DropdownItem>
				        </DropdownMenu>
					    </Dropdown>
	    		</div>
	    		<div class="title">
	    			<span>Leica 样本管理系统</span>
	    		</div>
        </Col>
        <Col span="8" class="ta-r">
        	<div class="right-panel">
        		<span style="margin-right: 10px;">{{this.curTime.format("MM月DD日 HH:mm")}}</span>
        		<span style="margin-right: 10px;">{{weekdays[this.curTime.day()]}}</span>
        		<div class="logo">
        			<img src="@/assets/login_logo.png" />
        		</div>
        	</div>
        </Col>
	    </Row>
    </Header>
    <Content class="com-content">
    	<!--<div class="head">
    		<Row>
    			<Col span="8"><strong>手术站点</strong></Col>
    			<Col span="16" class="ta-r">
    				<slot name="action-btns"></slot>
    			</Col>
    		</Row>
    	</div>
    	<div class="com-content-body">
    	</div>-->
    	<slot></slot>
    </Content>
	</Layout>
</template>

<script>
import * as authUtils from '@/utils/auth'
import {userLogout} from '@/api/userApi'
import {mapActions} from 'vuex'

export default {
  name: 'GlobalLayout',
  components: {},
  data () {
    return {
    	curTime:moment(),
    	weekdays: moment.weekdays(),
    	username:null
    }
  },
  mounted() {
  	this.username = authUtils.getUsername() || '--'
  	setInterval(()=>{
  		this.curTime = moment()
  	},1000)
  },
  methods: {
  	...mapActions({
			getSiteList: 'account/getSiteList',
		}),
  	logout(){
  		userLogout().then(res=>{
  			authUtils.removeToken();
  			authUtils.removeRefreshToken();
  			this.$router.push('/login')
  		})
  	},
  },
  beforeCreate () {
  }
}
</script>

<style lang="less">
.com-layout{
    height: 100%;
    .com-header{
    		color: #FFFFFF;
        background: #454551;
        padding: 0;
        .login-user{
        	display: inline-block;
        	padding: 0 20px;
        	background: #33333c;
        }
        .title{
        	display: inline-block;
        	height: 100%;
        	line-height: 1;
        	padding: 0 20px;
        	font-size: 16px;
        	font-weight: 600;
        }
        .right-panel{
        	font-size: 16px;
        }
        .logo{
        	display: inline-block;
        	width: 20%;
        	img{
        		position: relative;
        		top: 10px;
        		width: 100%;
        		max-width: 70px;
        	}
        }
    }
    .com-content{
        background: #f1f1f2;
        height:calc(~"100% - 50px");
        padding: 0 40px;
        .head{
        	padding: 10px;
        	font-size: 20px;
        }
        .com-content-body{
        	height: calc(~"100% - 70px");
        	
        }
    }
}
</style>
