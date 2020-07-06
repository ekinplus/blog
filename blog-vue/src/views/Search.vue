<template>
    <div class="mcontaner">
        <Header></Header>
        <div class="block">
            <h4 style="text-align: center">您正在搜索关键字{{this.$route.params.q}},共有{{total}}条记录！</h4>
            <el-timeline>
                <el-timeline-item :timestamp="blog.created" placement="top" v-for="blog in blogs">
                    <el-card>
                        <el-row>
                            <el-col :span="22" >
                                <router-link :to="{name: 'BlogDetail', params: {blogId: blog.id}}">
                                    {{blog.title}}
                                </router-link>
                            </el-col>

                            <el-col :span="2">
                                <i class="el-icon-user"></i>  {{blog.userName}}
                            </el-col>
                        </el-row>
                        <p>{{blog.description}}
                        </p>
                    </el-card>
                </el-timeline-item>
            </el-timeline>
            <el-pagination class="mpage"
                           background
                           layout="prev, pager, next"
                           :current-page="currentPage"
                           :page-size="pageSize"
                           :total="total"
                           @current-change=page>
            </el-pagination>

        </div>

    </div>
</template>

<script>
    import Header from "../components/Header";

    export default {
        name: "Search.vue",
        components: {Header},
        data() {
            return {
                blogs: {},
                currentPage: 1,
                total: 0,
                pageSize: 5
            }
        },
        methods: {
            page(currentPage) {
                const _this = this
                const q=this.$route.params.q
                _this.$axios.get("/blog/search?currentPage=" + currentPage+"&word="+q).then(res => {
                    console.log(res)
                    _this.blogs = res.data.data.records
                    _this.currentPage = res.data.data.current
                    _this.total = res.data.data.total
                    _this.pageSize = res.data.data.size
                })
            },

        },
        created() {
           this.page(1)
        },
    }

</script>

<style scoped>

    .mpage {
        margin: 0 auto;
        text-align: center;
    }

</style>