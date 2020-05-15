import Main from '@/components/main'
// import parentView from '@/components/parent-view'

/**
 * iview-admin中meta除了原生参数外可配置的参数:
 * meta: {
 *  hideInMenu: (false) 设为true后在左侧菜单不会显示该页面选项
 *  notCache: (false) 设为true后页面不会缓存
 *  access: (null) 可访问该页面的权限数组，当前路由设置的权限会影响子路由
 *  icon: (-) 该页面在左侧菜单、面包屑和标签导航处显示的图标，如果是自定义图标，需要在图标名称前加下划线'_'
 *  beforeCloseName: (-) 设置该字段，则在关闭当前tab页时会去'@/router/before-close.js'里寻找该字段名对应的方法，作为关闭前的钩子函数
 * }
 */
const routerSystem = [{
  path: '/authority',
  component: Main,
  name: '系统管理',
  meta: {
    icon: 'ios-boat',
    title: '权限管理'
  },
  children: [
    {
      path: '/function',
      name: '权限列表',
      component: () => import('@/views/authority/function'),
      meta: {
        icon: 'ios-boat',
        title: '权限列表'
      }
    },
    {
      path: '/menu',
      name: '菜单列表',
      component: () => import('@/views/authority/menu'),
      meta: {
        icon: 'ios-boat',
        title: '菜单列表'
      }
    },
    {
      path: '/component',
      name: '组件列表',
      component: () => import('@/views/authority/component'),
      meta: {
        icon: 'ios-boat',
        title: '组件列表'
      }
    },
    {
      path: '/role',
      name: '角色列表',
      component: () => import('@/views/authority/role'),
      meta: {
        icon: 'ios-boat',
        title: '角色列表'
      }
    }
  ]
}]

const routerApp = [{

}]

export const routerMap = [{
  path: '/404',
  meta: {
    title: '404',
    icon: 'md-home',
    hideInMenu: true
  },
  component: () => import('@/views/404'),
  hidden: true
},
{
  path: '/login',
  meta: {
    title: 'Login - 登录',
    icon: 'md-home',
    hideInMenu: true
  },
  component: () => import('@/views/login/index'),
  hidden: true
},
{
  path: '/',
  component: Main,
  meta: { title: '应用', icon: 'form', hideInMenu: true },
  redirect: '/apps',
  children: [
    {
      path: '/apps',
      name: '应用列表',
      component: () => import('@/views/app/index'),
      meta: { title: '应用列表', icon: 'form', hideInMenu: true }
    },
    {
      path: 'apps/:appId',
      name: '应用详情',
      component: () => import('@/views/app/detail'),
      meta: { title: '应用详情', icon: 'form', hideInMenu: true }
    }
  ]
},
{
  path: '/dashboard',
  component: Main,
  meta: { title: '概览', icon: 'form' },
  children: [
    {
      path: '/dashboard',
      name: '概览',
      component: () => import('@/views/dashboard/index'),
      meta: { title: '概览', icon: 'form'}
    }
  ]
},
// 404 page must be placed at the end !!!
{ path: '*', meta: {
  title: 'Login - 4040',
  icon: 'md-home',
  hideInMenu: true
}, redirect: '/404' }
]
