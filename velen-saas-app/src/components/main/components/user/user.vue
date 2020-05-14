<template>
  <div class="user-avator-dropdown">
    <div>
      {{account}}<el-tag size="mini" closable>{{role}}</el-tag>
      <i class="el-icon-arrow-down el-icon--right"></i>
    </div>

    <el-dropdown-menu slot="dropdown">
      <el-dropdown-item>修改密码</el-dropdown-item>
      <el-dropdown-item>退出登录</el-dropdown-item>
    </el-dropdown-menu>
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
    data(){

    }
  }
}
</script>
