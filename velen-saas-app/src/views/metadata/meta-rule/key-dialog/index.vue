<template>
  <el-dialog :visible.sync="visible" :before-close="handleClose">
    <template slot="title">
      <p>Key规则设置</p>
    </template>
  </el-dialog>
</template>

<script>
  export default {
    props:{
      visible:{
        required:true,
        type:Boolean,
      }
    },
    methods: {
      handleClose() {
        this.$emit('close-key-rule')
      },
    }
  }
</script>

<style scoped>

</style>
