<template>
  <div class="homeHeader">
    <ul class="homeHeaderTop">
      <li>
        <div>
          <v-app-bar-nav-icon></v-app-bar-nav-icon>
          <v-icon>search</v-icon>
        </div>
      </li>
      <li>
        <div>
          <v-btn text small>Korean</v-btn>
          <v-btn text small>English</v-btn>
        </div>
      </li>
      <li>
        <template v-if="!account">
          <v-btn depressed to="/en/auth/signIn">Sign in</v-btn>
        </template>
        <template v-else>
          <AccountMenu v-bind="account" />
        </template>
      </li>
    </ul>
    <ul class="homeHeaderTop">
      <li>
        <div>
          {{today}}
        </div>
        <div>
          Team Kodark's News
        </div>
      </li>
      <li>
        <router-link to="/en/home">
          <img width="300" src="../../assets/img/kodark.svg">
        </router-link>
      </li>
      <li>
        날씨 넣을 공간
      </li>
    </ul>
    <div class="homeHeaderNavi">
      <ul class="homeHeaderNaviChild1">
        <li v-for="(section, i) in sections" :key="i">
          <v-btn text small color="black">
            {{section.rel}}
          </v-btn>
        </li>
      </ul>
    </div>
    <v-app-bar
      absolute
      color="white"
      inverted-scroll
      scroll-threshold=95
      id="homeHeaderNavi"
      height=40px
    >
    <div class="homeHeaderBar">
      <div class="homeHeaderNavi">
        <ul class="homeHeaderNaviChild2">
          <li>
            <v-btn icon color="black">
              <v-icon>home</v-icon>
            </v-btn>
          </li>
          <li>
            <v-app-bar-nav-icon></v-app-bar-nav-icon>
          </li>
          <li v-for="(section, i) in sections" :key="i">
            <v-btn text small color="black">
              {{section.rel}}
            </v-btn>
          </li>
        </ul>
      </div>
    </div>
    </v-app-bar>
  </div>
</template>

<script>
import AccountMenu from '../units/AccountMenu'
import { mapGetters } from 'vuex'

export default {
  components: {
    AccountMenu
  },
  data: () => ({
    sections: [{ rel: 'Politics', href: '/section/politics' }, { rel: 'Economy', href: '/section/economy' }, { rel: 'Society', href: '/section/society' }, { rel: 'Tech', href: '/section/tech' }, { rel: 'World', href: '/section/world' }, { rel: 'Sports', href: '/section/sports' }, { rel: 'Weather', href: '/section/weather' }],
    isSignedIn: false
  }),
  
  computed: {
    today () {
      const weeks = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday']
      const months = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December']
      const date = new Date()

      const year = date.getFullYear()
      const month = months[date.getMonth()]
      const day = date.getDate() < 10 ? '0' + date.getDate() : date.getDate()
      const week = weeks[date.getDay()]
      return `${week}, ${month} ${day}, ${year}`
    },
    ...mapGetters({
      account: 'getAccount'
    })
  },
  created () {
    this.$store.dispatch('users/getUserData')
  }
}
</script>

<style scoped>
.homeHeader {
  padding: 0;
  border-bottom: 1px solid black;
}
.homeHeaderTop {
  list-style: none;
  padding: 5px 10px;
  display: grid;
  grid-template-columns: 1fr 3fr 1fr;
  align-items: end;
  font-size: 12px;
}
.homeHeaderTop > li:nth-child(1) {
  display: grid;
  justify-content: start;
}
.homeHeaderTop > li:nth-child(2) {
  display: grid;
  justify-content: center;
}
.homeHeaderTop > li:nth-child(3) {
  display: grid;
  justify-content: end;
}

.homeHeaderTop:nth-child(2) {
  border-bottom: 0.2px solid rgb(170, 169, 169);
}

#homeHeaderNavi {
  position: fixed;
}

.homeHeaderNavi {
  padding: 6px 60px;
}

.homeHeaderNaviChild1 {
  list-style: none;
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  justify-items: center;
  align-items: center;
}
.homeHeaderNaviChild2 {
  list-style: none;
  display: grid;
  grid-template-columns: repeat(9, 1fr);
  justify-items: center;
  align-items: center;
}

.homeHeaderBar {
  width: 1200px;
  margin: 0 auto;
  padding: 0;
}
</style>
