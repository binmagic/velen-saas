<template>
  <el-dialog :visible.sync="visible" :before-close="handleClose" width="20%" @open="openDialog"
             append-to-body>
    <template slot="title">
      <p>添加解释器</p>
    </template>
    <el-form :model="form">
      <el-form-item label="解释器类型">
        <el-input placeholder="请输入解释器类型" v-model="form.name"></el-input>
      </el-form-item>
      <el-form-item label="规则">
        <el-input placeholder="请输入规则" v-model="form.rule"></el-input>
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
    },
    data() {
      return {
        form: {
          name: '',
          rule: ''
        }
      }
    },
    methods: {
      openDialog() {
        this.form.name = ''
        this.form.rule = ''
      },
      handleClose() {
        this.$emit('close-add-rule')
      },
      addRule() {
        if (this.type == 'event') {
          this.$emit('add-event-rule', this.form)
        } else if (this.type == 'profile') {
          this.$emit('add-profile-rule', this.form)
        }
        this.$emit('close-add-rule')
      }
    }
  }
</script>

<style scoped>

</style>
