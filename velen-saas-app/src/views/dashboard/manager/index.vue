<template>
  <el-container>
    <el-header height="30px" class="manager-header">
      <h4 class="manager-h4">
        <i class="manager-icon el-icon-arrow-left" @click="turnToPage"/>
        {{$route.meta.title}}
      </h4>
    </el-header>
    <el-main>
      <el-row v-if="isChecked" style="padding-bottom: 18px;">
        <el-col :span="4">
          <span style="font-size: 14px;">创建人</span>
          <el-select size="small" v-model="creator" @change="filterTable">
            <el-option label="不限" :value="null"></el-option>
            <el-option v-for="creator in creators" :key="creator" :label="creator" :value="creator"></el-option>
          </el-select>
        </el-col>
        <el-col :span="15">
          <span style="font-size: 14px;">所属分组</span>
          <el-select size="small" v-model="group" @change="filterTable">
            <el-option label="不限" :value="null"></el-option>
            <el-option v-for="group in groups" :key="group.id" :label="group.name" :value="group.id"></el-option>
          </el-select>
        </el-col>
        <el-col :span="3">
          <div class="manager-input">
            <el-input prefix-icon="el-icon-search" size="small" @input="filterTable" v-model="filterName"
                      placeholder="搜索概览名称"/>
          </div>
        </el-col>
        <el-col :span="2">
          <el-button icon="el-icon-s-fold" size="small" type="primary" @click="modal.sortShow=true">设置概览显示顺序</el-button>
        </el-col>
      </el-row>
      <el-row v-else style="padding-bottom: 18px;">
        <el-col>
          <el-button size="mini">增加可访问成员</el-button>
          <el-button size="mini">重置分享设置</el-button>

          <el-popconfirm
            width="213"
            confirmButtonText='确定'
            cancelButtonText='取消'
            icon="el-icon-info"
            iconColor="#ffbf00"
            title="删除所选概览?"
            @onConfirm="delSelectDashboard"
          >
            <el-button size="mini" slot="reference" style="color: red;">删除</el-button>
          </el-popconfirm>
          <el-button size="mini" @click="$refs.multipleTable.clearSelection()">取消</el-button>
        </el-col>
      </el-row>
      <el-table :data="filterGroup" @selection-change="selectionChange" ref="multipleTable">
        <el-table-column type="selection"/>
        <el-table-column width="309" prop="name" label="名称"/>
        <el-table-column width="239" prop="typeName" label="所属分组"/>
        <el-table-column width="742" label="共享给...">
          <template slot-scope="scope">
            <span v-if="scope.row.isPublic==0">仅自己可见</span>
            <span v-else>不是你创建的概览，须复制为自己的概览后，方可分享给Ta人</span>
          </template>
        </el-table-column>
        <el-table-column width="214" prop="userName" label="创建人"/>
        <el-table-column width="288" label="操作">
          <template v-if="scope.row.isPublic==0" slot-scope="scope">
            <span v-if="scope.row.isPublic==0">
            <el-link :underline="false" type="primary" @click="dialogVisible=true">分享设置</el-link>
              <el-popover placement="bottom" width="318" trigger="click" v-model="scope.row.edit"
                          @show="popoverInfo(scope.row)">
                <div style="margin-bottom: 20px;">
                  <p style="text-align: center;">编辑此概览</p>
                  <p>概览名称</p>
                  <el-row>
                    <el-col :span="22">
                      <el-input size="mini" v-model="edit.dashboardName"></el-input>
                    </el-col>
                  </el-row>
                  <p>概览分组</p>
                  <el-select size="mini" v-model="edit.groupName">
                    <el-option v-for="group in groups" :disabled="group.name==='分享给我的概览'"
                               :key="group.id" :label="group.name" :value="group.id"></el-option>
                  </el-select>
                </div>
                <div style="text-align: right;margin-bottom: 10px;">
                  <el-button size="mini" @click="$set(scope.row,'edit',false)">取消</el-button>
                  <el-button size="mini" type="primary" @click="editDashboard(scope.row)">确定</el-button>
                </div>
                <i class="el-icon-edit" slot="reference"
                   @click="scope.row.move?$set(scope.row,'edit',true):$set(scope.row,'edit',false)"
                   style="cursor: pointer;margin: 0px 20px;"/>
              </el-popover>
              <el-popover placement="bottom" width="200" trigger="click" v-model="scope.row.del">
                <div style="margin-bottom: 20px;">
                  <p><i class="el-icon-warning" style="color: #ffbf00;"/>删除概览?</p>
                  <p>删除概览</p>
                </div>
                <div style="text-align: right;margin-bottom: 10px;">
                  <el-button size="mini" @click="$set(scope.row,'del',false)">取消</el-button>
                  <el-button size="mini" type="primary" @click="delDashboard(scope.row)">确定</el-button>
                </div>
                <i class="el-icon-delete" slot="reference"
                   @click="scope.row.move?$set(scope.row,'del',true):$set(scope.row,'del',false)"
                   style="cursor: pointer;color: red;"/>
              </el-popover>
          </span>
          </template>
        </el-table-column>
      </el-table>
      <pagination
        :hidden="filterGroup.length<=0"
        :total="filterGroup.length"
        :page.sync="page"
        :limit.sync="limit"
        @pagination="changePage"
      />
    </el-main>
    <el-dialog title="分享设置" :visible.sync="dialogVisible" width="30%">
      <el-row>
        <el-col :span="15">
          <el-radio-group v-model="radio">
            <el-radio :label="1">仅自己可见</el-radio>
            <el-radio :label="2">全部成员</el-radio>
            <el-radio :label="3">指定成员</el-radio>
          </el-radio-group>
        </el-col>
      </el-row>
      <div v-if="radio===1">
        <p>设置后，此概览将仅可你自己查看。其他成员均不可查看。</p>
      </div>
      <div v-if="radio===3">
        <el-input style="margin-top: 10px;" v-model="memberName" size="small" placeholder="搜索成员姓名"/>
      </div>
      <div slot="footer" style="text-align: right;">
        <el-button size="small" @click="dialogVisible = false">取消</el-button>
        <el-button size="small" type="primary">保存</el-button>
      </div>
    </el-dialog>
    <sort
      :sort-groups="groups"
      :sort-show-visible="modal.sortShow"
      @update-group="findGroup"
      @close-sort="handleCloseSort"
    />

  </el-container>
