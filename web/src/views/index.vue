<template>
  <main role="main">

    <section class="jumbotron text-center">
      <div class="container">
        <h1>在线视频课程平台</h1>
        <p class="lead text-muted m-3">
          知识付费时代刚刚起步，在这个领域有很多的发展机会。整个课程以实战为基础，手把手从零开始，一步一步搭建一个完整的企业级开发架构。不讲废话，只讲干货。
        </p>
        <p>
          <!--<a href="#" class="btn btn-primary my-2 p-3 font-weight-bold">点击进入所有课程</a>-->
          <router-link to="/list" class="btn btn-primary my-2 p-3 font-weight-bold">点击进入所有课程</router-link>
        </p>
      </div>
    </section>

    <div class="album py-5 bg-light">
      <div class="container">
        <div class="title1">最新上线</div>
        <div class="row">
          <div v-for="o in news" class="col-md-4">
            <the-course v-bind:course="o"></the-course>
          </div>
        </div>

        <hr>

        <div class="title2">好课推荐</div>
        <div class="row">
          <div v-for="o in news" class="col-md-4">
            <the-course v-bind:course="o"></the-course>
          </div>
        </div>
      </div>
    </div>

  </main>
</template>

<script>
  import TheCourse from "../components/the-course";
  export default {
    name: 'index',
    components: {TheCourse},
    data: function () {
      return {
        news: [],
      }
    },
    mounted() {
      let _this = this;
      _this.listNew();
    },
    methods: {
      /**
       * 查询新上好课
       */
      listNew() {
        let _this = this;
        _this.$ajax.get('http://127.0.0.1:9000/business/web/course/list-new').then((response)=>{
          console.log("查询新上好课结果：", response);
          let resp = response.data;
          if (resp.success) {
            _this.news = resp.content;
          }
        }).catch((response)=>{
          console.log("error：", response);
        })
      },
    }
  }
</script>
<style>
  .title1{
    margin-bottom: 2rem;
    color: #fafafa;
    letter-spacing: 0;
    text-shadow: 0px 1px 0px #999, 0px 2px 0px #888, 0px 3px 0px #777, 0px 4px 0px #666, 0px 5px 0px #555, 0px 6px 0px #444, 0px 7px 0px #333, 0px 8px 7px #001135;
    font-size: 2rem;
  }
  .title2 {
    margin-bottom: 2rem;
    color: transparent;
    text-shadow: 0 -3px 2px #ff3,
    1px -5px 3px #fd3,
    -1px -6px 5px #f80,
    1px -9px 9px #f20;
    font-size: 2rem;
  }


  .course h4 {
    font-size: 1.25rem;
    margin: 15px 0;
  }

  .course .text-muted .badge{
    font-size: 1rem;
  }
</style>
