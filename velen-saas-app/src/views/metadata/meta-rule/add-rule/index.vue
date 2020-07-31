<template>
  <el-dialog :visible.sync="visible" :before-close="handleClose" width="20%" @open="openDialog"
             append-to-body>
    <template slot="title">
      <p>添加解释器</p>
    </template>
    <el-form :model="form">
      <el-form-item label="解释器类型">
        <el-select v-model="ruleType" value-key="key">
          <el-option v-for="type in parseType" :key="type.key" :label="type.name" :value="type"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="规则">
        <el-input placeholder="请输入规则" v-model="form.rule"></el-input>
      </el-form-item>
      <el-form-item label="入库规则设置">
        <el-input placeholder="输入入库规则" v-model="form.valueRule" :disabled="fieldType==='parseRule'"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer">
      <el-button @click="handleClose">取消</el-button>
      <el-button type="primary" @click="addRule">确定</el-button>
    </div>
  </el-dialog>
</template>

<script>
  export default {
    props: {
      visible: {
        required: true,
        type: Boolean,
      },
      type: {
        required: true,
        type: String
      },
      parseType: {
        required: true,
        type: Array
      },
      fieldType:{
        required:true,
        type:String
      }
    },
    data() {
      return {
        form: {
          name: '',
          ruleKey: -1,
          rule: '',
          valueRule: ''
        },
        ruleType: this.parseType[0]
      }
    },
    methods: {
      openDialog() {
        this.form.name = ''
        this.form.ruleKey = -1
        this.form.rule = ''
        this.form.valueRule = ''
        this.ruleType = {}
      },
      handleClose() {
        this.$emit('close-add-rule')
      },
      addRule() {
        this.form.name = this.ruleType.name
        this.form.ruleKey = this.ruleType.key
        if (this.type == 'event') {
          this.$emit('add-event-rule', this.form)
        } else if (this.type == 'profile') {
          this.$emit('add-profile-rule', this.form)
        }else if (this.type == 'eventKeyRule'){
          this.$emit('add-key-rule',this.form)
        }
        this.$emit('close-add-rule')
      }
    }
  }
</script>

<style scoped>

</style>
