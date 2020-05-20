import request from '@/utils/request'

export function getRoleList() {
  return request({
    url: '/permission/role',
    method: 'get'
  })
}

export function addRole(data) {
  return request({
    url: '/permission/role',
    method: 'post',
    data
  })
}

export function delRole(id) {
  return request({
    url: `/permission/role/${id}`,
    method: 'delete'
  })
}

export function updateRole(data) {
  return request({
    url: '/permission/role',
    method: 'put',
    data
  })
}

export function getRoleFunction(data) {
  return request({
    url: '/permission/role/function',
    method: 'get',
    params: { functionIds: data }
  })
}

export function getRoleResource(data) {
  return request({
    url: '/permission/role/resource',
    method: 'get',
    params: { resourceIds: data }
  })
}

export function saveRoleResource(data) {
  return request({
    url: '/permission/role/resource',
    method: 'post',
    data
  })
}

export function saveRoleFunction(data) {
  return request({
    url: '/permission/role/function',
    method: 'post',
    data
  })
}
