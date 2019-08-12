<template>
    <div class="competitionmsg">
        <div class="top_div">
            <div class="top_left">
                <h2>{{competitionMsg.title}}</h2>
                <h6>{{competitionMsg.title}}</h6>

                <el-button-group>
                    <el-button type="primary">排行榜</el-button>
                    <el-button type="success">提交记录</el-button>
                </el-button-group>  
            </div>
            
            <div class="top_right">
                
            </div>
            <el-progress :percentage="70" style="width:100%"></el-progress>
        </div>
        <div class="main_div">
            <div class="competition_div">
                <h4 style="margin:0 auto;display: block;height:40px;background-color: rgba(243,244,245,0.7);">
                    <span style="float:left;margin:10px;">信息与公告</span>
                </h4>
                <hr width="100%" style="margin:0 auto;"/>
                <h5 style="margin:0 auto;display: block;text-align:left;padding:10px;">
                    <span>{{competitionMsg.discription}}</span>
                </h5>
            </div>
            <div class="competition_div">
            <el-table
              :data="competition_problems"
              style="width: 100%">
              <el-table-column
                 type="index"
                 width="50">
               </el-table-column>
              <el-table-column
                prop="title"
                label="题目">
                <template slot-scope="scope"><a style="color:#4183c4;text-decoration:none;cursor:pointer;" @click="$router.push({path:'/competition_problems/'+scope.row.competition.id+'/'+scope.row.problem.id})">{{ scope.row.problem.title }}</a></template>
              </el-table-column>
            </el-table>
        </div>
        </div>
        
        <div class="buttom_div">
        
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            competitionMsg:{},
            competition_problems:[]
        }
    },
    methods: {
            showDetailedCompetition(){
            var id = this.$route.params.id;
            var url = this.Competition + '/showDetailedCompetition';
            this.$http({
              method:'get',
              url: url,
              params: {
                competitionId:id
              }
              }).then(res=>{
                  console.log(res.data)
                  if(res.data.success){
                    const message = JSON.parse(res.data.message);
                    console.log(message);
                    this.competitionMsg = message;

                  }else{
                    this.$message.error('没有更多的数据！');
                  }
              }).catch(e => {
                this.$message.error('出现错误！');
              })
        },

        getCompetition_Problems(){
            var id = this.$route.params.id;
            var url = this.Competition + '/getCompetition_Problems';
            this.$http({
              method:'get',
              url: url,
              params: {
                competitionId:id
              }
              }).then(res=>{
                  console.log(res.data)
                  if(res.data.success){
                    const message = JSON.parse(res.data.message);
                    console.log(message);
                    this.competition_problems = message;

                  }else{
                    this.$message.error('没有更多的数据！');
                  }
              }).catch(e => {
                this.$message.error('出现错误！');
              })
        },
    },

    created() {
        this.showDetailedCompetition();
        this.getCompetition_Problems();
    },

    
}
</script>

<style>
.competitionmsg {
   margin: 0 auto;
   top:65px;
   width: 70%;
   height:100%;
   position:relative;
  }
  .competition_div {
    color: #333;
   text-align: center;
   margin:0 auto;
   width:90%;
   line-height:20px;
   margin-top:30px;
   border:solid;
   border-width:1px;
   border-color:gray;
   border-radius:10px;
   float:left;
}
  .top_left {
   float:left;
   width: 30%;
   position:relative;
   text-align : left ;
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
   margin-top:0px;
   padding-top:20px;
}
</style>