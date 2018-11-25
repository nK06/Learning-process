import {commonParams} from './config'
import axios from 'axios'

// 获取排行榜数据
export function getTopList() {
  const url = 'api/getTopList'
  const data = Object.assign({}, commonParams, {
    uin: 0,
    format: 'json',
    inCharset: 'utf-8',
    outCharset: 'utf-8',
    notice: 0,
    platform: 'h5',
    needNewCode: 1,
    _: +new Date()
  })
  // 使用了axios 伪装请求
  return axios.get(url, {
    params: data
  }).then((res) => {
    return Promise.resolve(res.data)
  })
}

// 获取排行榜歌曲数据
export function getMusicList(topId) {
  const url = 'api/getMusicList'
  const data = Object.assign({}, commonParams, {
    uin: 0,
    format: 'json',
    inCharset: 'utf-8',
    outCharset: 'utf-8',
    notice: 0,
    platform: 'h5',
    needNewCode: 1,
    tpl: 3,
    page: 'detail',
    type: 'top',
    topid: topId
  })
  // 使用了axios 伪装请求
  return axios.get(url, {
    params: data
  }).then((res) => {
    return Promise.resolve(res.data)
  })
}
