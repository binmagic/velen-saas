<template>
  <div class="mainLeft">
    <el-input
      placeholder="概览名称"
      prefix-icon="el-icon-search"
      v-model="value"
      @change="inputChange"
      style="margin: 20px 10px;"
    >
    </el-input>
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="公共概览" name="first">
        公共概览
      </el-tab-pane>
      <el-tab-pane label="我的概览" name="second">
        <div>
          <div>
            <h3 class="menu-dashboard-span">
              <span>分享给我的概览</span>
              <i class="el-icon-arrow-right"></i>
              <span style="float: right;">
                <i class="el-icon-more" style="margin-right: 5px;display: none;"></i>
                <span class="dashboard-aside-num">20</span>
              </span>
            </h3>
            <ul class="dashboard-aside-ul" style="margin:0px;list-style: none;">
              <li class="dashboard-aside-li dashboard-aside-li-active">
                <span>
                  11
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
          <el-button type="primary" icon="el-icon-plus"></el-button>
        </el-tooltip>
        <el-tooltip content="管理我的概览排序">
          <el-button type="primary" icon="el-icon-d-caret"></el-button>
        </el-tooltip>
        <el-tooltip content="分享我的概览">
          <el-button type="primary" icon="el-icon-share"></el-button>
        </el-tooltip>
      </el-button-group>
    </div>
  </div>
</template>
<script>
  export default {
    name: "mainLeft",
    props: {
      overview:{
        type:String,

      },
      tabName:{
        type:String,
        default:'second'
      },
    } ,


    data() {
      return {
        value: this.overview,

      }
    },
    computed: {
      activeName:{
        get(){
          return this.tabName
        },
        set(val){
          this.$emit('update:tabName',val)
        }
      }
    },
    methods: {
      inputChange() {
        this.$emit('input-change', this.value)
      },
      handleClick() {

      },
      handleNodeClick() {

      },
    }
  }
</script>

<style scoped>
  .mainLeft{
    background: #ffffff;
    height: 100%;
  }

  .el-btn{
    position: fixed;
    bottom: 20px;
    left: 17px;
    text-align: center;
  }

  .menu-dashboard-span{
    margin: 0;
    padding: 10px 12px 10px 21px;
    font-size: 12px;
    color: #8492a6;
    cursor: pointer;
    user-select: none;
  }

  .dashboard-aside-ul{
    list-style: none;
    padding: 0;
    margin: 0;
  }

  .dashboard-aside-li{
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

  .dashboard-aside-num{
    padding: 0 5px;
    border-radius: 8px;
    line-height: 1;
    background: #e8eef2;
  }

  .dashboard-aside-li-active{
    border-right: 2px solid;
    background: #e5f9f4;
    color: #04cb94;
    font-weight: 500;
  }

  .group-hide{
    display: none;
  }

  .group-show{
    display: block;
  }
</style>
