<template>
  <div class="app-container">
    <custom-header :show-name="$route.meta.title">
      <div slot="tools">
        <el-popover
          placement="bottom"
          width="200"
          trigger="click"
        >
          <el-button slot="reference" icon="el-icon-folder-add"/>

          <el-form :model="formData">
            <el-form-item label="书签名">
              <el-input v-model="formData.name"/>
            </el-form-item>
            <el-form-item label="备注">
              <el-input v-model="formData.comment" type="textarea"/>
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
        <div style="position: relative;">
          <el-row>
            <el-col :span="24" style="height: 40px;">
              <span>元事件</span>
              <el-select size="small" v-model="switchEvent" @change="handleSelectEvent">
                <el-option v-for="event of meta_events" :key="event.id" :label="event.showName" :value="event.id"/>
              </el-select>
              <span v-if="filterList.length>=1">的</span>
              <el-button class="meta-event-button" type="text" icon="el-icon-plus" @click="handleFilter">筛选条件
              </el-button>
            </el-col>
          </el-row>
          <!--<div v-if="filterList.length>=2" class="filter-group-relation">
            <div class="relation-topline"></div>
            <el-button size="small" v-model="btnVal" @click="btnVal==='and'?btnVal = 'or':btnVal = 'and'">
              {{btnVal==='and'?'且':'或'}}
            </el-button>
            <div class="relation-bottomline"></div>
          </div>
          <el-row v-if="filterList.length>=1" v-for="(filterItem,index) in filterList" class="filterRow">
            <el-col :span="4">
              <el-select size="small" v-model="filterItem.switchProp" value-key="id"
                         @change="handleSelectChange(index)">
                <el-option v-for="(prop,key) in meta_props" :key="key" :label="prop.showName"
                           :value="prop"></el-option>
              </el-select>
            </el-col>
            <el-col :span="4">
              <el-select v-show="filterItem.switchProp.type === 'NUMBER'" size="small" v-model="filterItem.filter">
                <el-option v-for="item in $const.filter_type.option1" :key="item.value" :label="item.label"
                           :value="item.value"></el-option>
              </el-select>
              <el-select v-show="filterItem.switchProp.type === 'STRING'" size="small" v-model="filterItem.filter">
                <el-option v-for="item in $const.filter_type.option2" :key="item.value" :label="item.label"
                           :value="item.value"></el-option>
              </el-select>
              <el-select v-show="filterItem.switchProp.type === 'BOOL'" size="small" v-model="filterItem.filter">
                <el-option v-for="item in $const.filter_type.option3" :key="item.value" :label="item.label"
                           :value="item.value"></el-option>
              </el-select>
              <el-select v-show="filterItem.switchProp.type === 'DATETIME'" size="small" v-model="filterItem.filter">
                <el-option v-for="item in $const.filter_type.option4" :key="item.value" :label="item.label"
                           :value="item.value"></el-option>
              </el-select>
            </el-col>
            <el-col
              v-if="filterItem.filter ==='equal' || filterItem.filter ==='notEqual' || filterItem.filter ==='contain' || filterItem.filter === 'notContain'"
              :span="14">
              <tag-input :size="'1'" @input="handleInput"></tag-input>
            </el-col>
            <el-col
              v-else-if="filterItem.filter==='less'|| filterItem.filter === 'greater' || filterItem.filter==='rlike' || filterItem.filter==='notrlike'"
              :span="3">
              <el-input v-model="params" size="small"></el-input>
            </el-col>
            <el-col class="between-input" v-else-if="filterItem.filter ==='between'" :span="4">
              <el-input v-model="filterItem.form" size="small"></el-input>
              与
              <el-input v-model="filterItem.to" size="small"></el-input>
              之间
            </el-col>
            <el-col :span="1" class="delRow">
              <span @click="delRow(index)" style="line-height: 32px;cursor:pointer;">
                <i class="el-icon-close" style="font-size: 18px"/></span>
            </el-col>
          </el-row>-->
          <screening-condition :filter="filterList" :props="meta_props" @update-filter="handleUpdate"
                               @get-btn-val="handleBtnVal"/>
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
          <el-col :span="8"/>
        </el-row>
        <custom-table :columns="columns" :value="data"/>
        <custom-charts
          :chartName="chartName"
        ></custom-charts>
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
  import {reportMetaEvent} from '@/api/query'
  import ScreeningCondition from '../components/filter'

  import {getMetaEvent, getMetaEventProp} from '@/api/metadata'
  import {create} from '@/api/bookmarks'
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
        switchEvent: '',
        filterValue: '',
        btnVal: '',
        filterList: [],
        meta_events: [],
        meta_props: {},
        params: '',
        query: {},
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
          if (this.filterList.length > 1)
            filter['relation'] = this.btnVal
        }
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
      },
      handleInput(val) {

      },
      handleSelectChange(index) {
        this.filterList[index].filter = ''
      },
      handleFilter() {
        this.filterList.push({switchProp: {}, filter: '', params: [], form: '', to: ''})
      },
      delRow(index) {
        this.filterList.splice(index, 1)
      },
      handleUpdate(val) {
        this.filterList = val
      },
      handleBtnVal(val) {
        this.btnVal = val
      }
    }
  }

</script>
