<template>
  <div class="app-container">
    <custom-header :show-name="resolveName()">
      <span slot="title">
        <el-dropdown @command="handleSelect">
          <i class="el-icon-arrow-down el-icon--right" />
          <el-dropdown-menu>
            <el-dropdown-item command="">全部书签</el-dropdown-item>
            <el-dropdown-item v-for="type of types" :command="type.command">{{ type.cname }}</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </span>
      <span slot="title">
        <el-link disabled>共{{ total }}个</el-link>
      </span>
      <span slot="tools" style="margin-right: 15px">
        <el-input
          v-model="query.search"
          placeholder="请输入内容"
          suffix-icon="el-icon-search"
          @change="searchChange"
        />
      </span>
      <span slot="tools">
        <el-popover
          placement="bottom"
          width="500"
          trigger="click"
        >
          <div style="display: flex; height: 200px">
            <div v-for="type of types" style="height: 50px;width: 200px;padding: 16px; margin-bottom: 10px;" @click="jump(type)">
              <i :class="type.icon" />
              <span slot="title">{{ type.cname }}</span>
            </div>
          </div>
          <el-button
            slot="reference"
            icon="el-icon-plus"
            type="success"
          >
            新建书签
          </el-button>
        </el-popover>

      </span>
    </custom-header>

    <el-table
      v-loading="listLoading"
      :data="list"
      border
      fit
      highlight-current-row
      style="width: 100%;"
    >
      <el-table-column label="书签名称" align="center" :show-overflow-tooltip="true">
        <template slot-scope="{row}">
          <span>{{ row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column label="统计时段" align="center" :show-overflow-tooltip="true">
        <template slot-scope="{row}">
          <span>{{ row.data | formatAnalyze }}</span>
        </template>
      </el-table-column>
      <el-table-column label="类型" align="center" :show-overflow-tooltip="true">
        <template slot-scope="{row}">
          <span>{{ row.type | formatType }}</span>
        </template>
      </el-table-column>
      <el-table-column label="已添加到下列概览中" align="center" :show-overflow-tooltip="true">
        <template slot-scope="{row}">
          <span>{{ row.dashboards | formatDashboard }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" :show-overflow-tooltip="true">
        <template slot-scope="{row}">
          <span>{{ row.createTime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="#" align="center" class-name="small-padding fixed-width">
        <template slot-scope="{row}">
          <i class="el-icon-edit" />
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="query.page"
      :limit.sync="query.limit"
      @pagination="fetchBookmarks"
    />
  </div>
</template>

<script>
import { create, update, all } from '@/api/bookmarks'
import CustomHeader from '_c/custom-header'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination
import waves from '@/directive/waves' // waves directive
import consts from '@/consts'
export default {
  components: {
    CustomHeader,
    Pagination
  },
  directives: {
    waves
  },
  filters: {
    formatAnalyze(data) {
      const jsonData = JSON.parse(data)
      if (jsonData.toDate) {
        return `${jsonData.fromDate}至${jsonData.toDate}`
      } else {
        return '实时统计'
      }
    },
    formatDashboard(dashboards) {
      if (dashboards.length < 1) {
        return '尚未加入任何概览'
      }
    },
    formatType(type) {
      const event_type = consts.event_type[type]
      if (event_type) {
        return event_type.cname
      } else {
        return '未知类型'
      }
    }
  },
  data() {
    return {
      listLoading: false,
      list: [],
      total: 0,
      query: {
        page: 1,
        limit: 10,
        type: '',
        search: ''
      },
      types: [{
        cname: '元数据分析',
        command: 'meta-event',
        name: 'menu-analyze-meta-event',
        desc: '元数据详细查询分析',
        icon: 'el-icon-hel'
      }]
    }
  },
  async created() {
    await this.fetchDashboard()
    this.fetchBookmarks()
  },
  methods: {
    fetchDashboard() {
      return new Promise((resolve, reject) => {
        resolve()
      })
    },
    fetchBookmarks() {
      this.listLoading = true
      all(this.query).then(resp => {
        this.total = resp.total
        this.list = resp.items
        setTimeout(() => {
          this.listLoading = false
        }, 1000)
      })
    },
    handleSelect(command) {
      this.query.type = command
      this.fetchBookmarks()
    },
    resolveName() {
      let cname = '全部书签'
      for (const index in this.types) {
        if (Object.is(this.types[index].command, this.query.type)) {
          cname = this.types[index].cname
          break
        }
      }
      return cname
    },
    searchChange() {
      this.fetchBookmarks()
    },
    jump(type) {
      console.log(type)
      this.$router.push({ 'name': type.name, 'query': this.$route.query })
    }
  }
}
</script>
