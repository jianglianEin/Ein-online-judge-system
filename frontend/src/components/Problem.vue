<template>
    <div class="problem">
        <div class="top_div">
           <h1>#{{problem.id}}.{{problem.title}}</h1>
        </div>
        <div class="main_div">
            <div class="problem_div">
                <h4 style="margin:0 auto;display: block;height:40px;background-color: rgba(243,244,245,0.7);">
                    <span style="float:left;margin:10px;">题目描述</span>
                </h4>
                <hr width="100%" style="margin:0 auto;"/>
                <h5 style="margin:0 auto;display: block;text-align:left;padding:10px;">
                    <span>{{problem.discription}}</span>
                </h5>
            </div>

            <div class="problem_div">
                <h4 style="margin:0 auto;display: block;height:40px;background-color: rgba(243,244,245,0.7);">
                    <span style="float:left;margin:10px;">输入格式</span>
                </h4>
                <hr width="100%" style="margin:0 auto;"/>
                <h5 style="margin:0 auto;display: block;padding:10px;text-align:left;">
                    <span>{{problem.inputData}}</span>
                </h5>
            </div>

            <div class="problem_div">
                <h4 style="margin:0 auto;display: block;height:40px;background-color: rgba(243,244,245,0.7);">
                    <span style="float:left;margin:10px;">输出格式</span>
                </h4>
                <hr width="100%" style="margin:0 auto;"/>
                <h5 style="margin:0 auto;display: block;text-align:left;padding:10px;">
                    <span>{{problem.outputData}}</span>
                </h5>
            </div>

            <div class="problem_div">
                <h4 style="margin:0 auto;display: block;height:40px;background-color: rgba(243,244,245,0.7);">
                    <span style="float:left;margin:10px;">样例</span>
                </h4>
                <hr width="100%" style="margin:0 auto;"/>
                <h3 style="margin:0 auto;display: block;height:40px;">
                    <span style="float:left;margin:20px;">输入样例</span>
                </h3>
                <div class="problem_div"　style="margin:0 auto;margin-top:20px;margin-bottom:20px;padding:20px;text-align:left;">
                    <span style="color:#F5871f">{{problem.example.inputdata}}</span>
                </div>

                <h3 style="margin:0 auto;display: block;height:40px;">
                    <span style="float:left;margin:20px;">输出样例</span>
                </h3>
                <div class="problem_div"　style="margin:0 auto;margin-top:20px;margin-bottom:20px;padding:20px;text-align:left;">
                    <span style="color:#F5871f">{{problem.example.outputdata}}</span>
                </div>
            </div>
            <div class="problem_div">
                <codemirror
                :value="code"
                :options="options"
                ref="myEditor"
                @change="change">
                </codemirror>
            
            </div>
            <div class="language_select">
            <select name="mode" v-model="mode">
                <!--<option value="javascript">javascript</option>
                <option value="python">python</option>-->
                <option value="text/x-java">java</option>
                <option value="cmake">cmake</option>
                
            </select>
            </div>
        </div>
        <div class="buttom_div">
            <el-button type="info" style="float:right" @click="commit()"><i class="el-icon-edit"></i> 提交</el-button>
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
            problem: {
                id:'1',
                title:'A+B Problem',
                discription:'给你两个整数，求两个整数之和。',
                inputData:'一行，两个整数a,b。',
                outputData:'一行，a+b的值.',
                example:{
                    input:'5 7',
                    output:'12'
                }
            },
            mode: 'text/x-java',
            codes: {
                javascript: 'var component = {\n\tname: "vue-codemirror-lite",\n\tauthor: "Fangxw",\n\trepo: "https://github.com/cnu4/vue-codemirror-lite"\n}\n\n//Press Ctrl-Space to trigger hint',
                cmake:'int main(){}',
                'python':'def:',
                'text/x-java':'public class db_test {\n\t@Test\n\tpublic void test() throws Exception {}\n\t}'
            },
            commitCode:''
        }
    },
    components: {
    codemirror
  },
    computed: {
        code: function () {
        return this.codes[this.mode]
    },
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
        change: function (code) {
            // console.log('change', code)
            this.commitCode = code;
            // console.log(this.commitCode)

        },
        showDetailedProblem(){
            var id = this.$route.params.id;
            var url = this.Problem + '/showDetailedProblem';
            this.$http({
              method:'get',
              url: url,
              params: {
                problemId:id
              }
              }).then(res=>{
                  console.log(res.data)
                  if(res.data.success){
                    const message = JSON.parse(res.data.message);
                    console.log(message);
                    message.example = JSON.parse(message.example);
                    this.problem = message;
                    // this.discussionMsg = message;
                  }else{
                    this.$message.error('没有更多的数据！');
                  }
              }).catch(e => {
                this.$message.error('出现错误！');
              })
        },
        showSolutionByProblemAndLanguageType(mode){
            var languageType = "";
            if(mode == "javascript"){
                languageType = "js";
            }else if(mode == "cmake"){
                languageType = "cpp";
            }else if(mode == "python"){
                languageType = "py";
            }else if(mode == "text/x-java"){
                languageType = "java";
            }
            var id = this.$route.params.id;
            var url = this.Solution + '/showSolutionByProblemAndLanguageType';
            this.$http({
              method:'get',
              url: url,
              params: {
                problemId:id,
                username:JSON.parse(sessionStorage.getItem('user')).username,
                languageType:languageType
              }
              }).then(res=>{
                  console.log(res.data)
                  if(res.data.success){
                    const message = JSON.parse(res.data.message);
                    console.log(message);
                    this.codes[this.mode] = message.solution.code;
                    this.commitCode = message.solution.code;
                    
                    // this.discussionMsg = message;
                  }else{
                    this.$message.error(res.data.message);
                  }
              }).catch(e => {
                this.$message.error('出现错误！');
              })
        },
        commit(){
        const formdata = new FormData();
        formdata.append('code',this.commitCode);
        if(this.mode == "javascript"){
            formdata.append('languageType',"js");
        }else if(this.mode == "cmake"){
            formdata.append('languageType',"cpp");
        }
        else if(this.mode == "python"){
            formdata.append('languageType',"py");
        }else if(this.mode == "text/x-java"){
            formdata.append('languageType',"java");
        }
        
        formdata.append('problemId',this.$route.params.id);
        formdata.append('username',JSON.parse(sessionStorage.getItem('user')).username);

        // console.log(JSON.parse(sessionStorage.getItem('user')).username);
        // console.log("languageType: "+this.mode)
        // console.log(this.commitCode)


        var url = this.Solution + '/commit';
        this.$http({
        url: url,
        method: 'post',
        data: formdata,
        headers: { 'Content-Type': 'multipart/form-data' }
            }).then(res=>{
                console.log(res.data)
                if(res.data.success){
                const message = JSON.parse(res.data.message);
                console.log(message)
                this.$router.push("/solution/"+message.id);
                
                this.$message({
                message: '提交成功！',
                type: 'success'
                })
                // this.reload();
                }else{
                this.$message.error('提交错误！');
                }
            }).catch(e => {
            this.$message.error('提交出现错误！');
            })
        }
    },
    created() {
      this.showDetailedProblem();  
      this.showSolutionByProblemAndLanguageType(this.mode);
    },

    watch: {
        mode(newMode,oldMode){
            this.showSolutionByProblemAndLanguageType(newMode);
        }
    },
}
</script>

<style>
.language_select {
   margin:0 auto;
   width:90%;
   line-height:20px;
   margin-top:30px;
   text-align:left;
}
.problem {
   margin: 0 auto;
   top:65px;
   width: 70%;
   height:100%;
   position:relative;
  }
.problem_div {
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