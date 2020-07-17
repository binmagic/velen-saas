import request from '@/utils/request'

export function getAppList(query) {
  return request({
    url: '/app/my',
    method: 'get',
    params: query
  })
}

export function getAppInfo() {
  return request({
    url: `/app`,
    method: 'get'
  })
}

export function getMemberInfo() {
  return request({
    url: `/app/member`,
    method: 'get'
  })
}

export function addMember(data) {
  return request({
    url: `/app/member`,
    method: 'post',
    data
  })
}

export function deleteMember(id) {
  return request({
    url: `/app/member/${id}`,
    method: 'delete'
  })
}

export function addApp(data) {
  return request({
    url: '/app',
    method: 'post',
    data
  })
}

export function delApp(id) {
  return request({
    url: `/app/${id}`,
    method: 'delete'
  })
}

export function updateApp(data) {
  return request({
    url: '/app',
    method: 'put',
    data
  })
}

export function fetchTemplates(){
  return request({
    url: '/app/template',
    method: 'get'
  })
}
