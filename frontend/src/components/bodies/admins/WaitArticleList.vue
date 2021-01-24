<template>
  <div class="WaitArticleComponent">
  <h1 class="title">Wait Article List</h1>
  <v-simple-table
  fixed-header
  height="500px"
  >
  <template v-slot:default>
    <thead>
      <tr>
        <th class="text-left">
          ID
        </th>
        <th class="text-left">
          Title
        </th>
        <th class="text-left">
          Reporter
        </th>
        <th class="text-left">
          Email
        </th>
      </tr>
    </thead>
    <tbody>
      <tr
        v-for="article in waitArticles"
        :key="article.id"
      >
        <td>{{article.article.id}}</td>
        <td class="acticleTitle">
          <router-link :to="`/en${article._link.href}` ">
            {{ formatedText(article.article.title, 45) }}
          </router-link>
        </td>
        <td>{{article.reporter.name}}</td>
        <v-tooltip bottom>
          <template v-slot:activator="{ on, attrs }">
            <td
            class="email"
            @click="openEmailForm(article.reporter.email)"
            v-bind="attrs"
            v-on="on"
            >
              {{ formatedText(article.reporter.email, 20) }}
            </td>
          </template>
          <span>Send email</span>
        </v-tooltip>
      </tr>
    </tbody>
  </template>
  </v-simple-table>
  <v-dialog
  v-model="showEmailForm"
  max-width="500"
  @click:outside="closeEmailForm"
  >
    <v-card>
      <v-card-title class="headline">To : {{ email.address }}</v-card-title>
      <div class="emailContent">
        <v-text-field
        ref="title"
        label="Title"
        outlined
        color="black"
        height="45px"
        dense=true
        v-model="email.title"
        required
        />
        <v-textarea
        outlined
        name="input-7-4"
        label="Content"
        v-model="email.content"
        />
      </div>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn
          color="green darken-1"
          text
          @click="sendEmail"
        >
          Send
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
  <v-snackbar
  v-model="snack.show"
  top="true"
  :color="snack.color"
  :timeout="5000"
  >
    {{ snack.msg }}
    <template v-slot:action="{ attrs }">
      <v-btn
        class="text-capitalize"
        dark
        text
        v-bind="attrs"
        @click="snack.show = false"
      >
        Close
      </v-btn>
    </template>
  </v-snackbar>
  </div>
</template>

<script>
export default {
  data: () => ({
    waitArticles: [],
    showEmailForm: false,
    email: {
      address: '',
      title: '',
      content: ''
    },
    snack: {
      show: false,
      msg: '',
      color: ''
    },
  }),
  methods: {
    formatedText(text, length) {
      return text.length <= length ? text : text.substring(0, length) + '...'
    },
    openEmailForm(email) {
      this.email.address = email
      this.showEmailForm = true
    },
    closeEmailForm() {
      this.email.address = ''
      this.email.title = ''
      this.email.content = ''
      this.showEmailForm = false
    },
    async sendEmail() {
      const email = this.email.address
      const title = this.email.title
      const content = this.email.content
      const { status } = await this.$store.dispatch('admin/sendEmail', { email, title, content })

      if(status === 204) {
        this.snack.msg = 'Successfully sended an email.'
        this.snack.color = 'success'
        this.snack.show = true
        this.closeEmailForm()
      } else if(status === 401 || status === 403) {
        this.snack.msg = 'This function requires sign in.'
        this.snack.color = 'warning'
        this.snack.show = true
        this.closeEmailForm()
      }
    }
  }, 
  async created () {
    const { status, data } = await this.$store.dispatch('admin/getArticles');
    if(status === 200) {
      console.log(data)
      this.waitArticles = data;
    }
    if(status === 404){
      //에러처리필요
    }
  }
}
</script>

<style scoped>
.title{
  padding: 10px 10px 10px 0 ;
  border-bottom: 1px solid black;
  margin-bottom: 50px;
}
.acticleTitle a{
  color: black;
}
.email{
  cursor: pointer;
}
.emailContent {
  width: 90%;
  margin: 0 auto;
}
</style>