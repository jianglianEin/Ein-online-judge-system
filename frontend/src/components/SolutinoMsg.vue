<template>
    <div class="solutionmsg">
        <div class="top_div">
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
            <div class="solution_div">
                <codemirror
                :value="solutionCode"
                :options="options"
                ref="myEditor"
                @change="">
                </codemirror>
            
            </div>
        </div>
        
        <div class="buttom_div">
        
        </div>
    </div>
</template>

<script>
import { codemirror } from 'vue-codemirror-lite'
require('codemirror/mode/javascript/javascript')
require('codemirror/mode/vue/vue')
require('codemirror/mode/cmake/cmake')
require('codemirror/mode/python/python')
require("codemirror/mode/clike/clike.js");

require('codemirror/addon/hint/show-hint.js')
require('codemirror/addon/hint/show-hint.css')
require('codemirror/addon/hint/javascript-hint.js')
export default {
    data() {
        return {
            solution_data:[],
            mode: 'text/x-java',
            solutionCode:'public class db_test {\n\t@Test\n\tpublic void test() throws Exception {}\n\t}'
        }
        
    },
    computed: {
        options: function () {
        return {
        mode: this.mode,
        tabSize: 2,
        lineNumbers: true,
        lineWrapping: true,
        extraKeys: {'Ctrl-Space': 'autocomplete'},
        }
    }
},
    methods: {
        showDetailedUser_Solution(){
            var solutionId = this.$route.params.id;
            var userId = JSON.parse(sessionStorage.getItem('user')).id;
            var url = this.Solution + '/showDetailedUser_Solution';
            this.$http({
              method:'get',
              url: url,
              params: {
                solutionId:solutionId,
                userId:userId
              }
              }).then(res=>{
                  console.log(res.data)
                  if(res.data.success){
                    const message = JSON.parse(res.data.message);
                    console.log(message);
                    this.solution_data.push(message)
                    this.solutionCode = message.solution.code;

                    // this.problem = message;
                    // this.discussionMsg = message;
                  }else{
                    this.$message.error('没有更多的数据！');
                  }
              }).catch(e => {
                this.$message.error('出现错误！');
              })
        },
    },
    created() {
        this.showDetailedUser_Solution();
    },
}
</script>

<style>
.solutionmsg {
   margin: 0 auto;
   top:65px;
   width: 70%;
   height:100%;
   position:relative;
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
.solution_div {
    color: #333;
   margin:0 auto;
   width:90%;
   line-height:20px;
   margin-top:30px;
   border:solid;
   border-width:1px;
   border-color:gray;
   border-radius:10px;
   text-align:left;
}
</style>