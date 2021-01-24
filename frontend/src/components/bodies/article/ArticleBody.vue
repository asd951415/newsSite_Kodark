<template>
  <div>
    <div class="articleBody">
      <ArticleTitle :title="title" :subTitle="subTitle" :reporter="reporter" :editedAt="editedAt" />
      
      <div class="articleTitleMargin">
        <ArticleSubFunction
        :commentCount="comment.commentCount"
        :emotions="emotions"
        :userEmotion="userEmotion"
        :reporter="reporter"
        @openCommentWindow="openCommentWindow"
        @chooseEmotion="chooseEmotion"
        @openUnauthorizedWindow="openUnauthorizedWindow"
        />
      </div>

      <ArticleContent 
      :content="content"
      :reporter="reporter"
      :createdAt="createdAt"
      :editedAt="editedAt"
      />

      <div class="articleBottomMargin">
        <ArticleSubFunction
        :commentCount="comment.commentCount"
        :emotions="emotions"
        :userEmotion="userEmotion"
        :reporter="reporter"
        @openCommentWindow="openCommentWindow"
        @chooseEmotion="chooseEmotion"
        @openUnauthorizedWindow="openUnauthorizedWindow"
        />
      </div>
    </div>
    <v-navigation-drawer
    id="commentWindow"
    app
    right
    width="450px"
    v-model="comment.showCommentWindow"
    >
      <div class="commentCotainer">
        <v-app-bar
        absolute
        color="white"
        elevate-on-scroll
        height="60px"
        class="commentTop"
        >
          <v-btn text icon
          absolute
          right
          @click="closeCommentWindow"
          >
            <v-icon>close</v-icon>
          </v-btn>
          Comments {{ comment.commentCount }}
        </v-app-bar>
        <template v-if="!comment.openCommentForm">
          <div>
            <v-text-field
              placeholder="Share your toughts."
              solo
              @click="comment.openCommentForm = true"
            ></v-text-field>
            <div class="commentWelcomeText">
              Kodark Times needs your voice. We welcome your on-topic commentary, criticism and expertise.
            </div>
          </div>
          <div v-for="(comment, i) in comment.commentList" :key="i">
            <Comment :comment="comment" @openUnauthorizedWindow="openUnauthorizedWindow" />
          </div>
          <template v-if="comment.commentList.length < comment.commentCount">
            <v-btn depressed class="editBtn text-capitalize white--black"
            color="white"
            width="100%"
            @click="getMoreComments"
            >
              More comments
            </v-btn>
          </template>
        </template>
        <template v-if="comment.openCommentForm">
          <CommentForm
          @submitComment="submitComment"
          @closeCommentForm="closeCommentForm"
          @openUnauthorizedWindow="openUnauthorizedWindow"
          />
        </template>
      </div>
    </v-navigation-drawer>
    <v-dialog
      v-model="unauthorized"
      max-width="400"
      @click:outside="moveRoute"
    >
      <v-card>
        <v-card-title class="headline">This function requires sign in.</v-card-title>
        <v-card-text>   
          Would you like to go to the sign in page?
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            color="green darken-1"
            text
            @click="moveSignInPage"
          >
            Go
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import ArticleTitle from './ArticleTitle'
import ArticleSubFunction from './ArticleSubFunction'
import ArticleContent from './ArticleContent'
import Comment from '../../units/Comment'
import CommentForm from '../../units/CommentForm'

