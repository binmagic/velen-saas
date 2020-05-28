<template>

  <div class="app-container">

    <custom-header show-name="事件属性">
      <div slot="tools" style="align-items: center;margin-right: 20px">
        <el-checkbox>只显示公共属性</el-checkbox>
      </div>
      <div slot="tools">
        <el-input
          v-model="query.search"
          placeholder="请输入内容"
          suffix-icon="el-icon-search"
        />
      </div>
    </custom-header>

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
          <span>{{ row.createTime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="上报数据" align="center" :show-overflow-tooltip="true">
        <template slot-scope="{row}">
          <span>{{ row.owner }}</span>
        </template>
      </el-table-column>
      <el-table-column label="显示状态" align="center" :show-overflow-tooltip="true">
        <template slot-scope="{row}">
          <span>{{ row.owner }}</span>
        </template>
      </el-table-column>
      <el-table-column label="是否接收" align="center" :show-overflow-tooltip="true">
        <template slot-scope="{row}">
          <span>{{ row.owner }}</span>
        </template>
      </el-table-column>
      <el-table-column label="过去30天入库" align="center" :show-overflow-tooltip="true">
        <template slot-scope="{row}">
          <span>{{ row.owner }}</span>
        </template>
      </el-table-column>
      <el-table-column label="应埋点平台" align="center" :show-overflow-tooltip="true">
        <template slot-scope="{row}">
          <span>{{ row.owner }}</span>
        </template>
      </el-table-column>
      <el-table-column label="标签" align="center" :show-overflow-tooltip="true">
        <template slot-scope="{row}">
          <span>{{ row.owner }}</span>
        </template>
      </el-table-column>
      <el-table-column label="#" align="center" class-name="small-padding fixed-width">
        <template slot-scope="{row}">
          <i class="el-icon-edit" />
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
import CustomHeader from '_c/custom-header'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination
import waves from '@/directive/waves' // waves directive
export default {
  components: {
    CustomHeader,
    Pagination
  },
  directives: {
    waves
  },
  data() {
    return {
      total: 0,
      list: [],
      listLoading: false,
      query: {}
    }
  },
  created(){
    this.fetchData()
  },
  methods: {
    fetchData(){

    }
  }
}
</script>
