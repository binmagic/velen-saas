<template>

  <div class="app-container">

    <custom-header :show-name="$route.meta.title">
      <div slot="tools">
        <el-dropdown trigger="click" @command="handleClickPlus">
          <span><el-button icon="el-icon-plus" style="margin-left: 20px" type="success" /></span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="manuallyCreate">手动创建</el-dropdown-item>
            <el-dropdown-item command="batchUpload">批量上传</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </custom-header>
    <div>
      <el-form :inline="true" size="mini">
        <el-form-item label="上报数据">
          <el-input />
        </el-form-item>
        <el-form-item label="显示状态">
          <el-select @change="fetchData" v-model="visible">
            <el-option label="全部" value=""/>
            <el-option label="显示" :value="true"/>
            <el-option label="隐藏" :value="false"/>
          </el-select>
        </el-form-item>
        <el-form-item>
          <div slot="tools" style="align-items: center;margin-right: 20px">
            <el-checkbox>只显示公共属性</el-checkbox>
          </div>
          <el-input
            v-model="query.search"
            placeholder="请输入内容"
            suffix-icon="el-icon-search"
          />
        </el-form-item>
      </el-form>
    </div>

    <event-create :enable.sync="componentFlag.enableDrawer" @on-create-event="handleCreatedEvent" />
    <event-update :event="event" :enable.sync="componentFlag.updateDrawer" @on-update-event="handleUpdateEvent" />

    <el-table
      v-loading="listLoading"
      :data="list"
      border
      fit
      highlight-current-row
      style="width: 100%;"
    >
      <el-table-column label="事件名" align="center" :show-overflow-tooltip="true">
        <template slot-scope="{row}">
          <span>{{ row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column label="事件显示名" align="center" :show-overflow-tooltip="true">
        <template slot-scope="{row}">
          <span>{{ row.showName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="上报数据" align="center" :show-overflow-tooltip="true">
        <template slot-scope="{row}">
          <span>{{ row.upload }}</span>
        </template>
      </el-table-column>
      <el-table-column label="是否接收" align="center" :show-overflow-tooltip="true">
        <template slot-scope="{row}">
          <span>{{ row.accept }}</span>
        </template>
      </el-table-column>
      <el-table-column label="过去30天入库" align="center" :show-overflow-tooltip="true">
        <template slot-scope="{row}">
          <span>{{ row.lastMonthSave }}</span>
        </template>
      </el-table-column>
      <el-table-column label="应埋点平台" align="center" :show-overflow-tooltip="true">
        <template slot-scope="{row}">
          <el-tag v-for="platform of row.platform" style="margin-left: 20px"><span >{{platform}}</span></el-tag>
        </template>
      </el-table-column>
      <el-table-column label="标签" align="center" :show-overflow-tooltip="true">
        <template slot-scope="{row}">
          <span><el-tag v-for="tag of row.tag">{{tag}}</el-tag></span>
        </template>
      </el-table-column>
      <el-table-column label="上次修改时间" align="center" :show-overflow-tooltip="true">
        <template slot-scope="{row}">
          <span>{{ row.updateTime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建人" align="center" :show-overflow-tooltip="true">
        <template slot-scope="{row}">
          <span>{{ row.createUser }}</span>
        </template>
      </el-table-column>
      <el-table-column label="触发时机" align="center" :show-overflow-tooltip="true">
        <template slot-scope="{row}">
          <span>{{ row.timing }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" :show-overflow-tooltip="true">
        <template slot-scope="{row}">
          <span>{{ row.remark }}</span>
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="#" align="center" class-name="small-padding fixed-width">
        <template slot-scope="{row}">
          <i class="el-icon-edit" @click="showEventUpdate(row)"/>
          <i class="el-icon-more" />
        </template>
      </el-table-column>

    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="query.page"
      :limit.sync="query.limit"
      @pagination="fetchData"
    />

  </div>

</template>
<script>
import { getMetaEvent,all } from '@/api/metadata'
import CustomHeader from '_c/custom-header'
import Pagination from '@/components/Pagination'
import waves from '@/directive/waves'
import EventCreate from './create'
import EventUpdate from './update'

export default {
  components: {
    CustomHeader,
    Pagination,
    EventCreate,
    EventUpdate
  },
  directives: {
    waves
  },
  data() {
    return {
      total: 0,
      list: [],
      listLoading: false,
      visible:'',
      query: {
        page: 1,
        limit: 10
      },
      componentFlag: {
        enableDrawer: false,
        updateDrawer:false,
      },
      event:{
        platform: [],
        timing: '',
        example: '',
        type: '',
        name: '',
        showName: '',
        tag: [],
        propIds: []
      }
    }
  },
  created() {
    this.fetchData()
    this.fetchAll()
  },
  methods: {
    fetchData() {
      this.listLoading = true
      getMetaEvent(this.query).then(resp => {
        this.total = resp.total
        this.list = resp.items
        this.listLoading = false
      }).catch(err =>{
        this.$notify({
          type:'error',
          message:err.message,
          title:'失败'
        })
      })
    },
    fetchAll(){
      all().then(resp =>{
        this.fetchData()
      }).catch(err => {
        this.$notify({
          type: 'error',
          message: err.message,
          title: '失败'
        })
      })
    },
    handleClickPlus(command) {
      switch (command) {
        case 'manuallyCreate':
          this.componentFlag.enableDrawer = true
          break
      }
    },
    handleCreatedEvent(){
      this.componentFlag.enableDrawer = false
      this.fetchData()
    },
    handleUpdateEvent(){
      this.componentFlag.updateDrawer = false
      this.fetchData()
    },
    showEventCreate(row){
      this.componentFlag.enableDrawer = true
    },
    showEventUpdate(row){
      this.componentFlag.updateDrawer = true
      this.event = row
      console.log(this.event)
    }
  }
}
</script>
