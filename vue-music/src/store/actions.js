/*
 * 异步操作或者是对多个mutation 进行操作时在这里写
 */

import * as types from './mutations-types'
import {playMode} from 'common/js/config'
import {shuffle} from 'common/js/util'
import {saveSearch, deleteSearch, clearSearch, savePlay, saveFavorite, deleteFavorite} from 'common/js/cache'

// 查歌曲的index
function findIndex(list, song) {
  return list.findIndex((item) => {
    return item.id === song.id
  })
}

// 歌手详情页面点击歌曲
export const selectPlay = function ({commit, state}, {list, index}) {
  commit(types.SET_SEQUENCELIST, list)
  if (state.mode === playMode.random) {
    let randomList = shuffle(list)
    commit(types.SET_PLAYLIST, randomList)
    index = findIndex(randomList, list[index])
  } else {
    commit(types.SET_PLAYLIST, list)
  }
  commit(types.SET_CURRENT_INDEX, index)
  commit(types.SET_FULL_SCREEN, true)
  commit(types.SET_PLAYING_STATE, true)
}

// 歌手详情页面 随机播放全部
export const randomPlay = function ({commit}, {list}) {
  commit(types.SET_PLAY_MODE, playMode.random)
  commit(types.SET_SEQUENCELIST, list)
  let randomList = shuffle(list)
  commit(types.SET_PLAYLIST, randomList)
  commit(types.SET_CURRENT_INDEX, 0)
  commit(types.SET_FULL_SCREEN, true)
  commit(types.SET_PLAYING_STATE, true)
}

// 搜索列表，点击搜索结果的歌曲
// export const insertSong = function ({commit, state}, song) {
//   let playList = state.playList.slice()
//   let sequenceList = state.sequenceList.slice()
//   let currentIndex = state.currentIndex
//
//   // 记录当前歌曲
//   let currentSong = playList[currentIndex]
//
//   // 查找当前列表中是否有待插入的歌曲，并返回其索引
//   let fpIndex = findIndex(playList, song)
//
//   // 因为是插入歌曲， 索引+1
//   currentIndex++
//   // 插入这首歌到当前索引位置
//   playList.splice(currentIndex, 0, song)
//   // 如果已经包含了这首歌
//   if (fpIndex > -1) {
//     // 当前插入的序号 大于 列表中的序号
//     if (currentIndex >= fpIndex) {
//       playList.splice(fpIndex, 1)
//       currentIndex--
//     } else {
//       playList.splice(fpIndex + 1, 1)
//     }
//   }
//
//   let currentSIndex = findIndex(sequenceList, currentSong) + 1
//   // 检查在sequenceList 中是否包含待插入的歌曲
//   let fsIndex = findIndex(sequenceList, song)
//
//   sequenceList.splice(currentSIndex, 0, song)
//   if (fsIndex > -1) {
//     // 当前插入的序号 大于 列表中的序号
//     if (currentSIndex >= fsIndex) {
//       playList.splice(fsIndex, 1)
//     } else {
//       playList.splice(fsIndex + 1, 1)
//     }
//   }
//   commit(types.SET_PLAYLIST, playList)
//   commit(types.SET_SEQUENCELIST, sequenceList)
//   commit(types.SET_CURRENT_INDEX, currentIndex)
//   commit(types.SET_FULL_SCREEN, true)
//   commit(types.SET_PLAYING_STATE, true)
// }

