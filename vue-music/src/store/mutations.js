import * as types from './mutations-types'

// 挂载一些修改方法
const mutations = {
  [types.SET_SINGER](state, singer) {
    state.singer = singer
  }
}

export default mutations
