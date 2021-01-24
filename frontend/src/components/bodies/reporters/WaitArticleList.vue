<template>
  <div class="WaitArticleComponent">
  <h1 class="title">Wait Article List</h1>
  <v-simple-table
  fixed-header
  >
  <template v-slot:default>
    <thead>
      <tr>
        <th class="text-left">
          Created At
        </th>
        <th class="text-left">
          Title
        </th>
        <th class="text-left" style="width: 49px;">
          Hit
        </th>
        <th class="text-left">
          Edit
        </th>
         <th class="text-left">
          Delete
        </th>
      </tr>
    </thead>
    <tbody>
      <tr
        v-for="article in articles"
        :key="article.id"
      >
        <td>{{article["createdAt "]}}</td>
        <td class="articleTitle">
          <!-- <router-link :to="`/en/article?articleId=${article.id}` "> -->
          <router-link :to="article._links[3].href">
            {{article["title "]}} {{article._links[3].href}}
          </router-link>
        </td>
        <td>{{article.hit}}</td>
        <td>  
          <v-btn
          depressed
          small
          color="primary"
          >
          Edit
          </v-btn>
        </td>
         <td>  
          <v-btn
          depressed
          small
          color="primary"
          >
          Delete
          </v-btn>
        </td>
      </tr>
    </tbody>
  </template>
  </v-simple-table>
   <div class="text-center page">
    <v-pagination
      v-model="page"
      :length="6"
    ></v-pagination>
  </div>
  </div>
</template>

<script>
export default {
 data: () => ({
    articles: [],
    page: 1,
  }),
  async created () {
    const { status, data } = await this.$store.dispatch('reporters/getArticles', {status: 'null'});
    if(status === 200) {
      console.log('created')
      console.log(data)
      this.articles= data
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
.articleTitle a{
  color: black;
}
.page{
  margin-top: 30px;
}
</style>