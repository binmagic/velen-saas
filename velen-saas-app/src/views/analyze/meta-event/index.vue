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
          <el-col :span="8">

          </el-col>
        </el-row>
        <custom-table :columns="columns" :value="data"/>
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
import {reportMetaEvent} from '@/api/query'

import { getMetaEvent, getMetaEventProp } from '@/api/metadata'

export default {
  components: {
    CustomTable,
    CustomHeader
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
      }
    }
  },
  created() {
    this.fetchMetaEventProps()
    this.fetchMetaEvent()
  },
  methods: {
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
