import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/accounts/login',
    // url: '/user/login',
    method: 'post',
    data
  })
}

export function getInfo(token) {
  return request({
    url: '/accounts/info',
    // url: '/user/info',
    method: 'get',
    params: {token}
  })
}

export function logout() {
  return request({
    url: '/accounts/logout',
    method: 'post'
  })
}

export function authorization() {
  return request({
    url: '/accounts/authorization',
    method: 'get'
  })
}

