import Vue from 'vue'
import App from './App.vue'
import router from './router/router.js'
import axios from 'axios'
import filter from './filter/filter.js'

Vue.config.productionTip = false;
Vue.prototype.$ajax = axios;


/**
 * axios拦截器
 */
axios.interceptors.request.use(function (config) {
  console.log("请求：", config);
  let token = Tool.getLoginUser().token;
  if (Tool.isNotEmpty(token)) {
    config.headers.token = token;
    console.log("请求headers增加token:", token);
  }
  return config;
}, error => {});
axios.interceptors.response.use(function (response) {
  console.log("返回结果：", response);
  return response;
}, error => {});

// 全局过滤器
Object.keys(filter).forEach(key => {
  Vue.filter(key, filter[key])
});

// 路由登录拦截
router.beforeEach((to, from, next) => {
  // 要不要对meta.loginRequire属性做监控拦截
  if (to.matched.some(function (item) {
      return item.meta.loginRequire
    })) {
    let loginUser = Tool.getLoginUser();
    if (Tool.isEmpty(loginUser)) {
      Toast.warning("无权限");
      next('/login');
    } else {
      next();
    }
  } else {
    next();
  }
});

new Vue({
  el: '#app',
  router, // 在挂载点中注入vue
  components: { App },
  template: '<app/>'
})
console.log(process); // 打印 true
console.log("环境：", process.env);

