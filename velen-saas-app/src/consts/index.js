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

const filter_type = {
  option1: [
    {label: '等于', value: 'equal'},
    {label: '不等于', value: 'notEqual'},
    {label: '小于', value: 'less'},
    {label: '大于', value: 'greater'},
    {label: '区间', value: 'between'},
    {label: '有值', value: 'isSet'},
    {label: '无值', value: 'notSet'},
  ],
  option2: [
    {label: '等于', value: 'equal'},
    {label: '不等于', value: 'notEqual'},
    {label: '包含', value: 'contain'},
    {label: '不包含', value: 'notContain'},
    {label: '有值', value: 'isSet'},
    {label: '没值', value: 'notSet'},
    {label: '为空', value: 'isEmpty'},
    {label: '不为空', value: 'isNotEmpty'},
    {label: '正则匹配', value: 'rlike'},
    {label: '正则不匹配', value: 'notrlike'},
  ],
  option3: [
    {label: '为真', value: 'isTrue'},
    {label: '为假', value: 'isFalse'},
    {label: '有值', value: 'isSet'},
    {label: '没值', value: 'notSet'},
  ],
  option4:[
    {label:'绝对时间',value:'absolute_between'},
    {label:'相对当前时间点',value:'relative_within'},
    {label:'相对当前时间区间',value:'relative_between'},
    {label:'有值',value:'isSet'},
    {label:'没值',value:'notSet'},
  ]
}


export default {
  metadata,
  event_type,
  filter_type
}
