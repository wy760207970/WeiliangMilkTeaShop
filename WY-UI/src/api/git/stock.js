import request from '@/utils/request'

// 查询库存列表
export function listStock(query) {
  return request({
    url: '/stock/list',
    method: 'get',
    params: query
  })
}

// 查询库存详细
export function getStock(stockId) {
  return request({
    url: '/stock/' + stockId,
    method: 'get'
  })
}

// 新增库存
export function addStock(data) {
  return request({
    url: '/stock',
    method: 'post',
    data: data
  })
}

// 修改库存
export function updateStock(data) {
  return request({
    url: '/stock',
    method: 'put',
    data: data
  })
}

// 删除库存
export function delStock(stockId) {
  return request({
    url: '/stock/' + stockId,
    method: 'delete'
  })
}

// 导出库存
export function exportStock(query) {
  return request({
    url: '/stock/export',
    method: 'get',
    params: query
  })
}
