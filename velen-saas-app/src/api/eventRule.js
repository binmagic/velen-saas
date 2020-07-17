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

export function getEventCheckRule() {
  return request({
    url:'/dashboard/eventCheckRule',
    method:'get'
  })
}

export function addEventCheckRule(data) {
  return request({
    url:'/dashboard/eventCheckRule',
    method:'post',
    data
  })
}

export function updateEventCheckRule(data) {
  return request({
    url:'/dashboard/eventCheckRule',
    method:'put',
    data
  })
}

export function deleteEventCheckRule(id) {
  return request({
    url:`/dashboard/eventCheckRule/${id}`,
    method:'delete'
  })
}

export function getInputParseType() {
  return request({
    url:'/dashboard/rule/inputParseType',
    method:'get'
  })
}

export function getFieldRuleType() {
  return request({
    url:'/dashboard/rule/fieldRuleType',
    method:'get'
  })
}
