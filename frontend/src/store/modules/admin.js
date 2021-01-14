
import axios from 'axios'
import Util from '../../utils/util'

const util = new Util()

const actions = {
  async createReporter({}, { email, pwd, nickName, name, local, birth, gender, image}) {
    try {
      const formData = new FormData();
      formData.append('email', email)
      formData.append('pwd', pwd)
      formData.append('nickName', nickName)
      formData.append('name', name)
      formData.append('local', local)
      formData.append('birth', birth)
      formData.append('gender', gender)
      formData.append('image', image)
      console.log(formData)
      const res = await axios.post('/admin/reporters', formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      })
      
      return { status: res.status}
    } catch(err) {
      return { status: err.response.status }
    }
  },

  async getReporters () {
    try{
      const res = await axios.get('/admin/reporters',{
        headers: {
          'Content-Type': 'application/json'
        }
      })
      const data = res.data
      return {reporters: data, status: res.status}
    }catch(err){
      return {status: err.response.status}
    }
  },
  async getWaitArticles () {
    try{
      const res = await axios.get('/admin/article',{
        params: {
          status: 'wait'
        }
      } 
      ,{
        headers: {
          'Content-Type': 'application/json'
        }
      })
      const data = res.data
      return {data, status: res.status}
    }catch(err){
      return {status: err.response.status}
    }
  }
}

export default {
  namespaced: true,
  actions
}
