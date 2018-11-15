<template>
  <div class="singer" ref="singer">
    <list-view :data="singers" @select="selectSinger" ref="singerlist"></list-view>
    <router-view></router-view>
  </div>
</template>

<style scoped lang="stylus" rel="stylesheet/stylus" type="text/stylus">
  .singer
    position: fixed
    top: 88px
    bottom: 0
    width: 100%
</style>
