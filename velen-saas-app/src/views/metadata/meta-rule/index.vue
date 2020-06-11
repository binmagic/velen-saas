<template>
  <div class="meta-rule-container">
    <div class="meta-rule-title">
      <p>入库校验规则设置</p>
    </div>
    <el-card>
      <p class="meta-card-header">
        事件与事件属性的入库设置
      </p>
      <p style="margin: 0;font-size: 14px;">
        上报事件属性的类型与「事件属性」已有事件的类型不一致时，系统将按规则尝试自动转换，如无法完成则视为异常数据不能入库。
      </p>
      <div class="meta-card-form">
        <el-checkbox>强校验模式</el-checkbox>
        <el-form label-position="top">
          <el-form-item label="解析格式:">
            <el-select v-model="select" size="mini">
              <el-option v-for="role in roles" :key="role.value" :label="role.label" :value="role.value"></el-option>
            </el-select>
<!--            <i class="el-icon-plus"></i>-->
            <el-row>
              <el-col :span="3">
                <el-input type="textarea" v-model="select" :disabled="select==='JSON解析'?true:false" :autosize="{ minRows:2, maxRows:4 }"></el-input>
              </el-col>
            </el-row>
          </el-form-item>
        </el-form>
      </div>
    </el-card>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        select: '',
        roles: [{
          label: 'JSON解析',
          value: 'JSON解析'
        }, {
          label: '正则解析',
          value: '正则解析'
        }, {
          label: '分隔符解析',
          value: '分隔符解析'
        }]
      }
    }
  }
</script>

<style lang="scss">
  @import "./meta-rule.scss";
</style>
