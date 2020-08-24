<template>
  <div>
    <custom-header :show-name="$route.meta.title"/>
    <el-card>
      <div slot="header">
        <span>调度规则设置</span>
        <el-button style="float: right" size="small" type="primary" @click="handleDialog()">添加</el-button>
      </div>
      <div class="more-card-body">
        <el-table :data="tableData">
          <el-table-column label="业务名" prop="businessName"/>
          <el-table-column label="平台" prop="platform"/>
          <el-table-column label="process" prop="process"/>
          <el-table-column label="DSL" prop="dsl"/>
          <el-table-column label="启动参数" :show-overflow-tooltip="true">
            <template v-if="row.appParameters!=null" slot-scope="{row}">
              <span v-for="(r,key) in row.appParameters">{{ r + key == row.appParameters.length -1 ? '' : ',' }}</span>
            </template>
          </el-table-column>
          <el-table-column label="环境变量" :show-overflow-tooltip="true">
            <template v-if="row.environmentVariables!=null" slot-scope="{row}">
              <span v-for="(r,key) in row.environmentVariables">{{ key }} : {{ r }};</span>
            </template>
          </el-table-column>
          <el-table-column label="平台属性" :show-overflow-tooltip="true">
            <template v-if="row.properties!=null" slot-scope="{row}">
              <span v-for="(r,key) in row.properties">{{ key }} : {{ r }};</span>
            </template>
          </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="{row}">
              <i class="el-icon-edit" style="cursor:pointer;" @click="handleDialogUpdate(row)"/>
              <el-button style="margin-left: 10px;" size="small" type="primary" @click="fastDispatch(row)">dispatch
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="query.page"
        :limit.sync="query.limit"
        @pagination="findData"
      />
    </el-card>
    <basic-create :dialogVisible.sync="dialogVisible" @close-dialog="handleClose"
                  @on-create-dispatch="handleCreate"/>
    <basic-update :dialog-visible="dialogVisibleUpdate" :data="data" @close-dialog="handleCloseUpdate"
                  @on-update-dispatch="handleUpdate"/>

  </div>
</template>

<script>
  import CustomHeader from '_c/custom-header'
  import {getPage, fastDispatch} from '@/api/dispatch'
  import BasicCreate from './basic-create/basic-create'
  import BasicUpdate from './basic-update/basic-update'
  import Pagination from '@/components/Pagination'

  export default {
    name: "index",
    components: {
      CustomHeader,
      BasicCreate,
      BasicUpdate,
      Pagination
    },
    data() {
      return {
        formData: {},
        dialogVisible: false,
        dialogVisibleUpdate: false,
        data: {},
        dialogData: null,
        total: 0,
        query: {
          page: 1,
          limit: 10,
        },
        tableData: []
      }
    },
    created() {
      this.findData()
    },
    methods: {
      findData() {
        getPage(this.query).then(resp => {
          this.total = resp.total
          this.tableData = resp.items
        })
      },
      handleDialog() {
        this.dialogVisible = true
      },
      handleDialogUpdate(row) {
        this.dialogVisibleUpdate = true
        this.data = row
      },
      handleClose() {
        this.dialogVisible = false
        this.findData()
      },
      handleCloseUpdate() {
        this.dialogVisibleUpdate = false
        this.findData()
      },
      handleCreate() {
        //this.dialogVisible = false
        this.findData()
      },
      handleUpdate() {
        this.findData()
      },
      fastDispatch(row) {
        fastDispatch(row).then(resp => {
          this.$notify({
            title: '成功',
            message: '快速调用',
            type: 'success'
          });
        }).catch(msg => {
          this.$notify({
            title: '失败',
            message: msg.message,
            type: 'error'
          });
        })
      }
    }
  }
</script>

<style scoped>
  .more-card-body {

  }
</style>
