<template>
  <div class="side-menu-wrapper">
    <side-menu-dashboard v-if="$route.name.startsWith('menu-dashboard')" @on-select="handleSelect"/>
    <side-menu-analyze v-if="$route.name.startsWith('menu-analyze')" :active-index="$route.name"  @on-select="handleSelect"/>
    <side-menu-metadata v-if="$route.name.startsWith('menu-metadata')" :active-index="$route.name"  @on-select="handleSelect"/>
    <side-menu-more v-if="$route.name.startsWith('menu-more')" :active-index="$route.name" @on-select="handleSelect"/>
  </div>
</template>

<script>
import mixin from './mixin'
import SideMenuDashboard from './dashboard'
import SideMenuAnalyze from './analyze'
import SideMenuMetadata from './metadata'
import SideMenuMore from './more'

export default {
  name: 'SideMenu',
  components: {
    SideMenuDashboard,
    SideMenuAnalyze,
    SideMenuMetadata,
    SideMenuMore
  },
  mixins: [mixin],
  methods: {
    handleSelect(name) {
      this.$emit('on-select', name)
    }
  }
}
</script>
<style lang="scss">
@import './side-menu.scss';
</style>
