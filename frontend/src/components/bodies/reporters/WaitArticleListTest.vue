<template>
  <div class="waitArticleListComponent">
    <h1 class="title">Waiting Article Lists</h1>
    <div class="topNav">
      <span>기사</span>
    </div>
    <ul class="articleItemList">
      <li class="articleItem">
        <div class="article">
          <img src="../../../assets/img/articleList1.jpg" alt="" class="articleImg">
          <div class="articleDetail">
            <h3 class="articleTitle">Title of Article</h3>
            <h5 class="articleSubTitle">Lorem ipsum dolor sit amet consectetur a
              dipisicing elit. Voluptates, quibusdam, perspiciatis expli
              rum quae aliquam autem?</h5>
            <h6 class="reporterName">이종현기자</h6>
          </div>
        </div>
      </li>
      <li class="articleItem">
        <div class="article">
          <img src="../../../assets/img/articleList1.jpg" alt="" class="articleImg">
          <div class="articleDetail">
            <h3 class="articleTitle">Title of Article</h3>
            <h5 class="articleSubTitle">Lorem ipsum dolor sit amet consectetur a
              dipisicing elit. Voluptates, quibusdam, perspiciatis expli
              rum quae aliquam autem?</h5>
            <h6 class="reporterName">이종현기자</h6>
          </div>
        </div>
      </li>
    </ul>
  </div>
</template>

<script>
export default {

}
</script>

<style scoped>
ul{
  padding-left: 0;
}
li{
  list-style: none;
}
img{
  width: 110px;
  height: 100px;
}

.title{
  font-weight: 300;
  font-size: 50px;
  line-height: 56px;
  margin-bottom: 24px;
  border-bottom: 1px solid black;
}
.topNav{
  margin-bottom: 10px;
  display: grid;
  grid-template-columns: 9fr 2fr 1fr;
  border-bottom: 1px solid black;
}
.topNav > :nth-child(1){
  padding-left: 5px;
}
.topNav > :nth-child(2){
  padding-left: 41px;
}
.topNav > :nth-child(3){
  padding-left: 4px;
}


.articleItem{
  padding: 4px 0 10px 4px;
  margin-bottom: 10px;
  display: grid;
  grid-template-columns: 3fr 1fr;
  border-bottom: 1px solid black;
}

.article{
  display: grid;
  grid-template-columns: 1fr 4fr;
}

.articleSubTitle{
  margin: 9px 0 9px 0;
}

.btns{
  display: grid;
  grid-template-columns: 1fr 1fr;
  justify-items: center;
  align-content: center;
}
</style>