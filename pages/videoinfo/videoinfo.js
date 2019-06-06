const app = getApp()

Page({
  data:{
    cover: "cover"
  },

  showSearch: function() {
    wx.navigateTo({
      url: '../searchVideo/searchVideo'
    })
  }
})