<template>
  <div class="side-navigation-container">
    <el-menu :default-active="activeIndex" @select="handleSelect">
      <el-menu-item index="menu-more-setting">基础设置</el-menu-item>
    </el-menu>
  </div>
</template>

<script>
  export default {
    name: "side-menu-more",
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

<style scoped>

</style>
