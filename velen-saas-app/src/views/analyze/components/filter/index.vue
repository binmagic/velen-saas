<template>
  <div style="position: relative;">
    <div v-if="filterList.length>=2" class="filter-group-relation">
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
          <el-option v-for="(prop,key) in props" :key="key" :label="prop.showName"
                     :value="prop"/>
        </el-select>
      </el-col>
      <el-col :span="4">
        <el-select v-show="filterItem.switchProp.type === 'NUMBER'" size="small" v-model="filterItem.filter"
                   @change="handleUpdate">
          <el-option v-for="item in $const.filter_type.option1" :key="item.value" :label="item.label"
                     :value="item.value"></el-option>
        </el-select>
        <el-select v-show="filterItem.switchProp.type === 'STRING'" size="small" v-model="filterItem.filter"
                   @change="handleUpdate">
          <el-option v-for="item in $const.filter_type.option2" :key="item.value" :label="item.label"
                     :value="item.value"></el-option>
        </el-select>
        <el-select v-show="filterItem.switchProp.type === 'BOOL'" size="small" v-model="filterItem.filter"
                   @change="handleUpdate">
          <el-option v-for="item in $const.filter_type.option3" :key="item.value" :label="item.label"
                     :value="item.value"></el-option>
        </el-select>
        <el-select v-show="filterItem.switchProp.type === 'DATETIME'" size="small" v-model="filterItem.filter"
                   @change="handleUpdate">
          <el-option v-for="item in $const.filter_type.option4" :key="item.value" :label="item.label"
                     :value="item.value"></el-option>
        </el-select>
      </el-col>
      <el-col
        v-if="filterItem.filter ==='equal' || filterItem.filter ==='notEqual' || filterItem.filter ==='contain' || filterItem.filter === 'notContain'"
        :span="14">
        <tag-input :size="'1'" @input="handleInput(filterItem)"></tag-input>
      </el-col>
      <el-col
        v-else-if="filterItem.filter==='less'|| filterItem.filter === 'greater' || filterItem.filter==='rlike' || filterItem.filter==='notrlike'"
        :span="3">
        <el-input v-model="filterItem.params[0]" size="small" @blur="handleUpdate"></el-input>
      </el-col>
      <el-col class="between-input" v-else-if="filterItem.filter ==='between'" :span="4">
        <el-input v-model="filterItem.params[0]" size="small" @blur="handleUpdate"/>
        与
        <el-input v-model="filterItem.params[1]" size="small" @blur="handleUpdate"/>
        之间
      </el-col>
      <el-col class="between-date-picker" v-else-if="filterItem.filter === 'absolute_between'" :span="5">
        <span>在</span>
        <el-date-picker
          v-model="filterItem.params"
          size="small"
          type="datetimerange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期">
        </el-date-picker>
        <span>之间</span>
      </el-col>
      <el-col class="within-data-picker" v-else-if="filterItem.filter === 'relative_within'" :span="5">
        <span>在</span>
        <el-input v-model="filterItem.params[0]" size="small"></el-input>
        <span>天</span>
        <el-select size="small" v-model="filterItem.params[1]">
          <el-option label="之内" value="within"/>
          <el-option label="之前" value="before"/>
        </el-select>
      </el-col>
      <el-col v-else-if="filterItem.filter ==='relative_between'" :span="5">
        <span>在过去</span>
        <el-input v-model="filterItem.params[0]" size="small" />
        <span>天到过去</span>
        <el-input v-model="filterItem.params[1]" size="small" />
        <span>天之内</span>
        <el-tooltip effect="dark" content="起始数值要大于终止数值" placement="top">
          <i class="el-icon-question" />
        </el-tooltip>
      </el-col>
      <el-col :span="1" class="delRow">
              <span @click="delRow(index)" style="line-height: 32px;cursor:pointer;">
                <i class="el-icon-close" style="font-size: 18px"/></span>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import './index.scss'
  import TagInput from '_c/tag-input'

  export default {
    name: "index",
    components: {
      TagInput
    },
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
      }
    },
    computed: {
      filterList() {
        return JSON.parse(JSON.stringify(this.filter))
      },

    },
    methods: {
      handleSelectChange(index) {
        this.filterList[index].filter = ''
        this.handleUpdate()
      },
      handleInput(item, val) {
        item.params = val
        this.handleUpdate()
      },
      delRow(index) {
        this.filterList.splice(index, 1)
        this.handleUpdate()
      },
      handleUpdate() {
        this.$emit('update-filter', this.filterList)
        this.$emit('get-btn-val',this.filterList.length =1 ? '' : this.btnVal)
      }
    }
  }
</script>

<style scoped>

</style>
