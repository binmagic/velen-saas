import request from '@/utils/request'

export function getAppList(query) {
  return request({
    url: '/accounts/app/my',
    method: 'get',
    params: query
  })
}

export function getAppInfo() {
  return request({
    url: `/accounts/app`,
    method: 'get'
  })
}

export function getMemberInfo() {
  return request({
    url: `/accounts/app/member`,
    method: 'get'
  })
}

export function addMember(data) {
  return request({
    url: `/accounts/app/member`,
    method: 'post',
    data
  })
}

export function deleteMember(id) {
  return request({
    url: `/accounts/app/member/${id}`,
    method: 'delete'
  })
}

export function addApp(data) {
  return request({
    url: '/accounts/app',
    method: 'post',
    data
  })
}

export function delApp(id) {
  return request({
    url: `/accounts/app/${id}`,
    method: 'delete'
  })
}

export function updateApp(data) {
  return request({
    url: '/accounts/app',
    method: 'put',
    data
  })
}
