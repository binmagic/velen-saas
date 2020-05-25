import request from '@/utils/request'

export function addDashboard(data) {
  return request({
    url: '/dashboard',
    method: 'post',
    data
  })
}
