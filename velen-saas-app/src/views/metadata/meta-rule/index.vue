<template>
  <div class="meta-rule-container">
    <div class="meta-rule-title">
      <p>入库校验规则设置</p>
    </div>
    <el-card style="margin-bottom: 20px">
      <p class="meta-card-header">
        事件与事件属性的入库设置
      </p>
      <p style="margin: 0;font-size: 14px;">
        上报事件属性的类型与「事件属性」已有事件的类型不一致时，系统将按规则尝试自动转换，如无法完成则视为异常数据不能入库。
      </p>
      <div class="meta-card-form">
        <el-checkbox v-model="eventCheck">强校验模式</el-checkbox>
        <el-form label-position="top">
          <el-form-item label="解析格式:">
            <el-button type="primary" @click="addTableRow('event')">添加</el-button>
            <el-table :data="eventTable" max-height="200">
              <el-table-column prop="name" label="解析器类型"/>
              <el-table-column label="规则">
                <template slot-scope="scope">
                  <span v-if="!scope.row.update">{{scope.row.rule}}
                  </span>
                  <el-input v-else v-model="scope.row.rule" @blur="$set(scope.row,'update',false)" v-focus></el-input>
                </template>
              </el-table-column>
              <el-table-column label="操作">
                <template slot-scope="scope">
                  <el-button type="text" @click="updateRow(scope.row)">修改</el-button>
                  <el-popconfirm
                    confirmButtonText="确定"
                    cancelButtonText="取消"
                    icon="el-icon-info"
                    iconColor="red"
                    title="删除该解析器?"
                    @onConfirm="delRow(scope.row,'event')"
                  >
                    <el-button slot="reference" type="text" style="color: red;">删除</el-button>
                  </el-popconfirm>
                </template>
              </el-table-column>
            </el-table>
          </el-form-item>
          <el-row>
            <el-col v-for="event in events" :span="4" style="margin-right: 50px;">
              <span>{{event.label}}</span>
              <p class="meta-key-desc">{{event.desc}}</p>
            </el-col>
          </el-row>
          <p>Key规则
            <el-link type="primary" @click="keyVisible=true">默认规则</el-link>
          </p>
          <p>入库规则
            <el-link type="primary" @click="ruleVisible=true">未设置任意格式ID均可上报入库</el-link>
          </p>
        </el-form>
      </div>
    </el-card>
    <el-card>
      <p class="meta-card-header">
        用户属性入库设置
      </p>
      <p style="margin: 0;font-size: 14px;">
        上报用户属性的类型与「用户属性」已有事件的类型不一致时，系统将按规则自动转换，如无法自动转换则视为异常数据不能入库。
      </p>
      <div class="meta-card-form">
        <el-checkbox v-model="userCheck">强校验模式</el-checkbox>
        <el-form label-position="top">
          <el-form-item label="解析格式:">
            <el-button type="primary" @click="addTableRow('profile')">添加</el-button>
            <el-table :data="profileTable" max-height="200">
              <el-table-column prop="name" label="解析器类型"/>
              <el-table-column prop="rule" label="规则">
                <template slot-scope="scope">
                  <span v-if="!scope.row.update">{{scope.row.rule}}
                  </span>
                  <el-input v-else v-model="scope.row.rule" @blur="$set(scope.row,'update',false)" v-focus></el-input>
                </template>
              </el-table-column>
              <el-table-column label="操作">
                <template slot-scope="scope">
                  <el-button type="text" @click="updateRow(scope.row)">修改</el-button>
                  <el-popconfirm
                    confirmButtonText="确定"
                    cancelButtonText="取消"
                    icon="el-icon-info"
                    iconColor="red"
                    title="删除该解析器?"
                    @onConfirm="delRow(scope.row,'profile')"
                  >
                    <el-button slot="reference" type="text" style="color: red;">删除</el-button>
                  </el-popconfirm>
                </template>
              </el-table-column>
            </el-table>
          </el-form-item>
          <el-row>
            <el-col v-for="profile in profiles" :span="4" style="margin-right: 50px;">
              <span>{{profile.label}}</span>
              <p class="meta-key-desc">{{profile.desc}}</p>
            </el-col>
          </el-row>
          <p>Key规则
            <el-link type="primary" @click="keyVisible=true">默认规则</el-link>
          </p>
          <p>入库规则
            <el-link type="primary" @click="ruleVisible=true">未设置任意格式ID均可上报入库</el-link>
          </p>
        </el-form>
      </div>
    </el-card>
    <key-dialog
      :visible="keyVisible"
      @close-key-rule="handleCloseKey"
    />
    <rule-dialog
      :visible="ruleVisible"
      @close-rule="handleCloseRule"
    />
    <add-dialog
      :visible="addVisible"
      :type="type"
      @close-add-rule="handleAddClose"
      @add-event-rule="addEventRule"
      @add-profile-rule="addProfileRule"
    />
  </div>
