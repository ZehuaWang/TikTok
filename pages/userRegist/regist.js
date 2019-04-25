const app = getApp()

Page({
    data: {

    },

    doRegist: function(e) {
      var formObject = e.detail.value;
      var username = formObject.username;
      var password = formObject.password;

      //简单验证非空
      if(username.length == 0 || password.length == 0) {
        wx.showToast({
          title: '用户名密码不能为空',
          icon:'none',
          duration: 3000
        })
      }
    }
})