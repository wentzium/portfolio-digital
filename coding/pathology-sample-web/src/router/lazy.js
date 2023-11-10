import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/pages/login/Login'
import MainView from '@/layouts/MainView'
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
    }
    
  ]
});


router.beforeEach((to, from, next) => {
	console.log(to)
	if(to.path.includes('/leica/appointment')){//
		next();
	} else if(to.path != '/login' && !authUtils.getToken()){
		next('/login')
	} else {
		next()
	}
});

export default router;