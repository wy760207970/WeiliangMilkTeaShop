import request from '@/utils/request'

// 查询订单商品列表
export function listOrder(query) {
  return request({
    url: '/order/list',
    method: 'get',
    params: query
  })
}

// 新增订单
export function addOrder(data) {
  return request({
    url: '/order',
    method: 'post',
    data: data
  })
}

// 查询订单商品列表
export function listOrderItem(orderNo) {
  return request({
    url: '/orderItem/list/' + orderNo,
    method: 'get'
  })
}

// 新增订单商品
export function addOrderItem(data) {
  return request({
    url: '/orderItem',
    method: 'post',
    data: data
  })
}

