<template>
  <div class="app-container">
    <custom-header :show-name="$route.meta.title">
      <div slot="tools" >
        <div v-if="componentFlag.enableDatePick" style="margin-right: 40px; align-self: center;display: flex">
          <el-date-picker
            v-model="query.dateRange"
            type="daterange"
            align="right"
            unlink-panels
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            :picker-options="componentOption.datePickerOptions"
          />
          <i class="el-icon-close" style="align-self: center;margin-left: 20px" @click="componentFlag.enableDatePick = false" />
        </div>
        <el-button v-else icon="el-icon-date" style="margin-right: 20px" @click="componentFlag.enableDatePick = true" />
        <el-button-group>
          <el-button icon="el-icon-refresh-left" />
          <el-button icon="el-icon-share" />
          <el-button icon="el-icon-more" />
        </el-button-group>
        <el-dropdown trigger="click" @command="handleClickPlus">
          <span><el-button icon="el-icon-plus" style="margin-left: 20px" type="success" /></span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item>从书签添加</el-dropdown-item>
            <el-dropdown-item command="createComponent">新建组件</el-dropdown-item>
            <el-dropdown-item>新建描述</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </custom-header>

    <div style="margin-top: 25vh">
      <div>
        <el-button type="success" style="display:block;margin:0 auto" @click="componentFlag.enableCreateComponent = true">新建组件</el-button>
      </div>
    </div>

    <el-dialog
      title="新建组件"
      :visible.sync="componentFlag.enableCreateComponent"
      width="30%"
      center
    >
      <el-row>
        <el-col :span="8">
          <el-card shadow="hover" @click.native="jump('menu-analyze-meta-event')">
            元数据分析
          </el-card>
        </el-col>
      </el-row>
    </el-dialog>

  </div>
</template>

<script>
import CustomHeader from '_c/custom-header'
export default {
  name: 'Overview',
  components: {
    CustomHeader
  },
  data() {
    return {
      query: {
        dateRange: []
      },
      componentOption: {
        datePickerOptions: {}
      },
      componentFlag: {
        enableDatePick: false,
        enableCreateComponent: false
      },
      input: '',
      tabName: 'second',
      contented: false
    }
  },
  methods: {
    handleInput(val) {
      this.input = val
    },
    spanChange(e) {
      console.log(e.target.innerText)
    },
    handleClickPlus(command) {
      if (Object.is(command, 'createComponent')) {
        this.componentFlag.enableCreateComponent = true
      }
    },
    jump(name) {
      this.$router.push({ 'name': name ,query: {'app': this.$store.state.app.appId}})
    }
  }
}
</script>

<style type="scss">
  .custom-tools{
    .el-button {
      height: 40px;
      width: 50px;
      align-self: center;
    }

    i{
      align-self: center;
    }

    .el-button-group{
      align-self: center;
      width: 150px;
    }

    .el-button-group .el-button{
      padding: 0 10px;
      width: 50px;
    }
  }
</style>
