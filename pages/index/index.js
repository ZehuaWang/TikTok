const app = getApp()

Page({
  data: {
    // 用于分页的属性
    totalPage: 1,
    page:1,
    videoList:[],
    serverUrl: "",
    screenWidth: 350,
  },

  onLoad: function (params) {
    var me = this;
    var screenWidth = wx.getSystemInfoSync().screenWidth;
    me.setData({
      screenWidth: screenWidth,
    });

    //获取当前的分页数
    var page = me.data.page;
    var serverUrl = app.serverUrl;

    wx.showLoading({
      title: 'Please Wait'
    })

    wx.request({
      url: serverUrl + '/video/showAll?page=' + page,
      method: "POST",
      success: function(res) {
        wx.hideLoading();
        console.log(res.data);
      }
    })

  }
})
