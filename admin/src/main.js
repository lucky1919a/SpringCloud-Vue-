import Vue from 'vue'
import App from './App.vue'
import router from './router/router.js'
import axios from 'axios'

Vue.config.productionTip = false;
Vue.prototype.$ajax = axios;

new Vue({
  el: '#app',
  router, // 在挂载点中注入vue
  components: { App },
  template: '<app/>'
})
