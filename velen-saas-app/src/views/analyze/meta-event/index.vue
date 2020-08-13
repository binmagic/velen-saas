<template>
  <div class="app-container">
    <custom-header :show-name="$route.meta.title">
      <div slot="tools">
        <el-popover
          placement="bottom"
          width="200"
          trigger="click"
          v-model="popoverVisible"
          @show="handleCleanData"
        >
          <el-button slot="reference" icon="el-icon-folder-add" />

          <el-form :model="formData">
            <el-form-item label="书签名">
              <el-input v-model="formData.name" />
            </el-form-item>
            <el-form-item label="备注">
              <el-input v-model="formData.comment" type="textarea" />
            </el-form-item>
            <el-form-item>
              <el-button @click="popoverVisible = false">取消</el-button>
              <el-button type="primary" @click="submitData">确定</el-button>
            </el-form-item>
          </el-form>
        </el-popover>

      </div>
    </custom-header>
    <el-container class="main">
      <el-header class="meta-event-header">
        <div style="position: relative;">
          <el-row>
            <el-col :span="24" style="height: 40px;">
              <span>元事件</span>
              <el-select v-model="switchEvent" size="small" @change="handleSelectEvent">
                <el-option v-for="event of meta_events" :key="event.id" :label="event.showName" :value="event.id" />
              </el-select>
              <span v-if="filterList.length>=1">的</span>
              <el-button class="meta-event-button" type="text" icon="el-icon-plus" @click="handleFilter">筛选条件
              </el-button>
            </el-col>
          </el-row>
          <screening-condition
            :filter.sync="filterList"
            :props="meta_props"
            @update-filter="handleUpdate"
            @get-btn-val="handleBtnVal"
          />
          <!--<div>
            <el-button type="primary" size="mini" icon="el-icon-plus" circle>
            </el-button>
          </div>-->
        </div>
      </el-header>
      <el-main>
        <el-row style="display: flex; align-items: center">
          <el-col :span="8">
            <el-date-picker
              v-model="originQuery.dateRange"
              type="daterange"
              align="right"
              unlink-panels
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
            />
          </el-col>
          <el-col :span="8" style="text-align: center;">
            <h4>{{ getMetaEventName() }}</h4>
          </el-col>
          <el-col :span="8" />
        </el-row>
        <custom-table :columns.sync="columns" :value.sync="data" :total.sync="total" :loading.sync="loading" @fetch="fetchData"/>
<!--        <custom-charts-->
<!--          :chart-name="chartName"-->
<!--        />-->
      </el-main>
    </el-container>
  </div>
</template>

<style type="scss">

</style>

<script>
import './index.scss'
import CustomTable from '_c/custom-table'
import CustomHeader from '_c/custom-header'
import CustomCharts from '_c/custom-charts'
import { query } from '@/api/query'
import ScreeningCondition from '../components/filter'

import { getMetaEvent, getMetaEventProp } from '@/api/metadata'
import { create } from '@/api/bookmarks'
import TagInput from '_c/tag-input'

export default {
  components: {
    CustomTable,
    CustomHeader,
    CustomCharts,
    TagInput,
    ScreeningCondition
  },
  data() {
    return {
      id: '',
      columns: [],
      data: [],
      total: 0,
      loading: false,
      switchEvent: '',
      filterValue: '',
      btnVal: '',
      filterList: [],
      meta_events: [],
      meta_props: {},
      params: '',
      query: {
        subTaskType: 'META_EVENT',
        measures: {
          props: [],
          filter: {
            union: 'AND',
            conditions: []
          },
          page: 1,
          limit: 10,
        },
      },
      originQuery: {
        dateRange: []
      },
      formData: {
        name: '',
        comment: ''
      },
      saveData: {
        data: '',
        name: '',
        config: '',
        type: '/meta_event_analytics/'
      },
      chartName: '测试',
      popoverVisible:false
    }
  },
  created() {
    this.fetchMetaEventProps()
    this.fetchMetaEvent()
  },
  methods: {
    submitData() {
      var filter = {}
      if (this.filterList.length >= 1) {
        var condition = []
        this.filterList.some(item => {
          condition.push({
            field: this.meta_events.find(v => {
              return this.switchEvent === v.id ? this.meta_events.name + '.' + item.name : ''
            }),
            function: item.filter,
            params: item.params
          })
        })
        filter['condition'] = condition
        if (this.filterList.length > 1) { filter['relation'] = this.btnVal }
      }
      this.saveData.name = this.formData.name
      this.saveData.config = JSON.stringify({
        comment: this.comment
      })
      this.saveData.data = JSON.stringify(this.query)
      create(this.saveData).then(resp => {
        this.id = resp.id
        this.$message.success("添加成功")
      })
      this.popoverVisible = false
    },
    fetchData(param) {
      console.log("fetchData")
      this.loading = true
      const params = Object.assign({}, this.query)
      params.measures = Object.assign({}, params.measures, param)
      const that = this
      query(params).then(resp => {
        const _data = []
        for(const index in resp.rows){
          const row = resp.rows[index]
          const _row = {}
          for(const key in row){
            _row[key.toLowerCase()] = row[key]
          }
          _data.push(_row)
        }
        this.data = _data
        this.total = resp.total || resp.rows.length
        setTimeout(function() {
          that.loading = false
        }, 1000)
      }).catch(err => {
        this.loading = false
      })
    },
    fetchMetaEventProps() {
      getMetaEventProp().then(resp => {
        for (const index in resp.items) {
          this.meta_props[resp.items[index].id] = resp.items[index]
        }
      })
    },
    fetchMetaEvent() {
      getMetaEvent().then(resp => {
        this.meta_events = resp.items
      })
    },
    handleSelectEvent() {
      let props = []
      for (const index in this.meta_events) {
        const meta_event = this.meta_events[index]
        if (Object.is(meta_event.id, this.switchEvent)) {
          props = meta_event.propIds
          break
        }
      }
      this.handleColumn(props)
      this.fetchData()
    },
    handleColumn(props) {
      this.columns.length = 0
      this.query.measures.props = []
      for (const index in props) {
        const prop = this.meta_props[props[index]]
        if (!prop) {
          continue
        }
        this.columns.push({
          'prop': prop.name.toLowerCase(),
          'label': prop.showName
        })
        this.query.measures.props.push(prop.name)
      }
      this.query.measures.eventName = this.switchEvent
    },
    getMetaEventName() {
      for (const index in this.meta_events) {
        const meta_event = this.meta_events[index]
        if (Object.is(meta_event.id, this.switchEvent)) {
          return meta_event.showName
        }
      }
    },
    handleInput(val) {

    },
    handleSelectChange(index) {
      this.filterList[index].filter = ''
    },
    handleFilter() {
      this.filterList.push({ switchProp: {}, filter: '', params: [], form: '', to: '' })
    },
    delRow(index) {
      this.filterList.splice(index, 1)
    },
    handleUpdate(val) {
      this.filterList = val
      this.query.measures.filter.conditions = []
      for (const index in this.filterList) {
        const data = this.filterList[index]
        this.query.measures.filter.conditions
          .push({ prop: data.switchProp.name, filter: data.filter, params: data.params })
      }
      this.fetchData()
    },
    handleBtnVal(val) {
      this.btnVal = val
    },
    handleCleanData(){
      this.formData.name = ''
      this.formData.comment = ''
    }
  }
}

</script>
