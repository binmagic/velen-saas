<template>
  <el-container class="main-container">
    <el-header class="main-header">
      <header-bar>
        <user account="187****0825" />
        <user-manager @on-select="turnToPage" />
        <logo @on-select="turnToPage" />
      </header-bar>
    </el-header>
    <el-header v-if="!$route.meta.hideHeader" class="nav-header">
      <app-manager class="app-manager" :active-app="$route.query.app" @on-select="turnToPage" />
      <header-navigation @on-select="turnToPage" />
    </el-header>
    <el-container class="main-content-container">
      <el-aside v-if="!$route.meta.hideAside" class="main-aside">
        <side-menu @on-select="turnToPage" />
      </el-aside>
      <el-main class="main-content">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>
<script>
import './main.scss'
import { mapGetters } from 'vuex'
import AppManager from './components/app-manager'
import Logo from './components/logo'
import User from './components/user'
import HeaderBar from './components/header-bar'
import HeaderNavigation from './components/header-navigation'
import UserManager from './components/user-manager'
import SideMenu from './components/side-menu/side-menu'
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
    ])
  },
  methods: {
    turnToPage(route) {
      let { name, params, query } = {}
      if (typeof route === 'string') {
        name = route
      } else {
        name = route.name
        params = route.params
        query = route.query
      }
      if (name.indexOf('isTurnByHref_') > -1) {
        window.open(name.split('_')[1])
        return
      }
      if (!query) {
        query = this.$route.query
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
