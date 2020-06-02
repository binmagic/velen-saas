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

export function getMetaEventProp(id) {
  return request({
    url: `/metadata/event/prop/${id}`,
    method: 'get'
  })
}

export function createMetaEventProp(data) {
  return request({
    url: '/metadata/event/prop',
    method: 'post',
    data
  })
}
