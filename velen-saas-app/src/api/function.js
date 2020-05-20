import request from '@/utils/request'

export function getFunctionList(query) {
  return request({
    url: '/permission/function',
    method: 'get',
    params: query
  })
}

export function addFunction(data) {
  return request({
    url: '/permission/function',
    method: 'post',
    data
  })
}

export function delFunction(id) {
  return request({
    url: `/permission/function/${id}`,
    method: 'delete'
  })
}

export function updateFunction(data) {
  return request({
    url: '/permission/function',
    method: 'put',
    data
  })
}
