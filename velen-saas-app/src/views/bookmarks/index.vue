<template>
  <div>
    <div class="filter-container">
      <el-dropdown>
        <span class="el-dropdown-link">
            下拉菜单<i class="el-icon-arrow-down el-icon--right"></i>
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item>黄金糕</el-dropdown-item>
          <el-dropdown-item>狮子头</el-dropdown-item>
          <el-dropdown-item>螺蛳粉</el-dropdown-item>
          <el-dropdown-item disabled>双皮奶</el-dropdown-item>
          <el-dropdown-item divided>蚵仔煎</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>

      <el-button
        class="filter-item"
        style="margin-bottom: 20px;"
        type="primary"
        icon="el-icon-edit"
        @click="handleSelect"
      >
        {{ $t('app.button.add') }}
      </el-button>
    </div>

    <el-table
      v-loading="listLoading"
      :data="list"
      border
      fit
      highlight-current-row
      style="width: 100%;"
    >
      <el-table-column :label="$t('app.label.name')" align="center" :show-overflow-tooltip="true">
        <template slot-scope="{row}">
          <span>{{ row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('app.label.createTime')" align="center" :show-overflow-tooltip="true">
        <template slot-scope="{row}">
          <span>{{ row.createTime }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('app.label.owner')" align="center" :show-overflow-tooltip="true">
        <template slot-scope="{row}">
          <span>{{ row.owner }}</span>
        </template>
      </el-table-column>
      <el-table-column label="#" align="center" class-name="small-padding fixed-width">
        <template slot-scope="{row}">
          <el-button type="primary" size="mini" @click="goDetail(row)">
            {{ $t('app.button.setting') }}
          </el-button>
          <el-button v-if="row.status!='deleted'" size="mini" type="danger" @click="handleDelete(row)">
            {{ $t('app.button.del') }}
          </el-button>
        </template>
      </el-table-column>
    </el-table>

  </div>
</template>

<script>

export default {
  data() {
    return {
      listLoading: false,
      list: []
    }
  },
  methods: {
    handleSelect() {

    }
  }
}
</script>
