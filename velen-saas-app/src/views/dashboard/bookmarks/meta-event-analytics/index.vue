<template>
  <card>
    <template slot="title">{{ event_name }}</template>
    <template slot="tools" />
    <template slot="content">
      <custom-table :columns="columns" :value="data" :loading="loading" :total="total" @fetch="fetch" />
    </template>
  </card>
</template>
<script>
import CustomTable from '_c/custom-table'
import Card from '../../card'
import { query } from '@/api/query'

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
    },
    config: {
      type: String,
      default: () => {
        return {}
      }
    }
  },
  data() {
    return {
      columns: [],
      event_id: '',
      event_name: '',
      data: [],
      total: 0,
      loading: false,
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
    getMetaEvent(eventId) {
      for (const index in this.meta_events) {
        const meta_event = this.meta_events[index]
        if (Object.is(eventId, meta_event.id)) {
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
        if(prop) {
          this.columns.push({
            'prop': prop.name.toLowerCase(),
            'label': prop.showName
          })
        }
      }
    },
    fetch(param) {
      const that = this
      this.loading = true
      const params = JSON.parse(this.bookmarks.data)
      params.measures = Object.assign({}, params.measures, param || {})
      query(params).then(resp => {
        setTimeout(function() {
          that.loading = false
        }, 1000)
        const _data = []
        for(const index in resp.rows){
          const row = resp.rows[index]
          const _row = {}
          for(const key in row){
            _row[key.toLowerCase()] = row[key]
          }
          _data.push(_row)
        }
        this.data = _data
        this.total = resp.total || resp.rows.length
      }).catch(err => {
        this.loading = false
      })
    }
  }
}
</script>
