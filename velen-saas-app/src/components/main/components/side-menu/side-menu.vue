<template>
  <div class="side-menu-wrapper">
    <slot></slot>
    <el-menu ref="menu" mode="horizontal" @select="handleSelect" active-text-color="#ffd04b" background-color="#fff"  text-color="#503434">
      <template v-for="item in menuList">
        <template v-if="item.children && item.children.length === 1">
          <side-menu-item v-if="showChildren(item)" :key="resolvePath(item.path)" :parent-item="item"></side-menu-item>
          <el-menu-item v-else :index="resolvePath(item.children[0].path)"><span>{{ showTitle(item.children[0]) }}</span></el-menu-item>
        </template>
        <template v-else>
          <side-menu-item v-if="showChildren(item)" :key="resolvePath(item.path)" :parent-item="item"></side-menu-item>
          <el-menu-item v-else :index="resolvePath(item.path)"><span>{{ showTitle(item) }}</span></el-menu-item>
        </template>
      </template>
    </el-menu>
  </div>
</template>

<script>
import SideMenuItem from './side-menu-item.vue'
import mixin from './mixin'
import path from 'path'
import { isExternal } from '@/utils/validate'
import { getUnion } from '@/libs/tools'

export default {
  name: 'SideMenu',
  components: {
    SideMenuItem
  },
  mixins: [mixin],
  props: {
    menuList: {
      type: Array,
      default() {
        return []
      }
    },
    rootIconSize: {
      type: Number,
      default: 20
    },
    iconSize: {
      type: Number,
      default: 16
    },
    activeName: {
      type: String,
      default: ''
    },
    openNames: {
      type: Array,
      default: () => []
    }
  },
  data() {
    return {
      openedNames: []
    }
  },
  computed: {
    textColor() {
      return this.theme === 'dark' ? '#fff' : '#495060'
    }
  },
  watch: {
    activeName(name) {
      // if (this.accordion) this.openedNames = this.getOpenedNamesByActiveName(name)
      // else this.openedNames = getUnion(this.openedNames, this.getOpenedNamesByActiveName(name))
    },
    openNames(newNames) {
      // this.openedNames = newNames
    },
    openedNames() {
      this.$nextTick(() => {
        // this.$refs.menu.updateOpened()
      })
    }
  },
  mounted() {
    this.openedNames = getUnion(this.openedNames, this.getOpenedNamesByActiveName(name))
  },
  methods: {
    handleSelect(name) {
      this.$emit('on-select', name)
    },
    getOpenedNamesByActiveName(name) {
      return this.$route.matched.map(item => item.name).filter(item => item !== name)
    },
    updateOpenName(name) {
      if (name === 'home') this.openedNames = []
      else this.openedNames = this.getOpenedNamesByActiveName(name)
    },
    resolvePath(routePath) {
      if (isExternal(routePath)) {
        return routePath
      }
      if (isExternal(this.basePath)) {
        return this.basePath
      }
      return path.resolve(this.basePath, routePath)
    }
  }
}
</script>
<style lang="scss">
@import './side-menu.scss';
</style>
