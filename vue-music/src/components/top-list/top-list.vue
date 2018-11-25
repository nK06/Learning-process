<template>
  <transition name="slide">
    <music-list :rank="rank" :title="title" :bg-image="bgImage" :songs="songs"></music-list>
  </transition>
</template>

<script>
  import MusicList from 'components/music-list/music-list'
  import {mapGetters} from 'vuex'
  import {getMusicList} from 'api/rank'
  import {ERR_OK} from 'api/config'
  import {getMusicPurl} from 'api/singer'
  import {createSong} from 'common/js/song'

  export default {
    created() {
      this._getMusicList()
    },
    computed: {
      title() {
        return this.topList.topTitle
      },
      bgImage() {
        if (this.songs.length) {
          return this.songs[0].image
        }
        return ''
        // return this.topList.picUrl
      },
      // vuex 的getters 最终映射到的是computde 方法
      ...mapGetters([
        'topList'
      ])
    },
    data() {
      return {
        songs: [],
        rank: true
      }
    },
    methods: {
      _getMusicList() {
        if (!this.topList.id) {
          this.$router.push('/rank')
          return
        }
        getMusicList(this.topList.id).then((res) => {
          if (res.code === ERR_OK) {
            this._normalizeSongs(res.songlist)
          }
        })
      },
      _normalizeSongs(list) {
        let ret = []
        let promiseArr = []
        list.forEach((item) => {
          let musicData = item.data
          if (musicData.songid && musicData.albummid) {
            let promise = getMusicPurl(musicData.songmid)
            promiseArr.push(promise)
            promise.then((res) => {
              if (res.code === ERR_OK) {
                // 获取vKey 数据
                const songVkey = res.req_0.data.midurlinfo[0].purl
                ret.push(createSong(musicData, songVkey))
              }
            })
          }
        })
        Promise.all(promiseArr).then(() => {
          this.songs = ret
        })
      }
    },
    components: {
      MusicList
    }
  }
</script>

<style scoped lang="stylus" rel="stylesheet/stylus" type="text/stylus">
  .slide-enter-active, .slide-leave-active
    transition: all 0.3s ease

  .slide-enter, .slide-leave-to
    transform: translate3d(100%, 0, 0)
</style>
