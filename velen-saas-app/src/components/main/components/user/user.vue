<template>
  <div class="user-avator-dropdown">
    <span style="font-size: 12px">{{ account }}</span>
    <el-tag v-if="role != null" size="mini" style="color:white">{{ role }}</el-tag>
    <el-dropdown @command="handleCommand">
      <i class="el-icon-arrow-down el-icon--right" />
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item command="menu-my_app">我的应用</el-dropdown-item>
        <el-dropdown-item command="menu-edit_password">修改密码</el-dropdown-item>
        <el-dropdown-item command="menu-logout">退出登录</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>
<script>
import './user.scss'

export default {
  name: 'User',
  props: {
    role: {
      type: String
    },
    account: {
      type: String
    },
    data() {

    }
  },
  methods: {
    handleCommand(name) {

    }
  }
}
</script>
