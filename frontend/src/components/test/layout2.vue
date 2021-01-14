<template>
  <div class="container">
    <header class="header">
      <v-btn text>
        <p class="latest">Latest</p>
      </v-btn>
       <v-btn text>
        <span class="material-icons">
        search
        </span>
        Search
      </v-btn>
    </header>
    <div class="content__container">
      <div class="articleItemList">
         <div class="articleItem">
          <div class="articleDate">
            <p>Dec 22, 2020</p>
          </div>
          <div class="articleItem__content">
            <div class="articleTitle">
              <p>  Inside a Deadly Siege: How a String of Failures Led to a Dark Day at the Capitol</p>
            </div>
            <div class="articleSubTitle">
              <p>subTitle Lorem ipsum dolor tur nulla similique temporealias cumque.</p>
            </div>
            <div class="reportName">
              <p>report name</p>
            </div>
          </div>
          <div class="articleImg">
            <img src="../../assets/img/articleList1.jpg" alt="articleImg">
          </div>
        </div>
           <div class="articleItem">
          <div class="articleDate">
            <p>Dec 22, 2020</p>
          </div>
          <div class="articleItem__content">
            <div class="articleTitle">
              <p>Title</p>
            </div>
            <div class="articleSubTitle">
              <p>subTitle Lorem ipsum dolor tur nulla similique temporealias cumque.</p>
            </div>
            <div class="reportName">
              <p>report name</p>
            </div>
          </div>
          <div class="articleImg">
            <img src="../../assets/img/articleList1.jpg" alt="articleImg">
          </div>
        </div>
           <div class="articleItem">
          <div class="articleDate">
            <p>Dec 22, 2020</p>
          </div>
          <div class="articleItem__content">
            <div class="articleTitle">
              <p>Title</p>
            </div>
            <div class="articleSubTitle">
              <p>subTitle Lorem ipsum dolor tur nulla similique temporealias cumque.</p>
            </div>
            <div class="reportName">
              <p>report name</p>
            </div>
          </div>
          <div class="articleImg">
            <img src="../../assets/img/articleList1.jpg" alt="articleImg">
          </div>
        </div>
           <div class="articleItem">
          <div class="articleDate">
            <p>Dec 22, 2020</p>
          </div>
          <div class="articleItem__content">
            <div class="articleTitle">
              <p>Title</p>
            </div>
            <div class="articleSubTitle">
              <p>subTitle Lorem ipsum dolor tur nulla similique temporealias cumque.</p>
            </div>
            <div class="reportName">
              <p>report name</p>
            </div>
          </div>
          <div class="articleImg">
            <img src="../../assets/img/articleList1.jpg" alt="articleImg">
          </div>
        </div>
           <div class="articleItem">
          <div class="articleDate">
            <p>Dec 22, 2020</p>
          </div>
          <div class="articleItem__content">
            <div class="articleTitle">
              <p>Title</p>
            </div>
            <div class="articleSubTitle">
              <p>subTitle Lorem ipsum dolor tur nulla similique temporealias cumque.</p>
            </div>
            <div class="reportName">
              <p>report name</p>
            </div>
          </div>
          <div class="articleImg">
            <img src="../../assets/img/articleList1.jpg" alt="articleImg">
          </div>
        </div>
           <div class="articleItem">
          <div class="articleDate">
            <p>Dec 22, 2020</p>
          </div>
          <div class="articleItem__content">
            <div class="articleTitle">
              <p>Title</p>
            </div>
            <div class="articleSubTitle">
              <p>subTitle Lorem ipsum dolor tur nulla similique temporealias cumque.</p>
            </div>
            <div class="reportName">
              <p>report name</p>
            </div>
          </div>
          <div class="articleImg">
            <img src="../../assets/img/articleList1.jpg" alt="articleImg">
          </div>
        </div>
           <div class="articleItem">
          <div class="articleDate">
            <p>Dec 22, 2020</p>
          </div>
          <div class="articleItem__content">
            <div class="articleTitle">
              <p>Title</p>
            </div>
            <div class="articleSubTitle">
              <p>subTitle Lorem ipsum dolor tur nulla similique temporealias cumque.</p>
            </div>
            <div class="reportName">
              <p>report name</p>
            </div>
          </div>
          <div class="articleImg">
            <img src="../../assets/img/articleList1.jpg" alt="articleImg">
          </div>
        </div>
      </div>
     
      <div class="adItem">
        <img src="../../assets/img/ad.png" alt="">
      </div>
    
    </div>
    
    
  </div>
</template>

<script>
export default {

}
</script>

<style scoped>
body{
  box-sizing: border-box;
}
.container{
  margin-bottom: 100px;
}
.header{
  height: 38px;
  border-top: 1px solid black;
  border-bottom: 1px solid #e2e2e2;
}
.articleImg img{
  width: 215px;
  height: 143px;
}
.latest{
  padding-left: 45px;
  padding-right: 45px;
  margin: 0;
}
.content__container{
  margin-top: 10px;
  display: grid;
  grid-template-columns: 6fr 1fr;
}
.articleItem{
  margin: 15px 20px 0 0 ;
  max-height: 178px;
  display: grid;
  grid-template-columns: 1fr 3fr 2fr;
  border-bottom:  1px solid #e2e2e2;
}
.articleItem__content{
  padding-right: 26px;
}
.articleDate p{
  margin-top: 20px;
  font-size: 11px;
  color: rgb(153, 153, 153);
}
.articleTitle p{
  font-size: 19px;
  line-height: 23px;
  color: #000;
  margin: 0;
}
.articleSubTitle p{
  margin-top: 10px;
  font-size: 14px;
  color: #333;
}
.reportName p{
  color: #999;
  font-size: 11px;
}
</style>