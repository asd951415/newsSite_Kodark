<template>
  <div>
    <MyPageHeader />
    <div class="adminPage">
      <IntroduceSideBar :sideMenu="sideMenu" />
      <template v-if="currentRoute === 'users'">
        <UserList />
      </template>
      <template v-if="currentRoute === 'new'">
        <CreateReporterForm />
      </template>
      <template v-if="currentRoute === 'reporters'">
        <ReportList />
      </template>
      <template v-if="currentRoute === 'article/new'">
        <WaitArticleList />
      </template>
    </div>
  </div>
</template>

<script>
import MyPageHeader from '../components/headers/MyPageHeader'
import IntroduceSideBar from '../components/sidebars/IntroduceSideBar'
import CreateReporterForm from '../components/bodies/admins/CreateReporterForm'
import ReportList from '../components/bodies/admins/ReportList'
import WaitArticleList from '../components/bodies/admins/WaitArticleList'
import UserList from '../components/bodies/admins/UserList'

export default {
  components: {
    MyPageHeader,
    IntroduceSideBar,
    CreateReporterForm,
    ReportList,
    WaitArticleList,
    UserList
  },
  computed: {
    // side menu
    sideMenu() {
      const sideMenu = []
      sideMenu.push({ type: 'title', text: 'Admin' })
      sideMenu.push({ type: 'sub', text: 'Statistics', link: '/en/admin/statistics' })
      sideMenu.push({ type: 'sub', text: 'Waiting articles', link: '/en/admin/article/waiting' })
      sideMenu.push({ type: 'title', text: 'Users' })
      sideMenu.push({ type: 'sub', text: 'User list', link: '/en/admin/users' })
      sideMenu.push({ type: 'title', text: 'Reporter' })
      sideMenu.push({ type: 'sub', text: 'Reporter list', link: '/en/admin/reporters' })
      sideMenu.push({ type: 'sub', text: 'Create reporter', link: '/en/admin/reporters/new' })
      sideMenu.push({ type: 'title', text: 'Report' })
      sideMenu.push({ type: 'sub', text: 'Reported articles', link: '/en/admin/report/article' })
      sideMenu.push({ type: 'sub', text: 'Reported comments', link: '/en/admin/report/comment' })
      sideMenu.push({ type: 'title', text: 'Question' })
      sideMenu.push({ type: 'sub', text: 'Question list', link: '/en/admin/question-list' })
      return sideMenu
    },
    // 현재 라우트 반환
    currentRoute () {
      const routeArr = this.$route.fullPath.split('/')
      const lastUrl = routeArr[routeArr.length - 1];
      // return lastUrl === 'new' ? `${routeArr[routeArr.length - 2]}/${lastUrl}` : lastUrl
      return routeArr[routeArr.length - 1]
    }
  }
}
</script>

<style>
.adminPage {
  display: grid;
  grid-template-columns: 1fr 3fr;
  padding-top: 120px;
  padding-bottom: 120px;
}
.admin_title{
    font-weight: 300;
    font-size: 50px;
    line-height: 56px;
    margin-bottom: 24px;
}
.admin_subTitle{
    border-bottom: 1px solid #bdbdbd;
    font-weight: 700;
    font-size: 17px;
    margin-bottom: 9px;
}
.admin_content {
  /* border: 1px solid red; */
  max-width: 600px;
  margin-top: 35px;
  margin-bottom: 60px;
}
</style>
