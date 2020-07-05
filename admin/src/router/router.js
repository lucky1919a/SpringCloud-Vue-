import Vue from 'vue'
import Router from 'vue-router'
import Login from "../views/login.vue"
import Admin from "../views/admin.vue"
import Welcome from "../views/admin/welcome.vue"

Vue.use(Router)  //注册vue-router

export default new Router({
  mode: "history",//使用H5 history模式
  routes: [
    {
      path: "",
      redirect: "/login",
    },
    {
      path: "/login",
      component: Login
    },
    {
      path: '/admin',
      component: Admin,
      children: [{
        path: 'welcome',
        component: Welcome,
      }]
    }
  ],
})
