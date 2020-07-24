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
      <el-header class="property-header">
        <el-row>
          <el-col>
            <span>显示</span>
            <el-select size="small" v-model="switchProp">
              <el-option value="a">a</el-option>
            </el-select>
          </el-col>
        </el-row>
        <el-row v-for="(groupItem,index) in groupList" style="margin-top: 10px;margin-left: 25px;">
          <el-col :span="6">
            <span>按</span>
            <el-select size="small" v-model="groupItem.switchGroup">
              <el-option value="a">a</el-option>
            </el-select>
            <span>查看</span>
            <el-button v-if="groupList.length>1" type="text" icon="el-icon-close" @click="delGroup(index)"></el-button>
            <el-button v-if="index+1 === groupList.length" type="text" icon="el-icon-plus"
                       @click="addGroup"></el-button>
          </el-col>
        </el-row>
        <screening-condition :filter="filter" :props="meta_props" @update-filter="handleUpdate"/>
        <el-row>
          <el-col>
            <el-button type="text" icon="el-icon-plus" @click="handleFilter">筛选条件</el-button>
          </el-col>
        </el-row>
      </el-header>
      <el-main>
        <h3 class="property-title">总人数</h3>
        <custom-table></custom-table>
      </el-main>
    </el-container>
  </div>
</template>

<script>
  import CustomTable from '_c/custom-table'
  import CustomHeader from '_c/custom-header'
  import CustomCharts from '_c/custom-charts'
  import ScreeningCondition from '../components/filter'
  import {create} from '@/api/bookmarks'
  import {getMetaEventProp} from '@/api/metadata'

  export default {
    components: {
      CustomTable,
      CustomHeader,
      CustomCharts,
      ScreeningCondition
    },
    data() {
      return {
        formData: {
          name: '',
          comment: ''
        },
        saveData: {
          data: '',
          name: '',
          config: '',
          type: '/property_analytics/'
        },
        switchProp: '',
        groupList: [
          {switchGroup: ''}
        ],
        filter: [],
        meta_props:{},
      }
    },
    created() {
      this.fetchMetaEventProps()
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
      fetchMetaEventProps() {
        getMetaEventProp().then(resp => {
          for (const index in resp.items) {
            this.meta_props[resp.items[index].id] = resp.items[index]
          }
        })
      },
      addGroup() {
        this.groupList.push({switchGroup: ''})
      },
      delGroup(index) {
        this.groupList.splice(index, 1)
      },
      handleFilter() {
        this.filter.push({switchProp: {}, filter: '', params: '', form: '', to: ''})
      },
      handleUpdate(val) {
        this.filter = val
      }
    }

  }
</script>

<style type="scss">
  .property-header {
    height: auto !important;
  }

  .property-title {
    text-align: center;
  }
</style>
