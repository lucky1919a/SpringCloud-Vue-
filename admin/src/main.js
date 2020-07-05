import Vue from 'vue'
import App from './App.vue'
import router from './router/router.js'


new Vue({
  el: '#app',
  router, // 在挂载点中注入vue
  components: { App },
  template: '<app/>'
})
