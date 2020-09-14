import request from '@/utils/request'

// 查询菜单列表
export function listMenu(query) {
  return request({
    url: '/enterprise/drinkMenu/list',
    method: 'get',
    params: query
  })
}

// 查询菜单详细
export function getMenu(menuId) {
  return request({
    url: '/enterprise/drinkMenu/' + menuId,
    method: 'get'
  })
}

// 新增菜单
export function addMenu(data) {
  return request({
    url: '/enterprise/drinkMenu',
    method: 'post',
    data: data
  })
}

// 修改菜单
export function updateMenu(data) {
  return request({
    url: '/enterprise/drinkMenu',
    method: 'put',
    data: data
  })
}

// 删除菜单
export function delMenu(menuId) {
  return request({
    url: '/enterprise/drinkMenu/' + menuId,
    method: 'delete'
  })
}

// 导出菜单
export function exportMenu(query) {
  return request({
    url: '/enterprise/drinkMenu/export',
    method: 'get',
    params: query
  })
}

// 下载菜单导入模板
export function importTemplate() {
  return request({
    url: '/enterprise/drinkMenu/importTemplate',
    method: 'get'
  })
}

