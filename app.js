//app.js
App({
  serverUrl:"http://192.168.2.10:8081", //服务器ip
  //serverUrl: "http://10.70.80.182:8081", //服务器ip
  userInfo: null, //全局用户对象暂时设置为空 -> 改为本地缓存的存储方式

  setGlobalUserInfo: function(user) {
    wx.setStorageSync("userInfo", user);
  },

  getGlobalUserInfo: function() {
    return wx.getStorageInfoSync("userInfo");
  }

})