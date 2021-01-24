<template>
  <v-app-bar
    absolute
    color="white"
    dense
    id="MyPageHeader"
    class="MyPageHeaderContent"
  >
  <div class="headerContent">
    <div class="homeBtn">
      <router-link to="/en/home" class="">
        <span><img class="teamLogo" width="25" src="../../assets/img/k.svg"></span>
        <span class="home">Home</span>
      </router-link>
    </div>
    <div class="logo">
      <router-link to="/en/home" class="">
        <img class="teamLogo" width="200" src="../../assets/img/kodark.svg">
      </router-link>
    </div>
    <div class="publishBtn">
      <v-btn depressed class="text-capitalize black--text"
      color="gray"
      width="100px"
      @click="backToList">
        Back
      </v-btn>
      <v-btn depressed class="text-capitalize white--text"
      color="indigo"
      width="100px"
      @click="publishArticle">
        Publish
      </v-btn>
    </div>
  </div>

    <v-dialog
    v-model="unauthorized"
    max-width="400"
    >
      <v-card>
        <v-card-title class="headline">This function requires sign in.</v-card-title>
        <v-card-text>   
          Would you like to go to the sign in page?
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            color="green darken-1"
            text
            @click="moveSignInPage"
          >
            Go
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-app-bar>
</template>

<script>
export default {
  data() {
    return {
      unauthorized: false
    }
  },
  methods: {
    async publishArticle() {
      const articleId = this.$route.query.articleId
      const { status } = await this.$store.dispatch('admin/publishArticle', { articleId })

      console.log('status', status)
      if(status === 201) {
        this.backToList()
      } else if(status === 401) {
        this.unauthorized = true
      }
    },
    moveSignInPage() {
      this.unauthorized = false
      this.$router.push({ path: '/en/auth/signIn' })
    },
    backToList() {
      this.$router.push({ path: '/en/admin/article/waiting' })
    }
  }
}
</script>

<style scoped>
#MyPageHeader {
  position: fixed;
  width: 100%;
}

.headerContent {
  max-width: 1200px;
  width: 100%;
  margin: 0 auto;
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  justify-content: center;
}

.headerContent a{
  text-decoration: none;
  color: black;
  height: 40px;
  line-height: 40px;
}

.headerContent img {
  vertical-align: middle;
}

.homeBtn{
  justify-self: start;
}

.home{
  margin-left: 24px;
}

.logo {
  justify-self: center;
}

.publishBtn {
  display: grid;
  grid-template-columns: 110px 110px;
  justify-content: end;
}

</style>
