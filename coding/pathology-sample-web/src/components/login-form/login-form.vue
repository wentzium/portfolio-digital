<template>
  <Form style="padding:0 10px;" ref="loginForm" :model="form" :rules="rules" @keydown.enter.native="handleSubmit">
  	<p style="padding-bottom: 10px;font-size: 20px;font-weight: bold;">登陆</p>
  	<label class="fs12x">账号</label>
    <FormItem prop="userName">
      <Input v-model="form.userName" placeholder="请输入账号">
      </Input>
    </FormItem>
    <label class="fs12x">密码</label>
    <FormItem prop="password">
      <Input type="password" v-model="form.password" placeholder="请输入密码">
      </Input>
    </FormItem>
    <Checkbox style="padding-bottom: 12px;"><span>记住密码</span></Checkbox>
    <FormItem>
      <Button @click="handleSubmit" type="primary" long>登录</Button>
    </FormItem>
  </Form>
</template>
<script>
export default {
  name: 'LoginForm',
  props: {
    userNameRules: {
      type: Array,
      default: () => {
        return [
          { required: true, message: '账号不能为空', trigger: 'blur' }
        ]
      }
    },
    passwordRules: {
      type: Array,
      default: () => {
        return [
          { required: true, message: '密码不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  data () {
    return {
      form: {
        userName: '',
        password: ''
      }
    }
  },
  computed: {
    rules () {
      return {
        userName: this.userNameRules,
        password: this.passwordRules
      }
    }
  },
  methods: {
    handleSubmit () {
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          this.$emit('on-success-valid', {
            username: this.form.userName,
            password: this.form.password
          })
        }
      })
    }
  }
}
</script>

<style lang="less" scoped>
.ivu-form-item{
	.ivu-form-item-content{
		.ivu-form-item-error-tip{padding-top: 2px;}
	}
}
</style>