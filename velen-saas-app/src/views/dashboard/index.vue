<template>
  <div class="app-container">
    <custom-header :show-name="$route.meta.title">
      <div v-if="enableDatePick" style="margin-right: 40px; align-self: center;display: flex">
        <el-date-picker
          v-model="query.dateRange"
          type="daterange"
          align="right"
          unlink-panels
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :picker-options="pickerOptions"
        />
        <i class="el-icon-close" style="align-self: center;margin-left: 20px" @click="showDatePick" />
      </div>
      <el-button v-else icon="el-icon-date" size="mini" style="margin-right: 20px" @click="showDatePick" />
      <el-button-group>
        <el-button icon="el-icon-refresh-left" />
        <el-button icon="el-icon-share" />
        <el-button icon="el-icon-more" />
      </el-button-group>
      <el-dropdown trigger="click">
        <span><el-button icon="el-icon-plus" size="mini" style="margin-left: 20px" type="success"/></span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item>从书签添加</el-dropdown-item>
          <el-dropdown-item>新建标签</el-dropdown-item>
          <el-dropdown-item>新建描述</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </custom-header>

    <div style="margin-top: 25vh">
      <div>
        <el-button type="success" style="display:block;margin:0 auto">新建组件</el-button>
      </div>
    </div>
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
      pickerOptions: {},
      enableDatePick: false,
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
    showDatePick() {
      this.enableDatePick = !this.enableDatePick
    }
  }
}
</script>
