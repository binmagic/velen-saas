<template>
  <div class="app-container">
    <custom-header :show-name="$route.meta.title">
      <div slot="tools">
        <i class="el-icon-s-order">保存</i>
      </div>
    </custom-header>
    <el-container class="main">
      <el-header class="meta-event-header">
        <div style="height: 50px">
          <span>元事件</span>
          <el-select v-model="switchTable">
            <el-option value="1" />
          </el-select>
        </div>
        <el-divider />
        <div>
          <el-tag
            v-for="column in columns"
            :key="column.prop"
            closable>
            {{column.label}}
          </el-tag>
        </div>
      </el-header>
      <el-main>
        <custom-table :columns="columns"></custom-table>
      </el-main>
    </el-container>
  </div>
</template>

<style type="scss">
  .meta-event-container{

  }
  .meta-event-header{
    height: auto;
  }
</style>

<script>

import CustomTable from '_c/custom-table'
import CustomHeader from '_c/custom-header'
export default {
  components: {
    CustomTable,
    CustomHeader
  },
  data() {
    return {
      columns: [{
        label: '序号',
        prop: 'ID'
      }],
      switchTable: ''
    }
  }
}

</script>
