import request from '@/utils/request'

export function all(params) {
  return request({
    url: '/bookmarks',
    method: 'get',
    params
  })
}

export function create(data) {
  return request({
    url: '/bookmarks',
    method: 'post',
    data
  })
}

export function update(data) {
  return request({
    url: '/bookmarks',
    method: 'put',
    data
  })
}

