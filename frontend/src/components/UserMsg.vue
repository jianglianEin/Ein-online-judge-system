<template>

    <div class="usermsg">
        <div class="user_data">
            <h4 style="margin:0 auto;display: block;height:40px;background-color: rgba(243,244,245,0.7);">
                    <span style="float:left;margin:10px;">用户数据</span>
                </h4>
            <hr width="100%" style="margin:0 auto;"/>
            <el-form label-position="right" style="width:80%;margin:30px auto;">
            <el-form-item label="学　号">
                <el-input v-model="userMsg.studentId" :disabled="true"></el-input>
              </el-form-item>
              <el-form-item label="用户名">
                <el-input v-model="userMsg.username" :disabled="true"></el-input>
              </el-form-item>
              <el-form-item label="专　业">
                <el-input v-model="userMsg.major" :disabled="isNotSelf"></el-input>
              </el-form-item>
              <el-form-item label="年　级">
                <el-input v-model="userMsg.grade" :disabled="isNotSelf"></el-input>
              </el-form-item>
              <el-form-item label="性　别">
                    <el-radio-group v-model="userMsg.sex">
                      <el-radio :label="false" :disabled="isNotSelf">男</el-radio>
                      <el-radio :label="true" :disabled="isNotSelf">女</el-radio>
                    </el-radio-group>
                </el-form-item>
              <el-form-item label="邮箱地址">
                <el-input v-model="userMsg.email" :disabled="isNotSelf"></el-input>
              </el-form-item>
              <el-form-item label="QQ">
                <el-input v-model="userMsg.QQ" :disabled="isNotSelf"></el-input>
              </el-form-item>
              <el-form-item label="个人简介">
                <el-input v-model="userMsg.discription" type="textarea" :rows="5" :disabled="isNotSelf"></el-input>
              </el-form-item>

              <el-button type="success" @click="changeMsg()" v-if="!isNotSelf">提交信息</el-button>
            </el-form>
        </div>
        <div class="pass_data">
                <h4 style="margin:0 auto;display: block;height:40px;background-color: rgba(243,244,245,0.7);">
                    <span style="float:left;margin:10px;">通过题目</span>
                </h4>
                <hr width="100%" style="margin:0 auto;"/>
                <h5 style="margin:0 auto;display: block;text-align:left;padding:10px;">
                    <el-input v-model="userMsg.discription" type="textarea" :rows="20" :disabled="true"></el-input>
                </h5>
            </div>
        <div class="user_icon">
            <img class="icon_img" :src=tempIcon></img>
            <el-button round @click="uploadImgVisible=true" v-if="!isNotSelf">更改头像</el-button>
            <el-button round @click="changePswVisible=true" v-if="!isNotSelf">更改密码</el-button>
        </div>

         <!--<<div class = "root" v-if="userMsg.rights=='root'">
        h4 style="margin:0 auto;display: block;height:40px;background-color: rgba(243,244,245,0.7);">
            <span style="float:left;margin:10px;">管理员管理功能</span>
        </h4>
        <hr width="100%" style="margin:0 auto;"/>
        <el-button type="primary" style="margin:10px;width:90%;" @click="$router.push({path:'/user/root/userManager'})">
          用户管理
        </el-button>
        <el-button type="primary" style="margin:10px;width:90%;" @click="$router.push({path:'/user/root/problemManager'})">
          题目操作
        </el-button>
        <el-button type="primary" style="margin:10px;width:90%;" @click="$router.push({path:'/user/root/competitionManager'})">
          比赛操作
        </el-button>
       

            <el-dropdown trigger="click" style="margin:10px">
            <el-button type="primary">
                用户管理<i class="el-icon-arrow-down el-icon--right"></i>
            </el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @click="$router.push({path:'/user/addUser'})">添加用户</el-dropdown-item>
              <el-dropdown-item @click="$router.push({path:'/user/setRoot'})">设置管理员</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>

          <el-dropdown trigger="click" style="margin:10px">
            <el-button type="primary">
                题目操作<i class="el-icon-arrow-down el-icon--right"></i>
            </el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @click="$router.push({path:'/problem/addProblem'})">新建题目</el-dropdown-item>
              <el-dropdown-item @click="$router.push({path:'/problem/changeProblemMsg'})">修改题目</el-dropdown-item>
              <el-dropdown-item @click="$router.push({path:'/problem/deleteProblem'})">删除题目</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>

          <el-dropdown trigger="click" style="margin:10px">
            <el-button type="primary">
                比赛操作<i class="el-icon-arrow-down el-icon--right"></i>
            </el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @click="$router.push({path:'/competition/addCompetition'})">创建比赛</el-dropdown-item>
              <el-dropdown-item @click="$router.push({path:'/competition/changeCompetitionMsg'})">修改比赛</el-dropdown-item>
              <el-dropdown-item @click="$router.push({path:'/competition/deleteCompetition'})">删除比赛</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
          
        </div>-->

      <el-dialog title="选择头像" :visible.sync="uploadImgVisible" :append-to-body="true" style="margin: 0 auto;width:800px">
        <el-upload　class="upload_from"
          drag
          name="usericon"
          v-bind:action="uploadurl"
          :limit="1"
          multiple
          :on-success="handleSuccess">
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过500kb</div>
        </el-upload>
      </el-dialog>

    <el-dialog title="更改密码" :visible.sync="changePswVisible" :append-to-body="true">
      <el-form :model="pswForm" status-icon :rules="rules" ref="changePswForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="原密码" prop="old_password">
          <el-input type="password" v-model.number="pswForm.old_password"></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="password">
          <el-input type="password" v-model="pswForm.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="checkPass">
          <el-input type="password" v-model="pswForm.checkPass" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('changePswForm')">提交</el-button>
          <el-button @click="resetForm('changePswForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    </div>
