<template>
  <div>
    <div class="verifyEmailGuide">
      <p class="text-h6">
        Verify your email address
      </p>
      <p>
        To make sure this email is yours, Kodark will send you a text message with a 6-digit verification code.
      </p>
    </div>
    <div class="verifyEmailForm">
      <v-text-field
        ref="emailAddress"
        label="Email Address"
        outlined
        color="black"
        height="45px"
        dense=true
        v-model="email"
        :rules="emailRules"
        required
      ></v-text-field>
      <v-btn class="text-capitalize white--text" height="45px" depressed color="black"
        @click="getVerifCode" :loading="gettingVerifCode"
      >
        Send
      </v-btn>
    </div>
    <v-alert type="warning" dense dismissible v-model="emailError">
      {{ emailErrorMsg }}
    </v-alert>
    <div class="verifyEmailForm">
      <v-text-field
        ref="verificationCode"
        label="Enter verification code"
        hint="Verification code was sent.(Valid time 30 minutes)"
        outlined
        height="45px"
        dense=true
        :persistent-hint="didSend"
        :disabled="!didSend"
        v-model="verifCode"
        :rules="verifCodeRules"
      ></v-text-field>
      <v-btn class="text-capitalize white--text" height="45px" depressed color="black"
        :disabled="!didSend" @click="sendVerifCode" :loading="sendingVerifCode"
      >
        Verify
      </v-btn>
    </div>
    <v-alert type="success" dense dismissible v-model="didAuth">
      Success
    </v-alert>
    <v-alert type="warning" dense dismissible v-model="didAuthFail">
      {{ authFailMsg }}
    </v-alert>
    <v-text-field
      ref="password"
      label="Password"
      :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
      :type="show1 ? 'text' : 'password'"
      @click:append="show1 = !show1"
      outlined
      color="black"
      height="45px"
      dense=true
      v-model="password"
      :rules="passwordRules"
      required
    ></v-text-field>
    <v-text-field
      ref="confirmPassword"
      label="Confirm password"
      :append-icon="show2 ? 'mdi-eye' : 'mdi-eye-off'"
      :type="show2 ? 'text' : 'password'"
      @click:append="show2 = !show2"
      outlined
      color="black"
      height="45px"
      dense=true
      v-model="confirmPassword"
      :rules="[confirmRule]"
      :error-messages="errorMessages"
      required
    ></v-text-field>
    <v-btn
      class="text-capitalize white--text font-weight-black"
      height="45px" width="100%" depressed color="black"
      @click="createAccount" :loading="creatingAccount"
    >
      Create Account
    </v-btn>
    <div class="createAccountBtn">
      <div>
        <span>
          Already have a KoDark account?
        </span>
        <v-btn class="font-weight-black text-capitalize text-decoration-underline" text height="24px" width="70px" depressed to="/en/auth/signIn">
          Sign in
        </v-btn>
      </div>
    </div>
    <v-dialog
      v-model="wasCreated"
      max-width="290"
      @click:outside="moveRoute"
    >
      <v-card>
        <v-card-title class="headline">Welcome!</v-card-title>
        <v-card-text>
          Account has been created
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            color="green darken-1"
            text
            @click="moveRoute"
          >
            Okay
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
export default {
  data: () => ({
    didSend: false,
    email: '',
    emailRules: [
      v => !!v || 'Email Address is required',
      v => /.+@.+/.test(v) || 'Email Address must be valid'
    ],
    emailError: false,
    emailErrorMsg: '',
    verifCode: '',
    verifCodeRules: [
      v => !!v || 'Verification code is required',
      v => v.length === 6 || 'Verification code must be 6-digit'
    ],
    gettingVerifCode: false,
    didAuth: false,
    didAuthFail: false,
    authFailMsg: '',
    sendingVerifCode: false,
    password: '',
    passwordRules: [
      v => !!v || 'Password is required',
      v => (v.length >= 8 && /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,25}$/.test(v)) || 'Use 8 or more characters with a mix of letters, numbers & symbols'
    ],
    show1: false,
    confirmPassword: '',
    show2: false,
    errorMessages: '',
    sendedEmail: '',
    creatingAccount: false,
    wasCreated: false,
    nextLink: ''
  }),
  methods: {
    // password와 confirmPassword가 일치하는지 확인하는 메소드
    confirmRule () {
      this.errorMessages = this.password === this.confirmPassword ? '' : "Those passwords didn't match"
      return this.password === this.confirmPassword
    },
    // 이메일로 인증문자를 요청하는 메소드
    async getVerifCode () {
      if (!this.$refs.emailAddress.validate(true)) return
      this.gettingVerifCode = true
      const status = await this.$store.dispatch('auth/getVerifCode', this.email)
      if(status === 201) { // success
        this.didSend = true
        this.emailError = false
        this.sendedEmail = this.email
        this.didAuth = false
      }
      if(status === 409) {
        this.emailErrorMsg = 'Those email is already taken'
        this.emailError = true // conflict
      }
      this.gettingVerifCode = false
    },
    // 인증문자를 인증하는 메소드
    async sendVerifCode () {
      if (!this.$refs.verificationCode.validate(true)) return
      this.sendingVerifCode = true
      const status = await this.$store.dispatch('auth/sendVerifCode', { email: this.email, authString: this.verifCode })
      console.log(status)
      if(status === 204) {
        this.didAuth = true
        this.didAuthFail = false
      }
      if(status === 401) {
        this.authFailMsg = 'Wrong code. Try again'
        this.didAuth = false
        this.didAuthFail = true
      }
      if(status === 408) {
        this.authFailMsg = 'Timed out. Get a new verification code'
        this.didAuth = false
        this.didAuthFail = true
      }
      this.sendingVerifCode = false
    },
    // 계정을 만드는 메소드
    async createAccount () {
      if(!this.sendedEmail) {
        this.emailErrorMsg = 'You need to get email verification code'
        return this.emailError = true
      }
      if(!this.didAuth) {
        this.authFailMsg = 'Verification code needs to be verified.'
        return this.didAuthFail = true
      }
      if(!this.$refs.password.validate(true) || !this.$refs.confirmPassword.validate(true)) return

      this.creatingAccount = true
      const { status, links } = await this.$store.dispatch('users/createAccount', { email: this.sendedEmail, pwd: this.password })
      console.log(links)
      if(status === 409) {
        this.emailErrorMsg = 'Those email is already taken'
        this.emailError = true
      }
      if(status === 201) {
        this.wasCreated = true
        this.nextLink = links.next
      }
      this.creatingAccount = false
    },
    // sing in 페이지로 이동하는 메소드
    moveRoute () {
      this.wasCreated = false
      this.$router.push({ path: this.nextLink })
      // this.$router.push({ path: '/en/auth/signIn' })
    }
  }
}
</script>

<style scoped>
.verifyEmailGuide {
  padding-top: 16px;
}

.verifyEmailForm {
  display: grid;
  grid-template-columns: 6fr 1fr;
  column-gap: 8px;
}

.createAccountBtn {
  padding: 12px 0;
  display: grid;
  justify-items: center;
  font-size: 14px;
}
</style>
