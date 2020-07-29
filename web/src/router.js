import Vue from "vue"
import Router from "vue-router"
import Index from "./views/index.vue"
import List from "./views/list.vue"
import Detail from "./views/detail.vue"

Vue.use(Router);

//解决vue-router在3.0版本以上重复点路由报错
const originalPush = Router.prototype.push
Router.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}

export default new Router({
  mode: "history",
  //base: process.env.BASE_URL,
  routes: [{
    path: "*",
    redirect: "/index",
  }, {
    path: "/index",
    component: Index
  },{
    path: "/list",
    component: List
  },
    {
      path: "/detail",
      component: Detail
    }

  ]
})
