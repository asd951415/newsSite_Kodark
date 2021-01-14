<template>
  <div class="profile">
        <h1 class="profile__title">Account</h1>
        <h2 class="profile__subTitle">Manage account</h2>
        <div class="profileContent">
            <div class="userProfile">
                <p>
                   You’re using <strong>{{ account.userData.email }}</strong> to log in — this is also where we send newsletters.
                </p>
            </div>
            <div class="editProfileBtns">
                <v-btn depressed class="editBtn text-capitalize white--text"
                color="indigo"
                :disabled="!didEdit"
                @click="emailFormShow">
                    Edit
                </v-btn>
                <v-btn depressed class="editBtn text-capitalize white--text"
                color="indigo"
                :disabled="!didpwdChange"
                 @click="pwdFormShow">
                    change Password
                </v-btn>
            </div>
        </div>
        <div class="editEmail show">
            <div class="editEmail__container">
                <div class="emailInput__container">
                    <div class="emailInput__title">Email</div>
                    <div class="verifyEmailForm">
                        <v-text-field
                            ref="emailAddress"
                            label="Email Address"
                            outlined
                            v-model="email"
                            :rules="emailRules"
                            
                        ></v-text-field>
                        <v-btn class="text-capitalize white--text sendBtn" height="45px" depressed color="black"
                        @click="getVerifCode" :loading="gettingVerifCode"
                        >
                        Send
                        </v-btn>
                    </div>
                    <v-alert type="warning" dense dismissible v-model="emailError">
                        {{ emailErrorMsg }}
                    </v-alert>
                    <div class="emailInput__title">Verify Code</div>
                    <div class="verifyEmailForm">
                        <v-text-field
                            ref="verificationCode"
                            label="Enter verification code"
                            hint="Verification code was sent.(Valid time 30 minutes)"
                            outlined
                            class="emailInput"
                            :persistent-hint="didSend"
                            :disabled="!didSend"
                            v-model="verifCode"
                            :rules="verifCodeRules"
                        ></v-text-field>
                        <v-btn class="text-capitalize white--text sendBtn" height="45px" depressed color="black"
                        :disabled="!didSend" @click="sendVerifCode" :loading="sendingVerifCode"
                        >
                        verify
                        </v-btn>
                    </div>
                    <v-alert type="success" dense dismissible v-model="didAuth">
                        Success
                    </v-alert>
                    <v-alert type="warning" dense dismissible v-model="didAuthFail">
                        {{ authFailMsg }}
                    </v-alert>
                </div>
                <div class="editProfileBtns">
                    <v-btn depressed class="editBtn text-capitalize white--text" color="indigo"
                    :disabled="!didAuth" @click="save('email')"
                    >
                        Save
                    </v-btn>
                    <v-btn depressed class="editBtn text-capitalize white--text" color="indigo" 
                    @click="emailFormCancle"
                    >
                        Cancle
                    </v-btn>
                </div>
            </div>
        </div>
        <div class="changePwd show">
            <div class="editEmail__container">
                <div class="changePwd__container">
                    <div class="emailInput__title">New Password</div>
                    <v-text-field
                        ref="newPassword"
                        label="New Password"
                        :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                        :type="show1 ? 'text' : 'password'"
                        @click:append="show1 = !show1"
                        outlined
                        class="emailInput"
                        v-model="newPassword"
                        :rules="passwordRules"
                        required
                    ></v-text-field>
                    <div class="emailInput__title">Confirm Password</div>
                    <v-text-field
                        ref="newConfirmPassword"
                        label="Confirm Password"
                        :append-icon="show2 ? 'mdi-eye' : 'mdi-eye-off'"
                        :type="show2 ? 'text' : 'password'"
                        @click:append="show2 = !show2"
                        outlined
                        class="emailInput"
                        v-model="confirmPassword"
                        :rules="[confirmRule]"
                        :error-messages="errorMessages"
                        required
                    ></v-text-field>
                </div>
                <div class="editProfileBtns">
                    <v-btn depressed class="editBtn text-capitalize white--text"
                    color="indigo"
                    :disabled="didConfirmPassword"
                    @click="save('password')"
                    >
                        Save
                    </v-btn>
                    <v-btn depressed class="editBtn text-capitalize white--text" color="indigo" @click="pwdFormCancle">
                        Cancle
                    </v-btn>
                </div>
            </div>
        </div>
        <v-dialog
        v-model="isActivatedSave"
        max-width="450"
        >
            <v-card :loading="processSubmit">
                <div class="verifyPwdContainer">
                    <v-card-title class="headline">Enter your password</v-card-title>
                    <v-card-text>
                        To continue, first verify it's you
                    </v-card-text>
                    <v-text-field
                    ref="verifPassword"
                    label="Enter your password"
                    :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
                    :type="show ? 'text' : 'password'"
                    @click:append="show = !show"
                    outlined
                    color="black"
                    height="45px"
                    dense=true
                    class="verifPassword"
                    v-model="verifPassword"
                    required
                    ></v-text-field>
                    <v-alert type="warning" dense dismissible v-model="verifPwdError">
                        {{ verifPwdErrorMsg }}
                    </v-alert>
                </div>
                <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn
                    color="green darken-1"
                    text
                    @click="target === 'email' ? changeEmail() : changePwd()"
                >
                    Submit
                </v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
    </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
    data: () => ({
        didEdit: true,
        didSend: false,
        email: '',
        emailRules: [
            v => /.+@.+/.test(v) || 'Email Address must be valid'
        ],
        emailError: false,
        emailErrorMsg: '',
        verifCode: '',
        verifCodeRules: [
            v => v.length === 6 || 'Verification code must be 6-digit'
        ],
        gettingVerifCode: false,
        didAuth: false,
        didAuthFail: false,
        authFailMsg: '',
        sendingVerifCode: false,
        sendedEmail: '',
        isActivatedSave: false,

        target: '',
        verifPassword: '',
        show: false,
        processSubmit: false,
        verifPwdError: false,
        verifPwdErrorMsg: '',

        show1: false,
        show2: false,
        didpwdChange: true,
        newPassword: '',
        confirmPassword: '',
        passwordRules: [
            v => !!v || 'Password is required',
            v => (v.length >= 8 && /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,25}$/.test(v)) || 'Use 8 or more characters with a mix of letters, numbers & symbols'
        ],
        errorMessages: ''
    }),
    methods: {
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
        save(target) {
            if(target === 'email') {
                if(!this.sendedEmail) {
                    this.emailErrorMsg = 'You need to get email verification code'
                    return this.emailError = true
                }
                if(!this.didAuth) {
                    this.authFailMsg = 'Verification code needs to be verified.'
                    return this.didAuthFail = true
                }
            } else {
                if(!this.$refs.newPassword.validate(true)
                || !this.$refs.newConfirmPassword.validate(true)) return
            }
            this.target = target
            this.isActivatedSave = true
        },
        // 이메일 바꾸는 메소드
        async changeEmail() {
            this.processSubmit = true
            const { status } = await this.$store.dispatch('users/changeEmail', { email: this.sendedEmail, verifPwd: this.verifPassword })

            if(status === 401) {
                this.verifPwdErrorMsg = 'The password you entered is incorrect'
                this.verifPwdError = true
            }
            if(status === 409) {
                this.emailErrorMsg = 'Those email is already taken'
                this.emailError = true
                this.isActivatedSave = false
                this.verifPassword = ''
            }
            if(status === 204) {
                this.$store.dispatch('users/getUserData')
                this.emailFormCancle()
                this.pwdFormCancle()
            }
            this.processSubmit = false
        },
        async changePwd() {
            console.log('changePwd')
            this.processSubmit = true
            const { status } = await this.$store.dispatch('users/changePwd', { pwd: this.newPassword, verifPwd: this.verifPassword })
            if(status === 401) {
                this.verifPwdErrorMsg = 'The password you entered is incorrect'
                this.verifPwdError = true
            }
            if(status === 204) {
                this.emailFormCancle()
                this.pwdFormCancle()
            }
            this.processSubmit = false
        },
        emailFormShow () {
            const emailForm = document.querySelector('.editEmail');
            emailForm.classList.remove('show')
            this.didEdit = false
        },
        emailFormCancle () {
            const emailForm = document.querySelector('.editEmail');
            emailForm.classList.add('show')
            this.didEdit = true
            this.didSend = false
            this.email = ''
            this.emailError = false
            this.emailErrorMsg = ''
            this.verifCode = ''
            this.gettingVerifCode = false
            this.didAuth = false
            this.didAuthFail = false
            this.authFailMsg = ''
            this.sendingVerifCode = false
            this.sendedEmail = ''
            this.isActivatedSave = false
            this.verifPassword = ''
        },
        pwdFormShow () {
            const changePwdForm = document.querySelector('.changePwd');
            changePwdForm.classList.remove('show')
            this.didpwdChange = false
        },
        pwdFormCancle () {
            const changePwdForm = document.querySelector('.changePwd');
            changePwdForm.classList.add('show')
            this.didpwdChange = true
            this.show1 = false
            this.show2 = false
            this.didpwdChange = true
            this.didConfirmPassword = false
            this.newPassword = ''
            this.confirmPassword = ''
            this.isActivatedSave = false
        },
        // password와 confirmPassword가 일치하는지 확인하는 메소드
        confirmRule () {
            this.errorMessages = this.newPassword === this.confirmPassword ? '' : "Those passwords didn't match"
            return this.newPassword === this.confirmPassword
        },
        async OnChangePwd(){
            const status = await this.$stroe.dispatch('users/pwd' , {pwd: this.newPassword})
            if(status === 204) {
                
            }
            if(status === 401) {
                
            }
            if(status === 404) {
                
            }
        }
    },
    computed: {
        ...mapGetters({
            account: 'getAccount'
        }),
        didConfirmPassword() {
            return this.newPassword !== this.confirmPassword
        }
    },
    created () {
        this.$store.dispatch('users/getUserData')
    }
}
</script>

