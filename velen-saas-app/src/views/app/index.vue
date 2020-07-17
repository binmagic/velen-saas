<template>
  <div class="padding-app-container">
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
          <span>{{ row.owner }}</span>
        </template>
      </el-table-column>
      <el-table-column label="#" align="center" class-name="small-padding fixed-width">
        <template slot-scope="{row}">
          <el-button type="primary" size="mini" @click="goDetail(row)">
            {{ $t('app.button.setting') }}
          </el-button>
          <el-button v-if="row.status!='deleted'" :loading="loading" size="mini" type="danger" @click="handleDelete(row)">
            {{ $t('app.button.del') }}
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form ref="dataForm" :rules="rules" :model="temp" label-position="top">
        <el-form-item :label="$t('app.label.name')">
          <el-input v-model="temp.name" />
        </el-form-item>
        <el-form-item :label="$t('app.label.desc')">
          <el-input v-model="temp.desc" />
        </el-form-item>
        <el-form-item label="模板">
          <el-row :gutter="24">
            <el-col :span="18">
              <el-select style="width: 100%" :disabled="temp.isTemplate" v-model="temp.template" :label="$t('app.label.template')">
                <el-option
                  v-for="item in appTemplateList"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
                </el-option>
              </el-select>
            </el-col>
            <el-col :span="6">
              <el-checkbox v-model="temp.isTemplate" label="设置为模板"/>
            </el-col>
          </el-row>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogStatus==='create'?createData():updateData()">
          {{ $t('app.button.confirm') }}
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { addApp, delApp, updateApp, fetchTemplates } from '@/api/app'
import waves from '@/directive/waves' // waves directive
import { mapActions } from 'vuex'

export default {
  directives: { waves },
  data() {
    return {
      list: [],
      listLoading: true,
      temp: {
        id: '',
        name: '',
        desc: '',
        template: '',
        isTemplate: false
      },
      appTemplateList: [],
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
      createLoading: false,
      loading: false
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    ...mapActions(['getAppList']),

    fetchAppTemplates() {
      fetchTemplates().then(resp => {
        this.appTemplateList = resp
      }).catch(err => {

      })
    },
    fetchData() {
      this.listLoading = true
      this.list = []
      this.getAppList().then(response => {
        this.list = response
        setTimeout(() => {
          this.listLoading = false
        }, 0.5 * 1000)
      }).catch(() => {
        this.listLoading = false
      })
    },
    goDetail(row) {
      this.$router.push({ name: `menu-app-detail`, query: { 'app': row.id }})
    },
    resetTemp() {
      this.temp = {
        id: '',
        name: '',
        desc: '',
        isTemplate: false
      }
    },
    handleCreate() {
      this.resetTemp()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.fetchAppTemplates()
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
      this.loading = true
      delApp(row.id).then(response => {
        this.$notify({
          message: '删除成功',
          type: 'success',
          duration: 2000
        })
        this.loading = false
        this.fetchData()
      }).catch(err => {
        this.$notify({
          message: err,
          type: 'error',
          duration: 2000
        })
        this.loading = false
      })
    },
    createData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.createLoading = true
          let data = Object.assign({}, this.temp)
          if(this.temp.isTemplate) {
            delete data.template
          }
          delete data.isTemplate
          addApp(data).then(() => {
            this.createLoading = false
            this.dialogFormVisible = false
            this.$notify({
              message: '创建成功',
              type: 'success',
              duration: 2000
            })
            this.fetchData()
          }).catch(err => {
            this.$notify({
              message: err,
              type: 'error',
              duration: 2000
            })
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
