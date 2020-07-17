<template>
  <div class='side-navigation-container'>
     <el-menu :default-active="activeIndex" @select="handleSelect">
      <el-menu-item index="menu-analyze-event" disabled>事件分析</el-menu-item>
      <el-menu-item index="menu-analyze-funnel" disabled>漏斗分析</el-menu-item>
      <el-menu-item index="menu-analyze-meta-event">元事件分析</el-menu-item>
    </el-menu>
  </div>
</template>

<script>
export default {
name: "SideMenuAnalyze",
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
