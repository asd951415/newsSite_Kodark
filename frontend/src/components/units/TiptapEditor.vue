<template>
  <div class="editor">
    <EditorMenuBar :editor="editor" v-slot="{ commands, isActive }">
      <v-toolbar style="margin: 5px 0;" dense>
        <v-btn-toggle
          color="primary"
          dense
          group
        >
          <v-btn
            :class="{ 'is-active': isActive.heading({ level: 1 }) }"
            @click="commands.heading({ level: 1 })"
          >
            H1
          </v-btn>

          <v-btn
            :class="{ 'is-active': isActive.heading({ level: 2 }) }"
            @click="commands.heading({ level: 2 })"
          >
            H2
          </v-btn>

          <v-btn
            :class="{ 'is-active': isActive.heading({ level: 3 }) }"
            @click="commands.heading({ level: 3 })"
          >
            H3
          </v-btn>
        </v-btn-toggle>

        <v-btn-toggle
          v-model="toggle_multiple"
          color="primary"
          dense
          group
          multiple
        >
          <v-btn
          :class="{ 'is-active': isActive.bold() }"
          @click="commands.bold" text>
            <v-icon>mdi-format-bold</v-icon>
          </v-btn>

          <v-btn
          :class="{ 'is-active': isActive.italic() }"
          @click="commands.italic" text>
            <v-icon>mdi-format-italic</v-icon>
          </v-btn>

          <v-btn
          :class="{ 'is-active': isActive.underline() }"
          @click="commands.underline" text>
            <v-icon>mdi-format-underline</v-icon>
          </v-btn>

        </v-btn-toggle>

        
        <v-btn text>
          <v-file-input
          id="imgUpload"
          hide-input
          :rules="imageRules"
          accept="image/png, image/jpeg, image/bmp"
          prepend-icon="image"
          @change="changeImage(commands.image)"
          >
          </v-file-input>
        </v-btn>

        <v-btn-toggle
          color="primary"
          dense
          group
        >
          <v-btn
            :class="{ 'is-active': isActive.bullet_list() }"
            @click="commands.bullet_list"
          >
            <v-icon>format_list_bulleted</v-icon>
          </v-btn>

          <v-btn
            :class="{ 'is-active': isActive.ordered_list() }"
            @click="commands.ordered_list"
          >
            <v-icon>format_list_numbered</v-icon>
          </v-btn>

          <v-btn
            :class="{ 'is-active': isActive.blockquote() }"
            @click="commands.blockquote"
          >
            <v-icon>format_quote</v-icon>
          </v-btn>

          <v-btn
            @click="commands.horizontal_rule"
          >
            <v-icon>horizontal_rule</v-icon>
          </v-btn>

          <v-btn
            @click="commands.undo"
          >
            <v-icon>undo</v-icon>
          </v-btn>

          <v-btn
            @click="commands.redo"
          >
            <v-icon>redo</v-icon>
          </v-btn>
        </v-btn-toggle>
      </v-toolbar>
    </EditorMenuBar>
    <EditorContent :editor="editor" class="editorContent" />
  </div>
</template>

<script>
import { Editor, EditorContent, EditorMenuBar } from 'tiptap'
import { Bold, Italic, Heading, Image, Underline,
  Blockquote, BulletList, OrderedList, ListItem, HorizontalRule, History } from 'tiptap-extensions'

export default {
  components: {
    EditorContent,
    EditorMenuBar
  },
  data: () => ({
    editor: null,
    imageRules: [
      value => !value || value.size < 2000000 || 'Image size should be less than 2 MB!',
    ],
    inputImageInfo: false,
    targetImageUrl: 'https://picsum.photos/1920/1080?random'
  }),
  methods: {
    async changeImage(command) {
      const image = document.getElementById('imgUpload').files[0]
      const {status, imageUrl} = await this.$store.dispatch('reporters/uploadImage' , { image })

      if(status === 200) {
        command({ src: imageUrl, class: 'imageClass' })
      }
    }
  },
  mounted() {
    this.editor = new Editor({
      content: '',
      extensions: [
        new Bold(),
        new Italic(),
        new Heading({ levels: [1, 2, 3] }),
        new Image(),
        new Underline(),
        new BulletList(),
        new OrderedList(),
        new Blockquote(),
        new ListItem(),
        new HorizontalRule(),
        new History(),
      ]
    })
    this.$emit('giveEditor', this.editor)
  },
  beforeDestroy() {
    this.editor.destroy()
  }
}
</script>

<style scoped>
.imageInfo {
  width: 80%;
  margin: 10px auto;
}
</style>

<style>
.editor img {
  width: 80%;
  display: block;
  margin: 10px auto;
}

.editor p {
  margin: 0;
}

.editorContent > div {
  border: 1px solid black;
  min-height: 800px;
}
</style>
