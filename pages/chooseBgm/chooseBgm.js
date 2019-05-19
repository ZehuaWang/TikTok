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
      // For test
      //console.log("bgmId: " + bgmId);
      //console.log("desc: " + desc);
      
      var duration    = me.data.videoParams.duration;
      var tmpWidth    = me.data.videoParams.tmpWidth;
      var tmpHeight   = me.data.videoParams.tmpHeight;
      var tmpVideoUrl = me.data.videoParams.tmpVideoUrl;
      var tmpCoverUrl = me.data.videoParams.tmpCoverUrl;

      //Upload short video
      wx.showLoading({
        title: 'Uploading...'
      })
      var serverUrl = app.serverUrl;
      wx.uploadFile({
        url: serverUrl + '/video/upload',
        formData:{
          userId: app.userInfo.id,
          bgmId: bgmId,
          desc: desc,
          videoSeconds: duration,
          videoHeight: tmpHeight,
          videoWidth: tmpWidth
        },
        filePath: tmpVideoUrl,
        name: 'files',
        header: {
          'content-type' : 'application/json'
        },
        success: function(res){
          var data = JSON.parse(res.data);
          wx.hideLoading();
          if(data.status == 200) {
              wx.showToast({
                title: 'Upload Successfully',
                icon: "success"
              });
          }
        }
      })
    }
})