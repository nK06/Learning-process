export default class Singer {
  constructor({id, mid, name}) {
    // id 为姓名首字母
    this.id = id
    this.mid = mid
    this.name = name
    // 头像图片Url
    this.avatar = `http://y.gtimg.cn/music/photo_new/T001R150x150M000${mid}.jpg`
  }
}
