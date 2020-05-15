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
        {{ $t('app.button.add') }}
      </el-button>
    </div>
    <el-table
      v-loading="listLoading"
      :data="list"
      border
      fit
      highlight-current-row
      style="width: 100%;"
    >
      <el-table-column :label="$t('app.label.name')" align="center" :show-overflow-tooltip="true">
        <template slot-scope="{row}">
          <span>{{ row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('app.label.createTime')" align="center" :show-overflow-tooltip="true">
        <template slot-scope="{row}">
          <span>{{ row.createTime }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('app.label.owner')" align="center" :show-overflow-tooltip="true">
        <template slot-scope="{row}">
          <span>{{ row.ownerName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="#" align="center" class-name="small-padding fixed-width">
        <template slot-scope="{row}">
          <el-button type="primary" size="mini" @click="goDetail(row)">
            {{ $t('app.button.setting') }}
          </el-button>
          <el-button v-if="row.status!='deleted'" size="mini" type="danger" @click="handleDelete(row)">
            {{ $t('app.button.del') }}
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form ref="dataForm" :rules="rules" :model="temp">
        <el-form-item :label="$t('app.label.name')">
          <el-input v-model="temp.name" />
        </el-form-item>
        <el-form-item :label="$t('app.label.desc')">
          <el-input v-model="temp.desc" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">
          {{ $t('app.button.cancel') }}
        </el-button>
        <el-button type="primary" @click="dialogStatus==='create'?createData():updateData()">
          {{ $t('app.button.confirm') }}
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getAppList, addApp, delApp, updateApp } from '@/api/app'
import waves from '@/directive/waves' // waves directive

export default {
  directives: { waves },
  data() {
    return {
      list: [],
      listLoading: true,
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
      rules: {
        type: [{ required: true, message: 'type is required', trigger: 'change' }],
        timestamp: [{ type: 'date', required: true, message: 'timestamp is required', trigger: 'change' }],
        title: [{ required: true, message: 'title is required', trigger: 'blur' }]
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
      this.$store.dispatch('app/getAppList').then(response => {
        this.list = response
        setTimeout(() => {
          this.listLoading = false
        }, 0.5 * 1000)
      }).catch(() => {
        this.listLoading = false
      })
    },
    goDetail(row) {
      this.$router.push({ path: `/apps/${row.id}` })
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
