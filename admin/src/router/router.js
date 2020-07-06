import Vue from 'vue'
import Router from 'vue-router'
import Login from "../views/login.vue"
import Admin from "../views/admin.vue"
import Welcome from "../views/admin/welcome.vue"
import Chapter from "../views/admin/chapter.vue"

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
      path: '/',
      name: "admin",
      component: Admin,
      children: [
        {
        path: 'welcome',
        name:"welcome",
        component: Welcome,
        },
        {
          path: 'business/chapter',
          name:"business/chapter",
          component: Chapter,
        }
      ]
    }
  ],
})
