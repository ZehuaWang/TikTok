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

  }
})
