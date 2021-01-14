<template>
  <div class="ProfileEditComponent">
      <h1 class="title">Profile Detail</h1>
      <div class="userDetailInfo">
        <div class="userDetailContainer">
            <p class="userInfoTitle">
              NickName
            </p>
            <div class="userInfoContent">
        
              <p>{{ nickName }}</p> 
            </div>
        </div>
        <div class="userDetailContainer">
            <div class="userInfoTitle">
              <p>Name</p>
            </div>
            <div class="userInfoContent">
              <p>{{name}}</p> 
            </div>
        </div>
        <div class="userDetailContainer">
            <div class="userInfoTitle">
              <p>Local</p>
            </div>
            <div class="userInfoContent">
              <p>{{local}}</p> 
            </div>
        </div>
        <div class="userDetailContainer">
            <div class="userInfoTitle">
              <p>Birth</p>
            </div>
            <div class="userInfoContent">
              <p>{{birth}}</p> 
            </div>
        </div>
        <div class="userDetailContainer">
            <div class="userInfoTitle">
              <p>Gender</p>
            </div>
            <div class="userInfoContent">
              <p>{{gender}}</p> 
            </div>
        </div>
        <div class="userDetailContainer">
            <div class="userInfoTitle">
              <p>Image</p>
            </div>
            <div class="userInfoContent image">
              <!-- <p>{{image}}</p> -->
              <img class="userInfoImage" :src="image">
            </div>
        </div>
        <div class="EditBtn">
          <v-btn
            depressed
            color="indigo"
            @click="InputShow"
          >
            <span>Edit</span>
          </v-btn>
        </div>
        <div class="delBtn">
          <v-btn
              depressed
              color="indigo"
              @click="userDelete"
            >
              <span>Delete</span> 
            </v-btn>
        </div>
      </div>

      <!-- Detail Form start -->
      <div class="editInputContainer inputHide">
        <div class="inputContainer">
            <div class="inputTitle" style="margin-right: 20px">
              <p>Nick Name</p>
            </div>
            <div class="inputContent nickNameInput">
              <v-text-field
                ref="nickName"
                label="NickName"
                height="40px"
                outlined
                v-model="userDetailForm.nickName"
                required
                dense=true
              >
              </v-text-field>
            </div>
        </div>
        <div class="inputContainer">
            <div class="inputTitle" style="margin-right: 76px">Name</div>
            <div class="inputContent">
              <v-text-field
                ref="name"
                label="Name"
                v-model="userDetailForm.name"
                height="40px"
                outlined
                required
                dense=true
              >
              </v-text-field>
            </div>
        </div>
        
        <div class="inputContainer">
            <div class="inputTitle" style="margin-right: 76px">Local</div>
            <div class="inputContent">
              <v-text-field
                ref="local"
                label="local"
                v-model="userDetailForm.local"
                height="40px"
                outlined
                required
                dense=true
              >
              </v-text-field>
            </div>

        </div>
          <div class="inputContainer">
            <div class="inputTitle" style="margin-right: 70px; line-height:70px ">Birth</div>
            <div class="inputContent">
              <v-menu
                      ref="menu"
                      v-model="menu"
                      :close-on-content-click="false"
                      transition="scale-transition"
                      offset-y
                      min-width="290px"
                  >
                  <template v-slot:activator="{ on, attrs }">
                  <v-text-field
                      v-model="userDetailForm.date"
                      label="Birthday date"
                      prepend-icon="mdi-calendar"
                      readonly
                      v-bind="attrs"
                      v-on="on"
                  ></v-text-field>
                  </template>
                  <v-date-picker
                      ref="picker"
                      v-model="userDetailForm.date"
                      :max="new Date().toISOString().substr(0, 10)"
                      min="1950-01-01"
                      @change="save"
                  ></v-date-picker>
              </v-menu>
            </div>
          </div>

          <div class="inputContainer gender">
            <div class="inputTitle" style="margin-right: 76px; line-height: 65px">Gender</div>
            <div class="inputContent">
              <v-radio-group v-model="userDetailForm.gender" row>
                <v-radio label="Male" value="M"></v-radio>
                <v-radio label="Female" value="F" color="red"></v-radio>
              </v-radio-group>
            </div>
          </div>
          
          <div class="inputContainer gender">
            <div class="inputTitle" style="margin-right: 76px; line-height: 65px">Profile Image</div>
            <div class="inputContent">
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
          </div>
          

        <div class="inputFromBtn">
          <div class="CancleBtn">
            <v-btn
              depressed
              color="indigo"
              @click="InputHide"
            >
              <span>Cancle</span>
            </v-btn>
          </div>
          <div class="submitBtn">
              <v-btn
                depressed
                color="indigo"
                @click="userDetailSubmit"
              >
                <span>SubMit</span>
              </v-btn>
            </div>
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
      name: '',
      nickName: '',
      local: '',
      birth: '',
      gender: '',
      image: '',
    userDetailForm: {
      imageFile: undefined,
      imageRules: [
        value => !value || value.size < 2000000 || 'Image size should be less than 2 MB!',
      ],
      name: '',
      nickName: '',
      local: '',
      birth: new Date().toISOString().substr(0, 10),
      gender: ''
    },
    failCreate: false,
    failMsg: '',
    failMsgTimeOut: 10000
  
    }),
    watch: {
      menu (val) {
        val && setTimeout(() => (this.$refs.picker.activePicker = 'YEAR'))
      },
    },
    methods: {
      save (date) {
        this.$refs.menu.save(date)
      },
      InputShow () {
        const userDetailInfo = document.querySelector('.userDetailInfo');
        const inputHide = document.querySelector('.inputHide');
        userDetailInfo.style.display ="none";
        inputHide.style.visibility="visible";
        
      },
      InputHide () {
        const userDetailInfo = document.querySelector('.userDetailInfo');
        const inputHide = document.querySelector('.inputHide');
        userDetailInfo.style.display ="block";
        inputHide.style.visibility="hidden";
      },
      async userDetailSubmit () {
        if(!this.userDetailForm.name || !this.userDetailForm.nickName || !this.userDetailForm.local || !this.userDetailForm.birth || !this.userDetailForm.gender) {
        this.failMsg = 'All information is required'
        return this.failCreate = true
        }
        const nickName = this.userDetailForm.nickName
        , name = this.userDetailForm.name
        , local = this.userDetailForm.local
        , birth = this.userDetailForm.birth // string
        , gender = this.userDetailForm.gender
        , image = this.userDetailForm.imageFile // object
        console.log(image)
        console.log(nickName)
        const {status} = await this.$store.dispatch('users/updateDetail', 
        { nickName, name, local, birth, gender, image})
        console.log(status)
        if(status === 204){
          this.$router.push({path: '/en/users/my-page'})
        }
        else if(status === 401) {
          this.$router.push({ path: '/en/auth/signIn' })
        }else if(status === 409){
          this.failMsg = 'NickName is already in use'
          this.failCreate = true
        }
      },
      changeImage(imageFile) {
        this.userDetailForm.imageFile = imageFile;
      }
    },
    async created () {
      const {status, userDetail, links} = await this.$store.dispatch('users/getUserDetail');

      if(status === 200){
        this.nickName = userDetail.nickName;
        this.name = userDetail.name;
        this.local = userDetail.local;
        this.birth = userDetail.birth;
        this.gender = userDetail.gender;
        this.image = userDetail.image;
      }
      if(status===401){
        this.failMsg = ('Please Sign in your Account')
        this.failCreate = true
      }
    }
}
</script>

