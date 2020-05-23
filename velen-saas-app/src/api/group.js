import request from '@/utils/request'

export function addGroup(data) {
  return request({
    url:'/group/addGroup',
    method:'post',
    data
  })
}

export function updateGroup(data) {
  return request({
    url:'/group/updateGroup',
    method:'put',
    data
  })
}

export function deleteGroup(id) {
    return request({
      url:`/group/delete/${id}`,
      method:'delete'
    })
}
