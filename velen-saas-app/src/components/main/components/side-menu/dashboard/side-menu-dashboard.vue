<template>
  <div class="menu-dashboard">
    <el-input
      size="small"
      v-model="filterGroup"
      placeholder="概览名称"
      prefix-icon="el-icon-search"
      class="menu-dashboard-input"
    />
    <el-tabs v-model="tabName">
      <el-tab-pane label="公共概览" name="first">
        <el-collapse v-for="(commonGroup,index) in commonGroups">
          <el-collapse-item>
            <template slot="title">
              <div class="menu-dashboard-span">
                <span>{{commonGroup.name}}</span>
                <span style="float: right;">
              <el-popover
                placement="bottom"
                trigger="hover"
                width="74">
              <div>
                <div class="dashboard-aside-popover">
                  <i class="el-icon-edit"/>
                  <span>重命名</span>
                </div>
                <div class="dashboard-aside-popover">
                  <i class="el-icon-delete"/>
                  <span>删除</span>
                </div>
              </div>
              <i @click.stop slot="reference"
                 class="el-icon-more menu-dashboard-icon"/>
            </el-popover>
              <span class="dashboard-aside-num" v-if="commonGroup.list.length>0">{{ commonGroup.list.length }}</span>
            </span>
              </div>
            </template>
            <ul class="dashboard-aside-ul">
              <li v-for="commonDashboard in commonGroup.list" class="dashboard-aside-li">
                <span>
                  {{ commonDashboard.name }}
                </span>
              </li>
            </ul>
          </el-collapse-item>
        </el-collapse>
      </el-tab-pane>
      <el-tab-pane label="我的概览" name="second">
        <el-collapse v-for="(group,index) in groups">
          <el-collapse-item>
            <template slot="title">
              <div class="menu-dashboard-span" @mouseleave="leave" @mouseenter="enter(index)">
                <span>{{group.name}}</span>
                <span style="float: right;">
              <el-popover
                placement="bottom"
                trigger="hover"
                width="74">
              <div>
                <div class="dashboard-aside-popover">
                  <i class="el-icon-edit"/>
                  <span>重命名</span>
                </div>
                <div class="dashboard-aside-popover">
                  <i class="el-icon-delete"/>
                  <span>删除</span>
                </div>
              </div>
              <i v-show="hoverIndex==index && hover" @click.stop slot="reference"
                 class="el-icon-more menu-dashboard-icon"/>
            </el-popover>
              <span class="dashboard-aside-num" v-if="group.list.length>0">{{ group.list.length }}</span>
            </span>
              </div>
            </template>
            <ul class="dashboard-aside-ul">
              <li v-for="dashboard in group.list" class="dashboard-aside-li" @click="clickDashboard(dashboard)">
                <span>
                  {{ dashboard.name }}
                </span>
              </li>
            </ul>
          </el-collapse-item>
        </el-collapse>
      </el-tab-pane>
    </el-tabs>
    <div class="el-btn">
      <el-button-group style="border-radius: 20px;overflow: hidden;">
        <el-tooltip content="新建我的概览/分组">
          <el-button type="primary" icon="el-icon-plus" @click="modalSwitch"/>
        </el-tooltip>
        <el-tooltip content="管理我的概览排序">
          <el-button type="primary" icon="el-icon-d-caret" @click="modal.sortShow=true"/>
        </el-tooltip>
        <el-tooltip content="分享我的概览">
          <el-button type="primary" icon="el-icon-share"/>
        </el-tooltip>
      </el-button-group>
    </div>
    <add-dashboard-or-group
      v-if="modal.show"
      :groups="groups"
      :dashboards="dashboards"
      :show="modal.show"
      @modal-switch="modalSwitch"
    />
    <sort :sort-groups="groups"
          :sort-show-visible="modal.sortShow"
          @update-group="findGroup"
          @close-sort="handleCloseSort"
    />
  </div>
</template>
<script>

  import {getGroup, getCommonGroup} from '@/api/group'
  import AddDashboardOrGroup from './components/add-dashboard-or-group'
  import Sort from './components/sort'
  export default {
    name: 'SideMenuDashboard',
    components: {
      AddDashboardOrGroup,
      Sort
    },
    props: {},
    watch: {
      sortInput(val) {
        this.$refs.tree.filter(val)
      }
    },
    data() {
      return {
        tabName: 'second',
        filterGroup: '',
        modal: {
          show: false,
          sortShow: false,
        },
        groups: [],
        treeGroup: [],
        dashboards: [],
        hover: false,
        hoverIndex: -1,
        commonGroups: [],
        commonDashboards: [],
        sortInput: '',
        treeProps: {
          children: 'list',
          label: 'name'
        }
      }
    },

    created() {
      this.findGroup()
      this.findCommonGroup()
    },
    computed: {},
    directives: {
      focus: {
        inserted: function (el) {
          el.querySelector('input').focus();
          // 通过querySelector()方法获取input元素
        }
      }
    },
    methods: {
      findGroup() {
        getGroup().then(response => {
          this.groups = response
          this.treeGroup = JSON.parse(JSON.stringify(this.groups))
          for (let key in this.groups) {
            this.dashboards = this.dashboards.concat(this.groups[key].list)
          }
        })
      },
      findCommonGroup() {
        getCommonGroup().then(response => {
          this.commonGroups = response
          for (let key in this.commonGroups) {
            this.commonDashboards = this.commonDashboards.concat(this.commonGroups[key].list)
          }
        })
      },
      modalSwitch() {
        this.modal.show = !this.modal.show
      },
      enter(index) {
        this.hover = true
        this.hoverIndex = index
      },
      leave() {
        this.hover = false
        this.hoverIndex = -1
      },
      clickDashboard(dashboard) {
        this.$route.meta.title = dashboard.name
      },
      handleCloseSort(){
        this.modal.sortShow=false
      }
    }
  }
</script>

<style scoped>
  @import './side-menu-dashboard.scss';
</style>
