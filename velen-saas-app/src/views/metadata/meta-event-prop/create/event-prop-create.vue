<template>
  <el-drawer
    title="新增事件属性"
    :visible.sync="enable"
    :before-close="hideDrawer"
    direction="rtl"
    size="30%"
  >
    <el-form label-width="auto" label-position="left" style="padding-right: 50px" hide-required-asterisk>
      <el-form-item label="属性显示名">
        <el-input v-model="formData.showName" />
      </el-form-item>
      <el-form-item label="属性名">
        <el-input v-model="formData.name" />
      </el-form-item>
      <el-form-item label="数据类型">
        <el-select v-model="formData.type" style="width: 100%">
          <el-option v-for="type of $const.metadata.data_type" :label="type" :value="type" />
        </el-select>
      </el-form-item>
      <el-form-item label="设为公共属性">
        <el-checkbox v-model="formData.public" />
      </el-form-item>
      <el-form-item label="应埋点平台">
        <el-select v-model="formData.platform"  multiple style="width: 100%">
          <el-option v-for="_platform of $const.metadata.platform" :label="_platform" :value="_platform" />
        </el-select>
      </el-form-item>
      <el-form-item label="设置时机">
        <el-input
          v-model="formData.timing"
          type="textarea"
          :show-word-limit="true"
          :maxlength="200"
          :rows="2"
          placeholder="请输入内容"
        />
      </el-form-item>
      <el-form-item label="单位/格式">
        <el-input v-model="formData.unit" />
      </el-form-item>
      <el-form-item label="字典">
        数据上传后，可在列表管理字典
      </el-form-item>
      <el-form-item label="属性值示例或说明">
        <el-input
          v-model="formData.example"
          :show-word-limit="true"
          :maxlength="200"
          type="textarea"
          :rows="2"
          placeholder="请输入内容"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" :loading="createLoading" @click="handleSubmit">确定</el-button>
        <el-button>取消</el-button>
      </el-form-item>
    </el-form>
  </el-drawer>

</template>

<script>
import { createMetaEventProp } from '@/api/metadata'
export default {
  name: 'EventPropCreate',
  props: {
    enable: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      formData: {
        platform: [],
        timing: '',
        example: '',
        unit: '',
        public: '',
        type: '',
        name: '',
        showName: ''
      },
      createLoading: false
    }
  },
  methods: {
    hideDrawer() {
      this.$emit('update:enable', false)
    },
    handleSubmit() {
      this.createLoading = true
      delete this.formData['public']
      createMetaEventProp(this.formData).then(resp => {
        this.createLoading = false
        this.$message.success('success')
        this.$emit('on-create-event-prop')
      }).catch(reason => {
        this.createLoading = false
      })
    }
  }
}
</script>
