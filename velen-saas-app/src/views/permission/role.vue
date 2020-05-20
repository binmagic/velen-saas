<template>
  <div class="app-container">
    <div class="filter-container">
      <el-button
        class="filter-item"
        style="margin-bottom: 20px;"
        type="primary"
        icon="el-icon-edit"
        @click="handleCreate"
      >
        添加
      </el-button>
      <el-button
        class="filter-item"
        plain
        style="margin-bottom: 20px;"
        type="primary"
        icon="el-icon-edit"
        @click="handleConfig('function')"
      >
        {{$t('role.button.access_list')}}
      </el-button>
      <el-button
        class="filter-item"
        plain
        style="margin-bottom: 20px;"
        type="primary"
        icon="el-icon-edit"
        @click="handleConfig('resource')"
      >
        {{$t('role.button.resource_list')}}
      </el-button>
    </div>
    <el-table
      ref="roleTable"
      v-loading="listLoading"
      :data="list"
      border
      fit
      highlight-current-row
      style="width: 100%;"
    >
      <el-table-column
        type="selection"
        width="55"
      />
      <el-table-column :label="$t('role.label.name')" align="center" :show-overflow-tooltip="true">
        <template slot-scope="{row}">
          <span>{{ row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('role.label.cname')" align="center" :show-overflow-tooltip="true">
        <template slot-scope="{row}">
          <span>{{ row.cname }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('role.label.state')" align="center" :show-overflow-tooltip="true">
        <template slot-scope="{row}">
          <el-switch
            v-model="row.state"
            active-color="#13ce66"
            inactive-color="#ff4949"
          />
        </template>
      </el-table-column>
      <el-table-column label="#" align="center" class-name="small-padding fixed-width">
        <template slot-scope="{row}">
          <el-button size="mini" type="primary" @click="handleDelete(row)">
            {{ $t('role.button.edit') }}
          </el-button>
          <el-button size="mini" type="danger" @click="handleDelete(row)">
            {{ $t('role.button.delete') }}
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--权限一览-->
    <el-dialog :title="configTextMap[configDialogStatus]" :visible.sync="configDialogFormVisible">
      <div class="filter-container">
        <el-button
          class="filter-item"
          style="margin-bottom: 20px;"
          type="primary"
          icon="el-icon-edit"
          @click="saveConfig"
        >
          保存
        </el-button>
      </div>
      <el-table
        v-loading="configListLoading"
        :data="configList"
        border
        fit
        highlight-current-row
        style="width: 100%;"
      >
        <el-table-column :label="$t('role_config.label.name')" align="center" :show-overflow-tooltip="true">
          <template slot-scope="{row}">
            <span>{{ row.name }}</span>
          </template>
        </el-table-column>
        <el-table-column
          v-for="role of switchRole"
          :label="role.name"
          align="center"
        >
          <template slot-scope="{row}">
            <el-checkbox v-model="row[role.id]" />
          </template>
        </el-table-column>
      </el-table>

      <pagination
        v-show="configListTotal>0"
        :total="configListTotal"
        :page.sync="configListQuery.page"
        :limit.sync="configListQuery.limit"
        @pagination="fetchList"
      />
    </el-dialog>

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form ref="dataForm" :rules="rules" :model="temp">
        <el-form-item :label="$t('role.label.name')">
          <el-input v-model="temp.name" />
        </el-form-item>
        <el-form-item :label="$t('role.label.cname')">
          <el-input v-model="temp.cname" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">
          {{ $t('role.button.cancel') }}
        </el-button>
        <el-button type="primary" @click="dialogStatus==='create'?createData():updateData()">
          {{ $t('role.button.confirm') }}
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { addRole, delRole, getRoleList, updateRole, getRoleFunction, getRoleResource, saveRoleFunction, saveRoleResource } from '@/api/role'
import { getFunctionList } from '@/api/function'
import { getResourceList } from '@/api/resource'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination
import waves from '@/directive/waves' // waves directive

export default {
  components: { Pagination },
  directives: { waves },
  data() {
    return {
      list: [],
      listLoading: true,
      temp: {
        id: '',
        name: '',
        state: 1
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        view: '查看'
      },
      rules: {
        type: [{ required: true, message: 'type is required', trigger: 'change' }],
        timestamp: [{ type: 'date', required: true, message: 'timestamp is required', trigger: 'change' }],
        title: [{ required: true, message: 'title is required', trigger: 'blur' }]
      },
      configListTotal: 0,
      configListQuery: {
        page: 1,
        limit: 10
      },
      switchRole: [],
      configList: [],
      configListLoading: true,
      configDialogFormVisible: false,
      configDialogStatus: '',
      configTextMap: {
        function: this.$t('role.label.config')
      }
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    fetchData() {
      this.listLoading = true
      this.list = []

      getRoleList().then(response => {
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
        state: 1
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
      delRole(row.id).then(response => {
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
          addRole(this.temp).then(() => {
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
          updateRole(tempData).then(() => {
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
    },
    fetchFunctionList() {
      this.configListLoading = true
      getFunctionList(this.configListQuery).then(resp => {
        this.configListTotal = resp.total
        this.configList = resp.items
        if (this.configListTotal > 0) {
          const ids = []
          const map = {}
          for (const index in resp.items) {
            ids.push(resp.items[index].id)
            map[resp.items[index].id] = { id: resp.items[index].id, name: resp.items[index].name }
          }

          getRoleFunction(ids.join(',')).then(resp1 => {
            for (const index in resp1) {
              const data = resp1[index]
              map[data.functionId][data.roleId] = true
            }
            this.configList = Object.values(map)
            this.configListLoading = false
          })
        } else {
          this.configListLoading = false
        }
      })
    },
    fetchResourceList() {
      this.configListLoading = true
      getResourceList(this.configListQuery).then(resp => {
        this.configListTotal = resp.total
        this.configList = resp.items
        if (this.configListTotal > 0) {
          const ids = []
          const map = {}
          for (const index in resp.items) {
            ids.push(resp.items[index].id)
            map[resp.items[index].id] = { id: resp.items[index].id, name: resp.items[index].name }
          }

          getRoleResource(ids.join(',')).then(resp1 => {
            for (const index in resp1) {
              const data = resp1[index]
              map[data.resourceId][data.roleId] = true
            }
            this.configList = Object.values(map)
            this.configListLoading = false
          })
        } else {
          this.configListLoading = false
        }
      })
    },
    fetchList(type) {
      type = type || this.configDialogStatus
      if (Object.is(type, 'function')) {
        this.fetchFunctionList()
      } else if (Object.is(type, 'resource')) {
        this.fetchResourceList()
      }
    },
    handleConfig(type) {
      if (this.$refs.roleTable.selection.length < 1) {
        this.$notify({
          message: 'please select role',
          type: 'warn',
          duration: 2000
        })
        return
      }
      this.switchRole = Object.assign({}, this.$refs.roleTable.selection)
      this.fetchList(type)
      this.configDialogStatus = type
      this.configDialogFormVisible = true
    },
    saveConfig() {
      console.log(this.configList)
      const data = {}
      for (const index in this.configList) {
        const config = this.configList[index]
        data[config.id] = {}
        for (const index1 in this.switchRole) {
          data[config.id][this.switchRole[index1].id] = config[this.switchRole[index1].id] || false
        }
      }
      if (this.configDialogStatus == 'function') {
        this.configListLoading = true
        saveRoleFunction(data).then(resp => {
          this.$notify({
            message: 'success',
            type: 'success',
            duration: 2000
          })
          this.configListLoading = false
        })
      } else if (this.configDialogStatus == 'resource') {
        this.configListLoading = true
        saveRoleResource(data).then(resp => {
          this.$notify({
            message: 'success',
            type: 'success',
            duration: 2000
          })
          this.configListLoading = false
        })
      }
    }
  }

}
</script>
