<template>
  <div
    class="el-input-tag input-tag-wrapper"
    :class="[size ? 'el-input-tag--' + size : '']"
    @click="focusTagInput">
    <el-tag
      v-for="(tag, idx) in innerTags"
      :key="tag"
      :size="size"
      :closable="!readOnly"
      :disable-transitions="false"
      @close="remove(idx)">
      {{tag}}
    </el-tag>
    <el-input
      ref="input"
      size="small"
      v-model.trim="newTag"
      style="outline:none;      border:none;"
      @blur = "handleSelect"
      @keyup.delete.native="removeLastTag"
      @keyup.enter.native="handleSelect"
    ></el-input>
  </div>
</template>

<script>
  export default {
    props: {
      value: {
        type: Array,
        default: () => []
      },
      addTagOnKeys: {
        type: Array,
        default: () => [13, 188, 9]
      },
      readOnly: {
        type: Boolean,
        default: false
      },
      size: String
    },
    data () {
      return {
        newTag: '',
        innerTags: [...this.value],
        dialog_temp: {},
      }
    },
    watch: {
      value () {
        this.innerTags = [...this.value]
      }
    },
    methods: {
      focusTagInput () {
        this.$refs.input.focus()
      },
      // handleSelect是自己实现的方法
      handleSelect(item) {
        let addSuccess = false
        if (this.addTag(this.newTag.trim())) {
          addSuccess = true
        }
        if (addSuccess) {
          this.tagChange()
          this.newTag = ''
        }
      },

      addTag (tag) {
        tag = tag.trim()
        if (tag && !this.innerTags.includes(tag)) {
          this.innerTags.push(tag)
          return true
        }
        return false

      },
      remove (index) {
        this.innerTags.splice(index, 1)
        this.tagChange()
      },
      removeLastTag () {
        if (this.newTag) {
          return
        }
        this.innerTags.pop()
        this.tagChange()
      },
      tagChange () {
        this.$emit('input', this.innerTags)
      },
    }
  }
</script>

<style scoped>
  .input-tag-wrapper {
    position: relative;
    font-size: 14px;
    background-color: #fff;
    background-image: none;
    border-radius: 4px;
    border: 1px solid #dcdfe6;
    box-sizing: border-box;
    color: #606266;
    display: inline-block;
    outline: none;
    padding: 0 10px 0 5px;
    transition: border-color .2s cubic-bezier(.645,.045,.355,1);
    max-width: 100%;
  }

  .el-tag {
    margin-right: 4px;
  }

  .tag-input {
    background: transparent;
    border: none!important;
    font-size: 14px;
    height: 40px;
    outline: none;
    padding-left: 0;
    width: 100px;
  }

  .el-input-tag--mini .tag-input{
    height: 28px;
    line-height: 28px;
  }

  .el-input-tag--small .tag-input{
    height: 32px;
    line-height: 32px;
  }

  .el-input-tag--medium .tag-input{
    height: 36px;
    line-height: 36px;
  }

  /* 下面这个的效果是autocomplete的input没有边框*/
  /deep/ input {
    /* background-color: red!important;  调试用的样式 */
    outline:none!important;
    border:none!important;
  }

  /deep/ .el-input {
    width: 30%;
  }
</style>
