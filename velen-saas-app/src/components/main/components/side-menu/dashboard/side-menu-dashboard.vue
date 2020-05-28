<template>
  <div class="menu-dashboard">
        <el-input
          size="small"
          v-model="filterGroup"
          placeholder="概览名称"
          prefix-icon="el-icon-search"
          class="menu-dashboard-input"
          @change="inputChange"
        />
    <el-tabs v-model="tabName" @tab-click="handleClick">
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
              <li v-for="dashboard in group.list" class="dashboard-aside-li">
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
    <el-dialog :visible.sync="modal.sortShow" width="30%">
      <template slot="title">
        <div style="position: relative;">
          <span>管理我的概览排序</span>
          <el-input v-model="sortInput" placeholder="搜索概览名称" style="position: absolute;right: 30px;top: -10px;"/>
        </div>

      </template>
      <el-tree :data="groups" :props="treeProps" ref="tree" :filter-node-method="filterNode"></el-tree>
    </el-dialog>
  </div>
</template>
<script>

  import {getGroup, getCommonGroup} from '@/api/group'
  import AddDashboardOrGroup from './components/add-dashboard-or-group'

  export default {
    name: 'SideMenuDashboard',
    components: {
      AddDashboardOrGroup,
    },
    props: {},
    watch:{
      sortInput(val){
        this.$refs.tree.filter(val)
      }
    },
    data() {
      return {
        tabName: 'second',
        filterGroup: '',
        modal: {
          show: false,
          sortShow:false,
        },
        groups: [],
        dashboards: [],
        hover: false,
        hoverIndex: -1,
        commonGroups: [],
        commonDashboards: [],
        sortInput:'',
        treeProps:{
          children:'list',
          label:'name'
        }
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
      inputChange() {

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
      filterNode(value,data,node){
        if(!value){
          return true;
        }
        let level = node.level;
        let _array = [];//这里使用数组存储 只是为了存储值。
        this.getReturnNode(node,_array,value);
        let result = false;
        _array.forEach((item)=>{
          result = result || item;
        });
        return result;
      },
      getReturnNode(node,_array,value){
        let isPass = node.data &&  node.data.name && node.data.name.indexOf(value) !== -1;
        isPass?_array.push(isPass):'';
        this.index++;
        console.log(this.index)
        if(!isPass && node.level!=1 && node.parent){
          this.getReturnNode(node.parent,_array,value);
        }
      }
    }
  }
</script>

<style scoped>
  @import './side-menu-dashboard.scss';

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
    width: 100%;
    margin: 0;
    padding-right: 12px;
    padding-left: 21px;
    font-size: 12px;
    color: #8492a6;

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

  .dashboard-aside-popover {
    margin-top: 5px;
    height: 24px;
    cursor: pointer;
  }
</style>
