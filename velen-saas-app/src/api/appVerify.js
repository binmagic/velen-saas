import request from '@/utils/request'

export function getAppVerify() {
  return request({
    url:'/dashboard/appVerify',
    method:'get'
  })
}

export function updateAppVerify(data) {
  return request({
    url:'/dashboard/appVerify',
    method:'put',
    data
  })
}
