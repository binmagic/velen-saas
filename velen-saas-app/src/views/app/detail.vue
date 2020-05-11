<template>
  <div class="app-container">
    <el-tabs @tab-click="handleSwitch">
      <el-tab-pane :label="$t('app.label.app_info')" name="app_info">
        <el-form ref="dataForm" :rules="rules" :v-model="temp">
          <el-form-item :label="$t('app.label.name')">
            <el-input v-model="temp.name" />
          </el-form-item>
          <el-form-item :label="$t('app.label.desc')">
            <el-input v-model="temp.desc" />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="updateData()">
            {{ $t('app.button.confirm') }}
          </el-button>
        </div>
      </el-tab-pane>
      <el-tab-pane :label="$t('app.label.member_info')" name="member_info">
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
              <span>{{ row.name }}</span>
            </template>
          </el-table-column>
          <el-table-column :label="$t('app.label.role')" align="center" :show-overflow-tooltip="true">
            <template slot-scope="{row}">
              <el-select v-model="row.role" placeholder="请选择">
                <el-option
                  v-for="item in role"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="#" align="center" class-name="small-padding fixed-width">
            <template slot-scope="{row}">
              <el-button v-if="row.status!='deleted'" size="mini" type="danger" @click="handleDelete(row)">
                {{ $t('app.button.del') }}
              </el-button>
            </template>
          </el-table-column>
        </el-table>

        <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
          <el-form ref="dataForm" :rules="rules" :model="temp">
            <el-form-item :label="$t('app.label.account')">
              <el-input v-model="temp.account" />
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
                <span>{{ row.name }}</span>
              </template>
            </el-table-column>
            <el-table-column :label="$t('app.label.role')" align="center" :show-overflow-tooltip="true">
              <template slot-scope="{row}">
                <el-select v-model="row.role" placeholder="请选择">
                  <el-option
                    v-for="item in role"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id"
                  />
                </el-select>
              </template>
            </el-table-column>
            <el-table-column label="#" align="center" class-name="small-padding fixed-width">
              <template slot-scope="{row}">
                <el-button v-if="row.status!='deleted'" size="mini" type="danger" @click="handleDelete(row)">
                  {{ $t('app.button.del') }}
                </el-button>
              </template>
            </el-table-column>
          </el-table>
          <div slot="footer" class="dialog-footer">
            <el-button v-if="inviteList.length > 0" type="primary" @click="dialogStatus==='create'?createData():updateData()">
              {{ $t('app.button.invite_confirm') }}
            </el-button>
          </div>
        </el-dialog>

      </el-tab-pane>
      <el-tab-pane :label="$t('app.label.role_ability')" name="role_ability" />
    </el-tabs>

  </div>
</template>

<script>
import { getAppList, addApp, delApp, updateApp, getAppInfo, getMemberInfo } from '@/api/app'
import waves from '@/directive/waves' // waves directive

export default {
  directives: { waves },
  data() {
    return {
      list: [],
      listLoading: true,
      inviteList: [],
      memberList: [],
      memberListLoading: true,
      temp: {
        id: '',
        name: '',
        desc: ''
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        view: '查看'
      },
      role: [
        {
          id: '1',
          name: '管理员'
        }, {
          id: '2',
          name: '数据分析师'
        }
      ],
      rules: {
        type: [{ required: true, message: 'type is required', trigger: 'change' }],
        timestamp: [{ type: 'date', required: true, message: 'timestamp is required', trigger: 'change' }],
        title: [{ required: true, message: 'title is required', trigger: 'blur' }]
      }
    }
  },
  created() {
    this.fetchAppInfo()
  },
  methods: {
    fetchAppInfo() {
      getAppInfo(this.$route.params.appId).then(resp => {
        this.temp = resp
      })
    },
    fetchMember() {
      this.memberListLoading = true
      getMemberInfo(this.$route.params.appId).then(resp => {
        this.memberList = resp
        this.memberListLoading = false
      })
    },
    handleSwitch(tab, event) {
      if(tab.name == 'member_info'){
        this.fetchMember()
      }
    },
    fetchData() {
      this.listLoading = true
      this.list = []
      getAppList().then(response => {
        this.list = response
        setTimeout(() => {
          this.listLoading = false
        }, 0.5 * 1000)
      })
    },
    resetTemp() {
      this.temp = {
        id: '',
        name: '',
        desc: ''
      }
    },
    handleCreate() {
      this.resetTemp()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    handleUpdate(row) {
      this.temp = Object.assign({}, row) // copy obj
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    handleDelete(row) {
      delApp(row.id).then(response => {
        this.$notify({
          message: '删除成功',
          type: 'success',
          duration: 2000
        })
        this.fetchData()
      })
    },
    createData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          console.log(this.temp)
          addApp(this.temp).then(() => {
            this.dialogFormVisible = false
            this.$notify({
              message: '创建成功',
              type: 'success',
              duration: 2000
            })
            this.fetchData()
          })
        }
      })
    },
    updateData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          const tempData = Object.assign({}, this.temp)
          delete tempData.state
          updateApp(tempData).then(() => {
            this.dialogFormVisible = false
            this.$notify({
              message: '修改成功',
              type: 'success',
              duration: 2000
            })
            this.fetchData()
          })
        }
      })
    }
  }

}
</script>