</template>

<script>
  import KeyDialog from './key-dialog'
  import RuleDialog from './rule-dialog'
  import AddDialog from './add-rule'

  export default {
    components: {
      KeyDialog,
      RuleDialog,
      AddDialog
    },
    directives: {
      focus: {
        inserted: function (el) {
          el.querySelector('input').focus();
        }
      }
    },
    data() {
      return {
        eventCheck: false,
        userCheck: false,
        addVisible: false,
        type: '',
        keyVisible: false,
        ruleVisible: false,
        eventTable: [{
          name: '正则解释器',
          rule: '.*'
        }, {
          name: 'JSON',
          rule: ''
        }],
        profileTable: [{
          name: '正则解释器',
          rule: '.*'
        }, {
          name: 'JSON',
          rule: ''
        }],
        events: [{
          label: '接收用户「登录ID」',
          value: 'distinct_id',
          desc: '用户在产品内的唯一标识,不符合以下规则时,不能入库'
        }, {
          label: '接收用户「设备ID」',
          value: 'device_id',
          desc: '不符合以下规则时,不能入库'
        }, {
          label: '接收事件「事件时间」',
          value: 'time',
          desc: '不符合以下规则时,不能入库'
        }, {
          label: '接收事件「事件名」',
          value: 'event',
          desc: '不符合以下规则时,不能入库'
        }, {
          label: '接收事件「项目ID」',
          value: 'project',
          desc: '不符合以下规则时,不能入库'
        },],
        profiles: [{
          label: '接收用户「登录ID」',
          value: 'distinct_id',
          desc: '用户在产品内的唯一标识,不符合以下规则时,不能入库'
        }, {
          label: '接收用户「设备ID」',
          value: 'device_id',
          desc: '不符合以下规则时,不能入库'
        }, {
          label: '接收用户「时间」',
          value: 'time',
          desc: '不符合以下规则时,不能入库'
        }, {
          label: '接收用户「项目ID」',
          value: 'project',
          desc: '不符合以下规则时,不能入库'
        }, {
          label: '接收用户「Type」',
          value: 'profile_user',
          desc: '不符合以下规则时,不能入库'
        },],
        themeSet: '',
      }
    },
    methods: {
      handleCloseKey() {
        this.keyVisible = false
      },
      handleCloseRule() {
        this.ruleVisible = false
      },
      handleAddClose() {
        this.addVisible = false
      },
      addTableRow(type) {
        this.addVisible = true
        this.type = type
      },
      addEventRule(val) {
        this.eventTable.push(val)
      },
      addProfileRule(val) {
        this.profileTable.push(val)
      },
      updateRow(row) {
        if (row.name !== 'JSON')
          this.$set(row, 'update', true)
      },
      delRow(row, type) {
        if (type === 'event') {
          this.eventTable = this.eventTable.filter(item => {
            return item !== row
          })
        } else if (type === 'profile') {
          this.profileTable = this.profileTable.filter(item => {
            return item !== row
          })
        }
      }
    }
  }
</script>

<style lang="scss">
  @import "./meta-rule.scss";
</style>
