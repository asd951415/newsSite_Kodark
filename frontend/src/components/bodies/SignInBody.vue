<template>
  <div class="singComponent">
    <v-text-field
      ref="email"
      label="Email Address"
      outlined
      color="black"
      height="45px"
      dense=true
      v-model="email"
      :rules="emailRules"
      required
    ></v-text-field>
    <v-text-field
      ref="password"
      label="Password"
      :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
      :type="show ? 'text' : 'password'"
      @click:append="show = !show"
      outlined
      color="black"
      height="45px"
      dense=true
      v-model="password"
      :rules="passwordRules"
      required
    ></v-text-field>
    <div class="findPwd">
      <v-btn class="text-capitalize" text depressed x-small>
        Forgot your password?
      </v-btn>
    </div>
    <div class="signInBtn">
      <v-btn
        class="white--text text-capitalize font-weight-black"
        depressed
        large
        color="black"
        width="100%"
        @click="signIn"
        :loading="signningIn"
      >
      Sign In
      </v-btn>
    </div>
    <div class="createAccountBtn">
      <div>
        <span>
          Dont't have a KoDark account?
        </span>
        <v-btn class="font-weight-black text-capitalize text-decoration-underline" text height="24px" width="100px" depressed to="/en/auth/signUp">
          Create one
        </v-btn>
      </div>
    </div>
    <v-snackbar
      v-model="failMsg"
      bottom="true"
      color="error"
      :timeout="failMsgTimeOut"
    >
      The email or password you entered is incorrect
      <template v-slot:action="{ attrs }">
        <v-btn
          class="text-capitalize"
          dark
          text
          v-bind="attrs"
          @click="failMsg = false"
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
    email: '',
    emailRules: [
      v => !!v || 'Email Address is required'
    ],
    password: '',
    passwordRules: [
      v => !!v || 'Password is required'
    ],
    show: false,
    signningIn: false,
    failMsg: false,
    failMsgTimeOut: 10000
  }),
  methods: {
    async signIn () {
      if(!this.$refs.email.validate(true) && !this.$refs.password.validate(true)) return
      this.signningIn = true
      const { status, links } = await this.$store.dispatch('auth/signIn', { email: this.email, pwd: this.password })
      console.log(status)
      if(status === 401) this.failMsg = true
      if(status === 204) this.$router.push({ path: links.next })
      this.signningIn = false
    }
  }
}
</script>
<style scoped>
.singComponent {
  padding-top: 24px;
}
.findPwd {
  display: grid;
  justify-items: end;
}
.signInBtn {
  margin-top: 8px;
}

.createAccountBtn {
  padding-top: 12px;
  display: grid;
  justify-items: center;
  font-size: 14px;
}

</style>
