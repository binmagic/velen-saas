import { all } from '@/api/bookmarks'

const state = {

}

const mutations = {

}

const actions = {
  getGroupBookmarks({ commit }) {
    return new Promise((resolve, reject) => {
      console.log('store.getGroupBookmarks')
      all().then(data => {
        data = data.items
        const _data = {}
        for (const index in data) {
          if (!_data[data[index].type]) {
            _data[data[index].type] = []
          }
          _data[data[index].type].push(data[index])
        }
        resolve(_data)
      }).catch(error => {
        reject(error)
      })
    })
  }
}

export default {
  namespaced: false,
  state,
  mutations,
  actions
}

