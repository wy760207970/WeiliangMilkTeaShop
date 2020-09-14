import request from '@/utils/request'

export function listTurnover(query) {
  return request({
    url: '/turnover/list',
    method: 'get',
    params: query
  })
}

// 导出
export function exportTurnover(query) {
  return request({
    url: '/turnover/export',
    method: 'get',
    params: query
  })
}
