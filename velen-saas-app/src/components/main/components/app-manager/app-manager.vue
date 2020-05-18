<template>
  <div class="app-manager-wrapper">
    <span class="activeApp">{{activeApp}}</span>
    <el-dropdown @command="handleCommand">
      <i class="el-icon-arrow-down el-icon--right"></i>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item v-for="app of appList" :command="app.id">{{app.name}}</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
import './app-manager.scss'
export default {
  name: 'AppManager',
  props: {
    appList: {
      type: Array,
      default() {
        return []
      }
    },
    activeApp: {
      type: String,
      default: ''
    }
  },
  methods: {
    handleCommand(name){
      this.$emit('on-select', name)
    }
  }
}
</script>
