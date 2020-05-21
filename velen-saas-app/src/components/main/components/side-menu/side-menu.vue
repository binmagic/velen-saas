<template>
  <div class="side-menu-wrapper">
    <side-menu-dashboard v-if="$route.name.startsWith('menu-dashboard')" @on-select="handleSelect"/>
    <side-menu-analyze v-if="$route.name.startsWith('menu-analyze')"  @on-select="handleSelect"/>
  </div>
</template>

<script>
import mixin from './mixin'
import SideMenuDashboard from './dashboard'
import SideMenuAnalyze from './analyze'

export default {
  name: 'SideMenu',
  components: {
    SideMenuDashboard,
    SideMenuAnalyze
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
