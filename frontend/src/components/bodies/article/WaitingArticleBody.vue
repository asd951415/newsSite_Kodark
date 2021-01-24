<template>
  <div>
    <div class="articleBody">
      <ArticleTitle :title="title" :subTitle="subTitle" :reporter="reporter" :editedAt="editedAt" />

      <ArticleContent 
      :content="content"
      :reporter="reporter"
      :createdAt="createdAt"
      :editedAt="editedAt"
      />
    </div>
  </div>
</template>

<script>
import ArticleTitle from './ArticleTitle'
import ArticleContent from './ArticleContent'

export default {
  components: {
    ArticleTitle,
    ArticleContent
  },
  data() {
    return {
      title: '',
      subTitle: '',
      content: '',
      createdAt: '',
      editedAt: '',
      reporter: {},
    }
  },
  methods: {
    async setArticleDetail() {
      const articleId = this.$route.query.articleId
      const { status, article } = await this.$store.dispatch('reporters/getArticleDetail', { articleId })

      console.log('....')
      console.log(article)
      if(status === 200) {
        this.title = article.title
        this.subTitle = article.subTitle
        this.content = article.content
        this.createdAt = article.createdAt
        this.editedAt = article.editedAt
        this.reporter = article.reporter
      }
    },
  },
  created() {
    this.setArticleDetail()
  }
}
</script>

<style scoped>
.articleBody {
  padding-top: 100px;
}
</style>
