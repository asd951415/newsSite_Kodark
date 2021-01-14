<template>
  <v-app-bar
    absolute
    color="white"
    elevate-on-scroll
    id="LogoHeader"
  >
    <v-spacer></v-spacer>
    <div>
       <router-link to="/en/home">
        <img width="186" src="../../assets/img/kodark.svg">
      </router-link>
    </div>
    <v-spacer></v-spacer>
  </v-app-bar>
</template>

<script>
export default {

}
</script>

<style scoped>
#LogoHeader {
  position: fixed;
}
</style>
