<template>
  <div class="meta-rule-container">
    <div class="meta-rule-title">
      <p>入库校验规则设置</p>
    </div>
    <el-card style="margin-bottom: 20px">
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
            <el-select v-model="select" size="mini" style="margin-bottom: 10px;">
              <el-option v-for="role in roles" :key="role.value" :label="role.label" :value="role.value"></el-option>
            </el-select>
<!--            <i class="el-icon-plus"></i>-->
            <el-row>
              <el-col :span="3">
                <el-input type="textarea" v-model="select" :disabled="select==='JSON解析'?true:false" :autosize="{ minRows:2, maxRows:4 }"/>
              </el-col>
            </el-row>
          </el-form-item>
          <el-row>
            <el-col :span="4" style="margin-right: 50px;">
              <span>接收用户「登录ID」</span>
              <p class="meta-key-desc">用户在产品内的唯一标识,不符合以下规则时,不能入库</p>
              <p>Key规则 <el-link type="primary">默认规则</el-link></p>
              <p>入库规则 <el-link type="primary">未设置任意格式ID均可上报入库</el-link></p>
            </el-col>
            <el-col :span="4" style="margin-right: 50px;">
              <span>接收用户「设备ID」</span>
              <p class="meta-key-desc">不符合以下规则时,不能入库</p>
              <p>Key规则 <el-link type="primary">默认规则</el-link></p>
              <p>入库规则 <el-link type="primary">未设置任意格式ID均可上报入库</el-link></p>
            </el-col>
            <el-col :span="4" style="margin-right: 50px;">
              <span>接收用户「事件时间」</span>
              <p class="meta-key-desc">不符合以下规则时,不能入库</p>
              <p>Key规则 <el-link type="primary">默认规则</el-link></p>
              <p>入库规则 <el-link type="primary">未设置任意格式ID均可上报入库</el-link></p>
            </el-col>
            <el-col :span="4" style="margin-right: 50px;">
              <span>接收用户「事件名」</span>
              <p class="meta-key-desc">不符合以下规则时,不能入库</p>
              <p>Key规则 <el-link type="primary">默认规则</el-link></p>
              <p>入库规则 <el-link type="primary">未设置任意格式ID均可上报入库</el-link></p>
            </el-col>
            <el-col :span="4">
              <span>接收用户「项目名」</span>
              <p class="meta-key-desc">不符合以下规则时,不能入库</p>
              <p>Key规则 <el-link type="primary">默认规则</el-link></p>
              <p>入库规则 <el-link type="primary">未设置任意格式ID均可上报入库</el-link></p>
            </el-col>
          </el-row>
        </el-form>
      </div>
    </el-card>
    <el-card>
      <p class="meta-card-header">
        用户属性入库设置
      </p>
      <p style="margin: 0;font-size: 14px;">
        上报用户属性的类型与「用户属性」已有事件的类型不一致时，系统将按规则自动转换，如无法自动转换则视为异常数据不能入库。
      </p>
      <div class="meta-card-form">
        <el-checkbox>强校验模式</el-checkbox>
        <el-form label-position="top">
          <el-form-item label="主题设置:">
            <el-row>
              <el-col :span="4">
                <el-input size="small" v-model="themeSet" placeholder="多个主题以,隔开"></el-input>
              </el-col>
            </el-row>
          </el-form-item>
          <el-form-item label="解析格式:">
            <el-select v-model="select" size="mini" style="margin-bottom: 10px;">
              <el-option v-for="role in roles" :key="role.value" :label="role.label" :value="role.value"></el-option>
            </el-select>
            <el-row>
              <el-col :span="3">
                <el-input type="textarea" v-model="select" :disabled="select==='JSON解析'?true:false" :autosize="{ minRows:2, maxRows:4 }"/>
              </el-col>
            </el-row>
          </el-form-item>
          <el-row>
            <el-col :span="5">
              <span>接收用户「登录ID」</span>
              <p class="meta-key-desc">用户在产品内的唯一标识,不符合以下规则时,不能入库</p>
              <p>Key规则 <el-link type="primary">默认规则</el-link></p>
              <p>入库规则 <el-link type="primary">未设置任意格式ID均可上报入库</el-link></p>
            </el-col>
            <el-col :span="5">
              <span>接收用户「设备ID」</span>
              <p class="meta-key-desc">不符合以下规则时,不能入库</p>
              <p>Key规则 <el-link type="primary">默认规则</el-link></p>
              <p>入库规则 <el-link type="primary">未设置任意格式ID均可上报入库</el-link></p>
            </el-col>
            <el-col :span="5">
              <span>接收用户「时间」</span>
              <p class="meta-key-desc">不符合以下规则时,不能入库</p>
              <p>Key规则 <el-link type="primary">默认规则</el-link></p>
              <p>入库规则 <el-link type="primary">未设置任意格式ID均可上报入库</el-link></p>
            </el-col>
            <el-col :span="5">
              <span>接收用户「项目名」</span>
              <p class="meta-key-desc">不符合以下规则时,不能入库</p>
              <p>Key规则 <el-link type="primary">默认规则</el-link></p>
              <p>入库规则 <el-link type="primary">未设置任意格式ID均可上报入库</el-link></p>
            </el-col>
          </el-row>
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
        }],
        themeSet:'',
      }
    }
  }
</script>

<style lang="scss">
  @import "./meta-rule.scss";
</style>
