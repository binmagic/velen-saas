<template>
  <div class="app-manager-wrapper">
    <el-dropdown>
      <el-button type="primary">
        {{activeApp}}<i class="el-icon-arrow-down el-icon--right"></i>
      </el-button>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item v-for="app of appList">{{app.name}}</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
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
    handleSelect(name) {
      this.$emit('on-select', name)
    }
  }
}
</script>
