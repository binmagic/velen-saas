<template>
  <div>
    <el-table
      ref="tableMain"
      :data="insideTableData"
      v-loading="loading"
      style="width: 100%"
    >
      <el-table-column v-for="column of insideColumns" :prop="column.prop" :label="column.label" :align="column.align" :width="column.width"/>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="query.page"
      :limit.sync="query.limit"
      @pagination="fetchData"
    />
  </div>
</template>

<script>
import Pagination from '@/components/Pagination' // secondary package based on el-pagination
export default {
  name: 'Table',
  components: {
    Pagination
  },
  props: {
    value: {
      type: Array,
      default() {
        return []
      }
    },
    total: {
      type: Number,
      default() {
        return 0
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
    },
    loading: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      insideColumns: [],
      insideTableData: [],
      searchKey: '',
      searchValue: '',
      default_column: {
        align: 'center',
        width: 'auto'
      },
      query: {
        page: 1,
        limit: 10,
      }
    }
  },
  watch: {
    columns(columns) {
      this.handleColumns(columns)
      this.setDefaultSearchKey()
    },
    value() {
      this.handleTableData()
    },
    total() {
      console.log('total change')
    },
    loading() {
      console.log('loading change')
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
      console.log("columns")
      this.insideColumns = columns.map((item, index) => {
        let res = Object.assign({}, this.default_column, item)
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
    },
    fetchData() {
      this.$emit('fetch', this.query)
    }
  }

}
</script>
