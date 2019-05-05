//var videoUtil = require('../../utils/videoUtil.js')
const app = getApp()

Page({
  data: {
    faceUrl: "../resource/images/noneface.png"

  },

  onLoad: function (params) {

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
          var data = res.data;
          console.log(data);
          wx.hideLoading();
          debugger;
          wx.showToast({
            title: 'Upload success',
            icon: "success",
            duration: 3000
          })
          debugger;
        }
      })
      }
    })
  }
})