<template>
  <div class="header-bar">
    <sider-trigger :collapsed="collapsed" icon="md-menu" @on-change="handleCollpasedChange" />
    <div class="custom-content-con">
      <slot />
    </div>
  </div>
</template>
<script>
import siderTrigger from './sider-trigger'
import './header-bar.scss'
export default {
  name: 'HeaderBar',
  components: {
    siderTrigger
  },
  props: {
    collapsed: Boolean
  },
  computed: {

  },
  methods: {
    handleCollpasedChange(state) {
      this.$emit('on-coll-change', state)
    }
  }
}
</script>
