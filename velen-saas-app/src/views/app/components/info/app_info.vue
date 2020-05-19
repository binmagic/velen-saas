<template>
  <div class="app-info">
    <el-form hide-required-asterisk ref="dataForm" :rules="rules" :v-model="temp" label-position="left" label-width="150px">
      <el-form-item :label="$t('app.label.name')">
        <el-input v-model="temp.name" />
      </el-form-item>
      <el-form-item :label="$t('app.label.desc')">
        <el-input v-model="temp.desc" />
      </el-form-item>
      <el-form-item :label="$t('app.label.appId')">
        <el-input v-model="temp.id" />
      </el-form-item>
      <el-form-item :label="$t('app.label.appKey')">
        <el-input v-model="temp.appKey" />
      </el-form-item>
      <el-form-item :label="$t('app.label.owner')">
        <el-input v-model="temp.owner" />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="updateData()" :loading="loading">
        {{ $t('app.button.confirm') }}
      </el-button>
    </div>
  </div>
</template>
<script>
import { getAppInfo, updateApp } from '@/api/app'
export default {
  name: 'AppInfo',
  props: {
    appId: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      rules: {
        type: [{ required: true, message: 'type is required', trigger: 'change' }],
        timestamp: [{ type: 'date', required: true, message: 'timestamp is required', trigger: 'change' }],
        title: [{ required: true, message: 'title is required', trigger: 'blur' }]
      },
      temp: {
        id: '',
        name: '',
        desc: '',
        appKey: '',
        owner: '',
      },
      loading: false
    }
  },
  created() {
    this.fetchAppInfo()
  },
  methods: {
    fetchAppInfo() {
      getAppInfo(this.appId).then(resp => {
        this.temp = resp
      })
    },
    updateData() {
      // this.$refs['dataForm'].validate((valid) => {
      //   if (valid) {
      //
      //   }
      // })
      this.loading = true
      const tempData = Object.assign({}, this.temp)
      delete tempData.state
      updateApp(tempData).then(() => {
        this.loading = false
        this.$notify({
          message: '修改成功',
          type: 'success',
          duration: 2000
        })
        this.fetchAppInfo()
      })
    }
  }
}
</script>
