import request from '@/utils/request'

// 查询库存详情列表
export function listStockDetail(query) {
  return request({
    url: '/stockDetail/list',
    method: 'get',
    params: query
  })
}

// 查询库存详情详细
export function getStockDetail(stockDetailId) {
  return request({
    url: '/stockDetail/' + stockDetailId,
    method: 'get'
  })
}

// 新增库存详情
export function addStockDetail(data) {
  return request({
    url: '/stockDetail',
    method: 'post',
    data: data
  })
}

// 修改库存详情
export function updateStockDetail(data) {
  return request({
    url: '/stockDetail',
    method: 'put',
    data: data
  })
}

// 删除库存详情
export function delStockDetail(stockId) {
  return request({
    url: '/stockDetail/' + stockId,
    method: 'delete'
  })
}
