import request from '@/utils/request'

export function getRoleList() {
  return request({
    url: '/admin/role',
    method: 'get'
  })
}

export function addRole(data) {
  return request({
    url: '/admin/role',
    method: 'post',
    data
  })
}

export function delRole(id) {
  return request({
    url: `/admin/role/${id}`,
    method: 'delete'
  })
}

export function updateRole(data) {
  return request({
    url: '/admin/role',
    method: 'put',
    data
  })
}

export function getRoleFunction(data) {
  return request({
    url: '/admin/role/function',
    method: 'get',
    params: { functionIds: data }
  })
}

export function getRoleMenu(data) {
  return request({
    url: '/admin/role/menu',
    method: 'get',
    params: { menuIds: data }
  })
}

export function getRoleComponent(data) {
  return request({
    url: '/admin/role/component',
    method: 'get',
    params: { componentIds: data }
  })
}

export function saveRoleMenu(data) {
  return request({
    url: '/admin/role/menu',
    method: 'post',
    data
  })
}

export function saveRoleFunction(data) {
  return request({
    url: '/admin/role/function',
    method: 'post',
    data
  })
}

export function saveRoleComponent(data) {
  return request({
    url: '/admin/role/component',
    method: 'post',
    data
  })
}
