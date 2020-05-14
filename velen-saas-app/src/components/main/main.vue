<template>
  <el-container class="main-container">
    <el-header class="main-header">
      <header-bar>
        <user account="187****0825" role="数据分析师"></user>
      </header-bar>
    </el-header>
    <el-header class="nav-header" v-if="$route.path != '/apps'">
      <el-row>
        <el-col :span="4">
          <app-manager class="app-manager" :app-list="appList" active-app="1212121"></app-manager>
        </el-col>
        <el-col :span="20">
          <side-menu ref="sideMenu" accordion :active-name="$route.name" :menu-list="menuList" @on-select="turnToPage">
          </side-menu>
        </el-col>
      </el-row>
    </el-header>
    <el-main class="main-content">
        <h3>{{$route.name}}</h3>
        <router-view />
    </el-main>
  </el-container>
</template>
<script>
import { mapGetters } from 'vuex'
import SideMenu from './components/side-menu'
import AppManager from './components/app-manager'
import User from './components/user'
import HeaderBar from './components/header-bar'

import './main.scss'
export default {
  name: 'Main',
  components: {
    SideMenu,
    AppManager,
    User,
    HeaderBar
  },
  data() {
    return {
      isFullscreen: false
    }
  },
  computed: {
    ...mapGetters([
      'errorCount'
    ]),
    appList() {
      return this.$store.state.app.appList
    },
    menuList() {
      return this.$store.getters.menuList
    }
  },
  methods: {
    turnToPage(route) {
      console.log(route)
      let { path, params, query } = {}
      if (typeof route === 'string') path = route
      else {
        path = route.name
        params = route.params
        query = route.query
      }
      if (name.indexOf('isTurnByHref_') > -1) {
        window.open(name.split('_')[1])
        return
      }
      this.$router.push({
        path,
        params,
        query
      })
    }
  }
}
</script>
