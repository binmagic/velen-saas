<template>
  <el-drawer
    title="修改元事件"
    :visible.sync="enable"
    :before-close="hideDrawer"
    direction="rtl"
    size="50%"
    @open="openDrawer"
  >
    <div style="margin-left: 20px; margin-right: 20px">
      <el-form label-width="auto" label-position="left" style="padding-right: 50px" hide-required-asterisk>
        <el-form-item label="事件显示名">
          <el-input v-model="formData.showName"/>
        </el-form-item>
        <el-form-item label="事件名">
          <el-input v-model="formData.name"/>
        </el-form-item>
        <el-form-item label="应埋点平台">
          <el-select v-model="formData.platform" multiple style="width: 100%">
            <el-option v-for="_platform of $const.metadata.platform" :label="_platform" :value="_platform"/>
          </el-select>
        </el-form-item>
        <el-form-item label="触发时机">
          <el-input
            v-model="formData.timing"
            type="textarea"
            :show-word-limit="true"
            :maxlength="200"
            :rows="2"
            placeholder="请输入内容"
          />
        </el-form-item>
        <el-form-item label="标签">
          <el-tag v-for="tag of formData.tag" closable>{{ tag }}</el-tag>
          <el-select v-if="componentFlag.enableTagAdd" v-model="selectTag" placeholder="请选择" filterable multiple
                     @blur="componentFlag.enableTagAdd = false">
            <el-option
              v-for="item in tags"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
          <el-button v-else size="small" @click="componentFlag.enableTagAdd = true">+</el-button>
        </el-form-item>
      </el-form>
      <el-form label-position="top">
        <el-form-item label="自定义属性" class="custom-prop">
          <el-row :gutter="20" style="background: rgb(241, 240, 240); display: flex; margin-bottom: 10px">
            <el-col :span="8">属性名</el-col>
            <el-col :span="8">属性显示名</el-col>
            <el-col :span="8">数据类型</el-col>
          </el-row>
          <el-row v-for="prop of selectProperties" v-if="!prop.isCommon && !prop.isPerset" :gutter="20"
                  style="background: white; display: flex; margin-bottom: 10px">
            <el-col :span="8">
              <el-input disabled :value="prop.name"/>
            </el-col>
            <el-col :span="8">
              <el-input disabled :value="prop.showName"/>
            </el-col>
            <el-col :span="6">
              <el-select :value="prop.type" disabled>
                <el-option v-for="type of $const.metadata.data_type" :label="type" :value="type"/>
              </el-select>
            </el-col>
            <el-col :span="2">
              <i class="el-icon-close" @click="handleDeleteProp(prop)"/>
            </el-col>
          </el-row>
          <el-row style="background: white">
            <el-col>
              <el-popover
                placement="bottom"
                trigger="click"
                width="500"
                @hide="hidePopover"
                @show="showPopover"
              >
                <div style="display: flex;margin-bottom: 20px">
                  <el-button type="success" @click="handleAddToList()">添加</el-button>
                </div>
                <el-table
                  ref="propertiesTable"
                  :data="properties"
                  border
                  fit
                  highlight-current-row
                  height="600"
                  style="width: 100%;"
                >
                  <el-table-column
                    type="selection"
                    width="55"
                  />
                  <el-table-column label="属性名" align="center" :show-overflow-tooltip="true">
                    <template slot-scope="{row}">
                      <span>{{ row.name }}</span>
                    </template>
                  </el-table-column>
                  <el-table-column label="属性显示名" align="center" :show-overflow-tooltip="true">
                    <template slot-scope="{row}">
                      <span>{{ row.showName }}</span>
                    </template>
                  </el-table-column>
                  <el-table-column label="属性类型" align="center" :show-overflow-tooltip="true">
                    <template slot-scope="{row}">
                      <span>{{ row.type }}</span>
                    </template>
                  </el-table-column>
                </el-table>
                <i slot="reference" class="el-icon-plus" style="cursor: pointer;"/>
              </el-popover>
            </el-col>
          </el-row>
        </el-form-item>
        <el-form-item label="公共属性" class="custom-prop">
          <el-row :gutter="20" style="background: rgb(241, 240, 240); display: flex; margin-bottom: 10px">
            <el-col :span="8">属性名</el-col>
            <el-col :span="8">属性显示名</el-col>
            <el-col :span="8">数据类型</el-col>
          </el-row>
          <el-row v-for="prop of selectProperties" v-if="prop.isCommon" :gutter="20"
                  style="background: white; display: flex; margin-bottom: 10px">
            <el-col :span="8">
              <el-input disabled :value="prop.name"/>
            </el-col>
            <el-col :span="8">
              <el-input disabled :value="prop.showName"/>
            </el-col>
            <el-col :span="6">
              <el-select :value="prop.type" disabled>
                <el-option v-for="type of $const.metadata.data_type" :label="type" :value="type"/>
              </el-select>
            </el-col>
            <el-col :span="2">
              <i class="el-icon-close" @click="handleDeleteCommonProp(prop)"/>
            </el-col>
          </el-row>
        </el-form-item>
        <el-form-item label="预制属性" class="custom-prop">
          <el-row :gutter="20" style="background: rgb(241, 240, 240); display: flex; margin-bottom: 10px">
            <el-col :span="8">属性名</el-col>
            <el-col :span="8">属性显示名</el-col>
            <el-col :span="8">数据类型</el-col>
          </el-row>
          <el-row v-for="prop of selectProperties" v-if="prop.isPreset" :gutter="20"
                  style="background: white; display: flex; margin-bottom: 10px">
            <el-col :span="8">
              <el-input disabled :value="prop.name"/>
            </el-col>
            <el-col :span="8">
              <el-input disabled :value="prop.showName"/>
            </el-col>
            <el-col :span="6">
              <el-select :value="prop.type" disabled>
                <el-option v-for="type of $const.metadata.data_type" :label="type" :value="type"/>
              </el-select>
            </el-col>
            <el-col :span="2">
              <i class="el-icon-close" @click="handleDeletePresetProp(formData.presetProp, prop)"/>
            </el-col>
          </el-row>
          <el-row style="background: white">
            <el-col>
              <el-popover
                placement="bottom"
                width="200"
                trigger="click"
              >
                <i class="el-icon-plus"/>
              </el-popover>
            </el-col>
          </el-row>
        </el-form-item>
      </el-form>
    </div>
    <div
      style="width: 100%;text-align: right;line-height: 50px;background: white;
    box-shadow:  rgb(194, 210, 230) -7px -1px 2px 0px;padding-right: 50px;position: absolute;bottom: 0;clear: both;"
    >
      <span style="margin-right: 30px">
        <el-button type="text" @click="hideDrawer">取消</el-button>
      </span>
      <span>
        <el-button type="success" :loading="componentFlag.createLoading" @click="handleSubmit">确认</el-button>
      </span>
    </div>
  </el-drawer>

