<template>
  <div>
    <h4 class="lighter">
      <i class="ace-icon fa fa-hand-o-right icon-animated-hand-pointer blue"></i>
      <router-link to="/business/course" class="pink"> {{course1.name}} </router-link>
    </h4>
    <hr>
  <p>
     <router-link to="/business/course" class="btn btn-white btn-default btn-round">
        <i class="ace-icon fa fa-arrow-left"></i>
        返回课程
      </router-link>
      &nbsp;
    <button v-on:click="add()" class="btn btn-white btn-default btn-round">
      <i class="ace-icon fa fa-edit"></i>
      新增
    </button>
    &nbsp;&nbsp;
    <button v-on:click="list('1')" class="btn btn-white btn-default btn-round">
      <i class="ace-icon fa fa-refresh"></i>
      刷新
    </button>
  </p>

  <pagination ref="pagination" v-bind:list="list" v-bind:itemCount="8"></pagination>

  <table id="simple-table" class="table  table-bordered table-hover">
    <thead>
    <tr>
      <th>ID</th>
      <th>名称</th>
      <th>课程ID</th>
      <th>操作</th>
    </tr>
    </thead>

    <tbody>
    <tr v-for="chapter in chapters">
      <td>{{chapter.id}}</td>
      <td>{{chapter.name}}</td>
      <td>{{chapter.courseId}}</td>
      <td>
        <div class="hidden-sm hidden-xs btn-group">
          <button v-on:click="toSection(chapter)" class="btn btn-white btn-xs btn-info btn-round">
            小节
          </button>&nbsp;
         <!-- <button v-on:click="edit(chapter)" class="btn btn-xs btn-info">
            <i class="ace-icon fa fa-pencil bigger-120"></i>
          </button>
          <button  v-on:click="del(chapter.id)" class="btn btn-xs btn-danger">
            <i class="ace-icon fa fa-trash-o bigger-120"></i>
          </button>-->
          <button v-on:click="edit(chapter)" class="btn btn-white btn-xs btn-info btn-round">
            编辑
          </button>&nbsp;
          <button v-on:click="del(chapter.id)" class="btn btn-white btn-xs btn-warning btn-round">
            删除
          </button>
        </div>
      </td>
    </tr>
    </tbody>
  </table>

    <div id="form-modal" class="modal fade" tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">表单</h4>
          </div>
          <div class="modal-body">
            <form class="form-horizontal">
              <div class="form-group">
                <label class="col-sm-2 control-label">名称</label>
                <div class="col-sm-10">
                  <input v-model="chapter.name" class="form-control" placeholder="名称">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">课程ID</label>
                <div class="col-sm-10">
                  <input v-model="chapter.courseId" class="form-control" placeholder="课程ID">
                </div>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button type="button" v-on:click="save()" class="btn btn-primary">保存</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

  </div>



</template>

<script>
  import Pagination from "../../components/pagination";
  export default {
    components: {Pagination},
    name: "chapter",
    data: function () {
      return {
        chapter: {},
        chapters: [],
        course1: {},
      }
    },
    mounted: function () {
      let _this = this;
      _this.$refs.pagination.size=5;
      let course1 = SessionStorage.get(SESSION_KEY_COURSE) || {};
      console.log(course1.id+"--")
      if (Tool.isEmpty(course1)) {
        _this.$router.push("/welcome");
      }
      _this.course1 = course1;
      _this.list(1);
    },
    methods: {
      /*list() {
        let _this = this;
        _this.$ajax.get('http://127.0.0.1:9002/business/admin/chapter/query.do').then((response) => {
          console.log("查询大章列表结果：", response);
          _this.chapters = response.data;
        })
      }*/
      add() {
        let _this = this;
        _this.chapter = {};
        $("#form-modal").modal("show");
      },
      edit(chapter) {
        let _this = this;
        _this.chapter = $.extend({}, chapter);
        $("#form-modal").modal("show");
      },
      save(page) {
        let _this = this;
        _this.$ajax.post('http://127.0.0.1:9000/business/admin/chapter/save', _this.chapter).then((response)=>{
          console.log("保存大章列表结果：", response);
          let resp = response.data;
                    if (resp.success){
                      Toast.success("保存成功！");
                      $("#form-modal").modal("hide");
                     }else {
                      Toast.warning(resp.message);
                    }

        })
      },
      del(id) {
        let _this = this;

        Confirm.show("删除大章后不可恢复，确认删除？", function () {
           Loading.show();
          _this.$ajax.delete('http://127.0.0.1:9000/business/admin/chapter/delete/' + id).then((response)=>{
            Loading.hide();
            console.log("删除大章列表结果：", response);
            let resp = response.data;
            if (resp.success) {
              _this.list(1);
              Toast.success("删除成功！");
            }
          })
        });
      },
      /**
       * 点击【小节】
       */
      toSection(chapter) {
        let _this = this;
        SessionStorage.set(SESSION_KEY_CHAPTER, chapter);
        _this.$router.push("/business/section");
      },
      list(page) {
        let _this = this;
        Loading.show();
        _this.$ajax.post('http://127.0.0.1:9000/business/admin/chapter/query.do', {
          page: page,
          size: _this.$refs.pagination.size,
          courseId: _this.course1.id
        }).then((response)=>{
          Loading.hide();
          let resp = response.data;
          _this.chapters =resp.content.list;
          _this.$refs.pagination.render(page, resp.content.total);
        })
      }
    }
  }
</script>
