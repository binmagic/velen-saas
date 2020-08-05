<template>
  <el-dialog :visible.sync="dialogVisible" :before-close="handleClose" center>
    <div slot="title">
      创建
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
        <el-form-item label="properties">
          <el-row v-for="(prop,key) in formData.properties">
            <el-col :span="8" style="margin-right: 20px;">
              <el-input v-model="prop.name" placeholder="名字" size="small"/>
            </el-col>
            <el-col :span="8">
              <el-input v-model="prop.prop" size="small"/>
            </el-col>
            <el-col :span="1" style="text-align: right">
              <i v-if="formData.properties.length != 1" class="el-icon-remove-outline" @click="delRow(prop)"
                 style="cursor:pointer;  font-size: 18px;"/>
            </el-col>
            <el-col :span="1" style="text-align: right">
              <i v-if="key == formData.properties.length-1" class="el-icon-circle-plus-outline" @click="addRow"
                 style="cursor: pointer;  font-size: 18px;"/>
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
        formData:
          {
            businessName: '',
            dsl: '',
            properties: [{name: '', prop: ''}]
          }
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
          if (item.name != '')
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
        createDispatch(data).then(resp => {
          this.$emit('on-create-dispatch')
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
          if (item.name != '')
            properties[item.name] = item.prop
        })
        const dispatch = {
          platform: this.platform.name,
          platformType: this.platform.key,
          process: this.process.name,
          processType: this.process.key,
          businessName: this.formData.businessName,
          dsl: this.formData.dsl,
          properties: properties
        }
        fastDispatch(dispatch).then(resp => {
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
