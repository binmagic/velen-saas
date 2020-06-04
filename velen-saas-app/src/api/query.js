import request from '@/utils/request'

export function reportMetaEvent(data) {
  return request({
    url: '/query/meta-event',
    method: 'post',
    data
  })
}
