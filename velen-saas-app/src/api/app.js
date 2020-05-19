import request from '@/utils/request'

export function getAppList(query) {
  return request({
    url: '/accounts/app',
    method: 'get',
    params: query
  })
}

export function getAppInfo(id){
  return request({
    url: `/accounts/app/${id}`,
    method: 'get'
  })
}

export function getMemberInfo(id){
  return request({
    url: `/accounts/app/${id}/member`,
    method: 'get'
  })
}

export function addMember(id, data){
  return request({
    url: `/accounts/app/${id}/member`,
    method: 'post',
    data
  })
}

export function deleteMember(appId, id){
  return request({
    url: `/accounts/app/${appId}/member/${id}`,
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
