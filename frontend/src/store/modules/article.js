
import axios from 'axios'
import Util from '../../utils/util'
const util = new Util()

const state = () => ({
})

const getters = {
}

const mutations = {
}

const actions = {
  async getCategory({}) {
    try {
      const res = await axios.get('/article/category')

      return { status: res.status, categories: res.data }
    } catch(err) {
      return { status: err.response.status }
    }
  },

}

export default {
  namespaced: true,
  state,
  getters,
  mutations,
  actions
}
