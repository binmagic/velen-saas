<template>
  <div class="side-navigation-container">
    <el-menu :default-active="activeIndex" @select="handleSelect">
      <el-menu-item index="menu-metadata-rule">入库校验规则设置</el-menu-item>
      <el-menu-item index="menu-metadata-event">元事件</el-menu-item>
      <el-menu-item index="menu-metadata-event-prop">事件属性</el-menu-item>
      <el-menu-item index="menu-metadata-user-prop" disabled>用户属性</el-menu-item>
      <el-menu-item index="menu-metadata-dimension" disabled>维度表</el-menu-item>
      <el-menu-item index="menu-metadata-virtual-prop" disabled>虚拟属性</el-menu-item>
      <el-menu-item index="menu-metadata-virtual-event" disabled>虚拟事件</el-menu-item>
      <el-menu-item index="menu-metadata-session" disabled>会话管理</el-menu-item>
    </el-menu>
  </div>
</template>

<script>
export default {
  name: 'SideMenuMetadata',
  props: {
    activeIndex: {
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
