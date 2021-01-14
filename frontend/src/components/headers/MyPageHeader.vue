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
  </div>

  </v-app-bar>
</template>

<script>
export default {
  
}
</script>

<style scoped>
#MyPageHeader {
  position: fixed;
  width: 100%;
}

.headerContent {
  display: grid;
  grid-template-columns: 3fr 4fr;
  justify-content: center;
  width: 100%;
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
  justify-self: center;
}

.home{
  margin-left: 24px;
}


</style>
