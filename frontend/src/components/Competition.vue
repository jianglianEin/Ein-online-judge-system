<template>
    <div class="competition">
        <div class="main_div">
            <el-table
              :data="competition_data"
              style="width: 100%">
               <el-table-column
                prop="open"
                label="当前状态"
                width="90">
                <template slot-scope="scope"><a style="color: #4183c4;text-decoration:none;">{{ scope.row.open }}</a></template>
              </el-table-column>
              <el-table-column
                prop="title"
                label="比赛名称"
                width="120">
                <template slot-scope="scope"><a style="color: #4183c4;text-decoration:none;cursor: pointer;" @click="$router.push({path:'/competition/'+scope.row.id})">{{ scope.row.title }}</a></template>
              </el-table-column>
              <el-table-column
                prop="startDate"
                label="开始时间"
                width="180">
              </el-table-column>
              <el-table-column
                prop="discription"
                label="描述">
              </el-table-column＞

              <el-table-column>
              </el-table-column>
            </el-table>
        </div>
        <div class="buttom_div">
            <el-pagination
              @current-change="PageChange"
              :page-size="competitionNum"
              background
              layout="prev, pager, next"
              :total="totalCompetition">
            </el-pagination>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
        competition_data: [],

          pageNum : 1,
          competitionNum : 50,
          totalCompetition: 1000,
          discussion_data: []
        }
    },

    methods: {
      PageChange(pageNum){
        console.log(pageNum)
        this.pageNum = pageNum;
        this.getCompetitions(pageNum,this.competitionNum);
      },
      getCompetitions(pageNum,competitionNum){
        var url = this.Competition + '/getCompetitions';
        this.$http({
          method:'get',
          url: url,
          params: {
            page:pageNum,
            competitionsNum:competitionNum
          }
          }).then(res=>{
              console.log(res.data)
              if(res.data.success){
                const message = JSON.parse(res.data.message);
                console.log(message);
                this.competition_data = message;
              }else{
                this.$message.error('没有更多的数据！');
              }
          }).catch(e => {
            this.$message.error('出现错误！');
          })
      },
      getCount(){
        var url = this.Competition + '/getCount';
        this.$http({
          method:'get',
          url: url,
          }).then(res=>{
              console.log(res.data)
              if(res.data.success){
                const message = JSON.parse(res.data.message);
                // console.log(message);
                this.totalCompetition = message;
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
      this.getCompetitions(this.pageNum,this.competitionNum);
    },
}
</script>

<style>
.competition {
   margin: 0 auto;
   top:65px;
   width: 70%;
   height:100%;
   position:relative;
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
   margin-top:0px;
   padding-top:20px;
}
</style>