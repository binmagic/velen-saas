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
    <el-dialog :visible.sync="modal.sortShow" width="30%" :show-close="false" @closed="closeDialog">
      <template slot="title">
        <div style="position: relative;">
          <el-row>
            <el-col :span="14" style="line-height: 40px;">
              <span>管理我的概览排序</span>
            </el-col>
            <el-col :span="10">
              <el-input
                v-model="sortInput"
                placeholder="搜索概览名称"
                prefix-icon="el-icon-search"
                size="medium"
                clearable
                style="text-align: right"/>
            </el-col>
          </el-row>
        </div>
      </template>
      <el-tree
        :data="treeGroup"
        :props="treeProps"
        ref="tree"
        :filter-node-method="filterNode"
        show-checkbox
        draggable node-key="id"
        :allow-drop="allowDrop"
        @node-drop="handleDrop">
        <div slot-scope="{node , data}"
             @mouseenter="()=>treeEnter(node,data)"
             @mouseleave="()=>treeLeave(data)"
             style="height:32px;width:526px;lineHeight: 32px;">
          <el-row>
            <el-col :span="16">
              <span>{{data.name}}</span>
            </el-col>
            <el-col :span="8">
              <span style="float: right;margin-right: 5px;" v-if="data.show">
                <el-button type="text" size="small" @click.stop >重命名</el-button>
                <el-button v-if="node.level==2" type="text" size="small" @click.stop>移动到</el-button>
                <el-button type="text" size="small" @click.stop style="color: red;">删除</el-button>
              </span>
            </el-col>
          </el-row>
        </div>
      </el-tree>
      <div slot="footer">
        <el-button type="primary" size="medium" style="float:left;">新建分组</el-button>
        <el-button @click="sortShow" size="medium">取消</el-button>
        <el-button type="primary" size="medium" @click="updateSubmit">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>

  import {getGroup, getCommonGroup,updateGroup} from '@/api/group'
  import AddDashboardOrGroup from './components/add-dashboard-or-group'

  export default {
    name: 'SideMenuDashboard',
    components: {
      AddDashboardOrGroup,
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
      filterNode(value, data, node) {
        if (!value) {
          return true;
        }
        let level = node.level;
        let _array = [];//这里使用数组存储 只是为了存储值。
        this.getReturnNode(node, _array, value);
        let result = false;
        _array.forEach((item) => {
          result = result || item;
        });
        return result;
      },
      getReturnNode(node, _array, value) {
        let isPass = node.data && node.data.name && node.data.name.indexOf(value) !== -1;
        isPass ? _array.push(isPass) : '';
        this.index++;
        if (!isPass && node.level != 1 && node.parent) {
          this.getReturnNode(node.parent, _array, value);
        }
      },
      clickDashboard(dashboard) {
        this.$route.meta.title = dashboard.name
      },
      allowDrop(draggingNode, dropNode, type) {
        if (draggingNode.level == 1) {
          if (dropNode.level == 1) {
            return type !== 'inner'
          } else if (dropNode.level == 2) {
            return false
          } else {
            return true
          }
          return type !== 'inner'
        } else {
          if (dropNode.level == 2) {
            return type !== 'inner'
          } else if (dropNode.level == 1) {
            return type == 'inner'
          } else {
            return false
          }
        }
      },
      sortShow() {
        this.modal.sortShow = false
        this.treeGroup = JSON.parse(JSON.stringify(this.groups))
      },
      treeEnter(node, data) {
        this.$set(data, 'show', true)
      },
      treeLeave(data) {
        this.$set(data, 'show', false)
      },
      closeDialog() {
        this.treeGroup = JSON.parse(JSON.stringify(this.groups))
      },
      handleDrop(draggingNode, dropNode, type, event) {
        if (draggingNode.level == 1) {
          for (let key in this.treeGroup) {
            this.treeGroup[key].sort = key
          }
        } else {
          if (type === 'inner') {
            draggingNode.data.sort = dropNode.childNodes.length-1
            draggingNode.data.type = dropNode.data.id
          } else {
            let group = this.treeGroup.find(v => v.id === dropNode.data.type)
            for (let key in group.list) {
              group.list[key].sort = key
              group.list[key].type = dropNode.data.type
            }
          }
        }
      },
      updateSubmit(){
        console.log(this.treeGroup)
        updateGroup(this.treeGroup).then(response =>{
          console.log(response)

        })
        this.findGroup()
        this.modal.sortShow = false
      }
    }
  }
</script>

<style scoped>
  @import './side-menu-dashboard.scss';
</style>
