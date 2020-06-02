<template>
  <el-dialog :visible.sync="sortShowVisible" width="30%"
             :show-close="false"
             @open="openShow"
             @closed="closeDialog"
             :before-close="handleClose">
    <template slot="title">
      <div style="position: relative;">
        <el-row>
          <el-col :span="14" style="line-height: 40px;">
            <span v-if="!checkNode">管理我的概览排序</span>
            <span v-else>
              <el-popover
                placement="bottom"
                width="180"
                v-model="visible"
                trigger="click"
              >
                <el-select size="mini" v-model="selectGroup">
                  <el-option label="选择分组" :value="'0'">选择分组</el-option>
                  <el-option v-for="group in treeGroup" :key="group.id" :label="group.name"
                             :value="group.id"></el-option>
                </el-select>
                <el-button size="medium" icon="el-icon-rank" slot="reference">
                  移动到
                </el-button>
              </el-popover>
              <el-button size="medium" @click="resetChecked">
                取消选择
              </el-button>
            </span>
          </el-col>
          <el-col :span="10">
            <el-input
              v-model="sortInput"
              placeholder="搜索概览名称"
              prefix-icon="el-icon-search"
              size="medium"
              clearable
              style="text-align: right"/>
          </el-col>
        </el-row>
      </div>
    </template>
    <el-tree
      :data="treeGroup"
      :props="treeProps"
      ref="tree"
      :filter-node-method="filterNode"
      show-checkbox
      draggable node-key="id"
      :allow-drop="allowDrop"
      @node-drop="handleDrop"
      @check-change="checkNode=true"
    >
      <div slot-scope="{ node , data }"
           @mouseenter="()=>$set(data, 'show', true)"
           @mouseleave="()=>$set(data, 'show', false)"
           style="height:32px;width:526px;lineHeight: 32px;">
        <el-row v-if="!data.rename">
          <el-col :span="16">
            <span>{{data.name}}</span>
          </el-col>
          <el-col :span="8">
              <span style="float: right;margin-right: 5px;" v-show="data.show">
                <el-button type="text" size="small" @click.stop="$set(data, 'rename', true)">重命名</el-button>
                <el-popover
                  trigger="click"
                  placement="bottom"
                  width="180"
                  v-model="data.move"
                  >
                  <div style="text-align: center;">
                    <span style="font-size: 14px">移动到</span>
                  </div>
                  <el-select size="mini" filterable  v-model="moveGroup">
                    <el-option v-for="group in treeGroup" :key="group.id" :label="group.name"
                             :value="group.id"></el-option>
                  </el-select>
                  <div style="text-align: right; margin: 0; padding: 2px;">
                    <el-button size="mini" type="text" @click="$set(data,'move',false)">取消</el-button>
                    <el-button type="primary" size="mini" @click="moveDashboard">确定</el-button>
                  </div>
                  <el-button v-if="node.level==2" type="text" size="small" slot="reference"
                             @click.stop="data.move?$set(data,'move',true):$set(data,'move',false)">移动到</el-button>
                </el-popover>
                <el-popover trigger="click" placement="top" width="180" v-model="data.del" style="padding:10px;">
                  <div style="border-bottom: 1px solid #d3dce6;">
                    <span style="font-size: 14px"><i class="el-icon-warning" style="color: #ffbf00"/>确认删除</span>
                  </div>
                  <p>
                    删除后标签将移动到第一个分组
                  </p>
                  <div style="text-align: right; margin: 0; padding: 2px; border-top: 1px solid #d3dce6;">
                    <el-button size="mini" type="text" @click="$set(data,'del',false)">取消</el-button>
                    <el-button type="primary" size="mini" @click="delGroup(data)">确定</el-button>
                  </div>
                  <el-button type="text" size="small"
                             @click.stop="data.del?$set(data,'del',true):$set(data,'del',false)"
                             style="color: red;" slot="reference">删除</el-button>
                </el-popover>
              </span>
          </el-col>
        </el-row>
        <el-row v-else>
          <el-col :span="12">
            <el-input v-model="data.name" size="mini" v-focus @click.native.stop @blur="blurInput(data)"></el-input>
          </el-col>
        </el-row>
      </div>
    </el-tree>
    <div slot="footer">
      <el-button type="primary" size="medium" style="float:left;" @click="createGroup">新建分组</el-button>
      <el-button @click="sortClose" size="medium">取消</el-button>
      <el-button type="primary" size="medium" @click="updateSubmit">确定</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import {updateGroup, deleteGroup} from "@/api/group";

  export default {
    name: "sort",
    props: {
      sortShowVisible: {
        required: true,
        type: Boolean
      },
      sortGroups: {
        type: Array
      },
    },
    data() {
      return {
        sortInput: '',
        treeGroup: JSON.parse(JSON.stringify(this.sortGroups)),
        treeProps: {
          children: 'list',
          label: 'name'
        },
        visible: false,
        createIds: [],
        ids: [],
        checkNode: false,
        selectGroup: '0',
        moveGroup:''
      }
    },
    watch: {
      sortInput(val) {
        this.$refs.tree.filter(val)
      }
    },
    directives: {
      focus: {
        inserted: function (el) {
          el.querySelector('input').focus();
          // 通过querySelector()方法获取input元素
        }
      }
    },
    methods: {
      closeDialog() {
        this.treeGroup = JSON.parse(JSON.stringify(this.sortGroups))
      },
      filterNode(value, data, node) {
        if (!value) {
          return true;
        }
        let level = node.level;
        let _array = [];//这里使用数组存储 只是为了存储值。
        this.getReturnNode(node, _array, value);
        let result = false;
        _array.forEach((item) => {
          result = result || item;
        });
        return result;
      },
      getReturnNode(node, _array, value) {
        let isPass = node.data && node.data.name && node.data.name.indexOf(value) !== -1;
        isPass ? _array.push(isPass) : '';
        this.index++;
        if (!isPass && node.level != 1 && node.parent) {
          this.getReturnNode(node.parent, _array, value);
        }
      },
      allowDrop(draggingNode, dropNode, type) {
        if (draggingNode.level == 1) {
          if (dropNode.level == 1) {
            return type !== 'inner'
          } else if (dropNode.level == 2) {
            return false
          } else {
            return true
          }
          return type !== 'inner'
        } else {
          if (dropNode.level == 2) {
            return type !== 'inner'
          } else if (dropNode.level == 1) {
            return type == 'inner'
          } else {
            return false
          }
        }
      },
      handleDrop(draggingNode, dropNode, type, event) {
        if (draggingNode.level == 1) {
          for (let key in this.treeGroup) {
            this.treeGroup[key].sort = key
          }
        } else {
          if (type === 'inner') {
            draggingNode.data.sort = dropNode.childNodes.length - 1
            draggingNode.data.type = dropNode.data.id
          } else {
            let group = this.treeGroup.find(v => v.id === dropNode.data.type)
            for (let key in group.list) {
              group.list[key].sort = key
              group.list[key].type = dropNode.data.type
            }
          }
        }
      },
      updateSubmit() {
        updateGroup(this.treeGroup).then(response => {

        })
        if (this.ids.length > 0) {
          for (let key in this.ids) {
            deleteGroup(this.ids[key]).then(response => {

            })
          }
        }
        this.$emit('update-group')
        this.$emit('close-sort')
      },
      sortClose() {
        this.treeGroup = JSON.parse(JSON.stringify(this.sortGroups))
        this.$emit('close-sort')
      },
      handleClose() {
        this.$emit('close-sort')
      },
      openShow() {
        this.treeGroup = JSON.parse(JSON.stringify(this.sortGroups))
        this.moveGroup = JSON.parse(JSON.stringify(this.sortGroups[0].name))
      },
      blurInput(data) {
        if (data.name == '' || !data.name) {
          data.name = '新建分组' + this.treeGroup.length
        }
        this.$set(data, 'rename', false)
      },
      delGroup(data) {
        for (let key in data.list) {
          data.list[key].type = this.treeGroup[0].type
          this.treeGroup[0].list.push(data.list[key])
        }
        this.treeGroup.some((item, i) => {
          if (item.id == data.id) {
            this.treeGroup.splice(i, 1)
            return true
          }
        })
        if (this.createIds.indexOf(data.id) <= -1) {
          this.ids.push(data.id)
        }
      },
      createGroup() {
        const newGroup = {id: this.treeGroup.length + '', name: '新建分组' + this.treeGroup.length, list: [], rename: true}
        this.treeGroup.push(newGroup)
        this.createIds.push(newGroup.id)
      },
      resetChecked() {
        this.$set(this, 'checkNode', !this.checkNode)
        this.$refs.tree.setCheckedKeys([])
      },
      moveDashboard(){
        
      }
    }

  }
</script>

<style scoped>

</style>
