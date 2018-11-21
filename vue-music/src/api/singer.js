import {commonParams} from './config'
// import jsonp from 'common/js/jsonp'
import axios from 'axios'

export function getSingerList() {
  const url = '/api/getSingerList'
  const data = Object.assign({}, commonParams, {
    platform: 'yqq',
    hostUin: 0,
    loginUin: 0,
    needNewCode: 0,
    format: 'json',
    data: {
      comm: {
        ct: 24,
        cv: 10000
      },
      singerList: {
        module: 'Music.SingerListServer',
        method: 'get_singer_list',
        param: {
          area: -100,
          sex: -100,
          genre: -100,
          index: -100,
          sin: 0,
          cur_page: 1
        }
      }
    }
  })
  // 使用了axios 伪装请求
  return axios.get(url, {
    params: data
  }).then((res) => {
    return Promise.resolve(res.data)
  })
}

// 获取歌手详情数据
export function getSingerDetail(singerId) {
  const url = 'api/getSingerDetail'
  const data = Object.assign({}, commonParams, {
    hostUin: 0,
    format: 'jsonp',
    platform: 'yqq',
    needNewCode: 0,
    order: 'listen',
    begin: 0,
    num: 100,
    songstatus: 1,
    singermid: singerId
  })
  // 使用了axios 伪装请求
  return axios.get(url, {
    params: data
  }).then((res) => {
    return Promise.resolve(res.data)
  })
}
