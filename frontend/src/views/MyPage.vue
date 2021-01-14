<template>
  <div class="myPage">
    <MyPageHeader/>
    <div class="myPageContent">
      <IntroduceSideBar :sideMenu="sideMenu" />
      <template v-if="currentRoute === 'my-page'">
        <MyPageBody />
      </template>
      <template v-if="currentRoute === 'profile'">
        <ProfileEditBody />
      </template>
      <template v-if="currentRoute === 'subscribed-list'">
        <SubscribeListBody />
      </template>
    </div>
  </div>
</template>

<script>
import MyPageHeader from '../components/headers/MyPageHeader'
import IntroduceSideBar from '../components/sidebars/IntroduceSideBar'
import AdminSideBar from '../components/sidebars/AdminSideBar'
import MyPageBody from '../components/bodies/MyPageBody'
import ProfileEditBody from '../components/bodies/ProfileEditBody'
import SubscribeListBody from '../components/bodies/SubscribeListBody'

export default {
  data: () => ({
    // sideMenu: []
  }),
  components: {
    MyPageHeader,
    IntroduceSideBar,
    MyPageBody,
    ProfileEditBody,
    SubscribeListBody,
  },
  computed: {
    sideMenu() {
      const sideMenu = []
      sideMenu.push({ type: 'sub', text: 'Account', link: '/en/users/my-page' })
      sideMenu.push({ type: 'sub', text: 'Profile', link: '/en/users/my-page/profile' })
      sideMenu.push({ type: 'sub', text: 'Subscription', link: '/en/users/my-page/subscribed-list' })
      return sideMenu
    },
    currentRoute () {
      const routeArr = this.$route.fullPath.split('/')
      return routeArr[routeArr.length - 1]
    }
  }
}
</script>

<style scoped>
.myPageContent {
  display: grid;
  grid-template-columns: 1fr 3fr;
  padding-top: 120px;
  padding-bottom: 120px;
}
</style>
