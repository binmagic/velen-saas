import request from '@/utils/request'

export function getDashboard(id){
  return request({
    url: `/dashboard/${id}`,
    method: 'get'
  })
}

export function addDashboard(data) {
  return request({
    url: '/dashboard',
    method: 'post',
    data
  })
}

export function updateDashboard(data) {
  return request({
    url:'/dashboard',
    method:'put',
    data
  })
}

export function deleteDashboard(id) {
  return request({
    url:`/dashboard/${id}`,
    method:'delete'
  })
}

export function addCommonDashboard(data) {
  return request({
    url: '/dashboard/commonDashboard',
    method:'post',
    data
  })
}

export function updateCommonDashboard(data) {
  return request({
    url:'/dashboard/commonDashboard',
    method:'put',
    data
  })
}

export function deleteCommonDashboard(id) {
  return request({
    url:`/dashboard/commonDashboard/${id}`,
    method:'delete'
  })
}
