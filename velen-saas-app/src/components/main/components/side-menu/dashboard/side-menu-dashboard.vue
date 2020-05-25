<template>
  <div class="menu-dashboard">
    <el-input
      placeholder="概览名称"
      prefix-icon="el-icon-search"
      v-model="value"
      @change="inputChange"
      class="menu-dashboard-input"
    >
    </el-input>
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="公共概览" name="first">
        公共概览
      </el-tab-pane>
      <el-tab-pane label="我的概览" name="second">
        <div>
          <div v-for="(group,index) in groups">
            <h3 class="menu-dashboard-span"  @mouseenter="enter(index)" @mouseleave="leave" @click="group.show=!group.show">
              <span>{{group.name}}</span>
              <i class="el-icon-arrow-right"/>
              <span style="float: right;">
                <i class="el-icon-more menu-dashboard-icon" v-if="hoverIndex==index && hover"></i>
                <span class="dashboard-aside-num">{{group.list.length}}</span>
              </span>
            </h3>
            <ul :class="['dashboard-aside-ul',group.show?'group-show':'group-hide']">
              <li class="dashboard-aside-li"  v-for="dashboard in group.list">
                <span>
                  {{dashboard.name}}
                </span>
              </li>
            </ul>
          </div>

        </div>
      </el-tab-pane>
    </el-tabs>
    <div class="el-btn">
      <el-button-group style="border-radius: 20px;overflow: hidden;">
        <el-tooltip content="新建我的概览/分组">
          <el-button type="primary" icon="el-icon-plus" @click="modalSwitch"></el-button>
        </el-tooltip>
        <el-tooltip content="管理我的概览排序">
          <el-button type="primary" icon="el-icon-d-caret"></el-button>
        </el-tooltip>
        <el-tooltip content="分享我的概览">
          <el-button type="primary" icon="el-icon-share"></el-button>
        </el-tooltip>
      </el-button-group>
    </div>
    <add-dashboard-or-group v-if="modal.show" :groups="groups" :dashboards="dashboards" :show="modal.show"
                            @modal-switch="modalSwitch"></add-dashboard-or-group>
  </div>
</template>
<script>
  import {getGroup} from '@/api/group'
  import AddDashboardOrGroup from './components/AddDashboardOrGroup'

  export default {
    name: "SideMenuDashboard",
    components: {
      AddDashboardOrGroup
    },
    props: {
      overview: {
        type: String,
      },
      tabName: {
        type: String,
        default: 'second'
      },
    },


    data() {
      return {
        value: this.overview,
        modal: {
          show: false,
        },
        groups: [],
        dashboards: [],
        hover: false,
        hoverIndex: -1,
      }
    },
    created() {
      this.findGroup()
    },
    computed: {
      activeName: {
        get() {
          return this.tabName
        },
        set(val) {
          this.$emit('update:tabName', val)
        }
      }
    },
    methods: {
      findGroup() {
        getGroup().then(response => {
          this.groups = response
          for (let key in this.groups) {
            this.groups[key].show=false
            this.dashboards = this.dashboards.concat(this.groups[key].list)
          }
          console.log(this.groups)
        })
      },
      inputChange() {
        this.$emit('input-change', this.value)
      },
      handleClick() {

      },
      handleNodeClick() {

      },
      modalSwitch () {
        this.modal.show = !this.modal.show
      },
      enter(index) {
        this.hover = true
        this.hoverIndex = index
      },
      leave() {
        this.hover = false
        this.hoverIndex = -1
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
