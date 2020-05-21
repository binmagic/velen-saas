import path from "path";
<template>
  <div class="header-navigation-container">
    <el-menu :default-active="activeIndex"  mode="horizontal" @select="handleSelect">
      <el-menu-item index="menu-dashboard">概览</el-menu-item>
      <el-submenu index="menu-analyze">
        <template slot="title">分析</template>
        <el-menu-item index="menu-analyze-event">事件分析</el-menu-item>
        <el-menu-item index="menu-analyze-funnel">漏斗分析</el-menu-item>
        <el-menu-item index="menu-analyze-keep">留存分析</el-menu-item>
        <el-menu-item index="menu-analyze-meta-event">元事件分析</el-menu-item>
      </el-submenu>
      <el-menu-item index="3">用户分群</el-menu-item>
      <el-menu-item index="4"><a href="https://www.ele.me" target="_blank">用户标签</a></el-menu-item>
      <el-menu-item index="4"><a href="https://www.ele.me" target="_blank">书签</a></el-menu-item>
      <el-submenu index="2">
        <template slot="title">更多</template>
        <el-menu-item index="2-1">元数据</el-menu-item>
        <el-menu-item index="2-2">场景库</el-menu-item>
        <el-menu-item index="2-3">基本设置</el-menu-item>
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
