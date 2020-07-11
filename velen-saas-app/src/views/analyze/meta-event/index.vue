<template>
  <div class="app-container">
    <custom-header :show-name="$route.meta.title">
      <div slot="tools">
        <el-popover
          placement="bottom"
          width="200"
          trigger="click"
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
              <el-button>取消</el-button>
              <el-button type="primary" @click="submitData">确定</el-button>
            </el-form-item>
          </el-form>
        </el-popover>

      </div>
    </custom-header>
    <el-container class="main">
      <el-header class="meta-event-header">
        <div style="height: 50px">
          <span>元事件</span>
          <el-select v-model="switchEvent" @change="handleSelectEvent">
            <el-option v-for="event of meta_events" :key="event.id" :label="event.showName" :value="event.id" />
          </el-select>
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
        <custom-table :columns="columns" :value="data" />
        <custom-charts
          :chartName="chartName"
        ></custom-charts>
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
import CustomCharts from '_c/custom-charts'
import { reportMetaEvent } from '@/api/query'

import { getMetaEvent, getMetaEventProp } from '@/api/metadata'
import { create } from '@/api/bookmarks'

export default {
  components: {
    CustomTable,
    CustomHeader,
    CustomCharts
  },
  data() {
    return {
      id: '',
      columns: [],
      data: [],
      switchEvent: '',
      meta_events: [],
      meta_props: {},
      query: {

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
      chartName:'测试'
    }
  },
  created() {
    this.fetchMetaEventProps()
    this.fetchMetaEvent()
  },
  methods: {
    submitData() {
      console.log('xxxxxsubmitdata')
      this.saveData.name = this.formData.name
      this.saveData.config = JSON.stringify({
        comment: this.comment
      })
      this.saveData.data = JSON.stringify({
        measures: this.switchEvent,
        filter: {},
        chartsType: 'pie',
        rangeText: '',
        from_date: '2020-06-08', to_date: '2020-06-09'
      })
      create(this.saveData).then(resp => {
        this.id = resp.id
      })
    },
    fetchData() {
      reportMetaEvent(this.query).then(resp => {
        this.data = resp.items
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
      console.log(props)
      for (const index in props) {
        const prop = this.meta_props[props[index]]
        this.columns.push({
          'prop': prop.name,
          'label': prop.showName
        })
      }
    },
    getMetaEventName() {
      for (const index in this.meta_events) {
        const meta_event = this.meta_events[index]
        if (Object.is(meta_event.id, this.switchEvent)) {
          return meta_event.showName
        }
      }
    }
  }
}

</script>
