import {commonParams} from './config'
import axios from 'axios'

// 获取歌词数据
export function getSongLyric(songmid) {
  const url = 'api/getSongLyric'
  const data = Object.assign({}, commonParams, {
    hostUin: 0,
    loginUin: 0,
    pcachetime: +new Date(),
    format: 'json',
    inCharset: 'utf8',
    outCharset: 'utf-8',
    platform: 'yqq',
    needNewCode: 0,
    songmid: songmid
  })
  // 使用了axios 伪装请求
  return axios.get(url, {
    params: data
  }).then((res) => {
    return Promise.resolve(res.data)
  })
}
