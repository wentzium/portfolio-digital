import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/pages/login/Login'
import MainView from '@/layouts/MainView'
import AdminMainView from '@/layouts/AdminMainView'
import AppointmentView from '@/layouts/AppointmentView'
import * as authUtils from '@/utils/auth'

Vue.use(Router)

const router = new Router({
//mode: 'history',
  base: process.env.BASE_URL,
  routes: [
  	{
      path: '/login',
      name: '登录页',
      component: Login,
      invisible: true
    },
    {
      path: '/',
      name: '首页',
      redirect: '/leica',
    },
    {
      path: '/print',
      name: '首页',
      component: () => import('@/components/PrintPDF'),
    },
    {
      path: '/leica',
      name: '首页',
      component: MainView,
      redirect: '/leica/home',
      children: [
      	{
          path: '/leica/home',
          name: 'home',
          component: () => import('@/pages/Home'),
        },
        {
          path: '/leica/outpatient',
          name: 'outpatient',
          component: () => import('@/pages/outpatient/Index'),
        },
        {
          path: '/leica/operate',
          name: 'operate',
          component: () => import('@/pages/operate/Index'),
        },
        {
          path: '/leica/fixed',
          name: 'fixed',
          component: () => import('@/pages/fixed/Index'),
        },
        {
          path: '/leica/transport',
          name: 'transport',
          component: () => import('@/pages/transport/Index'),
        },
        {
          path: '/leica/multi-transport',
          name: 'multiTransport',
          component: () => import('@/pages/multi-transport/Index'),
        },
        {
          path: '/leica/accept',
          name: 'accept',
          component: () => import('@/pages/accept/Index'),
        },
        {
          path: '/leica/appointment',
          name: 'appointment',
          component: () => import('@/pages/appointment/Appointment'),
        }
      ]
    },
    {
		  path: '/leicaAdmin',
		  name: '管理',
		  component: AdminMainView,
		  redirect: '/leicaAdmin/site',
		  children: [
		  	{
		      path: '/leicaAdmin/site',
		      name: 'site',
		      component: () => import('@/pages/admin/site/Index'),
		    },{
		      path: '/leicaAdmin/user',
		      name: 'site',
		      component: () => import('@/pages/admin/user/Index'),
		    },{
		      path: '/leicaAdmin/dict',
		      name: 'site',
		      component: () => import('@/pages/admin/dict/Index'),
		    }
		  ]
    },
    {
    	path: '/appointment',
    	name: '首页',
    	component: AppointmentView,
    	redirect: '/appointment/list',
    	children: [
    	  {
    	    path: '/appointment/list',
    	    name: 'appointmentList',
    	    component: () => import('@/pages/appointment/List'),
    	  }
    	]
    },
    {
    	path: '/*',
    	redirect: '/leica',
    }
  ]
});


router.beforeEach((to, from, next) => {
	if(to.path.includes('/leica/appointment')){//
		next();
	} else if(to.path != '/login' && !authUtils.getToken()){
		next('/login')
	} else {
		next()
	}
});

export default router;