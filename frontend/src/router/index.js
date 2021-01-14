import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import AuthPage from '../views/AuthPage'
import IntroducePage from '../views/IntroducePage'
import MyPage from '../views/MyPage'
import AdminPage from '../views/AdminPage'
import ReporterPage from '../views/ReporterPage'


Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    redirect: '/en/home'
  },
  {
    path: '/en/home',
    name: 'Home',
    component: Home
  },
  {
    path: '/en/auth/:sign',
    component: AuthPage
  },
  {
    path: '/en/introduce',
    component: IntroducePage
  },
  {
    path: '/en/users/my-page',
    component: MyPage
  },
  {
    path: '/en/users/my-page/:manage',
    component: MyPage
  },
  {
    path: '/en/admin/*',
    component: AdminPage
  },
  {
    path: '/en/reporters',
    component: ReporterPage
  },
  {
    path: '/en/reporters/*',
    component: ReporterPage
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
