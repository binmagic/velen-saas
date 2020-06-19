import request from '@/utils/request'

export function getProfileRule(){
  return request({
    url:'/dashboard/profileRule',
    method:'get'
  })
}

export function addProfileRule(data) {
  return request({
    url:'/dashboard/profileRule',
    method:'post',
    data
  })
}

export function updateProfileRule(data) {
  return request({
    url: '/dashboard/profileRule',
    method: 'put',
    data
  })
}

export function deleteProfileRule(id) {
  return request({
    url:`/dashboard/profileRule/${id}`,
    method:'delete'
  })
}

export function getProfileKeyRule() {
  return request({
    url:'/dashboard/profileKeyRule',
    method:'get'
  })
}

export function addProfileKeyRule(data) {
  return request({
    url:'/dashboard/profileKeyRule',
    method:'post',
    data
  })
}

export function updateProfileKeyRule(data){
  return request({
    url:'/dashboard/profileKeyRule',
    method:'put',
    data
  })
}

export function deleteProfileKeyRule(id) {
  return request({
    url:`/dashboard/profileKeyRule/${id}`,
    method:'delete'
  })
}
