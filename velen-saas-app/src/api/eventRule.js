import request from '@/utils/request'

export function getEventRule(){
  return request({
    url:'/dashboard/rule',
    method:'get'
  })
}

export function addEventRule(data) {
  return request({
    url:'/dashboard/rule',
    method:'post',
    data
  })
}

export function updateEventRule(data) {
  return request({
    url: '/dashboard/rule',
    method: 'put',
    data
  })
}

export function deleteEventRule(id) {
  return request({
    url:`/dashboard/rule/${id}`,
    method:'delete'
  })
}

export function getEventKeyRule() {
  return request({
    url:'/dashboard/eventKeyRule',
    method:'get'
  })
}

export function addEventKeyRule(data) {
  return request({
    url:'/dashboard/eventKeyRule',
    method:'post',
    data
  })
}

export function updateEventKeyRule(data) {
  return request({
    url:'/dashboard/eventKeyRule',
    method:'put',
    data
  })
}

export function deleteEventKeyRule(id) {
  return request({
    url:`/dashboard/eventKeyRule/${id}`,
    method:'delete'
  })
}
