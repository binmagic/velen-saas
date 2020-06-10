<template>
  <div class="app-container">
    <custom-header :show-name="$route.meta.title">
      <div slot="tools">
        <div v-if="componentFlag.enableDatePick" style="margin-right: 40px; align-self: center;display: flex">
          <el-date-picker
            v-model="query.dateRange"
            type="daterange"
            align="right"
            unlink-panels
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            :picker-options="componentOption.datePickerOptions"
          />
          <i class="el-icon-close" style="align-self: center;margin-left: 20px" @click="componentFlag.enableDatePick = false" />
        </div>
        <el-button v-else icon="el-icon-date" style="margin-right: 20px" @click="componentFlag.enableDatePick = true" />
        <el-button-group>
          <el-button icon="el-icon-refresh-left" />
          <el-button icon="el-icon-share" />
          <el-button icon="el-icon-more" />
        </el-button-group>
        <el-dropdown trigger="click" @command="handleClickPlus" @visible-change="handleClickPlusVisible">
          <span><el-button icon="el-icon-plus" style="margin-left: 20px" type="success" /></span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item icon="el-icon-arrow-right" command="createFromBookmarks">从书签添加</el-dropdown-item>
            <el-dropdown-item command="createComponent">新建组件</el-dropdown-item>
            <el-dropdown-item>新建描述</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </custom-header>

    <el-popover
      ref="createBookmarksPopover"
      v-model="componentFlag.enableCreateFromBookmarks"
      placement="bottom-start"
      width="200"
      class="createBookmarks"
      trigger="manual"
    >
      <el-menu @select="handleSelectBookmarks">
        <el-menu-item-group v-for="(value, key) in pre_data.bookmarks">
          <template slot="title">{{ key }}</template>
          <el-menu-item v-for="v in value" :index="v.id">{{ v.name }}</el-menu-item>
        </el-menu-item-group>
      </el-menu>
    </el-popover>

    <!--    <div style="margin-top: 25vh">-->
    <!--      <div>-->
    <!--        <el-button type="success" style="display:block;margin:0 auto" @click="componentFlag.enableCreateComponent = true">新建组件</el-button>-->
    <!--      </div>-->
    <!--    </div>-->

    <div class="component">
      <component :is="item.type" v-for="item in pre_data.dashboard.items" />
    </div>

    <el-dialog
      title="新建组件"
      :visible.sync="componentFlag.enableCreateComponent"
      width="30%"
      center
    >
      <el-row>
        <el-col :span="8">
          <el-card shadow="hover" @click.native="jump('menu-analyze-meta-event')">
            元数据分析
          </el-card>
        </el-col>
      </el-row>
    </el-dialog>
    <router-view />
  </div>
</template>

<script>
import CustomHeader from '_c/custom-header'
import { mapActions } from 'vuex'
import MetaEventAnalytics from './bookmarks/meta-event-analytics'
import {getDashboard} from '@/api/dashboard'
export default {
  name: 'Dashboard',
  components: {
    CustomHeader,
    MetaEventAnalytics
  },
  data() {
    return {
      query: {
        id: '',
        dateRange: []
      },
      componentOption: {
        datePickerOptions: {}
      },
      componentFlag: {
        enableDatePick: false,
        enableCreateComponent: false,
        enableCreateFromBookmarks: false
      },
      pre_data: {
        bookmarks: {},
        bookmarksIdMapping: {},
        dashboards: [],
        dashboard: {}
      },
      save_data: {

      }
    }
  },
  watch: {
    $route() {
      this.query.id = this.$route.query.id
      this.fetchBookmarks()
      this.fetchDashboardInfo()
    }
  },
  async mounted() {
    await this.fetchDashboards()
    this.query.id = this.$route.query.id
    if (!this.query.id && this.pre_data.dashboards.length > 0) {
      this.query.id = this.pre_data.dashboards[0]
    }
  },
  created() {
    this.fetchBookmarks()
    this.fetchDashboardInfo()
  },
  methods: {
    ...mapActions(['getGroupBookmarks']),
    fetchDashboardInfo(){
      getDashboard(this.query.id).then(resp => {
        this.pre_data.dashboard = resp
      })
    },
    fetchDashboards() {
      return new Promise((resolve, reject) => {
        resolve()
      })
    },
    fetchBookmarks() {
      this.getGroupBookmarks().then(resp => {
        this.pre_data.bookmarks = resp
        for (const key in this.pre_data.bookmarks) {
          for (const index in this.pre_data.bookmarks[key]) {
            const _data = this.pre_data.bookmarks[key][index]
            this.pre_data.bookmarksIdMapping[_data.id] = _data
          }
        }
      })
    },

    handleClickPlus(command) {
      if (Object.is(command, 'createComponent')) {
        this.componentFlag.enableCreateComponent = true
      } else if (Object.is(command, 'createFromBookmarks')) {
        this.componentFlag.enableCreateFromBookmarks = true
        console.log()
      }
    },
    handleClickPlusVisible(flag) {
      if (flag) {
        this.componentFlag.enableCreateFromBookmarks = false
      }
    },
    jump(name) {
      this.$router.push({ 'name': name, query: this.$route.query })
    },
    handleSelectBookmarks(id) {
      const _data = this.pre_data.bookmarksIdMapping[id]
      this.componentFlag.enableCreateFromBookmarks = false
    }
  }
}
</script>

<style type="scss">
  .custom-tools{
    .el-button {
      height: 40px;
      width: 50px;
      align-self: center;
    }

    i{
      align-self: center;
    }

    .el-button-group{
      align-self: center;
      width: 150px;
    }

    .el-button-group .el-button{
      padding: 0 10px;
      width: 50px;
    }
  }
  .createBookmarks .el-popover{
      right: 20px;
  }
</style>
