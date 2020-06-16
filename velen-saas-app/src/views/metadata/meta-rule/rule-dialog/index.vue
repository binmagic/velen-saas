<template>
  <el-dialog :visible.sync="visible" :before-close="handleClose" width="20%">
    <template slot="title">
      <p>入库规则设置</p>
    </template>
    <el-form>
      <el-form-item label="入库规则">
        <el-input type="textarea" :autosize="{ minRows:2,maxRows:4 }" v-model="rule"/>
      </el-form-item>
    </el-form>
    <div slot="footer">
      <el-button @click="handleClose">取消</el-button>
      <el-button type="primary">确认</el-button>
    </div>
  </el-dialog>
</template>

<script>
  export default {
    props: {
      visible: {
        required: true,
        type: Boolean,
      }
    },
    data() {
      return {
        rule:''
      }
    },
    methods: {
      handleClose() {
        this.$emit('close-rule')
      }
    }
  }
</script>

<style scoped>

</style>
