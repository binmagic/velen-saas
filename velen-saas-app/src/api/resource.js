import request from '@/utils/request'

export function getResourceList(query) {
  return request({
    url: '/permission/resource',
    method: 'get',
    params: query
  })
}

export function addResource(data) {
  return request({
    url: '/permission/resource',
    method: 'post',
    data
  })
}

export function delResource(id) {
  return request({
    url: `/permission/resource/${id}`,
    method: 'delete'
  })
}

export function updateResource(data) {
  return request({
    url: '/permission/resource',
    method: 'put',
    data
  })
}
