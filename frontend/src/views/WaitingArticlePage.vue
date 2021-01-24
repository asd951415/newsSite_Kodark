<template>
  <div>
    <WaitingArticleHeader />
    <WaitingArticleBody />
  </div>
</template>

<script>
import WaitingArticleHeader from '../components/headers/WaitingArticleHeader'
import WaitingArticleBody from '../components/bodies/article/WaitingArticleBody'

export default {
  components: {
    WaitingArticleHeader,
    WaitingArticleBody
  }
}
</script>

<style scoped>

</style>
