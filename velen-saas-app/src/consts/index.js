const metadata = {
  data_type: ['NUMBER', 'STRING', 'BOOL', 'DATETIME'],
  platform: ['ANDROID', 'IOS', 'WEB']
}

const event_type = {
  '/mata_event_analytics/': {
    cname: '元事件统计',
    command: 'meta-event',
    name: 'menu-analyze-meta-event',
    desc: '元数据详细查询分析',
    icon: 'el-icon-hel'
  }
}








export default {
  metadata,
  event_type
}
