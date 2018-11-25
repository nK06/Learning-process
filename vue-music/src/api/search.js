import {commonParams} from './config'
// import jsonp from 'common/js/jsonp'
import axios from 'axios'

// 获取热搜关键字数据
export function getHotKey() {
  const url = 'api/getHotKey'
  const data = Object.assign({}, commonParams, {
    format: 'json',
    needNewCode: 1,
    uin: 0,
    inCharset: 'utf-8',
    outCharset: 'utf-8',
    notice: 0,
    platform: 'h5',
    _: +new Date()
  })
  // 使用了axios 伪装请求
  return axios.get(url, {
    params: data
  }).then((res) => {
    return Promise.resolve(res.data)
  })
}

// 获取搜索结果数据
export function search(query, page, zhida, perpage) {
  const url = 'api/search'
  const data = Object.assign({}, commonParams, {
    format: 'json',
    needNewCode: 1,
    uin: 0,
    inCharset: 'utf-8',
    outCharset: 'utf-8',
    notice: 0,
    platform: 'h5',
    w: query,
    zhidaqu: 1,
    catZhida: zhida ? 1 : 0,
    t: 0,
    flag: 1,
    ie: 'utf-8',
    sem: 1,
    aggr: 0,
    perpage: perpage,
    n: perpage,
    p: page,
    remoteplace: 'txt.mqq.all',
    _: +new Date()
  })
  // 使用了axios 伪装请求
  return axios.get(url, {
    params: data
  }).then((res) => {
    return Promise.resolve(res.data)
  })
}
