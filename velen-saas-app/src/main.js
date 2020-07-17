import Vue from 'vue'

import 'normalize.css/normalize.css' // A modern alternative to CSS resets
import i18n from './locale'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import './styles/index.scss' // global css
import App from './App'
import store from './store'
import router from './router'
import config from './config'
import consts from './consts'
import '@/icons' // icon
// import '@/permission' // permission control

import md5 from 'js-md5' // md5

import * as filters from './filters' // global filters

import echarts from 'echarts'

Vue.prototype.$echarts = echarts


/**
 * If you don't want to use mock-server
 * you want to use MockJs for mock api
 * you can execute: mockXHR()
 *
 * Currently MockJs will be used in the production environment,
 * please remove it before going online! ! !
 */
// TODO important
// import { mockXHR } from '../mock'
// if (process.env.NODE_ENV === 'development') {
//   mockXHR()
// }

Vue.use(ElementUI, {
  i18n: (key, value) => i18n.t(key, value)
})

Vue.prototype.$const = consts

Vue.prototype.$config = config

Vue.config.productionTip = false

Vue.prototype.$md5 = md5

// register global utility filters
Object.keys(filters).forEach(key => {
  Vue.filter(key, filters[key])
})

new Vue({
  el: '#app',
  router,
  i18n,
  store,
  render: h => h(App)
})
