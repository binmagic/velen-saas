import { getRoleList } from '@/api/role'

const state = {
  roleList: []
}

const mutations = {
  SET_ROLE_LIST: (state, roleList) => {
    state.roleList = roleList
  }
}

const actions = {
  getRoleList({ commit }) {
    return new Promise((resolve, reject) => {
      getRoleList().then(data => {
        commit('SET_ROLE_LIST', data)
        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

