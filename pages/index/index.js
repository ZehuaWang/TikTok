const app = getApp()

Page({
  data: {
    // 用于分页的属性
    screenWidth: 350,
  },

  onLoad: function (params) {
    var me = this;
    var screenWidth = wx.getSystemInfoSync().screenWidth;
    me.setData({
      screenWidth: screenWidth,
    });
  }
})