// 搜索页点击后添加一首歌曲
export const insertSong = function ({commit, state}, song) {
  // 这里为了避免直接修改由vuex管理的数据（这个playlist），应该复制一份操作
  let playList = state.playList.slice()
  let sequenceList = state.sequenceList.slice()
  let currentIndex = state.currentIndex
  // 记录当前歌曲
  let currentSong = playList[currentIndex]
  // 查找当前列表中是否有待插入的歌曲，返回其索引
  let fpIndex = findIndex(playList, song)
  // 因为是插入歌曲，在当前歌曲的后面插入，索引加1
  currentIndex++
  // 插入这首歌到当前索引位置
  playList.splice(currentIndex, 0, song)
  // 如果已经有这首歌了，那就删掉旧的
  if (fpIndex > -1) {
    // 如果原来的歌曲index在现在插入位置之前（或者刚好就是我们插入的这个位置，即当前播放歌曲刚好就是我们要新加的这首歌），之前记录的位置在插入后没有变化
    if (currentIndex >= fpIndex) {
      playList.splice(fpIndex, 1)
      // 删除后插入位置已经变了，需要重新设置
      currentIndex--
    } else {
      // 如果原来的歌曲index在现在插入位置之后，那么前面插入的操作已经将之前记录的这个位置往下顶了1
      playList.splice(fpIndex + 1, 1)
    }
  }

  // 当前播放歌曲在哪个位置，然后加1就是我们插入的位置
  let currentSIndex = findIndex(sequenceList, currentSong) + 1

  // 查找我们要插入的这首歌有没有在播放列表中
  let fsIndex = findIndex(sequenceList, song)

  // 插入这首歌到当前索引位置
  sequenceList.splice(currentSIndex, 0, song)
  // 如果已经有这首歌了，那就删掉旧的
  if (fsIndex > -1) {
    // 如果原来的歌曲index在现在插入位置之前（或者刚好就是我们插入的这个位置，即当前播放歌曲刚好就是我们要新加的这首歌），之前记录的位置在插入后没有变化
    if (currentSIndex >= fsIndex) {
      sequenceList.splice(fsIndex, 1)
    } else {
      // 如果原来的歌曲index在现在插入位置之后，那么前面插入的操作已经将之前记录的这个位置往下顶了1
      sequenceList.splice(fsIndex + 1, 1)
    }
  }
  commit(types.SET_SEQUENCELIST, sequenceList)
  commit(types.SET_PLAYLIST, playList)
  commit(types.SET_CURRENT_INDEX, currentIndex)
  commit(types.SET_FULL_SCREEN, true)
  commit(types.SET_PLAYING_STATE, true)
}

export const saveSearchHistory = function ({commit}, query) {
  commit(types.SET_SEARCH_HISTORY, saveSearch(query))
}

export const deleteSearchHistory = function ({commit}, query) {
  commit(types.SET_SEARCH_HISTORY, deleteSearch(query))
}

export const clearSearchHistory = function ({commit}) {
  commit(types.SET_SEARCH_HISTORY, clearSearch())
}

export const deleteSong = function ({commit, state}, song) {
  // 这里为了避免直接修改由vuex管理的数据（这个playlist），应该复制一份操作
  let playList = state.playList.slice()
  let sequenceList = state.sequenceList.slice()
  let currentIndex = state.currentIndex

  // 删除播放列表中的歌曲
  let pIndex = findIndex(playList, song)
  playList.splice(pIndex, 1)

  // 删除队列列表的歌曲
  let sIndex = findIndex(sequenceList, song)
  sequenceList.splice(sIndex, 1)

  if (currentIndex > pIndex || currentIndex === playList.length) {
    currentIndex--
  }

  commit(types.SET_PLAYLIST, playList)
  commit(types.SET_SEQUENCELIST, sequenceList)
  commit(types.SET_CURRENT_INDEX, currentIndex)

  const playingState = playList.length > 0
  commit(types.SET_PLAYING_STATE, playingState)
}

export const deleteSongList = function ({commit}) {
  commit(types.SET_PLAYLIST, [])
  commit(types.SET_SEQUENCELIST, [])
  commit(types.SET_CURRENT_INDEX, -1)
  commit(types.SET_PLAYING_STATE, false)
}

export const savePlayHistory = function ({commit}, song) {
  commit(types.SET_PLAY_HISTORY, savePlay(song))
}

export const saveFavoriteList = function ({commit}, song) {
  commit(types.SET_FAVORITE_LIST, saveFavorite(song))
}

export const deleteFavoriteList = function ({commit}, song) {
  commit(types.SET_FAVORITE_LIST, deleteFavorite(song))
}
