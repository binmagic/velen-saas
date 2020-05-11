import request from '@/utils/request'

export function getComponentList(query) {
  return request({
    url: '/admin/component',
    method: 'get',
    params: query
  })
}



export function addComponent(data) {
  return request({
    url: '/admin/component',
    method: 'post',
    data
  })
}

export function delComponent(id) {
  return request({
    url: `/admin/component/${id}`,
    method: 'delete'
  })
}

export function updateComponent(data) {
  return request({
    url: '/admin/component',
    method: 'put',
    data
  })
}