<style scoped>
.show{
    display: none;
}
.profile__title{
    font-weight: 300;
    font-size: 50px;
    line-height: 56px;
    margin-bottom: 24px;
}
.profile__subTitle{
    border-bottom: 1px solid #bdbdbd;
    font-weight: 700;
    font-size: 17px;
    margin-bottom: 9px;
}
.profileContent, .editEmail__container{
    display: grid;
    grid-template-columns: 3fr 1fr;
    margin-top: 30px;
}

.verifyEmailForm{
    display: flex;
    justify-content: space-between;
    width:380px;
}

.verifyEmailForm .emailInput {
    width: 320px;
}

.emailInput__pwd{
    width: 307px;
}

.sendBtn{
    margin-left: 10px;
    width: 30px;
}

.userProfile{
    padding-top: 10px;
    width: 490px;
}
button{
    display: block;
    padding: 10px;
    margin-top: 10px;
}

.editProfileBtns{
    justify-self: end;
}

.editBtn{
    width: 180px;
    height: 17px;
}
.editEmail, .changePwd{
    margin-top: 100px;
    border-top: 1px solid black;
}

.emailInput{
    width:320px;
}

.verifyPwdContainer {
    width: 80%;
    margin: 0 auto;
    padding-top: 10px;
    /* display: grid;
    justify-content: center; */
}

.verifPassword {
    padding: 0px 15px;
}

</style>