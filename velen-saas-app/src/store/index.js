import Vue from 'vue'
import Vuex from 'vuex'
import getters from './getters'
import app from './modules/app'
import settings from './modules/settings'
import user from './modules/user'
import router from './modules/router'
import role from './modules/role'
import bookmarks from "./modules/bookmarks";

Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    app,
    settings,
    user,
    router,
    role,
    bookmarks
  },
  getters
})

export default store
