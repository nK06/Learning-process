<template>
  <div class="singer" ref="singer">
    <listview @select="selectSinger" :data="singers" ref="list"></listview>
    <router-view></router-view>
  </div>
</template>

<script type="text/ecmascript-6">
  import {getSingerList} from 'api/singer'
  import {ERR_OK} from 'api/config'
  import {isChinese, getPinYinFirstCharacter} from 'common/js/pinyin'
  import Singer from 'common/js/singer'
  import Listview from 'base/listview/listview'
  import {playlistMixin} from 'common/js/mixin'

  // vuex 语法糖 在methods 中申明和 mutations-types的映射
  import {mapMutations} from 'vuex'

  const HOT_NAME = '热门'
  const HOT_SINGER_LENGTH = 10
  export default {
    mixins: [playlistMixin],
    data() {
      return {
        singers: []
      }
    },
    created() {
      this._getSingerList()
    },
    methods: {
      handlePlayList(playlist) {
        const bottom = playlist.length > 0 ? '60px' : ''
        this.$refs.singer.style.bottom = bottom
        this.$refs.list.refresh()
      },
      selectSinger(singer) {
        this.$router.push({
          path: `/singer/${singer.mid}`
        })
        // 这一步就实现了对一个mutation的提交,相当于在state中放了一些数据，这里是singer的信息，在singer-detail 中就可以获取到
        this.setSinger(singer)
      },
      _getSingerList() {
        getSingerList().then((res) => {
          if (res.code === ERR_OK) {
            this.singers = this._normalizeSinger(res.singerList.data.singerlist)
          }
        })
      },
      _normalizeSinger(list) {
        let map = {
          hot: {
            title: HOT_NAME,
            items: []
          }
        }
        // 数据构建
        list.forEach((item, index) => {
          // 先将姓名转换成英文格式
          if (isChinese(item.singer_name)) {
            item.firstCharacter = getPinYinFirstCharacter(item.singer_name, '-', false)
          } else {
            item.firstCharacter = item.singer_name
          }
          // 处理热门歌手数据，取前10条
          if (index < HOT_SINGER_LENGTH) {
            map.hot.items.push(new Singer({
              id: item.firstCharacter,
              mid: item.singer_mid,
              name: item.singer_name
            }))
          }
          // map[key] 数据格式 map[A] map[B]
          const key = item.firstCharacter.substring(0, 1).toUpperCase()
          if (!map[key]) {
            map[key] = {
              title: key,
              items: []
            }
          }
          map[key].items.push(new Singer({
            id: item.firstCharacter,
            mid: item.singer_mid,
            name: item.singer_name
          }))
        })
        // 为了的到有序列表，需要对map 数据格式进行处理
        let hot = []
        let ret = []
        for (let key in map) {
          let val = map[key]
          if (val.title.match(/[a-zA-Z]/)) {
            ret.push(val)
          } else if (val.title === HOT_NAME) {
            hot.push(val)
          }
        }
        ret.sort((a, b) => {
          return a.title.charCodeAt(0) - b.title.charCodeAt(0)
        })

        return hot.concat(ret)
      },
      // setSinger 跟Mutations-types 中的 SET_SINGER 做映射
      ...mapMutations({
        setSinger: 'SET_SINGER'
      })
    },
    components: {
      Listview
    }
  }
</script>
<style scoped lang="stylus" rel="stylesheet/stylus" type="text/stylus">
  .singer
    position: fixed
    top: 88px
    bottom: 0
    width: 100%
</style>
