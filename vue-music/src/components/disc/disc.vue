<template>
  <!-- 左滑动画 -->
  <transition name="slide">
    <music-list :title="title" :bg-image="bgImage" :songs="songs"></music-list>
  </transition>
</template>

<script type="text/ecmascript-6">
  import MusicList from 'components/music-list/music-list'
  import {mapGetters} from 'vuex'
  import {getSongList} from 'api/recommend'
  import {ERR_OK} from 'api/config'
  import {getMusicPurl} from 'api/singer'
  import {createSong} from 'common/js/song'

  export default {
    computed: {
      title() {
        return this.disc.dissname
      },
      bgImage() {
        return this.disc.imgurl
      },
      ...mapGetters([
        'disc'
      ])
    },
    data() {
      return {
        songs: []
      }
    },
    created() {
      this._getDiscSongList()
    },
    methods: {
      _getDiscSongList() {
        if (!this.disc.dissid) {
          this.$router.push('/recommend')
          return
        }
        getSongList(this.disc.dissid).then((res) => {
          if (res.code === ERR_OK) {
            this._normalizeSongs(res.cdlist[0].songlist)
          }
        })
      },
      _normalizeSongs(list) {
        let ret = []
        let promiseArr = []
        list.forEach((item) => {
          let musicData = item
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

</style>
