<template>
  <el-dialog :visible.sync="visible" :before-close="handleClose">
    <template slot="title">
      <p>入库规则设置</p>
    </template>
    <el-button type="primary" @click="addTableRow">添加</el-button>
    <el-table :data="data" max-height="200">
      <el-table-column prop="name" label="匹配规则名"/>
      <el-table-column label="规则">
        <template slot-scope="scope">
          <span v-if="!scope.row.update">{{scope.row.rule}}</span>
          <el-input v-else v-model="scope.row.rule" @blur="updRule(scope.row)" v-focus></el-input>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button type="text" @click="updateRow(scope.row)">修改</el-button>
          <el-popconfirm
            confirmButtonText="确定"
            cancelButtonText="取消"
            icon="el-icon-info"
            iconColor="red"
            title="删除该解析器?"
            @onConfirm="delRow(scope.row)"
          >
            <el-button slot="reference" type="text" style="color: red;">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <add-dialog
      :visible.sync="addVisible"
      :type="type"
      @close-add-rule="handleAddClose"
      @add-event-rule="insertEventKeyRule"
      @add-profile-rule="insertProfileKeyRule"
    />
  </el-dialog>
</template>

<script>
  import AddDialog from '../add-rule'
  import {addEventCheckRule, updateEventCheckRule, deleteEventCheckRule} from "@/api/eventRule";
  import {addProfileCheckRule, updateProfileCheckRule, deleteProfileCheckRule} from "@/api/profileRule";

  export default {
    components: {
      AddDialog
    },
    props: {
      type: {
        required: true,
        type: String
      },
      data: {
        type: Array
      },
      visible: {
        required: true,
        type: Boolean
      },
      keyName: {
        type: String
      }
    },
    directives: {
      focus: {
        inserted: function (el) {
          el.querySelector('input').focus();
        }
      }
    },
    data() {
      return {
        addVisible: false,
      }
    },
    methods: {
      handleClose() {
        this.$emit('close-check-table')
      },
      addTableRow(type) {
        this.addVisible = true
      },
      updateRow(row) {
        if (row.name.toLowerCase().indexOf('json')<=-1)
          this.$set(row, 'update', true)
      },
      updRule(row) {
        if (row.type === 'event') {
          updateEventCheckRule(row).then(resp => {
            this.$emit("find-event-data")
          })
        } else {
          updateProfileCheckRule(row).then(resp => {
            this.$emit("find-profile-data")
          })
        }
        this.$set(row, 'update', false)
      },
      delRow(row) {
        if (row.type == 'event') {
          deleteEventCheckRule(row.id).then(resp => {
            this.$emit("find-event-data")
          })
        } else {
          deleteProfileCheckRule(row.id).then(resp => {
            this.$emit("find-profile-data")
          })
        }
      },
      handleAddClose() {
        this.addVisible = false
      },
      insertEventKeyRule(val) {
        val.key = this.keyName
        val.type = 'event'
        addEventCheckRule(val).then(resp => {
          this.$emit("find-event-data")
        })
      },
      insertProfileKeyRule(val) {
        val.key = this.keyName
        val.type = 'profile'
        addProfileCheckRule(val).then(resp => {
          this.$emit("find-profile-data")
        })
      }
    }
  }
</script>

<style scoped>

</style>
