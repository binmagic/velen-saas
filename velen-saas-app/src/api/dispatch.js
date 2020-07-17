import request from '@/utils/request'

export function getPlatform() {
  return request({
    url: '/dispatch/platform',
    method: 'get'
  })
}

export function getProcess() {
  return request({
    url: '/dispatch/process',
    method: 'get'
  })
}

export function getPage(query) {
  return request({
    url: '/dispatch',
    method: 'get',
    query
  })
}

export function createDispatch(data) {
  return request({
    url: '/dispatch',
    method: 'post',
    data
  })
}

export function updateDispatch(data) {
  return request({
    url: '/dispatch',
    method: 'put',
    data
  })
}
