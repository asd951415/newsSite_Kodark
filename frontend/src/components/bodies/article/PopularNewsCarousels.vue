<template>
  <div class="popularNewsCarousels">
    <v-slide-group
      v-model="model"
      :show-arrows="false"
      class="slideContainer"
    >
      <v-slide-item
        v-for="(news, i) in formatedNews"
        :key="i"
      >
        <router-link :to="'/en' + news._link.href">
          <div class="slideNewsContainer">
            <div class="text-h5 font-weight-bold" style="word-break: break-all;">
              {{news.title}}
              <!-- 30자 -->
            </div>
            <div class="slideNewsBody">
              <div>
                {{news.subTitle}}
                <!-- 70자 -->
              </div>
              <div class="slideNewsImg">
                <img :src="news.image">
              </div>
            </div>
            <div class="slideNewsFoot">
              {{news.editedAt}}
              <!-- 10h ago - By Jack -->
            </div>
          </div>
        </router-link>
      </v-slide-item>
    </v-slide-group>
  </div>
</template>

<script>
export default {
  props:['newsData'],
  computed: {
    formatedNews() {
      return this.newsData.map((news, i) => {
        news.title = news.title.length > 30 ? news.title.substring(0, 30) + '...' : news.title
        news.subTitle = news.subTitle.length > 70 ? news.subTitle.substring(0, 70) + '...' : news.subTitle
        return news
      })
    }
  }
}
</script>

<style scoped>
.popularNewsCarousels a {
  color: black;
}

.popularNewsCarousels a:hover {
  text-decoration: underline;
}

.slideContainer {
  border-top: 1px solid black;
  margin-top: 30px;
  padding: 0;
  position: relative;
}

.slideNewsContainer {
  margin-top: 15px;
  border-left: 1px solid rgb(215, 215, 215);
  height: 200px;
  width: 350px;
  padding: 0 10px;
  /* white-space: normal; */

}

.slideNewsBody {
  display: grid;
  grid-template-columns: 1fr 90px;
}

.slideNewsBody img {
  object-fit: cover;
  width: 100%;
  height: 100%;
}

.slideNewsImg {
  width: 90px;
  height: 80px;
  padding-left: 15px;
  padding-bottom: 5px;
}

.slideNewsFoot {
  font-size: 10px;
  font-style: italic;
}

</style>
