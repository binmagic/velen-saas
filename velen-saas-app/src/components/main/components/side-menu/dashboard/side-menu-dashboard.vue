<template>
  <div class="menu-dashboard">
    <el-row>
      <el-col :span="21">
        <el-input
          size="small"
          v-model="filterName"
          placeholder="概览名称"
          prefix-icon="el-icon-search"
          class="menu-dashboard-input"
          @input="filterGroup"
        />
      </el-col>
    </el-row>
    <el-tabs v-if="!inputChange" v-model="tabName" style="height: 100%;">
      <el-tab-pane label="公共概览" name="first">
        <el-collapse>
          <el-collapse-item v-for="(commonGroup,index) in commonGroups">
            <template slot="title">
              <div class="menu-dashboard-span">
                <span>{{commonGroup.name}}</span>
                <span style="float: right;">
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
        <div style="height: 100%;overflow-y: auto;">
          <el-collapse>
            <el-collapse-item v-for="(group,index) in groups">
              <template slot="title">
                <div class="menu-dashboard-span" @mouseleave="leave" @mouseenter="enter(index)">
                  <span>{{group.name}}</span>
                  <span style="float: right;">
              <el-popover
                placement="bottom"
                trigger="hover"
                width="74">
              <template>
                <div class="dashboard-aside-popover">
                  <i class="el-icon-edit"/>
                  <span>重命名</span>
                </div>
                <div class="dashboard-aside-popover">
                  <i class="el-icon-delete"/>
                  <span>删除</span>
                </div>
              </template>
              <i v-if="group.name!='分享给我的概览'" v-show="hoverIndex==index && hover" @click.stop slot="reference"
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
        </div>
        <div class="el-btn">
          <el-button-group style="border-radius: 20px;overflow: hidden;">
            <el-tooltip content="新建我的概览/分组">
              <el-button type="primary" icon="el-icon-plus" @click="modalSwitch"/>
            </el-tooltip>
            <el-tooltip content="管理我的概览排序">
              <el-button type="primary" icon="el-icon-d-caret" @click="modal.sortShow=true"/>
            </el-tooltip>
            <el-tooltip content="分享我的概览">
              <el-button type="primary" icon="el-icon-share" @click="clickHandle"/>
            </el-tooltip>
          </el-button-group>
        </div>
      </el-tab-pane>
    </el-tabs>
    <div v-else style="height: calc(100% - 110px);overflow-y: auto;">
      <el-collapse v-model="activeName">
        <el-collapse-item v-for="group in allGroups" :name="group.id">
          <template slot="title">
            <div class="menu-dashboard-span">
              <span>{{group.name}}</span>
              <span style="float: right;">
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
    </div>

    <add-dashboard-or-group
      v-if="modal.show"
      :groups="groups"
      :dashboards="dashboards"
      :show="modal.show"
      @modal-switch="modalSwitch"
      @change-groups="findGroup"
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
        allGroups: [],
        activeName: [],
        inputChange: false,
        tabName: 'second',
        filterName: '',
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
          this.dashboards = []
          for (let key in this.groups) {
            this.dashboards = this.dashboards.concat(this.groups[key].list)
          }
        })
      },
      findCommonGroup() {
        getCommonGroup().then(response => {
          this.commonGroups = response
          this.commonDashboards = []
          for (let key in this.commonGroups) {
            this.commonDashboards = this.commonDashboards.concat(this.commonGroups[key].list)
          }
        })
      },
      filterGroup() {
        this.allGroups = JSON.parse(JSON.stringify(this.groups.concat(this.commonGroups)))
        this.activeName = []
        if (this.filterName === null || this.filterName === '') {
          this.inputChange = false
        } else {
          this.inputChange = true
          this.allGroups = this.allGroups.filter(item => {
            item.list = item.list.filter(i => {
              return i.name.indexOf(this.filterName) > -1
            })
            if (item.list.length > 0) this.activeName.push(item.id)
            return item.list.length > 0
          })
        }
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
      handleCloseSort() {
        this.modal.sortShow = false
      },
      clickHandle() {
        this.$emit('on-select', 'menu-dashboard-manager')
      }
    }
  }
</script>

<style scoped>
  @import './side-menu-dashboard.scss';
</style>
