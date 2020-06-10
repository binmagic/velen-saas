<template>
  <card>
    <template slot="title">{{event_name}}</template>
    <template slot="tools" />
    <template slot="content">
      <custom-table :columns="columns" :value="value" />
    </template>
  </card>
</template>
<script>
import CustomTable from '_c/custom-table'
import Card from '../../card'
export default {
  name: 'MetaEventAnalytics',
  components: {
    Card,
    CustomTable
  },
  props: {
    meta_props: {
      type: Array,
      default: () => {
        return []
      }
    },
    meta_events: {
      type: Array,
      default: () => {
        return []
      }
    },
    bookmarks: {
      type: Object,
      default: () => {
        return {}
      }
    }
  },
  data() {
    return {
      columns: [],
      value: [],
      event_id: '',
      event_name: '',
    }
  },
  mounted() {
    this.handleData()
    this.handleColumns()
  },
  methods: {
    handleData() {
      const data = JSON.parse(this.bookmarks.data)
      this.event_id = data.measures
      this.event_name = this.getMetaEvent(this.event_id).showName
    },
    getMetaEvent(eventId){
      for (const index in this.meta_events) {
        const meta_event = this.meta_events[index]
        if(Object.is(eventId, meta_event.id)){
          return meta_event
        }
      }
    },
    handleColumns() {
      console.log('handleColumns')
      const meta_event = this.getMetaEvent(this.event_id)
      for (const _index in meta_event.propIds) {
        const id = meta_event.propIds[_index]
        const prop = this.meta_props[id]
        this.columns.push({
          'prop': prop.name,
          'label': prop.showName
        })
      }
    }
  }
}
</script>
