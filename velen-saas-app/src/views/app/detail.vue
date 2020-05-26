<template>
  <div class="padding-app-container">
    <el-tabs value="app_info">
      <el-tab-pane :label="$t('app.label.app_info')" name="app_info">
        <app-info  />
      </el-tab-pane>
      <el-tab-pane :label="$t('app.label.member_info')" name="member_info">
        <member-info />
      </el-tab-pane>
      <el-tab-pane :label="$t('app.label.role_ability')" name="role_ability">
        <role-ability />
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import MemberInfo from './components/member'
import AppInfo from './components/info'
import RoleAbility from './components/role_ability'
import { mapActions } from 'vuex'

export default {
  components: {
    MemberInfo,
    AppInfo,
    RoleAbility
  },
  data() {
    return {
    }
  },
  methods: {
    ...mapActions(['handleSelectApp'])
  },
  async created() {
    await this.handleSelectApp(this.$route.query.app)
  }
}
</script>
