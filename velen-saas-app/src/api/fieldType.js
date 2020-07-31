import request from '@/utils/request'

export function getFieldType() {
  return request({
    url: '/dashboard/eventFieldType',
    method: 'get'
  })
}
