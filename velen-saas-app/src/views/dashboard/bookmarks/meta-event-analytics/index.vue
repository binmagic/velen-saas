<template>
  <card>
    <custom-table :columns="columns" :value="data"/>
  </card>
</template>
<script>
import CustomTable from '_c/custom-table'
import Card from '../../card'
export default {
  components:{
    Card,
    CustomTable
  },
  name: 'meta-event-analytics',
  data(){
    return {
      data: [],
      columns: [],
    }
  },
  methods: {
    handleColumns(){

    }
  }
}
</script>
