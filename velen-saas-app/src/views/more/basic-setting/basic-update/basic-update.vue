<template>
  <el-dialog :visible.sync="dialogVisible" :before-close="handleClose" center @open="openDialog">
    <div slot="title">
      {{ title }}
    </div>
    <div style="overflow-y: auto;max-height: 400px;">
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
          <el-form-item label="DSL">
            <el-input v-model="formData.dsl" placeholder="调度表达式"/>
          </el-form-item>
        </el-row>
      </el-form>
      <el-form label-position="top">
        <el-form-item v-for="(prop,key) in formData.properties" :label=" key ==0 ? 'properties' :''">
          <el-row>
            <el-col :span="8" style="margin-right: 20px;">
              <el-input v-model="prop.name" placeholder="名字" size="small"/>
            </el-col>
            <el-col :span="8">
              <el-input v-model="prop.prop" size="small"/>
            </el-col>
            <el-col :span="1" style="text-align: right">
              <i v-if="formData.properties.length != 1" class="el-icon-remove-outline" @click="delRow(prop)"
                 style="cursor:pointer; font-size: 18px;"/>
            </el-col>
            <el-col :span="1" style="text-align: right">
              <i v-if="key == formData.properties.length-1" class="el-icon-circle-plus-outline" @click="addRow"
                 style="cursor: pointer; font-size: 18px;"/>
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
        formData: {}
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
        this.formData.properties = list
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
          properties: [{name: '', prop: ''}]
        }
        this.$emit("close-dialog")
      },
      handleSubmit() {
        var properties = {}
        this.formData.properties.some(item => {
          properties[item.name] = item.prop
        })
        var data = {
          platform: this.platform.name,
          platformType: this.platform.key,
          process: this.process.name,
          processType: this.process.key,
          businessName: this.formData.businessName,
          dsl: this.formData.dsl,
          properties: properties
        }
        updateDispatch(data).then(resp => {
          console.log(resp)
          this.$emit('on-update-dispatch')
          this.$emit('close-dialog')
        })
      },
      addRow() {
        this.formData.properties.push({name: '', prop: ''})
      },
      delRow(prop) {
        var index = this.formData.properties.indexOf(prop)
        if (index !== -1) {
          this.formData.properties.splice(index, 1)
        }
      },
      fastDispatch() {
        var properties = {}
        this.formData.properties.some(item => {
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
          properties: properties
        }
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
      }
    }
  }
</script>

<style scoped>

</style>