</template>

<script>
  import {getMetaEventProp, updateMetaEvent} from '@/api/metadata'

  export default {
    name: 'EventCreate',
    components: {},
    directives: {},
    props: {
      enable: {
        type: Boolean,
        default: false
      },
      event: {
        type: Object,
        default() {
          return {
            platform: [],
            timing: '',
            example: '',
            type: '',
            name: '',
            showName: '',
            tag: [],
            propIds: []
          }
        }
      },

    },
    data() {
      return {
        formData: JSON.parse(JSON.stringify(this.event)),
        componentFlag: {
          enableTagAdd: false,
          createLoading: false
        },
        selectTag: '',
        tags: [],
        properties: [],
        selectProperties: []
      }
    },
    created() {
      this.fetchMetaEventProp()
    },
    methods: {
      hideDrawer() {
        this.formData = {}
        this.$emit('update:enable', false)
      },
      handleSubmit() {
        this.formData.propIds = []
        for (const index in this.selectProperties) {
          this.formData.propIds.push(this.selectProperties[index].id)
        }
        this.componentFlag.createLoading = true
        updateMetaEvent(this.formData).then(resp => {
          this.componentFlag.createLoading = false
          this.$message.success('success')
          this.$emit('on-update-event')
        }).catch(reason => {
          this.$notify({
            message: reason,
            type: 'error',
            duration: 2000
          })
          this.componentFlag.createLoading = false
        })
        this.hideDrawer()
      },
      handleDeleteProp(prop) {
        for (const index in this.selectProperties) {
          if (Object.is(this.selectProperties[index].id, prop.id)) {
            this.$delete(this.selectProperties, index)
            break
          }
        }
      },
      handleAddToList() {
        const selection = this.$refs.propertiesTable.store.states.selection
        for (const index in selection) {
          let isMatch = true
          for (const _index in this.selectProperties) {
            if (Object.is(this.selectProperties[_index].id, selection[index].id)) {
              isMatch = false
              break
            }
          }
          if (isMatch) {
            this.selectProperties.push(selection[index])
          }
        }
      },
      fetchMetaEventProp() {
        getMetaEventProp().then(resp => {
          this.properties = resp.items
          for (const index in this.properties) {
            if (this.properties[index].isCommon || this.properties[index].isPerset) {
              this.selectProperties.push(this.properties)
            }
          }
        })
      },
      hidePopover() {
        this.$refs.propertiesTable.clearSelection();
      },
      showPopover() {
        if (this.selectProperties) {
          this.selectProperties.forEach(row => {
            this.$refs.propertiesTable.toggleRowSelection(row)
          })
        }
      },
      openDrawer() {
        this.formData = JSON.parse(JSON.stringify(this.event))
        this.$set(this.formData, 'hide', this.formData['isVisible'])
        this.formData.propIds.some(id => {
          this.properties.some(item => {
            if (id === item.id) {
              this.selectProperties.push(item)
            }
          })
        })
      }
    }
  }
</script>

<style type="scss">
  .el-drawer__body {
    overflow-y: scroll;
    margin-bottom: 50px;
    overflow-x: hidden;
  }

  :focus {
    outline: 0;
  }
</style>
