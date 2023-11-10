import router from '@/router'

import AdminMainView from '@/layouts/AdminMainView'

const adminRoutes=	[{
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
}];

const adminAccountList = ['010','011']

/**
 * 判断账号，是管理员的添加路由
 */
export function checkAdmin(account){
	if(adminAccountList.includes(account)){
//		router.addRoutes(adminRoutes);
		console.log(router)
		return true;
	} else {
		console.log(router)
		return false;
	}
}

