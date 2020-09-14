import request from '@/utils/request'

// 查询菜单列表
export function listMenu(query) {
  return request({
    url: '/enterprise/batchMenu/list',
    method: 'get',
    params: query
  })
}

// 查询菜单详细
export function getMenu(batchId) {
  return request({
    url: '/enterprise/batchMenu/' + batchId,
    method: 'get'
  })
}

// 新增菜单
export function addMenu(data) {
  return request({
    url: '/enterprise/batchMenu',
    method: 'post',
    data: data
  })
}

// 修改菜单
export function updateMenu(data) {
  return request({
    url: '/enterprise/batchMenu',
    method: 'put',
    data: data
  })
}

// 删除菜单
export function delMenu(batchId) {
  return request({
    url: '/enterprise/batchMenu/' + batchId,
    method: 'delete'
  })
}

