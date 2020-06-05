<template>
  <el-container>
    <el-header height="30px" class="manager-header">
      <h4 class="manager-h4">
        <i class="manager-icon el-icon-arrow-left" @click="turnToPage"/>
        {{$route.meta.title}}
      </h4>
    </el-header>
    <el-main >
      <el-row style="padding-bottom: 18px;">
        <el-col :span="4">
          <span style="font-size: 14px;">创建人</span>
          <el-select size="small" v-model="creator">
            <el-option label="a" value="a"></el-option>
          </el-select>
        </el-col>
        <el-col :span="15">
          <span style="font-size: 14px;">所属分组</span>
          <el-select size="small" v-model="group">
            <el-option label="a" value="a"></el-option>
          </el-select>
        </el-col>
        <el-col :span="3">
          <div class="manager-input">
            <el-input prefix-icon="el-icon-search" size="small" placeholder="搜索概览名称"/>
          </div>
        </el-col>
        <el-col :span="2">
          <el-button icon="el-icon-s-fold" size="small" type="primary">设置概览显示顺序</el-button>
        </el-col>
      </el-row>
      <el-table :data="dashboards">
        <el-table-column type="selection"/>
        <el-table-column width="309" prop="name" label="名称"/>
        <el-table-column width="239" prop="typeName" label="所属分组"/>
        <el-table-column  width="742" label="共享给...">
          <template slot-scope="scope">
            <span v-if="scope.row.isPublic==0">仅自己可见</span>
            <span v-else>不是你创建的概览，须复制为自己的概览后，方可分享给Ta人</span>
          </template>
        </el-table-column>
        <el-table-column width="214" prop="userName" label="创建人"/>
        <el-table-column width="288" label="操作">
          <template slot-scope="scope">
            <span v-if="scope.row.isPublic==0">
            <el-link :underline="false" type="primary" @click="dialogVisible=true">分享设置</el-link>
            <i class="el-icon-edit" style="cursor: pointer;margin: 0px 20px;"/>
            <i class="el-icon-delete" style="cursor: pointer;color: red;"/>
          </span>
          </template>
        </el-table-column>
      </el-table>
    </el-main>
    <el-dialog title="分享设置" :visible.sync="dialogVisible" width="30%">
        <el-row >
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
          <el-input style="margin-top: 10px;" v-model="membeName" size="small" placeholder="搜索成员姓名"/>
        </div>
      <div slot="footer" style="text-align: right;">
        <el-button size="small">取消</el-button>
        <el-button size="small" type="primary">保存</el-button>
      </div>
    </el-dialog>

  </el-container>
</template>

<script>
  import {getGroup} from '@/api/group'
  export default {
    name: "DashboardManager",

    data(){
      return{
        creator:'',
        group:'',
        groups:[],
        dashboards:[],
        dialogVisible:false,
        radio:1,
        membeName:'',
      }
    },
    created() {
      this.findGroup()
    },
    methods:{
      findGroup(){
        getGroup().then(response => {
          this.groups = response
          this.dashboards = []
          for (let key in this.groups) {
            this.groups[key].list.some(item =>{
              if (item.type===this.groups[key].id){
                item.typeName=this.groups[key].name
              }
            })
            this.dashboards = this.dashboards.concat(this.groups[key].list)
          }
        })
      },
      turnToPage(){
        let { name, params, query } = {}
        name='menu-dashboard'
        if (!query) {
          query = this.$route.query
        }
        this.$router.push({
          name,
          params,
          query
        })
      }
    }
  }
</script>

<style>


  .manager-header{
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: space-between;
  }
  .manager-h4{
    font-size: 20px;
    color: #1f2d3d;
    font-weight: 600;
    margin: 0;
  }
  .manager-icon{
    color: #778ca2;
    cursor: pointer;
    padding: 5px 5px 5px 0;
  }
</style>
