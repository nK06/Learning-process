/*
 * 异步操作或者是对多个mutation 进行操作时在这里写
 */

import * as types from './mutations-types'

export const selectPlay = function ({commit, state}, {list, index}) {
  commit(types.SET_SEQUENCELIST, list)
  commit(types.SET_PLAYLIST, list)
  commit(types.SET_CURRENT_INDEX, index)
  commit(types.SET_FULL_SCREEN, true)
  commit(types.SET_PLAY_MODE, true)
}
