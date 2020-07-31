import request from '@/utils/request'

export function query(data){
  return request({
    url: '/query/report',
    method: 'post',
    data
  })
}
