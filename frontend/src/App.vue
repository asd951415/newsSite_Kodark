<template>
  <v-app>
    <router-view />
    <Footer />
  </v-app>
</template>

<script>
import Footer from './components/footer/Footer'

export default {
  name: 'App',

  components: {
    Footer
  },

  data: () => ({
    //
  })
}
</script>

<style>
* {
  box-sizing: border-box;
  margin: 0;
  padding: 0;
}

#app {
  max-width: 1200px;
  margin: 0 auto;
  /* padding: 0 3%; */
}

.displayNone {
  display: none;
}
</style>
