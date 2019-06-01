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

        // 判断当前页page是否是第一页,如果是第一页 则videoList清空
        if(page == 1) {
          me.setData({
            videoList: []
          })
        }

        var videoList = res.data.data.rows;
        var newVideoList = me.data.videoList;

        me.setData({
          videoList: newVideoList.concat(videoList),
          page: page,
          totalPage: res.data.data.total,
          serverUrl: serverUrl
        })
      }
    })

  }
})
