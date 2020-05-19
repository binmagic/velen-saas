import request from '@/utils/request'

export function getResourceList(query) {
  return request({
    url: '/admin/resource',
    method: 'get',
    params: query
  })
}

export function addResource(data) {
  return request({
    url: '/admin/resource',
    method: 'post',
    data
  })
}

export function delResource(id) {
  return request({
    url: `/admin/resource/${id}`,
    method: 'delete'
  })
}

export function updateResource(data) {
  return request({
    url: '/admin/resource',
    method: 'put',
    data
  })
}
