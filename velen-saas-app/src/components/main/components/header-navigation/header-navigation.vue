import path from "path";
<template>
  <div class="header-navigation-container">
    <el-menu :default-active="activeIndex"  mode="horizontal" @select="handleSelect">
      <el-menu-item index="menu-dashboard">概览</el-menu-item>
      <el-submenu index="menu-analyze">
        <template slot="title">分析</template>
        <el-menu-item index="menu-analyze-event" disabled>事件分析</el-menu-item>
        <el-menu-item index="menu-analyze-funnel" disabled>漏斗分析</el-menu-item>
        <el-menu-item index="menu-analyze-keep" disabled>留存分析</el-menu-item>
        <el-menu-item index="menu-analyze-meta-event">元事件分析</el-menu-item>
      </el-submenu>
      <el-menu-item index="menu-user-group" disabled>用户分群</el-menu-item>
      <el-menu-item index="menu-bookmarks">书签</el-menu-item>
      <el-menu-item index="menu-metadata-rule">元数据</el-menu-item>
      <el-menu-item index="menu-scene-lib" disabled>场景库</el-menu-item>
      <el-submenu index="menu-more">
        <template slot="title">更多</template>
        <el-menu-item index="menu-more-setting">基本设置</el-menu-item>
      </el-submenu>
    </el-menu>
  </div>
</template>

<script>
import './header-navigation.scss'

export default {
  name: 'HeaderNavigation',
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
