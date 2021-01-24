
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
  async getArticles () {
    try{
      const res = await axios.get('/admin/article',{
        params: {
          status: 'null' // null, publish, blind
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
  },
  async getReportedArticles () {
    try{
      const res = await axios.get('/admin/report/article',{
        headers: {
          'Content-Type': 'application/json'
        }
      })
      const data = res.data
      return {data, status: res.status}
    }catch(err){
      return {status: err.response.status}
    }
  },
 
  async getReportedComments ({}, {commentStartedId, doneFlag}) {
    try{
      const res = await axios.get(`/admin/report/comment?commentStartId=${commentStartedId}&doneFlag=${doneFlag}`,{
        headers: {
          'Content-Type': 'application/json'
        }
      })
      const data = res.data
      return {data, status: res.status}
    }catch(err){
      return {status: err.response.status}
    }
  },

  async getUsers ({}, {startIndex}) {
    try{
      const res = await axios.get(`/admin/users?startIndex=${startIndex}`,{
        headers: {
          'Content-Type': 'application/json'
        }
      })
      
      const data = res.data
      return {data, status: res.status}
    }catch(err){
      return {status: err.response.status}
    }
  },

  async publishArticle({}, { articleId }) {
    try{
      const res = await axios.patch(`/admin/article/publish?articleId=${articleId}`)
      
      return { status: res.status }
    }catch(err){
      return {status: err.response.status}
    }
  },

  async sendEmail({}, { email, title, content }) {
    try{
      const res = await axios.post('/admin/reporters/email', { email, title, content }, {
        headers: {
          'Content-Type': 'application/json'
        }
      })
      
      return { status: res.status }
    }catch(err){
      return {status: err.response.status}
    }
  },


}

export default {
  namespaced: true,
  actions
}
