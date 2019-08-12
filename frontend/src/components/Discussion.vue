<template>
    <div class="discussion">
        <div class="top_div">
            <div class="top_left">
                <el-breadcrumb separator-class="el-icon-arrow-right">
                  <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
                  <el-breadcrumb-item :to="{ path: '/discussion' }">讨论</el-breadcrumb-item>
                </el-breadcrumb>
            </div>
            <div class="top_right">
                <el-button type="primary" style="float:right" @click="$router.push({path:'/discussion/addDiscussion'})"><i class="el-icon-edit"></i> 发帖</el-button>
            </div>
        </div>
        <div class="main_div">
            <el-table
              :data="discussion_data"
              style="width: 100%">
              <el-table-column
                prop="title"
                label="标题"
                >
                <template slot-scope="scope"><a style="color: #4183c4;text-decoration:none;cursor: pointer;" @click="$router.push({path:'/discussion/'+scope.row.id})">{{ scope.row.title }}</a></template>
              </el-table-column>
              <el-table-column
                prop="lz.username"
                label="LZ"
                width="120">
                <template slot-scope="scope"><a style="color: #4183c4;text-decoration:none;cursor: pointer;" @click="$router.push({path:'/user/'+scope.row.lz.id})">{{ scope.row.lz.username }}</a></template>
              </el-table-column>
              <el-table-column
                prop="startDate"
                label="发表时间"
                width="180">
              </el-table-column>
            </el-table>
        </div>
        <div class="buttom_div">
            <el-pagination
              @current-change="PageChange"
              :page-size="bbsNum"
              background
              layout="prev, pager, next"
              :total="totalBBS">
            </el-pagination>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
        pageNum : 1,
        bbsNum : 50,
        totalBBS: 1000,
        discussion_data: []
        }
    },
    methods: {
      PageChange(pageNum){
        console.log(pageNum)
        this.pageNum = pageNum;
        this.getNotics(pageNum,this.bbsNum);
      },
      getNotics(pageNum,bbsNum){
        var url = this.Notic + '/getNotics';
        this.$http({
          method:'get',
          url: url,
          params: {
            pageNum:pageNum,
            bbsNum:bbsNum
          }
          }).then(res=>{
              console.log(res.data)
              if(res.data.success){
                const message = JSON.parse(res.data.message);
                console.log(message);
                this.discussion_data = message;
              }else{
                this.$message.error('没有更多的数据！');
              }
          }).catch(e => {
            this.$message.error('出现错误！');
          })
      },
      getCount(){
        var url = this.Notic + '/getCount';
        this.$http({
          method:'get',
          url: url,
          }).then(res=>{
              console.log(res.data)
              if(res.data.success){
                const message = JSON.parse(res.data.message);
                console.log(message);
                this.totalBBS = message;
              }else{
                this.$message.error(res.data.message);
              }
          }).catch(e => {
            this.$message.error('出现错误！');
          })
      }
    },
    created() {
      this.getCount();
      this.getNotics(this.pageNum,this.bbsNum);
    },
}
</script>

<style>
.discussion {
   margin: 0 auto;
   top:65px;
   width: 70%;
   height:100%;
   position:relative;
  }
.top_left {
   float:left;
   width: 45%;
   position:relative;
   margin:10px;
  }
.top_right {
   float:right;
   width: 30%;
   position:relative;
   margin:10px;
  }
.top_div {
   width: 80%;
   height:6%;
   position:relative;
   margin:10px auto; 
   margin-top:0px;
}
.main_div {
   width: 80%;
   position:relative;
   margin:10px auto;
   margin-top:0px;
}
.buttom_div {
   width: 80%;
   height:10%;
   position:relative;
   margin:10px auto;
   padding-top:20px;
   margin-top:0px;
}
</style>