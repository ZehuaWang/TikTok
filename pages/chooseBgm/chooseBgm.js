const app = getApp()

Page({
    data: {
      bgmList: [],
      serverUrl: "",
      videoParams: {}
    },

    onLoad: function (params) {
      var me = this;
      //打印从mine.js传递过来的视频参数
      console.log(params);

      me.setData({
       videoParams : params 
      });

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
    },

    upload: function(e) {
      var me = this;
      var bgmId = e.detail.value.bgmId;
      var desc = e.detail.value.desc;

      console.log("bgmId: " + bgmId);
      console.log("desc: " + desc);
    }
})

