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

export function getPage(params) {
  return request({
    url: '/dispatch',
    method: 'get',
    params
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

export function fastDispatch(data) {
  return request({
    url: '/dispatch/fast',
    method: 'post',
    data
  })
}
