import request from '@/utils/request'

export function getMetaEvent(params) {
  return request({
    url: '/metadata/event',
    method: 'get',
    params
  })
}

export function createMetaEvent(data) {
  return request({
    url: '/metadata/event',
    method: 'post',
    data
  })
}

export function getMetaEventProp(params) {
  return request({
    url: `/metadata/event/prop`,
    method: 'get',
    params
  })
}

export function createMetaEventProp(data) {
  return request({
    url: '/metadata/event/prop',
    method: 'post',
    data
  })
}

export function updateMetaEvent(data) {
  return request({
    url: '/metadata/event',
    method: 'put',
    data
  })
}

export function updateMetaEventProp(data) {
  return request({
    url: '/metadata/event/prop',
    method: 'put',
    data
  })
}

export function deleteMetaEventProp(id) {
  return request({
    url: `/metadata/event/prop/${id}`,
    method: 'delete',
  })
}

export function all() {
  return request({
    url:'/metadata/all',
    method:'get'
  })
}
