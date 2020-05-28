<template>
  <div class="custom-header">
    <div class="custom-header-title">
      <span><h3>{{ showName }}</h3></span>
      <slot name="title"></slot>
    </div>
    <div class="custom-tools">
      <slot name="tools"></slot>
    </div>
  </div>
</template>

<script>
import './custom-header.scss'
export default {
  name: 'CustomHeader',
  props: {
    showName: {
      type: String,
      default: ''
    }
  }
}
</script>

