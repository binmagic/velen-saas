import request from '@/utils/request'


export function getEventProp(params){
  return request({
    uri: '/metadata/event/prop',
    method: 'get',
    params
  })
}

export function createEventProp(data){
  return request({
    uri: '/metadata/event/prop',
    method: 'post',
    data
  })
}
