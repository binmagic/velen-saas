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

export const routerSystem = [{
  path: '/#',
  component: Main,
  name: 'menu-user-manager',
  meta: {
    icon: 'ios-boat',
    title: '用户管理'
  },
  children: [
    {
      path: '/permission/function',
      name: 'menu-function-list',
      component: () => import('@/views/permission/function'),
      meta: {
        icon: 'ios-boat',
        title: '权限列表'
      }
    },
    {
      path: '/permission/resource',
      name: 'menu-resource-list',
      component: () => import('@/views/permission/resource'),
      meta: {
        icon: 'ios-boat',
        title: '资源列表'
      }
    },
    {
      path: '/permission/role',
      name: 'menu-role-list',
      component: () => import('@/views/permission/role'),
      meta: {
        icon: 'ios-boat',
        title: '角色列表'
      }
    }
  ]
}]

export const routerApp = [
  {
    path: '/dashboard',
    name: 'menu-dashboard',
    component: Main,
    meta: { title: '概览', icon: 'form' },
    children: [
      {
        path: '/dashboard',
        name: 'menu-dashboard',
        component: () => import('@/views/dashboard/index'),
        meta: { title: '概览', icon: 'form' }
      }
    ]
  },
  {
    path: '/more',
    component: Main,
    meta: { title: '更多', icon: 'form' },
    children: [
      {
        path: '/dashboard',
        name: '产品设置',
        redirect: '/app'
      }
    ]
  }]

export const routerMap = routerApp.concat(routerSystem)

export const routes = [
  {
    path: '/login',
    meta: {
      title: 'Login - 登录',
      icon: 'md-home',
      hideInMenu: true
    },
    component: () => import('@/views/login/index'),
    hidden: true
  }, {
    path: '/',
    component: Main,
    meta: { title: '应用', icon: 'form', hideInMenu: true },
    redirect: '/app',
    children: [
      {
        path: '/app',
        component: () => import('@/views/app/index'),
        meta: { title: '应用列表', icon: 'form', hideInMenu: true }
      },
      {
        path: 'app/detail',
        component: () => import('@/views/app/detail'),
        meta: { title: '应用详情', icon: 'form', hideInMenu: true }
      }
    ]
  },
  // 404 page must be placed at the end !!!
  {
    path: '*',
    meta: {
      icon: 'md-home',
      hideInMenu: true
    },
    component: () => import('@/views/404')
  }
]
