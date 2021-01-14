<template>
  <div>
    <div class="admin_title">Craete Reporter</div>
    <div class="admin_subTitle">Enter information</div>
    <div class="admin_content">
      <div class="information">
        <div>Profile image</div>
        <v-file-input
        ref="profileImage"
        :rules="userDetailForm.imageRules"
        accept="image/png, image/jpeg, image/bmp"
        placeholder="Pick an image"
        prepend-icon="mdi-camera"
        label="Profile image"
        @change="changeImage"
      ></v-file-input>
      </div>
      <div class="information">
        <div>Email</div>
        <v-text-field
          ref="emailAddress"
          label="Email"
          color="black"
          height="45px"
          dense=true
          outlined
          v-model="emailForm.email"
          :rules="emailForm.emailRules"
        ></v-text-field>
      </div>
      <div class="information">
        <div>Password</div>
        <v-text-field
            ref="password"
            label="Password"
            :append-icon="passwordForm.show ? 'mdi-eye' : 'mdi-eye-off'"
            :type="passwordForm.show ? 'text' : 'password'"
            @click:append="passwordForm.show = !passwordForm.show"
            height="45px"
            dense=true
            outlined
            v-model="passwordForm.password"
            :rules="passwordForm.passwordRules"
            required
        ></v-text-field>
      </div>
      <div class="information">
        <div class="emailInput__title">Confirm Password</div>
        <v-text-field
            ref="confirmPassword"
            label="Confirm Password"
            :append-icon="passwordForm.show2 ? 'mdi-eye' : 'mdi-eye-off'"
            :type="passwordForm.show2 ? 'text' : 'password'"
            @click:append="passwordForm.show2 = !passwordForm.show2"
            height="45px"
            dense=true
            outlined
            class="emailInput"
            v-model="passwordForm.confirmPassword"
            :rules="[confirmRule]"
            :error-messages="passwordForm.errorMessages"
            required
        ></v-text-field>
      </div>
      <div class="information">
        <div>Name</div>
        <v-text-field
          ref="name"
          label="Name"
          color="black"
          height="45px"
          dense=true
          outlined
          v-model="userDetailForm.name"
        ></v-text-field>
      </div>
      <div class="information">
        <div>Nick name</div>
        <v-text-field
          ref="nickName"
          label="Nick name"
          color="black"
          height="45px"
          dense=true
          outlined
          v-model="userDetailForm.nickName"
        ></v-text-field>
      </div>
      <div class="information">
        <div>Local</div>
        <v-text-field
          ref="local"
          label="Local"
          color="black"
          height="45px"
          dense=true
          outlined
          v-model="userDetailForm.local"
        ></v-text-field>
      </div>
      <div class="information">
        <div>Birth</div>
        <v-col cols="12" sm="6" md="4">
          <v-menu
            ref="birth"
            v-model="menu"
            :close-on-content-click="false"
            :return-value.sync="userDetailForm.birth"
            transition="scale-transition"
            offset-y
            min-width="290px"
          >
            <template v-slot:activator="{ on, attrs }">
              <v-text-field
                v-model="userDetailForm.birth"
                label="Birth"
                prepend-icon="event"
                readonly
                v-bind="attrs"
                v-on="on"
              ></v-text-field>
            </template>
            <v-date-picker v-model="userDetailForm.birth" no-title scrollable>
              <v-spacer></v-spacer>
              <v-btn text color="primary" @click="menu = false">Cancel</v-btn>
              <v-btn text color="primary" @click="$refs.birth.save(userDetailForm.birth)">OK</v-btn>
            </v-date-picker>
          </v-menu>
        </v-col>
      </div>
      <div class="information">
        <div>Gender</div>
        <v-radio-group v-model="userDetailForm.gender" row>
          <v-radio label="Male" value="M"></v-radio>
          <v-radio label="Female" value="F" color="red"></v-radio>
        </v-radio-group>
      </div>
      <div>
        <v-btn
          class="text-capitalize white--text font-weight-black"
          height="45px" width="100%" depressed color="black"
          @click="createReporter" :loading="creatingReporter"
        >
          Create Reporter
        </v-btn>
      </div>
    </div>
    <v-snackbar
      v-model="failCreate"
      bottom="true"
      color="error"
      :timeout="failMsgTimeOut"
    >
      {{ failMsg }}
      <template v-slot:action="{ attrs }">
        <v-btn
          class="text-capitalize"
          dark
          text
          v-bind="attrs"
          @click="failCreate = false"
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
    emailForm: {
      email: '',
      emailRules: [
        v => !!v || 'Email Address is required',
        v => /.+@.+/.test(v) || 'Email Address must be valid'
      ],
    },
    passwordForm: {
      password: '',
      confirmPassword: '',
      show: false,
      show2: false,
      passwordRules: [
        v => !!v || 'Password is required',
        v => (v.length >= 8 && /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,25}$/.test(v)) || 'Use 8 or more characters with a mix of letters, numbers & symbols'
      ],
    },
    userDetailForm: {
      imageFile: undefined,
      imageRules: [
        value => !value || value.size < 2000000 || 'Image size should be less than 2 MB!',
      ],
      name: '',
      nickName: '',
      local: '',
      birth: new Date().toISOString().substr(0, 10),
      gender: 'M'
    },
    creatingReporter: false,
    failCreate: false,
    failMsg: '',
    failMsgTimeOut: 10000
  }),
  methods: {
    confirmRule () {
      this.passwordForm.errorMessages = this.passwordForm.password === this.passwordForm.confirmPassword ? '' : "Those passwords didn't match"
      return this.passwordForm.password === this.passwordForm.confirmPassword
    },
    async createReporter() {
      if(!this.emailForm.email || !this.userDetailForm.name || !this.userDetailForm.nickName || !this.userDetailForm.local || !this.userDetailForm.birth || !this.userDetailForm.gender) {
        this.failMsg = 'All information is required'
        return this.failCreate = true
      }
      if(!this.$refs.password.validate(true) || !this.$refs.confirmPassword.validate(true)) {
        this.failMsg = 'Check your password'
        return this.failCreate = true
      }
      this.creatingReporter = true
      const email = this.emailForm.email
        , pwd = this.passwordForm.password
        , nickName = this.userDetailForm.nickName
        , name = this.userDetailForm.name
        , local = this.userDetailForm.local
        , birth = this.userDetailForm.birth // string
        , gender = this.userDetailForm.gender
        , image = this.userDetailForm.imageFile // object
      const { status } = await this.$store.dispatch('admin/createReporter' ,
      { email, pwd, nickName, name, local, birth, gender, image})
      if(status === 201) {
        this.$router.push({ path: '/en/admin/reporters' })
      }
      else if(status === 409)  {
        this.failMsg = 'There are duplicate users.'
        this.failCreate = true
      }
      else if(status === 401 || status === 403) {
        this.$router.push({ path: '/en/auth/signIn' })
      }
      this.creatingReporter = false
    },
    changeImage(imageFile) {
      this.userDetailForm.imageFile = imageFile
    }
  }
}
</script>

<style scoped>
.information {
  height: 100px;
  display: grid;
  grid-template-rows: 3fr 7fr;
}
</style>
