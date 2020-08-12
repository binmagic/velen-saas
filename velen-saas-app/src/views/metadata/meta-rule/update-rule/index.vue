<template>
  <el-dialog :visible.sync="visible" :before-close="handleClose" width="20%" @open="openDialog"
             append-to-body>
    <template slot="title">
      <p>修改key规则</p>
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
        <el-input placeholder="输入入库规则" v-model="form.valueRule"></el-input>
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
      date: {
        type: Object
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
        this.from = JSON.parse(JSON.stringify(this.data))
        this.ruleType.name = this.from.name
        this.ruleType.key = this.from.ruleKey
      },
      handleClose() {
        this.$emit('close-update-rule')
      },
      addRule() {
        this.form.name = this.ruleType.name
        this.form.ruleKey = this.ruleType.key
        switch (this.type) {
          case 'eventKeyRule':
            this.$emit('update-key-rule', this.form)
            break
        }
        this.$emit('close-update-rule')
      }
    }
  }
</script>

<style scoped>

</style>
