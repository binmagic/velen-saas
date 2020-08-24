<template>
  <el-dialog :visible.sync="dialogVisible" :before-close="handleClose" center>
    <div slot="title">
      创建
    </div>
    <div>
      <el-form :inline="true" :model="formData">
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
          <el-col :span="7">
            <el-form-item label="业务名">
              <el-input type="businessName" v-model="formData.businessName" placeholder="业务名不可重复"/>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="DSL" class="dsl-class">
              <el-input v-model="formData.dsl" placeholder="调度表达式"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-radio-group v-model="radio" style="margin-left: 20%;margin-bottom: 20px;">
          <el-radio :label="1">启动参数</el-radio>
          <el-radio :label="2">环境变量</el-radio>
          <el-radio :label="3">平台属性</el-radio>
        </el-radio-group>
      </el-form>
      <el-form label-position="top" style="overflow-y: auto;max-height: 260px;">
        <el-form-item v-if="radio == 1" label="properties">
          <el-row v-for="(prop,index) in formData.appParameters" :key="index">
            <el-col :span="8">
              <el-input v-model="formData.appParameters[index]" size="small"/>
            </el-col>
            <el-col :span="1" style="text-align: right">
              <i class="el-icon-remove-outline"
                 @click="delRow(formData.appParameters[index])"
                 style="cursor:pointer;  font-size: 18px;"/>
            </el-col>
            <el-col :span="1" style="text-align: right">
              <i class="el-icon-circle-plus-outline"
                 @click="addRow(index)"
                 style="cursor: pointer;  font-size: 18px;"/>
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
              <i class="el-icon-remove-outline" @click="delRow(prop)"
                 style="cursor:pointer;  font-size: 18px;"/>
            </el-col>
            <el-col :span="1" style="text-align: right">
              <i class="el-icon-circle-plus-outline" @click="addRow(key)"
                 style="cursor: pointer;  font-size: 18px;"/>
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
              <i class="el-icon-remove-outline" @click="delRow(prop)"
                 style="cursor:pointer;  font-size: 18px;"/>
            </el-col>
            <el-col :span="1" style="text-align: right">
              <i class="el-icon-circle-plus-outline" @click="addRow(key)"
                 style="cursor: pointer;  font-size: 18px;"/>
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
    name: "basic-create",
    props: {
      dialogVisible: {
        type: Boolean,
        required: true
      },
    },
    data() {
      return {
        platforms: [],
        processes: [],
        platform: {},
        process: {},
        formData: {
          businessName: '',
          dsl: '',
          appParameters: [''],
          environmentVariables: [{name: '', prop: ''}],
          properties: [{name: '', prop: ''}]
        },
        radio: 0,
        id: ''
      }
    },
    created() {
      this.findPlatform()
      this.findProcess()
    },
    methods: {
      findPlatform() {
        getPlatform().then(resp => {
          this.platforms = resp
          this.platform = resp[0]
        })
      },
      findProcess() {
        getProcess().then(resp => {
          this.processes = resp
          this.process = resp[0]
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
        this.radio = 0
        this.$emit("close-dialog")
      },
      handleSubmit() {
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
        const data = {
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
        createDispatch(data).then(resp => {
          this.$emit('on-create-dispatch')
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
          id: this.id != '' ? this.id : null,
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
        fastDispatch(dispatch).then(resp => {
          this.id = resp.id
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
      }
    }
  }
</script>

<style scoped>

</style>
