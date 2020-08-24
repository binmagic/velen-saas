<template>
  <el-dialog :visible.sync="dialogVisible" :before-close="handleClose" center @open="openDialog">
    <div slot="title">
      修改
    </div>
    <div>
      <el-form :inline="true">
        <el-form-item label="平台">
          <el-select v-model="platform" value-key="key">
            <el-option v-for="platform in platforms" :key="platform.key" :label="platform.name"
                       :value="platform"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="process">
          <el-select v-model="process" value-key="key">
            <el-option v-for="process in processes" :key="process.key" :label="process.name"
                       :value="process"></el-option>
          </el-select>
          <el-button type="primary" @click="fastDispatch">快速dispatch</el-button>
        </el-form-item>
        <el-row>
          <el-form-item label="业务名">
            <el-input v-model="formData.businessName" placeholder="业务名不可重复"/>
          </el-form-item>
          <el-form-item label="DSL" class="dsl-class">
            <el-input v-model="formData.dsl" placeholder="调度表达式"/>
          </el-form-item>
        </el-row>
        <el-radio-group v-model="radio" style="margin-left: 20%;margin-bottom: 20px;">
          <el-radio :label="1">启动参数</el-radio>
          <el-radio :label="2">环境变量</el-radio>
          <el-radio :label="3">平台属性</el-radio>
        </el-radio-group>
      </el-form>
      <el-form label-position="top" style="overflow-y: auto;max-height: 400px;">
        <el-form-item v-if="radio == 1" label="properties">
          <el-row v-for="(prop,index) in formData.appParameters" :key="index">
            <el-col :span="8">
              <el-input v-model="formData.appParameters[index]" size="small"/>
            </el-col>
            <el-col :span="1" style="text-align: right">
              <i class="el-icon-remove-outline" @click="delRow(formData.appParameters[index])" style="cursor:pointer;  font-size: 18px;"/>
            </el-col>
            <el-col :span="1" style="text-align: right">
              <i class="el-icon-circle-plus-outline" @click="addRow(index)" style="cursor: pointer;  font-size: 18px;"/>
            </el-col>
          </el-row>
          <el-row v-if="formData.appParameters.length ==0">
            <el-col>
              <i class="el-icon-circle-plus-outline" @click="addRow(0)" style="cursor: pointer;  font-size: 18px;"/>
            </el-col>
          </el-row>
        </el-form-item>
        <el-form-item v-else-if="radio == 2" label="properties">
          <el-row v-for="(prop,key) in formData.environmentVariables">
            <el-col :span="10" style="margin-right: 20px;">
              <el-input v-model="prop.name" placeholder="名字" size="small"/>
            </el-col>
            <el-col :span="10">
              <el-input v-model="prop.prop" size="small"/>
            </el-col>
            <el-col :span="1" style="text-align: right">
              <i class="el-icon-remove-outline" @click="delRow(prop)" style="cursor:pointer;  font-size: 18px;"/>
            </el-col>
            <el-col :span="1" style="text-align: right">
              <i class="el-icon-circle-plus-outline" @click="addRow(key)" style="cursor: pointer;  font-size: 18px;"/>
            </el-col>
          </el-row>
          <el-row v-if="formData.environmentVariables.length ==0">
            <el-col>
              <i class="el-icon-circle-plus-outline" @click="addRow(0)" style="cursor: pointer;  font-size: 18px;"/>
            </el-col>
          </el-row>
        </el-form-item>
        <el-form-item v-else-if="radio == 3" label="properties">
          <el-row v-for="(prop,key) in formData.properties">
            <el-col :span="10" style="margin-right: 20px;">
              <el-input v-model="prop.name" placeholder="名字" size="small"/>
            </el-col>
            <el-col :span="10">
              <el-input v-model="prop.prop" size="small"/>
            </el-col>
            <el-col :span="1" style="text-align: right">
              <i class="el-icon-remove-outline" @click="delRow(prop)" style="cursor:pointer;  font-size: 18px;"/>
            </el-col>
            <el-col :span="1" style="text-align: right">
              <i class="el-icon-circle-plus-outline" @click="addRow(key)" style="cursor: pointer;  font-size: 18px;"/>
            </el-col>
          </el-row>
          <el-row v-if="formData.properties.length ==0">
            <el-col>
              <i class="el-icon-circle-plus-outline" @click="addRow(0)" style="cursor: pointer;  font-size: 18px;"/>
            </el-col>
          </el-row>
        </el-form-item>
      </el-form>
    </div>
    <div slot="footer">
      <el-button @click="handleClose">取消</el-button>
      <el-button type="primary" @click="handleSubmit">确定</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import './index.scss'
  import {getPlatform, getProcess} from '@/api/dispatch'
  import {createDispatch, updateDispatch, fastDispatch} from '@/api/dispatch'

  export default {
    name: "basic-update",
    props: {
      dialogVisible: {
        type: Boolean,
        required: true
      },
      title: {
        type: String,
      },
      data: {
        type: Object,
        default() {
          return {
            businessName: '',
            dsl: '',
            properties: {id: 'appId'}
          }
        }
      }
    },
    data() {
      return {
        platforms: [],
        processes: [],
        platform: {},
        process: {},
        formData: {},
        radio: 0
      }
    },
    created() {
      this.findPlatform()
      this.findProcess()
    },
    methods: {
      openDialog() {
        this.platform = JSON.parse(JSON.stringify({name: this.data.platform, key: this.data.platformType}))
        this.process = JSON.parse(JSON.stringify({name: this.data.process, key: this.data.processType}))
        this.formData = JSON.parse(JSON.stringify(this.data))
        var list = []
        for (let key in this.formData.properties) {
          list.push({name: key, prop: this.formData.properties[key]})
        }
        this.formData.properties = list.length > 0 ? list : [{name: '', prop: ''}]
        var list1 = []
        for (let key in this.formData.environmentVariables) {
          list1.push({name: key, prop: this.formData.environmentVariables[key]})
        }
        this.formData.environmentVariables = list1.length > 0 ? list1 : [{name: '', prop: ''}]
        var list2 = []
        for (let key in this.formData.appParameters) {
          list2.push(this.formData.appParameters[key])
        }
        this.formData.appParameters = list2.length > 0 ? list2 : ['']
      },
      findPlatform() {
        getPlatform().then(resp => {
          this.platforms = resp
        })
      },
      findProcess() {
        getProcess().then(resp => {
          this.processes = resp
        })
      },
      handleClose() {
        this.platform = {}
        this.process = {}
        this.formData = {
          businessName: '',
          dsl: '',
          appParameters: [''],
          environmentVariables: [{name: '', prop: ''}],
          properties: [{name: '', prop: ''}]
        }
        this.$emit("close-dialog")
      },
      handleSubmit() {
        const data = this.formatData()
        updateDispatch(data).then(resp => {
          this.$emit('on-update-dispatch')
          this.$emit('close-dialog')
        })
      },
      addRow(index) {
        switch (this.radio) {
          case 1:
            this.formData.appParameters.splice(index + 1, 0, '')
            break
          case 2:
            this.formData.environmentVariables.splice(index + 1, 0, {name: '', prop: ''})
            break
          case 3:
            this.formData.properties.splice(index + 1, 0, {name: '', prop: ''})
            break
        }
      },
      delRow(prop) {
        switch (this.radio) {
          case 1:
            var index = this.formData.appParameters.indexOf(prop)
            if (index !== -1) {
              this.formData.appParameters.splice(index, 1)
            }
            break
          case 2:
            var index = this.formData.environmentVariables.indexOf(prop)
            if (index !== -1) {
              this.formData.environmentVariables.splice(index, 1)
            }
            break
          case 3:
            var index = this.formData.properties.indexOf(prop)
            if (index !== -1) {
              this.formData.properties.splice(index, 1)
            }
            break
        }
      },
      fastDispatch() {
        const dispatch = this.formatData()
        fastDispatch(dispatch).then(resp => {
          this.$emit('on-update-dispatch')
          this.$notify({
            title: '成功',
            message: '快速调用',
            type: 'success'
          });
        }).catch(msg => {
          this.$notify({
            title: '失败',
            message: msg.message,
            type: 'error'
          });
        })
      },
      formatData(){
        var appParameters = []
        this.formData.appParameters.some(item => {
          if (item != '')
            appParameters.push(item)
        })
        var environmentVariables = {}
        this.formData.environmentVariables.some(item => {
          if (item.name != '')
            environmentVariables[item.name] = item.prop
        })
        var properties = {}
        this.formData.properties.some(item => {
          if (item.name != '')
            properties[item.name] = item.prop
        })
        const dispatch = {
          id: this.formData.id,
          platform: this.platform.name,
          platformType: this.platform.key,
          process: this.process.name,
          processType: this.process.key,
          businessName: this.formData.businessName,
          dsl: this.formData.dsl,
          appParameters: appParameters,
          environmentVariables: environmentVariables,
          properties: properties
        }
        return dispatch
      }
    }
  }
</script>

<style scoped>

</style>
