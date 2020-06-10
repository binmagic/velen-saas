<template>
  <el-card class="box-card small_card">
    <div slot="header" class="clearfix">
      <slot name="title" />
      <slot name="tools" />
      <el-dropdown style="float:right">
        <span class="el-dropdown-link">
          <i class="el-icon-more-outline el-icon--right"></i>
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item>小</el-dropdown-item>
          <el-dropdown-item>中</el-dropdown-item>
          <el-dropdown-item>大</el-dropdown-item>
          <el-dropdown-item divided>设置</el-dropdown-item>
          <el-dropdown-item>删除</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
    <slot name="content" />
  </el-card>
</template>
<script>
export default {
  name: 'Card'
}
</script>
<style type="scss">

  .small_card{

  }

  .middle_card{

  }

</style>
