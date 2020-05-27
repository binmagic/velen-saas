<template>
  <div class="menu-dashboard">
    <el-input
      v-model="filterGroup"
      placeholder="概览名称"
      prefix-icon="el-icon-search"
      class="menu-dashboard-input"
      @change="inputChange"
    />
    <el-tabs v-model="tabName" @tab-click="handleClick">
      <el-tab-pane label="公共概览" name="first" />
      <el-tab-pane label="我的概览" name="second" />
    </el-tabs>
    <div v-if="tabName=='second'">
      <div v-for="(group,index) in groups">
        <h3 class="menu-dashboard-span" @click="showDashboard(group,index)">
          <span>{{ group.name }}</span>
          <i :class="[group.show?'el-icon-arrow-down':'el-icon-arrow-right']" />
          <span style="float: right;">
            <i v-if="hoverIndex==index && hover" class="el-icon-more menu-dashboard-icon" />
            <span class="dashboard-aside-num">{{ group.list.length }}</span>
          </span>
        </h3>
        <ul :class="['dashboard-aside-ul',group.show?'group-show':'group-hide']">
          <li v-for="dashboard in group.list" class="dashboard-aside-li">
            <span>
              {{ dashboard.name }}
            </span>
          </li>
        </ul>
      </div>
    </div>
    <div v-else>
      <div v-for="commonGroup in commonGroups">
        <h3 class="menu-dashboard-span">
          <span>{{commonGroup.name}}</span>
          <i class="el-icon-arrow-right"/>
          <span style="float:right;">
          </span>
        </h3>
        <div v-if="commonGroup.hasSonGroup" style="padding-left: 19px;">
          <div v-for="sonGroup in commonGroup.list">
            <h3 class="menu-dashboard-span">
              <span>{{sonGroup.name}}</span>
              <i class="el-icon-arrow-right"/>
              <span style="float:right;">
                <span class="dashboard-aside-num">{{sonGroup.list.length}}</span>
              </span>
            </h3>
            <ul>
              <li v-for="sonCommonDashboard in sonGroup.list">
                <span>{{sonCommonDashboard.name}}</span>
              </li>
            </ul>
          </div>
        </div>
        <ul v-else>
          <li v-for="commonDashboard in commonGroup.list">
            <span>{{commonDashboard.name}}</span>
          </li>
        </ul>
      </div>
    </div>
    <div class="el-btn">
      <el-button-group style="border-radius: 20px;overflow: hidden;">
        <el-tooltip content="新建我的概览/分组">
          <el-button type="primary" icon="el-icon-plus" @click="modalSwitch" />
        </el-tooltip>
        <el-tooltip content="管理我的概览排序">
          <el-button type="primary" icon="el-icon-d-caret" />
        </el-tooltip>
        <el-tooltip content="分享我的概览">
          <el-button type="primary" icon="el-icon-share" />
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
  </div>
</template>
<script>
import { getGroup, getCommonGroup } from '@/api/group'
import AddDashboardOrGroup from './components/AddDashboardOrGroup'

export default {
  name: 'SideMenuDashboard',
  components: {
    AddDashboardOrGroup
  },
  props: {},


    data() {
      return {
        tabName: 'second',
        filterGroup: '',
        modal: {
          show: false,
        },
        groups: [],
        dashboards: [],
        hover: false,
        hoverIndex: -1,
        commonGroups: [],
        commonDashboards: [],
      }
    },
    created() {
      this.findGroup()
      this.findCommonGroup()
    },
    computed: {},
    methods: {
      findGroup() {
        getGroup().then(response => {
          this.groups = response
          for (let key in this.groups) {
            this.groups[key].show = false
            this.dashboards = this.dashboards.concat(this.groups[key].list)
          }
        })
      },
      findCommonGroup() {
        getCommonGroup().then(response => {
          this.commonGroups = response
          for (let key in this.commonGroups) {
            this.commonGroups[key].show = false
            if (!this.commonGroups[key].hasSonGroup){
              this.commonDashboards=this.commonDashboards.concat(this.commonGroups[key].list)
            }else {
              for (let i in  this.commonGroups[key].list){
                this.commonDashboards=this.commonDashboards.concat(this.commonGroups[key].list[i].list)
              }
            }
          }
        })
      },
      inputChange() {
        //this.$emit('input-change', this.value)

    },
    handleClick() {

    },
    handleNodeClick() {

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
      showDashboard(group, index) {
        this.$set(group, 'show', !group.show)
        console.log(group.show)
      }
    }
  }
</script>

<style scoped>
  .menu-dashboard {
    background: #ffffff;
    height: 100%;
  }

  .menu-dashboard-input {
    margin: 20px 10px;
  }

  .el-btn {
    position: fixed;
    bottom: 20px;
    left: 17px;
    text-align: center;
  }

  .menu-dashboard-span {
    margin: 0;
    padding: 10px 12px 10px 21px;
    font-size: 12px;
    color: #8492a6;
    cursor: pointer;
    user-select: none;
  }

  .dashboard-aside-ul {
    padding: 0px;
    margin: 0px;
    list-style: none;
  }

  .dashboard-aside-li {
    height: 36px;
    padding: 0 21px 0 33px;
    line-height: 36px;
    position: relative;
    font-size: 14px;
    color: #1f2d3d;
    cursor: pointer;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
  }

  .dashboard-aside-num {
    padding: 0 5px;
    border-radius: 8px;
    line-height: 1;
    background: #e8eef2;
  }

  .dashboard-aside-li-active {
    border-right: 2px solid;
    background: #e5f9f4;
    color: #04cb94;
    font-weight: 500;
  }

  .menu-dashboard-icon {
    margin-right: 5px;
  }

  .group-hide {
    display: none;
  }

  .group-show {
    display: block;
  }
</style>
