<template>
  <div class="member-info">
    <div class="filter-container">
      <el-button
        class="filter-item"
        style="margin-bottom: 20px;"
        type="primary"
        icon="el-icon-edit"
        @click="handleCreate"
      >
        {{ $t('app.button.invite') }}
      </el-button>
    </div>
    <el-table
      v-loading="memberListLoading"
      :data="memberList"
      border
      fit
      highlight-current-row
      style="width: 100%;"
    >
      <el-table-column :label="$t('app.label.member')" align="center" :show-overflow-tooltip="true">
        <template slot-scope="{row}">
          <span>{{ row.account }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('app.label.role')" align="center" :show-overflow-tooltip="true">
        <template slot-scope="{row}">
          <el-select v-model="row.role" placeholder="请选择">
            <el-option
              v-for="item in role"
              :key="item.name"
              :label="item.name"
              :value="item.name"
            />
          </el-select>
        </template>
      </el-table-column>
      <el-table-column label="#" align="center" class-name="small-padding fixed-width">
        <template slot-scope="{row}">
          <el-button v-if="row.status!='deleted'" size="mini" type="danger" @click="handleInviteDelete(row)">
            {{ $t('app.button.del') }}
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog :title="$t('app.member.dialog.title')" :visible.sync="dialogFormVisible">
      <el-form ref="dataForm" :inline="true" :rules="rules" :model="inviteTemp">
        <el-form-item :label="$t('app.label.account')">
          <el-input v-model="inviteTemp.account" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-share" @click="handleInvite" />
        </el-form-item>
      </el-form>
      <el-table
        v-if="inviteList.length > 0"
        :data="inviteList"
        border
        fit
        highlight-current-row
        style="width: 100%;"
      >
        <el-table-column :label="$t('app.label.member')" align="center" :show-overflow-tooltip="true">
          <template slot-scope="{row}">
            <span>{{ row.account }}</span>
          </template>
        </el-table-column>
        <el-table-column :label="$t('app.label.role')" align="center" :show-overflow-tooltip="true">
          <template slot-scope="{row}">
            <el-select v-model="row.role" placeholder="请选择">
              <el-option
                v-for="item in role"
                :key="item.name"
                :label="item.name"
                :value="item.name"
              />
            </el-select>
          </template>
        </el-table-column>
        <el-table-column label="#" align="center" class-name="small-padding fixed-width">
          <template slot-scope="{row}">
            <el-button v-if="row.status!='deleted'" size="mini" type="danger" @click="handleTempInviteDelete(row)">
              {{ $t('app.member.button.del') }}
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button
          v-if="inviteList.length > 0"
          type="primary"
          @click="handleConfirmInvite"
        >
          {{ $t('app.button.invite_confirm') }}
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getMemberInfo, addMember, deleteMember } from '@/api/app'
export default {
  name: 'MemberInfo',
  props: {

  },
  data() {
    return {
      dialogFormVisible: false,
      inviteList: [],
      inviteTemp: {},
      memberList: [],
      memberListLoading: false,
      role: [],
      rules: {
        type: [{ required: true, message: 'type is required', trigger: 'change' }],
        timestamp: [{ type: 'date', required: true, message: 'timestamp is required', trigger: 'change' }],
        title: [{ required: true, message: 'title is required', trigger: 'blur' }]
      }
    }
  },
  async created() {
    await this.fetchRoleList()
    await this.fetchMemberList()
  },
  methods: {
    fetchRoleList() {
      return new Promise((resolve, reject) => {
        this.$store.dispatch('role/getRoleList').then(resp => {
          this.role = resp
          resolve()
        }).catch(() => {
          reject()
        })
      })
    },
    fetchMemberList() {
      this.memberListLoading = true
      getMemberInfo().then(resp => {
        this.memberList = resp
        this.memberListLoading = false
      })
    },
    handleCreate() {
      this.dialogFormVisible = true
    },
    handleInvite() {
      this.inviteList.push({ account: this.inviteTemp.account })
    },
    handleTempInviteDelete(row) {
      for (const index in this.inviteList) {
        if (this.inviteList[index].account == row.account) {
          delete this.inviteList[index]
          return
        }
      }
    },
    handleInviteDelete(row){
      deleteMember(row.id).then(resp => {
        this.$notify({
          message: '删除成功',
          type: 'success',
          duration: 2000
        })
        this.fetchMemberList()
      })
    },
    handleConfirmInvite(){
      addMember(this.inviteList).then(resp => {
        this.dialogFormVisible = false
        this.$notify({
          message: '添加成功',
          type: 'success',
          duration: 2000
        })
        this.fetchMemberList()
      })
    },
  }
}

</script>
