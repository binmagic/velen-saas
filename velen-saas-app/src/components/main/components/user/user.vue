<template>
  <div class="user-avator-dropdown">
    <span style="font-size: 12px">{{ account }}</span>
    <el-tag size="mini" style="color:white">{{ role }}</el-tag>
    <el-dropdown>
      <i class="el-icon-arrow-down el-icon--right" />
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item>修改密码</el-dropdown-item>
        <el-dropdown-item>退出登录</el-dropdown-item>
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
      type: String,
      default: '分析师'
    },
    account: {
      type: String,
      default: '187****0825'
    },
    data() {

    }
  }
}
</script>