<style scoped>

/* UserDetailInfo */
.userDetailContainer{
  display: flex;
  margin-top: 20px;
  position: relative;
}
.userInfoContent{
  width: 250px;
  height: 40px;
  border-radius: 5px;
  background-color: #f7f7f5;
}

.image{
  width: 70px;
  /* height: 70px; */
  border-radius: 5px;
  background-color: #f7f7f5;
}

.userInfoContent p{
  margin-left: 5px;
  padding-top: 8px;
}

.userInfoTitle{
  margin-top: 10px;
  flex-basis: 20%;
}

.userInfoImage {
  width: 100%;
  object-fit: cover;
}

.EditBtn span,
.delBtn span,
.submitBtn span,
.CancleBtn span{
  color: white;
}

.EditBtn{
  position: absolute;
  margin: 70px 10px 0 100px;
}

.delBtn{
  margin: 70px 0 0 230px;
}

.inputFromBtn{
  margin: 100px 0 0 50px;
  display: grid;
  grid-template-columns: 1fr 4fr;
  justify-content: start;
  align-content: start;
}

.inputHide{
  visibility: hidden;
}
.title{
  padding: 10px 10px 10px 0 ;
  border-bottom: 1px solid black;
  margin-bottom: 50px;
}
.inputContainer{
    margin-top: 50px;
    width: 80%;
    height: 30px;
    display: flex;
}

.inputTitle{
    margin-right: 60px;
    line-height: 40px;
    height: 45px;
    font-size: 13px;
    flex-basis: 20%;
}

.inputTitle p{
  line-height: 50px;
  width: 100px;
}
.inputContent{
    width: 350px;
    height: 40px;
    flex-basis:70%;
}
.nickNameInput{
  padding-left: 57px;
  flex-basis:78%;
}

.maleCheck{
  margin-right: 40px;
}
.Btns{
  margin-top: 140px;
}
.saveBtn{
    display: block;
    margin-top: 50px;
    margin-left: 212px;
    width: 100px;
    /* margin: auto; */

}
.delAccountBtn{
    display: block;
    margin-top: 50px;
    margin-left: 190px;
    width: 150px;
}
</style>