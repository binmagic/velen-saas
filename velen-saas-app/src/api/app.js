import request from '@/utils/request'

export function getAppList(query) {
  return request({
    url: '/admin/app',
    method: 'get',
    params: query
  })
}

export function getAppInfo(id){
  return request({
    url: `/admin/app/${id}`,
    method: 'get'
  })
}

export function getMemberInfo(id){
  return request({
    url: `/admin/app/${id}/member`,
    method: 'get'
  })
}

export function addMember(id, data){
  return request({
    url: `/admin/app/${id}/member`,
    method: 'post',
    data
  })
}

export function deleteMember(appId, id){
  return request({
    url: `/admin/app/${appId}/member/${id}`,
    method: 'delete'
  })
}

export function addApp(data) {
  return request({
    url: '/admin/app',
    method: 'post',
    data
  })
}

export function delApp(id) {
  return request({
    url: `/admin/app/${id}`,
    method: 'delete'
  })
}

export function updateApp(data) {
  return request({
    url: '/admin/app',
    method: 'put',
    data
  })
}
