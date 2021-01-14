import Vue from 'vue'
import Vuex from 'vuex'
import auth from './modules/auth'
import users from './modules/users'
import admin from './modules/admin'
import reporters from './modules/reporters'
import article from './modules/article'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    account: null,
    xsrf: null
  },
  getters: {
    getAccount: (state) => {
      return state.account
    },
    getXsrf: (state) => {
      return state.xsrf
    }
  },
  mutations: {
    setAccount(state, account) {
      state.account = account
    },
    deleteAccount(state) {
      state.account = null
    },
    setXsrf(state, xsrf) {
      state.xsrf = xsrf
    }
  },
  actions: {
  },
  modules: {
    auth,
    users,
    admin,
    reporters,
    article
  }
})
