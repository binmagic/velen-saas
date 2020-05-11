import request from '@/utils/request'

export function getMenuList(query) {
  return request({
    url: '/admin/menu',
    method: 'get',
    params: query
  })
}

export function addMenu(data) {
  return request({
    url: '/admin/menu',
    method: 'post',
    data
  })
}

export function delMenu(id) {
  return request({
    url: `/admin/menu/${id}`,
    method: 'delete'
  })
}

export function updateMenu(data) {
  return request({
    url: '/admin/menu',
    method: 'put',
    data
  })
}
