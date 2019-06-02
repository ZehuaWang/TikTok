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
    me.getAllVideoList(page);
  },

  //获取全部的视频列表 -> 单独抽离成一个方法
  getAllVideoList: function(page) {
    var serverUrl = app.serverUrl;
    var me = this;
    wx.showLoading({
      title: 'Please Wait'
    })

    wx.request({
      url: serverUrl + '/video/showAll?page=' + page,
      method: "POST",
      success: function (res) {
        wx.hideLoading();
        wx.hideNavigationBarLoading(); //隐藏导航条加载动画
        wx.stopPullDownRefresh(); //停止下拉刷新
        console.log(res.data);

        // 判断当前页page是否是第一页,如果是第一页 则videoList清空
        if (page == 1) {
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
  },

  onPullDownRefresh: function() {
    wx.showNavigationBarLoading(); //显示导航条的加载动画
    this.getAllVideoList(1);
  },

  //上拉刷新
  onReachBottom: function() {
    var me = this;
    var currentPage = me.data.page;
    var totalPage = me.data.totalPage;
    //判断当前页数和总页数是否相等, 如果相等则无需查询
    if(currentPage == totalPage) {
      wx.showToast({
        title: 'No More...',
        icon: "none"
      })
      return;
    }

    var page = currentPage + 1;

    me.getAllVideoList(page);

  } 


})
