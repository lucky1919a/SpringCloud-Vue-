import Vue from 'vue'
import App from './App.vue'
import router from './router/router.js'
import axios from 'axios'
import filter from './filter/filter.js'

Vue.config.productionTip = false;
Vue.prototype.$ajax = axios;

// 全局过滤器
Object.keys(filter).forEach(key => {
  Vue.filter(key, filter[key])
});

new Vue({
  el: '#app',
  router, // 在挂载点中注入vue
  components: { App },
  template: '<app/>'
})
console.log(process); // 打印 true
console.log("环境：", process.env);

