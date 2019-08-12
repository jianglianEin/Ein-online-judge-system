<template>
    <div class="solution">
        <div class="top_div">
            <el-form :inline="true" :model="solution_search_data" class="demo-form-inline">
              <el-form-item label="题号">
                <el-input v-model="solution_search_data.id" placeholder="" style="width:50px"></el-input>
              </el-form-item>
              <el-form-item label="提交者">
                <el-input v-model="solution_search_data.username" placeholder="" style="width:150px"></el-input>
              </el-form-item>
              <el-form-item label="语言种类">
                <el-select v-model="solution_search_data.languageType" placeholder="" style="width:100px">
                  <el-option label="不限" value="none"></el-option>
                  <el-option label="cpp" value="cpp"></el-option>
                  <el-option label="java" value="java"></el-option>
                  <el-option label="nodejs" value="nodejs"></el-option>
                  <el-option label="python" value="python"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="状态">
                <el-select v-model="solution_search_data.state" placeholder="" style="width:100px">
                  <el-option label="不限" value="none"></el-option>
                  <el-option label="未通过" value="false"></el-option>
                  <el-option label="通过" value="true"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item>
                <el-button type="info" @click=""　icon="el-icon-search">查询</el-button>
              </el-form-item>
            </el-form>
        </div>
        <div class="main_div">
            <el-table
              :data="solution_data"
              style="width: 100%">
              <el-table-column
                 type="index"
                 width="50">
               </el-table-column>
              <el-table-column
                prop="solution"
                label="题目"
                width="180">
                <template slot-scope="scope"><a herf="" style="color: #4183c4;text-decoration:none;cursor: pointer; " @click="$router.push({path:'/problems/'+scope.row.solution.problem.id})">{{ scope.row.solution.problem.title }}</a></template>
              </el-table-column>
              <el-table-column
                prop="solution"
                label="状态"
                width="90">
                <template slot-scope="scope"><a herf="" style="color: #4183c4;text-decoration:none;cursor:pointer; " @click="$router.push({path:'/solution/'+scope.row.solution.id})">{{ scope.row.solution.state }}</a></template>
              </el-table-column>
              <el-table-column
                prop="cost"
                label="总用时"
                width="90">
              </el-table-column>
              <el-table-column
                prop="solution"
                label="代码"
                width="180">
                <template slot-scope="scope"><a herf="" style="color: #4183c4;text-decoration:none;cursor:pointer; " @click="$router.push({path:'/solution/'+scope.row.solution.id})">{{ scope.row.solution.languageType }}</a></template>
              </el-table-column>
               <el-table-column
                prop="user"
                label="提交者"
                width="180">
                <template slot-scope="scope"><a herf="" style="color: #4183c4;text-decoration:none;cursor:pointer; " @click="$router.push({path:'/user/'+scope.row.user.id})">{{ scope.row.user.username }}</a></template>
              </el-table-column>
               <el-table-column
                prop="postDate"
                label="提交时间"
                width="180">
              </el-table-column>
            </el-table>
        </div>
        <div class="buttom_div">
            <el-pagination
              @current-change="PageChange"
              :page-size="solutionNum"
              background
              layout="prev, pager, next"
              :total="totalSolution">
            </el-pagination>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
                solution_search_data: {
                    id:"",
                    username:"",
                    languageType:"none",
                    state:"none"
                },
                solution_data:[],
                pageNum : 1,
                solutionNum : 50,
                totalSolution: 1000,
            }
        },
        methods: {
          PageChange(pageNum){
            console.log(pageNum)
            this.pageNum = pageNum;
            this.getUser_Solutions(pageNum,this.solutionNum);
          },
          getUser_Solutions(pageNum,solutionNum){
            var url = this.Solution + '/getUser_Solutions';
            this.$http({
              method:'get',
              url: url,
              params: {
                page:pageNum,
                solutionsNum:solutionNum
              }
              }).then(res=>{
                  console.log(res.data)
                  if(res.data.success){
                    const message = JSON.parse(res.data.message);
                    console.log(message);
                    // console.log(message)
                    this.solution_data = message;
                    // this.discussion_data = message;
                  }else{
                    this.$message.error('没有更多的数据！');
                  }
              }).catch(e => {
                this.$message.error('出现错误！');
              })
          },
          getCount(){
            var url = this.Solution + '/getCount';
            this.$http({
              method:'get',
              url: url,
              }).then(res=>{
                  console.log(res.data)
                  if(res.data.success){
                    const message = JSON.parse(res.data.message);
                    console.log(message);
                    this.totalSolution = message;
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
          this.getUser_Solutions(this.pageNum,this.solutionNum);
        },
    
}
</script>

<style>
.solution {
   margin: 0 auto;
   top:65px;
   width: 70%;
   height:100%;
   position:relative;
  }
.top_right {
   float:right;
   width: 30%;
   position:relative;
   margin:10px;
  }
.top_div {
   width: 80%;

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