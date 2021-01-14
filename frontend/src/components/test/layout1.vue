<template>
  <div class="container">
    <div class="articleRow">
      <div class="sectionTop">
        <h3 class="sectionTitle">Politic</h3>
        <a class="sectionLink" href="#">More in The Politic</a>
      </div>
      <ul class="articles">
        <li class="item">
          <img src="../../assets/img/articleList1.jpg" alt="articleImg" class="articleImg">
          <p class="articleTitle">Lorem ipsum dolor sit amet consectetur adipisicing elit. Sed repell
            e?</p>
        </li>
        <li class="item">
          <img src="../../assets/img/articleList1.jpg" alt="articleImg" class="articleImg">
          <p class="articleTitle">Lorem ipsum dolor sitihil veritatis alias ratione?</p>
        </li>
        <li class="item">
          <img src="../../assets/img/articleList1.jpg" alt="articleImg" class="articleImg">
          <p class="articleTitle">Lorem ipsum dolor sit amet consectetur adipisicing elit. Sed repell
            s ratione?</p>
        </li>
        <li class="item">
          <img src="../../assets/img/articleList1.jpg" alt="articleImg" class="articleImg">
          <p class="articleTitle">Lorem ipsum doliis!ratione?</p>
        </li>
        <li class="item">
          <img src="../../assets/img/articleList1.jpg" alt="articleImg" class="articleImg">
          <p class="articleTitle">Lorem ipsum is alias ratione?</p>
        </li>
      </ul>
    </div>
    
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
body{
  box-sizing: border-box;
}
img{
  width: 200px;
  height: 143px;

}
.sectionTop{
  width: 100%;
  border-top: 1px solid black;
  display: grid;
  grid-template-columns: 1fr 1fr;
  margin-bottom: 15px;
  /* padding: 5px; */
}
.sectionTitle,
.sectionLink{
  margin-top: 10px;
}
.sectionLink{
  text-align: right;
  font-size: 14px;
  text-decoration: none;
}

.articles{
  /* width: 100%; */
  margin: 10px 0 30px 0;
  display: grid;
  grid-template-columns: repeat(5, 1fr);
  column-gap: 3px;
  margin-bottom: 30px;
}
.item{
  height: 230px;
  padding: 0 15px 10px 15px;
  display:grid;
  grid-template-rows: 1fr 1fr;
  border-right: 1px solid #e2e2e2;
}
.articles > li:nth-child(5){
  border: none;

}
.item {
  font-size: 16px;
  color: #121212;
}
.articleTitle{
  margin-top: 10px;
}

</style>