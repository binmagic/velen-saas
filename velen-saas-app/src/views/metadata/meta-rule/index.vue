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
        <el-checkbox v-model="eventCheck" @change="setVerify">强校验模式</el-checkbox>
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
            <el-col v-for="event in events" :span="4" style="margin-right: 50px;margin-top: 20px;">
              <span>{{event.name}}</span>
              <p class="rule-comment">{{event.comment}}</p>
              <p>Key规则</p>
              <el-link v-if="event.keyRule">
                {{event.keyRule ?event.keyRule.rule:'设置KeyRule'}}
              </el-link>
              <el-link type="primary" v-else @click="showParserTable(event.name,event.keyRule,'eventKeyRule')">
                {{event.keyRule ?event.keyRule.rule:'设置KeyRule'}}
              </el-link>
              <p>入库规则</p>
              <el-link>
                {{event.keyRule ?event.keyRule.valueRule:'未设置,任意字段可入库'}}
              </el-link>
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
            <el-col v-for="profile in profiles" :span="4" style="margin-right: 50px;margin-top: 20px;">
              <span>{{profile.label}}</span>
              <p>Key规则</p>
              <el-link type="primary"
                       @click="showParserTable(profile.name,profile.keyRule,'profile')">
                <!--{{profile.keyRule.length<=0?'未设置':'显示匹配规则'}}-->
              </el-link>
              <p>入库规则</p>
              <el-link type="primary">
                <!--{{profile.rule.length<=0?'未设置 任意格式均可上报入库':'显示入库规则'}}-->
              </el-link>
            </el-col>
          </el-row>
        </el-form>
      </div>
    </el-card>
    <!--<parser-table
      :visible.sync="parserVisible"
      :type="type"
      :data.sync="data"
      :keyName="keyName"
      @find-event-data="findEvenKeyRule"
      @find-profile-data="findProfileKeyRule"
      @close-parser-table="handleParserClose"
    />-->
    <!--<check-table
      :visible.sync="checkVisible"
      :type="type"
      :data.sync="data"
      :keyName="keyName"
      @find-event-data="findEventCheckRule"
      @find-profile-data="findProfileCheckRule"
      @close-check-table="handleCheckClose"
    />-->
    <add-dialog
      :visible.sync="addVisible"
      :type="type"
      :parse-type="showType"
      :field-type="fieldType"
      @close-add-rule="handleAddClose"
      @add-event-rule="insertEventRule"
      @add-profile-rule="insertProfileRule"
      @add-key-rule="insertEventKeyRule"
    />
  </div>
</template>

<script>
  import AddDialog from './add-rule'
  import ParserTable from './parser-table'
  import CheckTable from './check-table'
  import {
    getEventRule,
    addEventRule,
    updateEventRule,
    deleteEventRule,
    getEventKeyRule,
    getInputParseType,
    addEventKeyRule,
    updateEventKeyRule,
    deleteEventKeyRule,
    getFieldRuleType
  } from "@/api/eventRule";
  import {
    getProfileRule,
    addProfileRule,
    updateProfileRule,
    deleteProfileRule,
    getProfileKeyRule,
    addProfileKeyRule,
    updateProfileKeyRule,
    deleteProfileKeyRule
  } from "@/api/profileRule";
  import {getAppVerify, updateAppVerify} from "@/api/appVerify"
  import {getFieldType} from "@/api/fieldType";

  export default {
    components: {
      AddDialog,
      ParserTable,
      CheckTable
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
        checkVisible: false,
        type: '',
        keyName: '',
        eventTable: [],
        profileTable: [],
        profileRule: '',
        data: [],
        events: [],
        profiles: [{
          label: '接收用户「设备ID」',
          name: 'device_id',
          keyRule: [],
          rule: []
        }, {
          label: '接收用户「用户ID」',
          name: 'distinct_id',
          keyRule: [],
          rule: []
        }, {
          label: '接收用户「发生时间」',
          name: 'time',
          keyRule: [],
          rule: []
        }, {
          label: '接收用户「项目ID」',
          name: 'project',
          keyRule: [],
          rule: []
        }, {
          label: '接收用户「type」',
          name: 'profile_user',
          keyRule: [],
          rule: []
        }],
        inputParseType: [],
        fieldRuleType: [],
        fieldType: '',
        showType: []
      }
    },
    created() {
      this.findEventRule()
      this.findProfileRule()
      this.findEvenKeyRule()
      this.findProfileKeyRule()
      this.findInputParseType()
      this.findAppVerify()
      this.findFieldRuleType()
    },
    methods: {
      findFieldRuleType() {
        getFieldRuleType().then(resp => {
          this.fieldRuleType = resp
        })
      },
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
      findInputParseType() {
        getInputParseType().then(resp => {
          this.inputParseType = resp
        })
      },
      findAppVerify() {
        getAppVerify().then(resp => {
          this.eventCheck = resp.verify
        })
      },
      findEvenKeyRule() {
        getFieldType().then(response => {
          this.events = response
          getEventKeyRule().then(resp => {
            this.events.some(event => {
              resp.some(item => {
                if (event.value === item.key) {
                  event['keyRule'] = item
                  return
                }
              })
            })
          })
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
      handleCheckClose() {
        this.checkVisible = false
      },
      addTableRow(type) {
        this.showType = this.inputParseType
        this.addVisible = true
        this.type = type
        this.fieldType = 'parseRule'
      },
      showParserTable(key, data, type) {
        this.data = data
        this.addVisible = true
        this.type = type
        this.showType = this.fieldRuleType
        this.fieldType = 'keyRule'
      },
      showCheckTable(key, data, type) {
        this.data = data
        this.checkVisible = true
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
      insertEventKeyRule(val) {
        val.key = this.keyName
        val.type = 'event'
        addEventKeyRule(val).then(resp => {
          this.findEvenKeyRule()
        })
      },
      updateRow(row) {
        if (row.name.toLowerCase().indexOf('json') <= -1)
          this.$set(row, 'update', true)
      },
      updEventRule(row) {
        this.$set(row, 'update', false)
        updateEventRule(row).then(resp => {
        })
        this.findEventRule()
      },
      updProfileRule(row) {
        if (row.rule.split(' ').join('').length == 0) {
          row.rule = ""
        }
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
      setVerify(value) {
        const appVerify = {verify: value}
        updateAppVerify(appVerify).then(resp => {
          console.log(resp)
          this.eventCheck = resp.verify
        }).catch(r => {
          this.eventCheck = !this.eventCheck
        })
      }
    }
  }
</script>

<style lang="scss">
  @import "./meta-rule.scss";
</style>
