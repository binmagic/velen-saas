<template>
  <el-container class="main-container">
    <el-header class="main-header">
      <header-bar>
        <user account="187****0825" ></user>
        <user-manager @on-select="turnToPage"></user-manager>
        <logo @on-select="turnToPage"></logo>
      </header-bar>
    </el-header>
    <el-header class="nav-header" v-if="!$route.meta.hideHeader">
      <el-row>
        <el-col :span="4">
          <app-manager class="app-manager" :app-list="appList" :active-app="$route.params.id"></app-manager>
        </el-col>
        <el-col :span="20">
          <header-navigation @on-select="turnToPage"></header-navigation>
        </el-col>
      </el-row>
    </el-header>
    <el-container class="main-content-container">
      <el-aside class="main-aside" v-if="!$route.meta.hideAside">
        <side-menu @on-select="turnToPage"/>
      </el-aside>
      <el-main class="main-content">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>
<script>
import { mapGetters } from 'vuex'
import AppManager from './components/app-manager'
import Logo from './components/logo'
import User from './components/user'
import HeaderBar from './components/header-bar'
import HeaderNavigation from './components/header-navigation'
import UserManager from './components/user-manager'
import './main.scss'
import SideMenu from "./components/side-menu/side-menu";
export default {
  name: 'Main',
  components: {
    SideMenu,
    Logo,
    AppManager,
    User,
    HeaderBar,
    HeaderNavigation,
    UserManager
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
      let { name, params, query } = {}
      if (typeof route === 'string') name = route
      else {
        name = route.name
        params = route.params
        query = route.query
      }
      if (name.indexOf('isTurnByHref_') > -1) {
        window.open(name.split('_')[1])
        return
      }
      this.$router.push({
        name,
        params,
        query
      })
    }
  }
}
</script>
