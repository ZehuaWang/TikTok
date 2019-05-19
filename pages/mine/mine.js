//var videoUtil = require('../../utils/videoUtil.js')
const app = getApp()

Page({
  data: {
    faceUrl: "../resource/images/noneface.png"

  },

  onLoad: function () {
    var me = this;

    var user = app.userInfo;

    wx.showLoading({
      title: 'Please wait...',
    })

    var serverUrl = app.serverUrl;

    wx.request({
      url: serverUrl+'/user/query?userId='+user.id,
      method: "POST",
      header: {
        'content-type' : 'application/json' 
      },
      success: function(res) {
        console.log(res.data);
        wx.hideLoading();
        if(res.data.status == 200) {
          var userInfo = res.data.data;
          var faceUrl = "../resource/images/noneface.png";
          if(userInfo.faceImage != null && userInfo.faceImage != '' && 
          userInfo.faceImage != undefined) {
              faceUrl = serverUrl + userInfo.faceImage;
            }
          me.setData({
            faceUrl: faceUrl,
            fansCounts: userInfo.fansCounts,
            followCounts: userInfo.followCounts,
            receiveLikeCounts: userInfo.receiveLikeCounts,
            nickname: userInfo.nickname
          });
        }
      }
    })
  },

  logout: function () {

    var user = app.userInfo;
    var serverUrl = app.serverUrl;

    wx.request({
      url: serverUrl + '/logout?userId=' + user.id,
      method: "POST",
      header: {
        'content-type' : 'application/json'
      },
      success: function (res) {
        console.log(res.data);
        wx.hideLoading();
        if(res.data.status == 200) {
          app.userInfo = null; //清除全局用户对象
          wx.showToast({
            title: 'Logout Successfully',
            icon: 'success',
            duration: 2000
          });
          //页面跳转
          wx.navigateTo({
            url: '../userLogin/login'
          })
          } 
      }
    })

  }, //End of log out function

  changeFace: function () {

    var me = this;

    //Open the photo
    wx.chooseImage({
      count: 1,
      sizeType: ['compressed'],
      sourceType: ['album'],
      success: function(res) {
      var tempFilePaths = res.tempFilePaths;
      console.log(tempFilePaths);

      var serverUrl = app.serverUrl;
      wx.showLoading({
        title: 'Uploading',
      })

      //Upload the face image
      wx.uploadFile({
        url: serverUrl + '/user/uploadFace?userId=' + app.userInfo.id,
        filePath: tempFilePaths[0],

        name: 'file',
        header: {
          'content-type' : 'application/json'
        },
        success: function(res) {
          var data = JSON.parse(res.data);
          console.log(data);
          wx.hideLoading();
          if(data.status == 200) {
            wx.showToast({
              title: 'Upload success',
              icon: "success",
              duration: 3000
            });
            var imageUrl = data.data;
            me.setData({
              faceUrl: serverUrl + imageUrl
            });
            console.log(faceUrl); 
          } else if(data.status == 500) {
            wx.showToast({
              title: data.msg
            });
          }
        }
      })
      }
    })
  },

  uploadVideo: function() {
    var me = this;

    wx.chooseVideo({
      sourceType: ['album'],
      success: function (res) {
        //console.log(res);

        var duration = res.duration;
        var tmpWidth = res.width;
        var tmpHeight = res.height;
        var tmpVideoUrl = res.tempFilePath;
        var tmpCoverUrl = res.thumbTempFilePath;

        //If the video duration is large than 10 seconds
        if(duration > 11) {
          wx.showToast({
            title: 'The video length can not longer than 10 seconds..',
            icon: "none",
            duration: 2500
          })
        } else if(duration < 1) {
           wx.showToast({
             title: 'The length of the video is too short..',
             icon: "none",
             duration: 2500 
           }) 
        } else {
          //TODO 打开选择bgm的页面
          //利用 navigateTo将视频的信息传递到下一个页面
          wx.navigateTo({
            url: '../chooseBgm/chooseBgm?duration='+duration
            +"&tmpHeight="+tmpHeight
            +"&tmpWidth="+tmpWidth
            +"&tmpVideoUrl="+tmpVideoUrl
            +"&tmpCoverUrl="+tmpCoverUrl
          })

        }
      }
    })

  }
})