<template>
  <v-footer
   padless
   class="footer">
   <div class="footer__container">
      <v-col
        class="text-center footers"
        cols="4"
      >
      <span>©</span> <span>2020</span> <span>version 0.0.1</span>
      </v-col>
      <v-col
        class="text-center footers"
        cols="4"
      >
       <router-link to="/en/introduce"><p>Introduce</p></router-link>
      </v-col>
      <v-col
        class="text-center footers"
        cols="4"
      >
      <a href="https://github.com/jeuk817/newsSite_Kodark" target="blank"><p>Git Repository</p>
      </a>
      </v-col>
      <v-col
        class="text-center footers"
        cols="4"
      >
      Help
      </v-col>
   </div>
  </v-footer>
</template>

<script>
export default {

}
</script>

<style scoped>
.v-sheet.v-footer {
position: absolute;
bottom: 0;
width: 100%;
border-radius: 5px;
height: 40px;
}
.footer__container{
text-align: center;
width: 60%;
margin: 0 auto;
}
.footers{
display: inline;
font-size: 13px;
font-weight: 500;
margin: 0;
}
a:link{
text-decoration: none;
}

p{
  display: inline;
  color: black;
}

span{
padding: 3px;
}

</style>
