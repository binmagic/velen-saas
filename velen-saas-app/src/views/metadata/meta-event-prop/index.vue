<template>

  <div class="app-container">

    <custom-header show-name="事件属性">

      <div slot="tools">
        <el-button
          slot="reference"
          icon="el-icon-plus"
          type="success"
          @click="handleCreate"
        >
          新建事件属性
        </el-button>
      </div>
    </custom-header>
    <div>
      <el-form :inline="true" size="mini">
        <el-form-item label="上报数据">
          <el-input/>
        </el-form-item>
        <el-form-item label="显示状态">
          <el-select @change="fetchData" v-model="visible">
            <el-option label="全部" :value="null"/>
            <el-option label="显示" :value="false"/>
            <el-option label="隐藏" :value="true"/>
          </el-select>
        </el-form-item>
        <el-form-item label="数据类型">
          <el-select @change="fetchData" v-model="type" style="width: 100%">
            <el-option label="全部" :value="null"/>
            <el-option v-for="type of $const.metadata.data_type" :label="type" :value="type"/>
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

    <event-prop-create :enable.sync="componentFlag.enableDrawer" @on-create-event-prop="handleCreatedEventProp"/>
    <event-prop-update :event-prop="eventProp" :enable.sync="componentFlag.updateDrawer"
                       @on-update-event-prop="handleUpdateEventProp"/>
    <el-table
      v-loading="listLoading"
      :data="list"
      border
      fit
      highlight-current-row
      style="width: 100%;"
    >
      <el-table-column label="属性名" align="center" :show-overflow-tooltip="true">
        <template slot-scope="{row}">
          <span>{{ row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column label="属性显示名" align="center" :show-overflow-tooltip="true">
        <template slot-scope="{row}">
          <span>{{ row.showName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="数据类型" align="center" :show-overflow-tooltip="true">
        <template slot-scope="{row}">
          <span>{{ row.type }}</span>
        </template>
      </el-table-column>
      <el-table-column label="上报数据" align="center" :show-overflow-tooltip="true">
        <template slot-scope="{row}">
          <span>{{ row.owner }}</span>
        </template>
      </el-table-column>
      <el-table-column label="预置" align="center" :show-overflow-tooltip="true">
        <template slot-scope="{row}">
          <span>{{ row.owner }}</span>
        </template>
      </el-table-column>
      <el-table-column label="字典" align="center" :show-overflow-tooltip="true">
        <template slot-scope="{row}">
          <span>{{ row.owner }}</span>
        </template>
      </el-table-column>
      <el-table-column label="显示状态" align="center" :show-overflow-tooltip="true">
        <template slot-scope="{row}">
          <span>{{ row.owner }}</span>
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="#" align="center" class-name="small-padding fixed-width">
        <template slot-scope="{row}">
          <i class="el-icon-edit" @click="handleUpdate(row)"/>
          <el-popconfirm
            confirmButtonText='确定'
            cancelButtonText='取消'
            icon="el-icon-info"
            iconColor="red"
            title="删除事件属性？"
            @onConfirm="deleteEventProp(row)"
          >
            <i class="el-icon-delete" slot="reference"/>
          </el-popconfirm>

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
  import {getMetaEventProp, deleteMetaEventProp} from '@/api/metadata'
  import CustomHeader from '_c/custom-header'
  import Pagination from '@/components/Pagination'
  import waves from '@/directive/waves'
  import EventPropCreate from './create'
  import EventPropUpdate from './update'

  export default {
    components: {
      CustomHeader,
      Pagination,
      EventPropCreate,
      EventPropUpdate
    },
    directives: {
      waves
    },
    data() {
      return {
        total: 0,
        list: [],
        listLoading: false,
        type: null,
        visible: null,
        query: {
          page: 1,
          limit: 10,
          search:''
        },
        componentFlag: {
          enableDrawer: false,
          updateDrawer: false,
        },
        eventProp: {
          platform: [],
          timing: '',
          example: '',
          unit: '',
          public: '',
          type: '',
          name: '',
          showName: ''
        }
      }
    },
    created() {
      this.fetchData()
    },
    methods: {
      fetchData() {
        this.listLoading = true
        var params = {}
        params.type = this.type
        params.isVisible = this.visible
        this.query.params = params

        getMetaEventProp(this.query).then(resp => {
          this.total = resp.total
          this.list = resp.items
          this.listLoading = false
        })
      },
      handleCreate() {
        this.componentFlag.enableDrawer = true
      },
      handleCreatedEventProp() {
        this.fetchData()
        this.componentFlag.enableDrawer = false
      },
      handleUpdateEventProp() {
        this.fetchData()
        this.componentFlag.updateDrawer = false
      },
      handleUpdate(row) {
        this.componentFlag.updateDrawer = true
        this.eventProp = row
      },
      deleteEventProp(row) {
        deleteMetaEventProp(row.id).then(resp => {
          this.fetchData()
          this.$notify({
            message: "成功",
            type: 'success',
            duration: 2000
          })
        }).catch(reason => {
          this.$notify({
            message: reason,
            type: 'error',
            duration: 2000
          })
        })
      }
    }
  }
</script>
