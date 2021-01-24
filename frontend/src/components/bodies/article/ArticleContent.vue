<template>
  <div>
    <div class="articleContentContainer" id="articleContent"></div>
    <div class="reporterInfo">
      <p>
        By Reporter {{ reporter.name }}
      </p>
      <p>
        Email : {{ reporter.email }}
      </p>
      <p>
        Created at {{ createdAt }}
      </p>
      <p>
        Edited at {{ editedAt }}
      </p>
    </div>
  </div>
</template>

<script>
export default {
  props: ['content', 'reporter', 'createdAt', 'editedAt'],
  watch: {
    content() {
      document.getElementById('articleContent').innerHTML = this.content
    }
  }
}
</script>

<style scoped>
.reporterInfo {
  position: relative;
  max-width: 600px;
  width: 600px;
  left: calc((100% - 600px) / 2);
  padding-top: 43px;
  font-size: 16px;
  font-style: italic;
}
</style>

<style>

.articleContentContainer *:not(img)
{
  position: relative;
  max-width: 600px;
  width: 600px;
  font-size: 18px;
  margin: 0 auto 16px auto;
  display: grid;
  justify-content: center;
}

.articleContentContainer img {
  max-width: 1200px;
  object-fit: cover;
  margin: 27px auto 43px auto;
}

</style>
