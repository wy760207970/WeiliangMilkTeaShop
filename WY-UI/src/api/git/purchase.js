import request from '@/utils/request'

// 查询采购清单列表
export function listPurchase(query) {
  return request({
    url: '/purchase/list',
    method: 'get',
    params: query
  })
}

// 查询采购清单详细
export function getPurchase(purchaseId) {
  return request({
    url: '/purchase/' + purchaseId,
    method: 'get'
  })
}

// 新增采购清单
export function addPurchase(data) {
  return request({
    url: '/purchase',
    method: 'post',
    data: data
  })
}

// 修改采购清单
export function updatePurchase(data) {
  return request({
    url: '/purchase',
    method: 'put',
    data: data
  })
}

// 删除采购清单
export function delPurchase(purchaseId) {
  return request({
    url: '/purchase/' + purchaseId,
    method: 'delete'
  })
}

// 导出采购清单
export function exportPurchase(query) {
  return request({
    url: '/purchase/export',
    method: 'get',
    params: query
  })
}

// 下载采购清单导入模板
export function importTemplate() {
  return request({
    url: '/purchase/importTemplate',
    method: 'get'
  })
}
