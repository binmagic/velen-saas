<template>
  <div style="position: relative;">
    <div v-if="filterList.length>=2" class="filter-group-relation">
      <div class="relation-topline"/>
      <el-button v-model="btnVal" size="small" @click="btnVal==='and'?btnVal = 'or':btnVal = 'and'">
        {{ btnVal==='and'?'且':'或' }}
      </el-button>
      <div class="relation-bottomline"/>
    </div>
    <template v-if="filterList.length>=1">
      <el-row v-for="(filterItem,index) in filterList" class="filterRow">
        <el-col :span="3">
          <el-select
            v-model="filterItem.switchProp"
            size="small"
            value-key="id"
            @change="handleSelectChange(index)"
          >
            <el-option
              v-for="(prop,key) in props"
              :key="key"
              :label="prop.showName"
              :value="prop"
            />
          </el-select>
        </el-col>
        <el-col :span="3" style="margin-left: 10px">
          <el-select
            v-show="equalsIgnoreCase(filterItem.switchProp.type,'NUMBER')"
            v-model="filterItem.filter"
            size="small"
            @change="handleUpdate"
          >
            <el-option
              v-for="item in $const.filter_type.option1"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
          <el-select
            v-show="equalsIgnoreCase(filterItem.switchProp.type, 'STRING')"
            v-model="filterItem.filter"
            size="small"
            @change="handleUpdate"
          >
            <el-option
              v-for="item in $const.filter_type.option2"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
          <el-select
            v-show="equalsIgnoreCase(filterItem.switchProp.type,'BOOL')"
            v-model="filterItem.filter"
            size="small"
            @change="handleUpdate"
          >
            <el-option
              v-for="item in $const.filter_type.option3"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
          <el-select
            v-show="equalsIgnoreCase(filterItem.switchProp.type, 'DATETIME')"
            v-model="filterItem.filter"
            size="small"
            @change="handleUpdate"
          >
            <el-option
              v-for="item in $const.filter_type.option4"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-col>
        <el-col
          v-if="filterItem.filter ==='equal' || filterItem.filter ==='notEqual' || filterItem.filter ==='contain' || filterItem.filter === 'notContain'"
          :span="4" style="margin-left: 10px"
        >
          <tag-input :size="'1'" @input="handleInput(filterItem, arguments)"/>
        </el-col>
        <el-col
          v-else-if="filterItem.filter==='less'|| filterItem.filter === 'greater' || filterItem.filter==='rlike' || filterItem.filter==='notrlike'"
          :span="3" style="margin-left: 10px"
        >
          <el-input v-model="params[0]" size="small" @blur="handleUpdateParams(index)"/>
        </el-col>
        <el-col v-else-if="filterItem.filter ==='between'" class="between-input" :span="4" style="margin-left: 10px">
          <el-input v-model="params[0]" size="small" @blur="handleUpdateParams(index)"/>
          与
          <el-input v-model="params[1]" size="small" @blur="handleUpdateParams(index)"/>
          之间
        </el-col>
        <el-col v-else-if="filterItem.filter === 'absolute_between'" class="between-date-picker" :span="8"
                style="margin-left: 10px">
          <span>在</span>
          <el-date-picker
            v-model="params"
            size="small"
            type="datetimerange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            @change="handleUpdateParams(index)"
          />
          <span>之间</span>
        </el-col>
        <el-col v-else-if="filterItem.filter === 'relative_within'" class="within-data-picker" :span="4"
                style="margin-left: 10px">
          <span>在</span>
          <el-input v-model="params[0]" size="small" @blur="handleUpdateParams(index)"/>
          <span>天</span>
          <el-select v-model="params[1]" size="small" @change="handleUpdateParams(index)">
            <el-option label="之内" value="within"/>
            <el-option label="之前" value="before"/>
          </el-select>
        </el-col>
        <el-col v-else-if="filterItem.filter ==='relative_between'" :span="6.5" style="margin-left: 10px"
                class="within-data-picker">
          <span>在过去</span>
          <el-input v-model="params[0]" size="small" @blur="handleUpdateParams(index)"/>
          <span>天到过去</span>
          <el-input v-model="params[1]" size="small" @blur="handleUpdateParams(index)"/>
          <span>天之内</span>
          <el-tooltip effect="dark" content="起始数值要大于终止数值" placement="top">
            <i class="el-icon-question"/>
          </el-tooltip>
        </el-col>
        <el-col :span="1" class="delRow">
        <span style="line-height: 32px;cursor:pointer;" @click="delRow(index)">
          <i class="el-icon-close" style="font-size: 18px"/></span>
        </el-col>
      </el-row>
    </template>
  </div>
</template>

<script>
  import './index.scss'
  import TagInput from '_c/tag-input'

  export default {
    name: 'Index',
    components: {
      TagInput
    },
    filters: {},
    props: {
      filter: {
        type: Array,
        default() {
          return []
        }
      },
      props: {
        type: Object,
        default() {
          return {}
        }
      }
    },
    data() {
      return {
        btnVal: 'and',
        params: []
      }
    },
    computed: {
      filterList() {
        return JSON.parse(JSON.stringify(this.filter))
      }
    },
    methods: {
      handleSelectChange(index) {
        console.log(this.filterList[index])
        this.filterList[index].filter = ''
        //this.handleUpdate()
        this.$emit('update:filter', this.filterList)
      },
      handleInput(item, val) {
        val = val[0]
        item.params = val
        this.handleUpdate()
      },
      delRow(index) {
        this.filterList.splice(index, 1)
        this.handleUpdate()
      },
      handleUpdate() {
        this.$emit('update-filter', this.filterList)
        this.$emit('get-btn-val', this.filterList.length === 1 ? '' : this.btnVal)
      },
      equalsIgnoreCase(source, target) {
        if (source == null || target == null) {
          return false
        }
        return source.toLowerCase() == target.toLowerCase()
      },
      handleUpdateParams(index){
        this.filterList[index].params = this.params
        this.handleUpdate()
      }
    }
  }
</script>

<style scoped>

</style>
