<template>
  <div class="m-content">
    <h3>欢迎来到EkinEureka的博客</h3>
    <div class="block">
      <el-avatar :size="50" :src="require('../img/'+user.avatar)"></el-avatar>
      <div>{{ user.username }}</div>
    </div>
    <div class="maction">
      <span><el-link href="/blogs">主页</el-link></span>
      <el-divider direction="vertical"></el-divider>
      <span><el-link type="success" href="/blog/add">发表博客</el-link></span>

      <el-divider direction="vertical"></el-divider>
      <span v-show="!hasLogin"><el-link type="primary" href="/login">登录</el-link><el-divider direction="vertical"></el-divider></span>

      <span v-show="hasLogin"><el-link type="danger" @click="logout">退出</el-link><el-divider direction="vertical"></el-divider></span>
        <el-button type="primary" plain icon="el-icon-search" size="mini" @click="dialogVisible = true">搜索</el-button>
        <el-dialog
                title="提示"
                :visible.sync="dialogVisible"
                width="30%"
                :before-close="handleClose">
           <!-- <el-form :model="ruleForm" :rules="rules" ref="ruleForm" >-->
            <el-input
                    placeholder="请输入内容"
                    v-model="keyword"
                    clearable>
                <i slot="prefix" class="el-input__icon el-icon-search"></i>
            </el-input>
           <!-- </el-form>-->
            <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <!-- <router-link :to="{name: 'Search', params: {q: ruleForm.keyword}}" >-->
    <el-button type="primary" @click="search(keyword)">确 定</el-button>
   <!--  </router-link>-->
  </span>
        </el-dialog>
    </div>

  </div>
</template>

<script>
  export default {
    name: "Header",
    data() {
      return {
        user: {
          username: '请先登录',
          avatar: 'user.jpg'
        },
        hasLogin: false,
        dialogVisible: false,
     /*    ruleForm: {*/
              keyword: '',
     /*     },*/
          /*rules: {
              title: [
                  { required: true, message: '请输入搜索内容', trigger: 'blur' },
              ]
          }*/
      }
    },
    methods: {
        search(word){
                    if (word=='') {

                        /*const _this = this
                        this.$axios.post('/blog/search', this.ruleForm, {
                        }).then(res => {
                            console.log(res),*/
                         /*   _this.$router.push("/search");*/
                        /*})*/
                        alert("内容不能为空")
                    }else if(this.$route.name=="Search"){
                        this.$router.push("/search/"+word)
                        this.dialogVisible=false
                        this.$router.go(0);
                    }else{
                        this.$router.push("/search/"+word)
                        this.dialogVisible=false
                    }

        },
      logout() {
        const _this = this
        _this.$axios.get("/logout", {
          headers: {
            "Authorization": localStorage.getItem("token")
          }
        }).then(res => {
          _this.$store.commit("REMOVE_INFO")
          _this.$router.push("/login")

        })
      },
        handleClose(done) {
                    done();
        }
    },
    created() {
      if(this.$store.getters.getUser.username) {
        this.user.username = this.$store.getters.getUser.username
        this.user.avatar = this.$store.getters.getUser.avatar

        this.hasLogin = true
      }

    }
  }
</script>

<style scoped>

  .m-content {
    max-width: 960px;
    margin: 0 auto;
    text-align: center;
  }
  .maction {
    margin: 10px 0;
  }

</style>