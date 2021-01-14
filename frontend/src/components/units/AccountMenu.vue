<template>
  <v-menu
    v-model="menu"
    :close-on-content-click="false"
    max-width="250px"
    offset-y
  >
    <template v-slot:activator="{ on, attrs }">
      <v-btn
        color="indigo"
        dark
        v-bind="attrs"
        v-on="on"
      >
        Account
      </v-btn>
    </template>

    <v-card>
      <v-list>
        <v-list-item>
          <v-list-item-content
          :class="userData.auth === 'admin' ? 'adminMenuList' : 'userMenuList' ">
            <div class="subtitle-2 black--text">{{ userData.email }}</div>

            <!-- user -->
            <template v-if="userData.auth === 'user'">
              <router-link class="subtitle-2 black--text font-weight-regular textDecoNone hoverAction"
                :to="'/en' + links.myPage"
              >
                My page
              </router-link>
              <router-link class="subtitle-2 black--text font-weight-regular textDecoNone hoverAction"
                :to="'/en' + links.subscribedList"
              >
                Subscription list
              </router-link>
            </template>

            <!-- reporter -->
            <template v-if="userData.auth === 'reporter'">
              <router-link class="subtitle-2 black--text font-weight-regular textDecoNone hoverAction"
                :to="'/en' + links.reporterInfo"
              >
                Reporter page
              </router-link>
              <router-link class="subtitle-2 black--text font-weight-regular textDecoNone hoverAction"
                :to="'/en' + links.reporterArticle"
              >
                Article Manage
              </router-link>
            </template>

            <!-- admin -->
            <template v-if="userData.auth === 'admin'">
              <router-link class="subtitle-2 black--text font-weight-regular textDecoNone hoverAction"
                :to="'/en' + links.statistics"
              >
                Admin page
              </router-link>
              <router-link class="subtitle-2 black--text font-weight-regular textDecoNone hoverAction"
                :to="'/en' + links.userList"
              >
                User Manage
              </router-link>
              <router-link class="subtitle-2 black--text font-weight-regular textDecoNone hoverAction"
                :to="'/en' + links.reporterList"
              >
                Reporter Manage
              </router-link>
            </template>
          </v-list-item-content>
        </v-list-item>
      </v-list>

      <v-divider></v-divider>

      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn class="text-capitalize" color="gray" text @click="signOut">Sign out</v-btn>
        <v-spacer></v-spacer>
      </v-card-actions>
    </v-card>
  </v-menu>
</template>

<script>
export default {
  props: ['userData', 'links'],
  data: () => ({
    menu: false
  }),
  methods: {
    signOut () {
      this.$store.dispatch('auth/signOut')
    }
  }
}
</script>

<style scoped>
.userMenuList {
  padding: 5px;
  display: grid;
  grid-template-rows: repeat(3, 40px);
}

.adminMenuList {
  padding: 5px;
  display: grid;
  grid-template-rows: repeat(4, 40px);
}

.textDecoNone {
  text-decoration: none;
}

.hoverAction:hover {
  text-decoration: underline;
}

</style>
