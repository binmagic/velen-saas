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
        <el-table-column type="selection"></el-table-column>
        <el-table-column prop="name" label="名称"></el-table-column>
        <el-table-column prop="typeName" label="所属分组"></el-table-column>
        <el-table-column prop="userId" label="共享给..."></el-table-column>
        <el-table-column prop="userName" label="创建人"></el-table-column>
        <el-table-column label="操作">
          <el-link :underline="false" type="primary">分享设置</el-link>
          <i class="el-icon-edit" style="cursor: pointer;margin: 0px 20px;"/>
          <i class="el-icon-delete" style="cursor: pointer;color: red;"/>
        </el-table-column>
      </el-table>
    </el-main>
  </el-container>
</template>

<script>
  import {getGroup, getCommonGroup} from '@/api/group'
  export default {
    name: "DashboardManager",

    data(){
      return{
        creator:'',
        group:'',
        groups:[],
        dashboards:[],
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
