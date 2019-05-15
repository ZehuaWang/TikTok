const app = getApp()

Page({
    data: {
      bgmList: [],
      serverUrl: ""
    },

    onLoad: function () {
      var me = this;
      wx.showToast({
        title: 'Please Wait...'
      });
      var serverUrl = app.serverUrl;
      wx.request({
        url: serverUrl+'/bgm/list',
        method: "POST",
        header:{
          'content-type' : 'application/json'
        },
        success: function(res) {
          console.log(res.data);
          wx.hideLoading();
          if(res.data.status == 200) {
            var bgmList = res.data.data;
            me.setData({
              bgmList: bgmList,
              serverUrl: serverUrl
            });
          }
        }
      })
    }
})

