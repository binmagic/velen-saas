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
                  <el-input v-else v-model="scope.row.rule" @blur="updEventRule(scope.row)" v-focus></el-input>
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
              <p>Key规则</p>
              <el-link type="primary" @click="showParserTable(event.name,event.keyRule,'event')">显示匹配规则</el-link>
              <!--<el-input type="textarea" :autosize="{ minRows:2,maxRows:4 }" style="width: 80%;margin-top: 10px;"></el-input>-->
              <!--<p>入库规则</p>
              <el-input type="textarea" :autosize="{ minRows:2,maxRows:4 }" v-model="event.rule"
                        @change="updEventKeyRule(event)"/>-->
            </el-col>
          </el-row>
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
                  <span v-if="!scope.row.update">{{ scope.row.rule }}
                  </span>
                  <el-input v-else v-model="scope.row.rule" @blur="updProfileRule(scope.row)" v-focus></el-input>
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
              <p>Key规则</p>
              <el-link type="primary" @click="showParserTable(profile.name,profile.keyRule,'profile')">显示匹配规则</el-link>
              <!--<el-input type="textarea" :autosize="{ minRows:2,maxRows:4 }" style="width: 80%;margin-top: 10px;"></el-input>-->
              <!--<p>入库规则</p>
              <el-input type="textarea" :autosize="{ minRows:2,maxRows:4 }" v-model="profile.rule"
                        @change="updProfileKeyRule(profile)"/>-->
            </el-col>
          </el-row>
        </el-form>
      </div>
    </el-card>
    <parser-table
      :visible.sync="parserVisible"
      :type="type"
      :data.sync="data"
      :keyName="keyName"
      @find-event-data="findEvenKeyRule"
      @find-profile-data="findProfileKeyRule"
      @close-parser-table="handleParserClose"
    />
    <add-dialog
      :visible.sync="addVisible"
      :type="type"
      @close-add-rule="handleAddClose"
      @add-event-rule="insertEventRule"
      @add-profile-rule="insertProfileRule"
    />
  </div>
</template>

<script>
  import AddDialog from './add-rule'
  import ParserTable from './parser-table'
  import {
    getEventRule,
    addEventRule,
    updateEventRule,
    deleteEventRule,
    getEventKeyRule
  } from "@/api/eventRule";
  import {
    getProfileRule,
    addProfileRule,
    updateProfileRule,
    deleteProfileRule,
    getProfileKeyRule
  } from "@/api/profileRule";

  export default {
    components: {
      AddDialog,
      ParserTable
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
        parserVisible: false,
        type: '',
        keyName: '',
        eventTable: [],
        profileTable: [],
        profileRule: '',
        data: [],
        events: [{
          label: '接收用户「设备ID」',
          name: 'device_id',
          keyRule: []
        }, {
          label: '接收用户「用户ID」',
          name: 'distinct_id',
          keyRule: []
        }, {
          label: '接收事件「发生时间」',
          name: 'time',
          keyRule: []
        }, {
          label: '接收事件「名称」',
          name: 'event',
          keyRule: []
        }, {
          label: '接收事件「项目ID」',
          name: 'project',
          keyRule: []
        }],
        profiles: [{
          label: '接收用户「设备ID」',
          name: 'device_id',
          keyRule: []
        }, {
          label: '接收用户「用户ID」',
          name: 'distinct_id',
          keyRule: []
        }, {
          label: '接收用户「发生时间」',
          name: 'time',
          keyRule: []
        }, {
          label: '接收用户「项目ID」',
          name: 'project',
          keyRule: []
        }, {
          label: '接收用户「type」',
          name: 'profile_user',
          keyRule: []
        }],
      }
    },
    created() {
      this.findEventRule()
      this.findProfileRule()
      this.findEvenKeyRule()
      this.findProfileKeyRule()
    },
    methods: {
      findEventRule() {
        getEventRule().then(resp => {
          this.eventTable = resp
        })
      },
      findProfileRule() {
        getProfileRule().then(resp => {
          this.profileTable = resp
        })
      },
      findEvenKeyRule() {
        getEventKeyRule().then(resp => {
          for (let key in this.events) {
            this.events[key].keyRule.splice(0)
            resp.some(item => {
              if (this.events[key].name === item.key) {
                this.events[key].keyRule.push(item)
              }
            })
          }
        })
      },
      findProfileKeyRule() {
        getProfileKeyRule().then(resp => {
          for (let key in this.profiles) {
            this.profiles[key].keyRule.splice(0)
            resp.some(item => {
              if (this.profiles[key].name === item.key) {
                this.profiles[key].keyRule.push(item)
              }
            })
          }
        })
      },
      handleAddClose() {
        this.addVisible = false
      },
      handleParserClose() {
        this.parserVisible = false
      },
      addTableRow(type) {
        this.addVisible = true
        this.type = type
      },
      showParserTable(key, data, type) {
        this.keyName = key
        this.data = data
        this.parserVisible = true
        this.type = type
      },
      insertEventRule(val) {
        addEventRule(val).then(resp => {
        })
        this.findEventRule()
      },
      insertProfileRule(val) {
        this.profileTable.push(val)
        addProfileRule(val).then(resp => {
        })
        this.findProfileRule()
      },
      updateRow(row) {
        if (row.name.toLowerCase().indexOf('json')<=-1)
          this.$set(row, 'update', true)
      },
      updEventRule(row) {
        this.$set(row, 'update', false)
        updateEventRule(row).then(resp => {
        })
        this.findEventRule()
      },
      updProfileRule(row) {
        this.$set(row, 'update', false)
        updateProfileRule(row).then(resp => {
        })
        this.findProfileRule()
      },
      delRow(row, type) {
        if (type === 'event') {
          deleteEventRule(row.id).then(resp => {
          })
          this.findEventRule()
        } else if (type === 'profile') {
          deleteProfileRule(row.id).then(resp => {
          })
          this.findProfileRule()
        }
      },

    }
  }
</script>

<style lang="scss">
  @import "./meta-rule.scss";
</style>