export default {
  components: {
    ArticleTitle,
    ArticleSubFunction,
    ArticleContent,
    Comment,
    CommentForm
  },
  data() {
    return {
      title: '',
      subTitle: '',
      content: '',
      createdAt: '',
      editedAt: '',
      reporter: {},
      emotions: [],
      userEmotion: undefined,
      comment: {
        commentCount: '0',
        showCommentWindow: false,
        openCommentForm: false,
        commentList: []
      },
      unauthorized: false
    }
  },
  methods: {
    async setArticleDetail() {
      const articleId = this.$route.query.articleId
      const { status, article, links } = await this.$store.dispatch('article/article', { articleId })
      
      if(status === 200) {
        this.title = article.title
        this.subTitle = article.subTitle
        this.content = article.content
        this.createdAt = article.createdAt
        this.editedAt = article.editedAt
        this.comment.commentCount = article.commentCount
        this.reporter = article.reporter
        // document.getElementById('articleContent').innerHTML = this.content
      }
    },
    async setEmotionData() {
      const articleId = this.$route.query.articleId
      var { status, emotions, links } = await this.$store.dispatch('article/emotion', { articleId })

      this.emotions = emotions
      var { status, chooseResult } = await this.$store.dispatch('users/chooseEmotion',
                                        { articleId, emotion: '' })

      if(status === 200) {
        for(let i = 0; i < chooseResult.length; i++) {
          if(chooseResult[i].count) {
            this.userEmotion = chooseResult[i].emotion
            break;
          }
          else this.userEmotion = undefined
        }
      }

    },
    // 댓글창 열기
    openCommentWindow() {
      this.comment.showCommentWindow = true
      this.setCommentList()
    },
    async setCommentList() {
      const articleId = this.$route.query.articleId
      const commentStartId = -1
      const { status, comments } = await this.$store.dispatch('article/getComments', { articleId, commentStartId })
      
      if(status === 200) {
        this.comment.commentList = comments
      }
    },
    closeCommentWindow() {
      this.comment.showCommentWindow = false
      this.closeCommentForm()
    },
    closeCommentForm() {
      this.comment.openCommentForm = false
      this.setCommentList()
    },
    async submitComment(content) {
      const articleId = this.$route.query.articleId
      const { status } = await this.$store.dispatch('users/createComment', { articleId, content })
      
      if(status === 204) {
        this.closeCommentForm()
      } else if(status === 401) {
        this.unauthorized = true
      }
    },
    moveSignInPage() {
      this.unauthorized = false
      this.$router.push({ path: '/en/auth/signIn' })
    },
    async chooseEmotion(emotion) {
      const articleId = this.$route.query.articleId
      const { status, chooseResult } = await this.$store.dispatch('users/chooseEmotion',
                                        { articleId, emotion })

      if(status === 200) {
        let newEmotions = this.emotions.map(el => {
          if(el.emotion === this.userEmotion) el.count--
          return el
        })
        for(let i = 0; i < chooseResult.length; i++) {
          if(chooseResult[i].count) {
            this.userEmotion = chooseResult[i].emotion
            newEmotions[i].count++
            break;
          }
          else this.userEmotion = undefined
        }
        this.emotions = newEmotions
      } else if(status === 401) {
        this.unauthorized = true
      }
    },
    async getMoreComments() {
      const last = this.comment.commentList[this.comment.commentList.length - 1]

      const articleId = this.$route.query.articleId
      const commentStartId = last.id
      const { status, comments } = await this.$store.dispatch('article/getComments', { articleId, commentStartId })
      
      if(status === 200) {
        let addedCommentList = this.comment.commentList
        addedCommentList.push(...comments)
        this.comment.commentList = addedCommentList
      }
    },
    openUnauthorizedWindow() {
      this.unauthorized = true
    }
  },
  created() {
    this.setArticleDetail()
    this.setEmotionData()
  }
}
</script>

<style scoped>
.articleBody {
  padding-top: 100px;
}

.articleTitleMargin {
  margin-left: calc((100% - 600px) / 2);
  margin-right: auto;
  padding: 20px 0;
}

.articleBottomMargin {
  position: relative;
  max-width: 600px;
  width: 600px;
  left: calc((100% - 600px) / 2);
  padding-top: 20px;
  padding-bottom: 10px;
  border-bottom: 1px solid black;
  margin-bottom: 40px;
}

.commentCotainer {
  padding: 60px 40px 40px 40px;
}

.commentTop {
  font-size: 30px;
  font-weight: bold;
  padding: 0 24px;
}

.commentWelcomeText {
  font-size: 14px;
  padding-bottom: 10px;
  border-bottom: 1px solid rgb(215, 215, 215);
}

</style>
