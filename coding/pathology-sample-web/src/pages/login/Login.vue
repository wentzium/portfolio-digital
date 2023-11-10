<template>
  <div class="login">
    <div class="login-con">
    	<Row :gutter="100" type="flex" justify="center" align="middle">
        <Col span="10" class="login-con-leica ta-c">
        	<img width="100%" src="@/assets/login_logo.png" />
        	<h1>Leica 样本管理系统</h1>
        	<p>Leica Sample Management System</p>
        </Col>
        <Col span="14">
        	<Card  :bordered="false">
		        <div class="form-con">
		          <login-form @on-success-valid="handleSubmit"></login-form>
		        </div>
		      </Card>
        </Col>
    	</Row>
    </div>
  </div>
</template>

<script>
import LoginForm from '_c/login-form'
import { mapActions } from 'vuex'

import {userLogin} from '@/api/userApi'
import * as authUtils from '@/utils/auth'
import {checkAdmin} from '@/utils/permission'

export default {
  components: {
    LoginForm
  },
  methods: {
    ...mapActions([
      'handleLogin',
      'getUserInfo'
    ]),
    handleSubmit ({ username, password }) {
    	userLogin({
    		"ip": "127.0.0.1",
			  password,
			  "type": 1,
			  username
    	}).then(res=>{
    		let {
    			data:{
    				jwt:{access_token,refresh_token},
    				user
    			}
    		} = res;
    		
    		if(user) authUtils.setUsername(user.username)
				if(this.autoLogin) authUtils.setTokenWith15Days(access_token); else authUtils.setToken(access_token);
				authUtils.setRefreshToken(refresh_token);
				if(checkAdmin(username)){
					this.$router.push('/leicaAdmin')
				} else {
					this.$router.push('/')
				}
    	},err=>{
    		console.log(err)
    	})
    }
  }
}
</script>

<style lang="less" scoped="">
.login{
    width: 100%;
    height: 100%;
    background-size: cover;
    /*background-image: url('../../assets/login-bg.jpg');*/
    background-color: #454551;
    background-position: center;
    position: relative;
    &-con{
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%,-60%);
        width: 800px;
        &-header{
            font-size: 16px;
            font-weight: 300;
            text-align: center;
            padding: 30px 0;
        }
        .form-con{
            padding: 10px 0 0;
        }
        .login-tip{
            font-size: 10px;
            text-align: center;
            color: #c3c3c3;
        }
        &-leica{
        	h1{
        		font-family: PingFangSC-Semibold;
						font-size: 22px;
						color: #FFFFFF;
        	}
        	p{
        		opacity: 0.3;
						font-family: PingFangSC-Semibold;
						font-size: 11px;
						color: #FFFFFF;
        	}
        }
    }
}
</style>