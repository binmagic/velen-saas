<template>
  <div class="user-manager-dropdown">
    <span style="font-size: 12px">用户管理</span>
    <el-dropdown @command="handleCommand">
      <i class="el-icon-arrow-down el-icon--right" />
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item command="menu-user-list">用户列表</el-dropdown-item>
        <el-dropdown-item command="menu-access-list">权限列表</el-dropdown-item>
        <el-dropdown-item command="menu-resource-list">资源列表</el-dropdown-item>
        <el-dropdown-item command="menu-role-list">角色管理</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>
<script>
export default {
  name: 'UserManager',
  method: {
    handleCommand(command) {
      console.log("nivoke")
      this.$emit('on-select', command)
    }
  }
}
</script>
