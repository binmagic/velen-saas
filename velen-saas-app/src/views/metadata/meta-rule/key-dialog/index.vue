<template>
  <el-dialog :visible.sync="visible" :before-close="handleClose" width="20%">
    <template slot="title">
      <p>Key规则设置</p>
    </template>
    <el-form>
      <el-form-item label="Key规则">
        <el-select size="small" v-model="selected">
          <el-option v-for="rule in rules" :key="rule.value" :label="rule.label" :value="rule.value"></el-option>
        </el-select>
        <el-row style="margin-left: 29px;">
          <el-col :span="18">
            <el-input type="textarea" :autosize="{ minRows:2,maxRows:4 }" v-model="set">
            </el-input>
          </el-col>
        </el-row>
      </el-form-item>
    </el-form>
    <div slot="footer">
      <el-button @click="handleClose">取消</el-button>
      <el-button type="primary">确定</el-button>
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
        selected: '',
        rules: [{
          label: '无',
          value: ''
        }, {
          label: '正则匹配',
          value: '正则匹配'
        }, {
          label: '位置匹配',
          value: '位置匹配'
        }, {
          label: '属性匹配',
          value: '属性匹配'
        }],
        set: '',
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
