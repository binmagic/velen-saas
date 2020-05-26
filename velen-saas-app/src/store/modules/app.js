import Cookies from 'js-cookie'
import { getAppList } from '@/api/app'
const state = {
  appId: '',
  appList: []
}
const APP_KEY = 'app'

const mutations = {
  SET_APP_LIST: (state, appList) => {
    state.appList = appList
  },
  SET_APP_ID: (state, appId) => {
    state.appId = appId
  }
}

const actions = {
  getAppList({ commit }) {
    console.log('getAppList')
    return new Promise((resolve, reject) => {
      getAppList().then(data => {
        commit('SET_APP_LIST', data)
        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  },
  handleSelectApp({ commit }, appId) {
    return new Promise((resolve, reject) => {
      commit('SET_APP_ID', appId)
      Cookies.set(APP_KEY, appId)
      resolve()
    })
  }
}

export default {
  state,
  mutations,
  actions
}
