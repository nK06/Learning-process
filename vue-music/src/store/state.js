// 声明一些公共的对象 （全局）
import {playMode} from 'common/js/config'

const state = {
  singer: {},
  playing: false,
  fullScreen: false,
  playList: [], // 播放列表
  sequenceList: [], // 歌曲顺序列表
  mode: playMode.sequence, // 播放模式
  currentIndex: -1
}

export default state
