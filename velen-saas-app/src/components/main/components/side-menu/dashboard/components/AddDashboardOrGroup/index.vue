<template>
  <div>
    <div class="add-modal-mask"></div>
    <div tabindex="-1" class="add-modal-wrap" @click="showChange">
      <div style="width: 318px;transform-origin: -746px 465px;" class="add-modal" @click.stop>
        <div class="add-modal-content">
          <div class="add-modal-header">
            <div class="header-title">
              新建我的概览/分组
            </div>
            <div class="add-modal-body">
              <el-form>
                <el-form-item label="新建类型">
                  <el-row>
                    <el-col :span="24">
                      <el-radio v-model="radio" :label="true">创建概览</el-radio>
                      <el-radio v-model="radio" :label="false">创建分组</el-radio>
                    </el-col>
                  </el-row>
                </el-form-item>
                <div v-if="radio">
                  <el-form-item label="概览名称">
                    <el-row>
                      <el-col :span="24">
                        <el-input v-model="dashboardName" placeholder="请输入"></el-input>
                      </el-col>
                    </el-row>
                  </el-form-item>
                  <el-form-item label="概览分组">
                    <el-row>
                      <el-col :span="24">
                        <el-select filterable v-model="group" @click="test">
                          <el-option v-for="item in groups" :key="item.id" :label="item.name" :value="item.id"/>
                        </el-select>
                      </el-col>
                    </el-row>
                  </el-form-item>
                </div>
                <div v-else>
                  <el-form-item label="分组名称">
                    <el-row>
                      <el-col :span="24">
                        <el-input v-model="groupName" style="width: 100%!important;" placeholder="请输入"></el-input>
                      </el-col>
                    </el-row>
                  </el-form-item>
                  <el-form-item label="组内概览">
                    <el-select filterable placeholder="请选择" multiple v-model="dashboard">
                      <el-option v-for="item in dashboards" :key="item.id" :label="item.name" :value="item.id"/>
                    </el-select>
                  </el-form-item>
                </div>
              </el-form>
            </div>
            <div class="add-modal-foot" style="text-align: right">
              <el-button @click="showChange">取消</el-button>
              <el-button type="primary" @click="insertSelect">确定</el-button>
            </div>
          </div>
        </div>
      </div>

    </div>
  </div>
</template>

<script>
  import {addGroup} from "@/api/group";
  import {addDashboard} from "@/api/dashboard";

  export default {
    name: "AddDashboardOrGroup",
    props: {
      show: {
        type: Boolean
      },
      groups: {
        type: Array
      },
      dashboards: {
        type: Array
      }
    },
    data() {
      return {
        radio: true,
        groupName: '',
        dashboardName: '',
        group: '',
        dashboard: [],
      }
    },
    methods: {
      insertSelect() {
        if (this.radio) {
          console.log('this is dashboard')
          let dashboardCreate = {name: this.dashboardName, type: this.group}
          addDashboard(dashboardCreate).then(response => {
            console.log(response)
          })
        } else {
          console.log('this is group')
          let groupCreate = {name: this.groupName, dashboards: this.dashboard}
          addGroup(groupCreate).then(response => {
            console.log(response)
            //this.$emit('change-group', response)
          })
        }
        this.$emit("modal-switch", {show: !this.show})
      },
      showChange: function () {
        this.$emit("modal-switch", {show: !this.show})
      },
      test() {
        console.log(this.groups)
      }
    }
  }
</script>

<style scoped>
  .add-modal-mask {
    position: fixed;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    z-index: 1000;
    height: 100%;
    background-color: rgba(0, 0, 0, .65);
  }

  .add-modal-wrap {
    position: fixed;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    z-index: 1000;
    overflow: auto;
    outline: 0;
  }

  .add-modal {
    box-sizing: border-box;
    color: #475669;
    font-size: 14px;
    font-variant: tabular-nums;
    line-height: 1.5;
    list-style: none;
    font-feature-settings: "tnum";
    position: relative;
    top: 100px;
    width: auto;
    margin: 0 auto;
    padding: 0 0 24px;
  }

  .add-modal-content {
    position: relative;
    background-color: #fff;
    background-clip: padding-box;
    border: 0;
    border-radius: 3px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, .15);
  }

  .add-modal-header {
    border-bottom-color: #e9f0f7;
    padding: 14px 20px;
  }

  .add-modal-body {
    font-size: 14px;
    line-height: 1.5;
    word-wrap: break-word;
  }

  .add-modal-foot {
    padding: 10px 15px;
    position: relative;
    overflow: hidden;
    border-top-color: #e9f0f7;

  }

  .header-title {
    color: #1f2d3d;
    line-height: 20px;
    margin: 0;
    color: rgba(0, 0, 0, .85);
    font-weight: 500;
    font-size: 16px;
    line-height: 22px;
    word-wrap: break-word;
  }
</style>
