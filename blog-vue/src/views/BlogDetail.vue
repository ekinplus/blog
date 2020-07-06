<template>
  <div>
    <Header></Header>

    <div class="mblog">
      <el-row>
        <h2> <el-col :span="23"><div class="grid-content bg-purple"> {{ blog.title }}</div></el-col></h2>
      <el-col :span="1" ><div class="grid-content bg-purple">

      <el-button icon="el-icon-delete" type="danger"  circle v-if="ownBlog" @click="deletBlog()"></el-button>
      </div></el-col>

      </el-row>
      <el-link icon="el-icon-edit" v-if="ownBlog">
        <router-link :to="{name: 'BlogEdit', params: {blogId: blog.id}}" >
        编辑
        </router-link>
      </el-link>
      <el-divider></el-divider>
      <div class="markdown-body" v-html="blog.content"></div>
    </div>

  </div>
</template>

<script>
  import 'github-markdown-css'
  import Header from "../components/Header";

  export default {
    name: "BlogDetail.vue",
    components: {Header},
    data() {
      return {
        blog: {
          id: "",
          title: "",
          content: "",
          userName: "",
          created:""
        },
        ownBlog: false
      }
    },
    created() {
      const blogId = this.$route.params.blogId
      console.log(blogId)
      const _this = this
      this.$axios.get('/blog/' + blogId).then(res => {
        const blog = res.data.data
        _this.blog.id = blog.id
        _this.blog.title = blog.title

        var MardownIt = require("markdown-it")
        var md = new MardownIt()

        var result = md.render(blog.content)
        _this.blog.content = result
        _this.ownBlog = (blog.userId === _this.$store.getters.getUser.id)

      })
    },
    methods:{

      deletBlog(){
        const blogId = this.$route.params.blogId
        console.log(blogId)
        const _this = this
        this.$alert('操作成功', '提示', {
          confirmButtonText: '确定',
          callback: action => {
            this.$axios.post('/blog/delete/' + blogId, {
              headers: {
                "Authorization": localStorage.getItem("token")
              }
            }).then(res => {
              console.log(res)
              _this.$router.push("/blogs")
            })
            }
        });
      }

    }

  }
</script>

<style scoped>
  .mblog {
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    width: 100%;
    min-height: 700px;
    padding: 20px 15px;
  }

</style>