</template>

<script>
  import {getGroup} from '@/api/group'
  import {updateDashboard, deleteDashboard} from "@/api/dashboard";
  import Sort from '@/components/main/components/side-menu/dashboard/components/sort'
  import Pagination from '@/components/Pagination'

  export default {
    name: "DashboardManager",
    components: {
      Sort,
      Pagination
    },
    data() {
      return {
        page:1,
        limit:10,
        creator: null,
        creators: [],
        group: null,
        groups: [],
        dashboards: [],
        isChecked: true,
        dialogVisible: false,
        radio: 1,
        memberName: '',
        modal: {
          sortShow: false,
        },
        edit: {
          dashboardName: '',
          groupName: '',
        },
        filterGroup: [],
        filterName: '',
        selectDel:false,
        selectionGroup:[],
      }
    },
    created() {
      this.findGroup()
    },
    methods: {
      findGroup() {
        getGroup().then(response => {
          this.groups = response
          this.dashboards = []
          for (let key in this.groups) {
            this.groups[key].list.some(item => {
              if (item.type === this.groups[key].id) {
                item.typeName = this.groups[key].name
              }
            })
            this.dashboards = this.dashboards.concat(this.groups[key].list)
          }
          this.filterGroup = JSON.parse(JSON.stringify(this.dashboards))
          this.dashboards.some(item => {
            if (this.creators.indexOf(item.userName)) {
              this.creators.push(item.userName)
            }
          })
        })
      },
      turnToPage() {
        let {name, params, query} = {}
        name = 'menu-dashboard'
        if (!query) {
          query = this.$route.query
        }
        this.$router.push({
          name,
          params,
          query
        })
      },
      handleCloseSort() {
        this.modal.sortShow = false
      },
      selectionChange(selection) {
        this.selectionGroup=selection
        if (selection.length > 0) {
          this.isChecked = false
        } else {
          this.isChecked = true
        }
      },
      filterTable() {
        this.filterGroup = JSON.parse(JSON.stringify(this.dashboards))
        if (this.creator != null && this.creator != '') {
          this.filterGroup = this.filterGroup.filter(item => {
            return item.userName == this.creator
          })
        }
        if (this.group != null && this.group != '') {
          this.filterGroup = this.filterGroup.filter(item => {
            return item.type == this.group
          })
        }
        if (this.filterName != null && this.filterName != '') {
          this.filterGroup = this.filterGroup.filter(item => {
            return item.name.indexOf(this.filterName) > -1
          })
        }
      },
      popoverInfo(dashboard) {
        this.edit.dashboardName = JSON.parse(JSON.stringify(dashboard.name))
        this.edit.groupName = JSON.parse(JSON.stringify(dashboard.type))
      },
      editDashboard(dashboard) {
        dashboard.name = this.edit.dashboardName
        dashboard.type = this.edit.groupName
        this.groups.some(item => {
          if (item.id === dashboard.type) {
            dashboard.sort = item.list.length
          }
        })
        updateDashboard(dashboard).then(resp => {
        })
        this.findGroup()
      },
      delDashboard(dashboard) {
        deleteDashboard(dashboard.id).then(resp => {
        })
        this.findGroup()
      },
      delSelectDashboard(){
        for (let key in this.selectionGroup){
          deleteDashboard(this.selectionGroup[key].id).then(resp=>{})
        }
        this.findGroup()
      },
      changePage(){

      }
    }
  }
</script>

<style>


  .manager-header {
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: space-between;
  }

  .manager-h4 {
    font-size: 20px;
    color: #1f2d3d;
    font-weight: 600;
    margin: 0;
  }

  .manager-icon {
    color: #778ca2;
    cursor: pointer;
    padding: 5px 5px 5px 0;
  }
</style>
