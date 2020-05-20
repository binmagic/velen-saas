<template>
  <div class="role_ability">
    <el-row v-for="(o, index) in Math.max(1, roleList.length / 3)" :key="o" :offset="index > 0 ? 2 : 0">
      <el-col :span="8" v-for="role in roleList">
        <el-card class="box-card">
          <div class="text item">
            {{role.name}}
          </div>
          <div>
            角色成员 {{1}}名
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { getRoleList } from '@/api/role'
export default {
  name: 'RoleAbility',
  props: {

  },
  data() {
    return {
      roleList: []
    }
  },
  created() {
    this.getRoleList()
  },
  methods: {
    getRoleList() {
      getRoleList().then(resp => {
        this.roleList = resp
      })
    }
  }
}
</script>
<style type="scss">
  .box-card{

  }
</style>
