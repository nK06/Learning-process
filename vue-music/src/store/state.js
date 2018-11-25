// 声明一些公共的对象 （全局）
import {playMode} from 'common/js/config'
import {loadSearch, loadPlay, loadFavorite} from 'common/js/cache'

const state = {
  singer: {},
  playing: false,
  fullScreen: false,
  playList: [], // 播放列表
  sequenceList: [], // 歌曲顺序列表
  mode: playMode.sequence, // 播放模式
  currentIndex: -1,
  disc: {}, // 歌单数据
  topList: {}, // 排行榜列表
  searchHistory: loadSearch(),
  playHistory: loadPlay(),
  favoriteList: loadFavorite()
}

export default state
