<template>
  <div class="app-manager-wrapper">
    <span class="activeApp">{{ activeApp | resolveAppName }}</span>
    <el-dropdown @command="handleCommand">
      <i class="el-icon-arrow-down el-icon--right" />
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item v-for="app of appList" :command="app.id">{{ app.name }}</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
import './app-manager.scss'
import { mapActions } from 'vuex'

let app0

export default {
  name: 'AppManager',
  filters: {
    resolveAppName(value) {
      for (const index in app0.appList) {
        if (Object.is(app0.appList[index].id, value)) {
          return app0.appList[index].name
        }
      }
    }
  },
  props: {
    activeApp: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      appList: []
    }
  },
  beforeCreate() {
    app0 = this
  },
  mounted() {
    console.log('app-manager mounted')
    this.getAppList().then(resp => {
      this.appList = resp
    })
  },
  created() {
  },
  methods: {
    ...mapActions(['getAppList']),
    handleCommand(name) {
      this.$emit('on-select', { name: 'menu-app-detail', query: { 'app': name }})
    }
  }
}
</script>
