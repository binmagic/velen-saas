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
          <el-table-column label="平台" prop="platform"/>
          <el-table-column label="process" prop="process"/>
          <el-table-column label="业务名" prop="businessName"/>
          <el-table-column label="DSL" prop="dsl"/>
          <el-table-column label="配置" :show-overflow-tooltip="true">
            <template slot-scope="{row}">
              <span v-for="(r,key) in row.properties">{{ key }} : {{ r }};</span>
            </template>
          </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="{row}">
              <i class="el-icon-edit"/>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-card>
    <basic-create :dialogVisible.sync="dialogVisible" :title="title" :type="type" @close-dialog="handleClose"
                  @on-create-dispatch="handleCreate"/>

    <el-button type="primary" @click="fastDispatch">快速dispatch</el-button>
  </div>
</template>

<script>
  import CustomHeader from '_c/custom-header'
  import {getPage,createDispatch} from '@/api/dispatch'
  import BasicCreate from './basic-create/basic-create'

  export default {
    name: "index",
    components: {
      CustomHeader,
      BasicCreate
    },
    data() {
      return {
        formData: {},
        dialogVisible: false,
        title: '',
        type: '',
        dialogData: null,
        total: 0,
        query: {
          page: 1,
          limit: 10,
        },
        tableData: [{
          platform: '1',
          process: '1',
          businessName: '1',
          dsl: '1',
          properties: {id: 'appId', name: 'name'}
        }]
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
      handleDialog(row) {
        this.dialogVisible = true
        this.title = '创建'
      },
      handleClose() {
        this.dialogVisible = false
      },
      handleCreate() {
        this.dialogVisible = false
        this.findData()
      },
      fastDispatch(){
        const dispatch = {
          platform: 'FLINK',
          platformType: 2,
          process: 'TASK',
          processType: 2,
          businessName: 'test',
          dsl: '',
          properties: {id:'app.id',name:'app.owner'}
        }
        createDispatch(dispatch).then(resp =>{
          this.$notify({
            title: '成功',
            message: '快速添加',
            type: 'success'
          });
        }).catch(msg =>{
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