</template>

<script>

export default {
  inject:['reload'],
  data () {
      return {
        userMsg: {
            id:'',
            studentId:"",
            username : "",
            email : "",
            rights : "",
            passNum:"",
            icon : "../assets/image/no_user.jpg",
            discription : "",
            major:'',
            grade:'',
            QQ:'',
            password:'',
            sex:false,
            islogin : false,
        },

        isNotSelf:true,

        uploadurl:this.Upload + "/user_icon",

        uploadImgVisible:false,
        changePswVisible:false,
        
        pswForm: {
          old_password: '',
          password: '',
          checkPass: ''
        },
        rules: {
          old_password: [
            { validator: this.validatePass, trigger: 'blur' }
          ],
          password: [
            { validator: this.validatePass, trigger: 'blur' }
          ],
          checkPass: [
            { validator: this.validatePass2, trigger: 'blur' }
          ]
      },

      tempIcon:""
    }
  },
  components: {},
    methods: {
      changeMsg() {
        const formdata = new FormData();
        formdata.append('username',this.userMsg.username);
        formdata.append('studentId',this.userMsg.studentId);
        formdata.append('rights',this.userMsg.rights);
        formdata.append('major',this.userMsg.major);
        formdata.append('grade',this.userMsg.grade);
        formdata.append('sex',this.userMsg.sex);
        formdata.append('email',this.userMsg.email);  
        formdata.append('QQ',this.userMsg.QQ); 
        formdata.append('discription',this.userMsg.discription);
        formdata.append('icon',this.userMsg.icon);
        formdata.append('password',this.userMsg.password);


        var url = this.User + '/changeMsg';
        this.$http({
        url: url,
        method: 'post',
        data: formdata,
        headers: { 'Content-Type': 'multipart/form-data' }
          }).then(res=>{
              console.log(res.data)
              if(res.data.success){
                
                this.$message({
                message: '修改成功！',
                type: 'success'
              })
              this.reload();
              }else{
                this.$message.error('修改错误！');
              }
          }).catch(e => {
            this.$message.error('修改出现错误！');
          })
      },

      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            const formdata = new FormData();

            formdata.append('studentId',this.userMsg.studentId);
            formdata.append('old_password',this.pswForm.old_password)
            formdata.append('password',this.pswForm.password)

            var url = this.User + '/confirmPassword';
            this.$http({
            url: url,
            method: 'post',
            data: formdata,
            headers: { 'Content-Type': 'multipart/form-data' }
              }).then(res=>{
                  console.log(res.data)
                  if(res.data.success){
                    this.$message({
                    message: '修改成功！请点击左侧的提交信息，完成信息更新',
                    type: 'success'
                  })
                  this.userMsg.password = res.data.message;
                  this.changePswVisible = false;
                    
                  }else{
                  this.$message.error(res.data.message);
                  this.changePswVisible = false;
                  this.$refs[formName].resetFields();
                  }
                  
              }).catch(e => {
                this.$message.error(e);
              })
            }
           else {
            console.log('error submit!!');
            return false;
          }
        });
      },

    resetForm(formName) {
      this.$refs[formName].resetFields();
    },

    handleSuccess(res, file) {
            this.$message({
                type: 'success',
                message: '图片上传成功!请点击左侧的提交信息，完成信息更新',
                duration: 6000
            });
            console.log(file.response);
            if (file.response.success) {
                this.userMsg.icon = file.response.message; //将返回的文件储存路径赋值picture字段
                this.tempIcon ="http://localhost:8081/" +file.response.message;
            }
            this.uploadImgVisible = false;
        },

      validatePass(rule, value, callback){
        if (value === '') {
          callback(new Error('请输入密码'));
        } else {
          if (this.pswForm.checkPass !== '') {
            this.$refs.changePswForm.validateField('checkPass');
          }
          callback();
        }
      },

      validateOldPass(rule, value, callback){
        if (value === '') {
          callback(new Error('请输入密码'));
        }
          callback();
        },
   
      validatePass2(rule, value, callback){
        if (value === '') {
          callback(new Error('请再次输入密码'));
        } else if (value !== this.pswForm.password) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
      },
      showDetailedUser(){
        const formdata = new FormData();

      var id = this.$route.params.id;
      formdata.append('id',id);

      //刷新后重新赋值
  
      var url = this.User + '/showDetailedUser'
      this.$http({
        url:url,
        method:'post',
        data:formdata,
        headers: { 'Content-Type': 'multipart/form-data' }
      }).then(res => {
        console.log(res.data.success);
        if(res.data.success){
          
          const message = JSON.parse(res.data.message);
          

          this.userMsg.id = message.id;
          this.userMsg.studentId = message.studentId;
          this.userMsg.username = message.username;
          this.userMsg.email = message.email;
          this.userMsg.rights = message.rights;
          this.userMsg.passNum = message.passNum;
          this.userMsg.icon = message.icon;
          this.userMsg.discription = message.discription;
          this.userMsg.major = message.major;
          this.userMsg.grade = message.grade;
          this.userMsg.QQ = message.qQ;
          this.userMsg.sex = message.sex;
          this.userMsg.islogin = true;

          this.tempIcon = "http://localhost:8081/"+message.icon;

          console.log(res.data);
          console.log(res.status);

          
          console.log(this.userMsg);

            
          if(id==JSON.parse(sessionStorage.getItem('user')).id){
            this.isNotSelf = false;
          }
         
        }
      }).catch(e => {
          console.log("获取用户失败")
        })
      }
  },
  created:function () {
      console.log("Create start");
      this.showDetailedUser();
      console.log("Create end")
  },

  watch: {
    '$route' (to, from) {
      this.showDetailedUser();
    }
  }
}
</script>

<style>
    .upload_from {
      margin: 0 auto;
    }
    
    .icon_img {
        
        height: 200px;
        width:200px; 
        margin:0 auto;
        margin-top:50px;
        border-radius:50%;
        -webkit-border-radius:50%;
        -moz-border-radius:50%; 
    }
    .root {
        float:left;
        width:20%;
        margin:0 auto;
        margin-top:50px;
        margin-left:5%;
        border:solid;
        border-width:1px;
        border-color:gray;
        border-radius:10px;
    }
    .user_icon {
        line-height:60px;
        float:left;
        width:300px;
        height:270px;
        margin:0 auto;
        margin-left:5%;
    }
    .user_data {
        float:left;
        width:30%;

        margin:0 auto;

        border:solid;
        border-width:1px;
        border-color:gray;
        border-radius:10px;
    }
    .pass_data {
        float:left;
        width:30%;

        margin:0 auto;
        margin-left:5%;

        border:solid;
        border-width:1px;
        border-color:gray;
        border-radius:10px;
    }
    .usermsg {
   margin: 0 auto;
   top:65px;
   width: 70%;
   height:100%;
   position:relative;
  }
</style>
