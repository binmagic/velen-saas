import request from '@/utils/request'

export function addGroup(data) {
  return request({
    url:'/dashboard/group',
    method:'post',
    data
  })
}

export function updateGroup(data) {
  return request({
    url:'/dashboard/group',
    method:'put',
    data
  })
}

export function deleteGroup(id) {
    return request({
      url:`/dashboard/group/${id}`,
      method:'delete'
    })
}

export function getGroup() {
  return request({
    url:'/dashboard/group',
    method:'get'
  })

}
