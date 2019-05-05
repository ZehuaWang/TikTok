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

    wx.request({
      url:serverUrl + '/logout?userId=' + user.id,
      method: "POST",
      header: {
        'content-type' : 'application/json'
      },
      success: function (res) {
        console.log(res.data);
        wx.hideLoading();
        if(res.data.status == 200) {}
      }
    })
    
  }
})
