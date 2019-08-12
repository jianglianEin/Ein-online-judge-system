<template>
    <div class="problems">
    <router-view exact/>
        <div class="top_div">
            <div class="top_left">
                <el-select v-model="value8" filterable placeholder="请输入题号：" style="width:50%;float:left" >
                  <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
            </div>
            
        </div>
        <div class="main_div">
            <el-table
              :data="problems_data"
              style="width: 100%">
              <!--<el-table-column
                prop="state"
                label="提交状态"
                width="90">
              </el-table-column>-->
              <el-table-column
                 type="index"
                 width="50">
               </el-table-column>
              <el-table-column
                prop="title"
                label="题目名称">
                <template slot-scope="scope"><a href="#/problems/1" style="color: #4183c4;text-decoration:none">{{ scope.row.title }}</a></template>
              </el-table-column>
              <el-table-column
                prop="pass"
                label="通过"
                width="90">
              </el-table-column>
              <el-table-column
                prop="commit"
                label="提交"
                width="90">
              </el-table-column>
              <el-table-column
                prop="pass_commit"
                label="通过率"
                width="90">
              </el-table-column>
            </el-table>
        </div>
        <div class="buttom_div">
            <el-pagination
              @current-change="PageChange"
              :page-size="problemNum"
              background
              layout="prev, pager, next"
              :total="totalProblem">
            </el-pagination>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
        options: [{
          value: '选项1',
          label: 'test1'
        }, {
          value: '选项2',
          label: 'test2'
        }, {
          value: '选项3',
          label: 'test3'
        }, {
          value: '选项4',
          label: 'test4'
        }, {
          value: '选项5',
          label: 'test5'
        }],
        value8: '',
        problems_data: [],
          
          pageNum : 1,
          problemNum : 50,
          totalProblem: 1000,
        }
    },
    methods: {
      PageChange(pageNum){
        console.log(pageNum)
        this.pageNum = pageNum;
        this.getProblems(pageNum,this.problemNum);
      },
      getProblems(pageNum,problemsNum){
        var url = this.Problem + '/getProblems';
        this.$http({
          method:'get',
          url: url,
          params: {
            pageNum:pageNum,
            problemsNum:problemsNum
          }
          }).then(res=>{
              console.log(res.data)
              if(res.data.success){
                const message = JSON.parse(res.data.message);
                console.log(message);
                message.forEach(v=>{
                  if(v.pass!=0&&v.commit!=0){
                    v.pass_commit = v.pass / v.commit;
                  }else{
                    v.pass_commit = 0
                  }
                })
                // console.log(message)
                this.problems_data = message;
                // this.discussion_data = message;
              }else{
                this.$message.error('没有更多的数据！');
              }
          }).catch(e => {
            this.$message.error('出现错误！');
          })
      },
      getCount(){
        var url = this.Problem + '/getCount';
        this.$http({
          method:'get',
          url: url,
          }).then(res=>{
              console.log(res.data)
              if(res.data.success){
                const message = JSON.parse(res.data.message);
                console.log(message);
                this.totalProblem = message;
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
      this.getProblems(this.pageNum,this.problemNum);
    },
}
</script>

<style>
.problems {
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
   margin-top:0px;
   padding-top:20px;
}
</style>