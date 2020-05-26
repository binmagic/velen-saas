<template>
  <div>
    <el-table
      ref="tableMain"
      :data="insideTableData"
      style="width: 100%"
    >
      <el-table-column v-for="column of insideColumns" :prop="column.prop" :label="column.lable" :width="column.width" />
    </el-table>
  </div>
</template>

<script>
export default {
  name: 'Table',
  props: {
    value: {
      type: Array,
      default() {
        return []
      }
    },
    columns: {
      type: Array,
      default() {
        return []
      }
    },
    /**
       * @description 全局设置是否可编辑
       */
    editable: {
      type: Boolean,
      default: false
    },
    searchable: {
      type: Boolean,
      default: false
    },
    searchPlace: {
      type: String,
      default: 'top'
    }
  },
  data() {
    return {
      insideColumns: [],
      insideTableData: [],
      searchKey: '',
      searchValue: ''
    }
  },
  watch: {
    columns(columns) {
      this.handleColumns(columns)
      this.setDefaultSearchKey()
    },
    value() {
      this.handleTableData()
    }
  },
  mounted() {
    this.handleColumns(this.columns)
    this.setDefaultSearchKey()
    this.handleTableData()
  },
  methods: {
    supportEdit(res, index) {

    },
    supportHandle(res) {

    },
    handleColumns(columns) {
      this.insideColumns = columns.map((item, index) => {
        let res = item
        if (res.editable) res = this.supportEdit(res, index)
        if (res.key === 'handle') res = this.supportHandle(res)
        return res
      })
    },
    setDefaultSearchKey(key) {

    },
    handleTableData() {
      this.insideTableData = this.value.map((item, index) => {
        const res = item
        res.initRowIndex = index
        return res
      })
    }
  }

}
</script>
