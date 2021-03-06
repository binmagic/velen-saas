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
    title: '用户管理',
  },
  children: [
    {
      path: '/permission/function',
      name: 'menu-function-list',
      component: () => import('@/views/permission/function'),
      meta: {
        icon: 'ios-boat',
        title: '权限列表',
        hideAside: true,
        hideHeader: true
      }
    },
    {
      path: '/permission/resource',
      name: 'menu-resource-list',
      component: () => import('@/views/permission/resource'),
      meta: {
        icon: 'ios-boat',
        title: '资源列表',
        hideAside: true,
        hideHeader: true
      }
    },
    {
      path: '/permission/role',
      name: 'menu-role-list',
      component: () => import('@/views/permission/role'),
      meta: {
        icon: 'ios-boat',
        title: '角色列表',
        hideAside: true,
        hideHeader: true
      }
    }
  ]
}]

export const routerApp = [
  {
    path: '/dashboard',
    component: Main,
    meta: {title: '概览', icon: 'form', header: true},
    children: [
      {
        path: '/dashboard',
        name: 'menu-dashboard',
        component: () => import('@/views/dashboard/index'),
        meta: {title: '概览', icon: 'form', hideHeader: false, hideAside: false}
      },
      {
        path: '/dashboard/manager',
        name: 'menu-dashboard-manager',
        component: () => import('@/views/dashboard/manager/index'),
        meta: {title: '管理概览', icon: 'from', hideHeader: false, hideAside: true}
      }
    ]
  },
  {
    path: '/analyze',
    component: Main,
    children: [
      {
        path: '/analyze-meta-event',
        name: 'menu-analyze-meta-event',
        component: () => import('@/views/analyze/meta-event/index'),
        meta: {title: '元数据分析', icon: 'form', hideHeader: false, hideAside: false}
      },
      {
        path: '/analyze-property',
        name: 'menu-analyze-property',
        component: () => import('@/views/analyze/property/index'),
        meta: {title: '属性分析', icon: 'form', hideHeader: false, hideAside: false}
      }
    ]
  },
  {
    path: '/bookmarks',
    component: Main,
    children: [
      {
        path: '/bookmarks',
        name: 'menu-bookmarks',
        component: () => import('@/views/bookmarks/index'),
        meta: {title: '书签', icon: 'form', hideHeader: false, hideAside: true}
      }
    ]
  },
  {
    path: '/metadata',
    component: Main,
    children: [
      {
        path: '/meta-data',
        name: 'menu-metadata',
        component: () => import('@/views/metadata/index'),
        meta: {title: '元数据管理', icon: 'form', hideHeader: false, hideAside: false}
      },
      {
        path:'/meta-rule-setting',
        name:'menu-metadata-rule',
        component:() =>import('@/views/metadata/meta-rule'),
        meta: {title:'入库校验规则设置',icon:'form',hideHeader: false, hideAside: false}
      },
      {
        path: '/meta-event-prop',
        name: 'menu-metadata-event-prop',
        component: () => import('@/views/metadata/meta-event-prop/index'),
        meta: {title: '事件属性', icon: 'form', hideHeader: false, hideAside: false}
      },
      {
        path: '/meta-event  ',
        name: 'menu-metadata-event',
        component: () => import('@/views/metadata/meta-event/index'),
        meta: {title: '元事件', icon: 'form', hideHeader: false, hideAside: false}
      }
    ]
  },
  {
    path: '/more',
    component: Main,
    children: [
      {
        path: '/more-setting',
        name: 'menu-more-setting',
        component: () => import('@/views/more/basic-setting/index'),
        meta: {title: '基本设置', icon: 'form', hideHeader: false, hideAside: false}
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
    redirect: '/app',
    children: [
      {
        path: '/app',
        name: 'menu-app',
        component: () => import('@/views/app/index'),
        meta: {title: '应用列表', icon: 'form', hideHeader: true, hideAside: true}
      },
      {
        path: '/app/detail',
        name: 'menu-app-detail',
        component: () => import('@/views/app/detail'),
        meta: {title: '应用详情', icon: 'form', hideHeader: false, hideAside: true}
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
