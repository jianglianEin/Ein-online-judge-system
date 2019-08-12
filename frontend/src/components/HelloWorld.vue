<template>
<div class="main_page">
<el-menu :default-active="activeIndex"  mode="horizontal" @select="handleSelect" class="oj_nav">
  <el-menu-item index="1" @click="$router.push({path:'/'})"><i class="el-icon-menu"></i>首页</el-menu-item>
  <el-menu-item index="2" @click="$router.push({path:'/problems'})"><i class="el-icon-document"></i>题库</el-menu-item>
  <el-menu-item index="3" @click="$router.push({path:'/competition'})"><i class="el-icon-edit"></i>比赛</el-menu-item>
  <el-menu-item index="4" @click="$router.push({path:'/ranklist'})"><i class="el-icon-tickets"></i>排名</el-menu-item>
  <el-menu-item index="5" @click="$router.push({path:'/solution'})"><i class="el-icon-document"></i>解答</el-menu-item>
  <el-menu-item index="6" @click="$router.push({path:'/discussion'})"><i class="el-icon-phone-outline"></i>讨论</el-menu-item>
  <el-menu-item index="7" @click="$router.push({path:'/help'})"><i class="el-icon-question"></i>帮助</el-menu-item>

  <div class="log_buttons">
   <el-row>

    <el-button type="primary" @click="logDialogFormVisible = true" v-if="!userMsg.islogin">登　录</el-button>
    <el-button type="info" @click="registerDialogFormVisible = true" v-if="!userMsg.islogin">注　册</el-button>


    
    <img v-if="userMsg.islogin" :src=userMsg.icon @click="$router.push({path:'/user/'+userMsg.id})" style="height: 50px;width:50px; float:left;border-radius:50%;-webkit-border-radius:50%;-moz-border-radius:50%;margin-right:20px;">
    <el-button  index="4"  type="primary" @click="Logout" style="right:50px;width:100px;flaot:right" v-if="userMsg.islogin">注销</el-button >
    
  </el-row>
  </div>
</el-menu>


<el-dialog title="登录窗口" :visible.sync="logDialogFormVisible" :append-to-body="true" :before-close="clearData">
  <el-form :model="loginForm" :rules="loginRules" ref="loginForm">
    <el-form-item label="用户名" label-width="120px" prop="username">
      <el-input v-model="loginForm.username" autocomplete="off" placeholder="请输入帐号..."></el-input>
    </el-form-item>
    <el-form-item label="密码" label-width="120px" prop="password">
      <el-input v-model="loginForm.password" autocomplete="off" placeholder="请输入密码..." type="password"></el-input>
    </el-form-item>
  </el-form>
  <div slot="footer" class="dialog-footer">
    <el-button @click="resetForm('registerForm')">重 置</el-button>
    <el-button type="primary" @click="Login('loginForm')">确 定</el-button>
  </div>
</el-dialog>

<el-dialog title="注册窗口" :visible.sync="registerDialogFormVisible" :append-to-body="true" :before-close="clearData">
  <el-form :model="registerForm" :rules="registerRules" ref="registerForm">
    <el-form-item label="帐号" label-width="120px" prop="username">
      <el-input v-model="registerForm.username" autocomplete="off" placeholder="请输入帐号..."></el-input>
    </el-form-item>
    <el-form-item label="密码" label-width="120px" prop="password">
      <el-input v-model="registerForm.password" autocomplete="off" placeholder="请输入密码..." type="password"></el-input>
    </el-form-item>
    <el-form-item label="确认密码" label-width="120px" prop="confirmPassword">
      <el-input v-model="registerForm.confirmPassword" autocomplete="off" placeholder="请再次输入密码..." type="password"></el-input>
    </el-form-item>
    <el-form-item label="学号" label-width="120px" prop="studentId">
      <el-input v-model="registerForm.studentId" autocomplete="off" placeholder="请输入学号..."></el-input>
    </el-form-item>
  </el-form>
  <div slot="footer" class="dialog-footer">
    <el-button @click="resetForm('registerForm')">重 置</el-button>
    <el-button type="primary" @click="Register('registerForm')">确 定</el-button>
  </div>
</el-dialog>

<router-view></router-view>
  
</div>
</template>

<script>
  export default {
    data() {
      return {
        activeIndex: '1',

        logDialogFormVisible:false,
        registerDialogFormVisible:false,

        loginForm:{
        username:"",
        password:""
      },

      registerForm:{
        username:"",
        password:"",
        confirmPassword:"",
        studentId:""
      },

      registerRules:{
        username: [
            { required: true, message: '请输入用户名', trigger: 'blur' },
            { min: 10, max: 20, message: '长度在 10 到 20 个字符', trigger: 'blur' }
          ],
        password: [
            { required: true, message: '请输入密码', trigger: 'blur' },
            { min: 6,  message: '请密码长度大于 6', trigger: 'blur' }
          ],
        confirmPassword: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6,  message: '请密码长度大于 6', trigger: 'blur' },
          { validator: this.validatorConfirmPassword, trigger: 'blur' }
        ],
        studentId:[
          { required: true, message: '请输入学号', trigger: 'blur' },
          { min: 10, max: 20, message: '长度在 10 到 20 个字符', trigger: 'blur' }
        ]
      },

        loginRules: {
        username: [
            { required: true, message: '请输入用户名', trigger: 'blur' },
            { min: 5, max: 20, message: '长度在 5 到 20 个字符', trigger: 'blur' }
          ],
        password: [
            { required: true, message: '请输入密码', trigger: 'blur' },
            { min: 6,  message: '请密码长度大于 6', trigger: 'blur' }
          ]
        },

        

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
            sex:false,
            islogin : false,
        }
      };
    },
    methods: {
      Logout(){
      console.log("in Logout");
      var url = this.User + '/logout'
      this.$http({
        url:url,
        method:'post'
      }).then(res => {
        console.log(res.data);
        console.log(res.status);
        if(res.data.success){

          this.userMsg.id='',
          this.userMsg.studentId="",
          this.userMsg.username="",
          this.userMsg.email = "",
          this.userMsg.rights = "",
          this.userMsg.passNum="",
          this.userMsg.icon = "../assets/image/no_user.jpg",
          this.userMsg.discription = "",
          this.userMsg.major='',
          this.userMsg.grade='',
          this.userMsg.QQ='',
          this.userMsg.sex=false,
          this.userMsg.islogin = false,

          sessionStorage.removeItem('user');

          this.$router.push({path: '/'});
          console.log("Logout end");
        }
      }).catch(e => {
          console.log("get logout resp err")
        })

      
    },
      handleSelect(key, keyPath) {
        console.log(key, keyPath);
      },
      clearData(formName){
      this.loginForm.username = "";
      this.loginForm.password = "";
      this.registerForm.username = "";
      this.registerForm.password = "";
      this.registerForm.confirmPassword="";
      this.registerForm.studentId="";
      this.logDialogFormVisible = false;
      this.registerDialogFormVisible = false;
    },

    Login(formName){
      this.$refs[formName].validate((valid) =>{
        if(valid){
          
      console.log("Login start");
      // console.log(this.form);

      // const _this = this
      const formdata = new FormData();

      formdata.append('username',this.loginForm.username);
      formdata.append('password',this.loginForm.password);

      var url = this.User + '/login';
      this.$http({
          url: url,
          method: 'post',
          data: formdata,
          headers: { 'Content-Type': 'multipart/form-data' }
        }).then(res => {
          console.log(res.data);
          if (res.data.success) {
            console.log(res.status);
            const message = JSON.parse(res.data.message);
            console.log(message);
            // utils.updataUserMsg(this,message);

            sessionStorage.setItem('user', JSON.stringify(message));

            this.userMsg.id = message.id;
            this.userMsg.studentId = message.studentId;
            this.userMsg.username = message.username;
            this.userMsg.email = message.email;
            this.userMsg.rights = message.rights;
            this.userMsg.passNum = message.passNum;
            this.userMsg.icon ="http://172.19.147.135:8081/" + message.icon;
            this.userMsg.discription = message.discription;
            this.userMsg.major = message.major;
            this.userMsg.grade = message.grade;
            this.userMsg.QQ = message.qQ;
            this.userMsg.sex = message.sex;
            this.userMsg.islogin = true;

            //成功登录去掉登录form
            this.logDialogFormVisible = false;
            this.clearData('loginForm');

            this.$message({
            message: '提交成功！',
            type: 'success'
          });

          } else {
            this.$message.error('用户名或者密码输入错误！');
            console.log("there is no res.data");
          }
        }).catch(e => {
          console.log("get login resp err"+e);
        })
          
          console.log("Login end");
        } else {
          this.$message.error('请合法输入！');
        }
      })
      
    },

    Register(formName){
      this.$refs[formName].validate((valid) =>{
        if(valid){
          const formdata = new FormData();

          formdata.append('username',this.registerForm.username);
          formdata.append('password',this.registerForm.password);
          formdata.append('studentId',this.registerForm.studentId);
    
          var url = this.User + '/register';
          this.$http({
              url: url,
              method: 'post',
              data: formdata,
              headers: { 'Content-Type': 'multipart/form-data' }
            }).then(res =>{
              console.log(res.data);
              if(res.data.success) {

                this.$message({
                  message: '创建成功！请前往登录！',
                  type: 'success'
                });
                this.registerDialogFormVisible = false;
                this.logDialogFormVisible = false;
              }else{
                  this.$message.error(res.data.message);
              }
            }).catch(err =>{
              this.$message.error("创建新用户出现错误！");
            })
        }else{
          this.$message.error('请合法输入！');
        }
      })
    },

    validatorConfirmPassword(rule, value, callback){
        if (value === '') {
          callback(new Error('请再次输入密码'));
        } else if (value !== this.registerForm.password) {
          // console.log(value+"     "+this.registerForm.password)
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
      },
    },
  created:function () {
      console.log("Create start");

      //刷新后重新赋值
  
      var url = this.User + '/login'
      this.$http({
        url:url,
        method:'get'
      }).then(res => {
        console.log(res.data);
        if(res.data.success){
          const message = JSON.parse(res.data.message);
          

           sessionStorage.setItem('user', JSON.stringify(message));

            this.userMsg.id = message.id;
            this.userMsg.studentId = message.studentId;
            this.userMsg.username = message.username;
            this.userMsg.email = message.email;
            this.userMsg.rights = message.rights;
            this.userMsg.passNum = message.passNum;
            this.userMsg.icon ="http://172.19.147.135:8081/" + message.icon;
            this.userMsg.discription = message.discription;
            this.userMsg.major = message.major;
            this.userMsg.grade = message.grade;
            this.userMsg.QQ = message.qQ;
            this.userMsg.sex = message.sex;
            this.userMsg.islogin = true;

          
          console.log(sessionStorage.getItem('user'));
          
          console.log(res.data);
          console.log(res.status);
        }else{
            console.log("there is no user");
            this.$message('请登录,用户名：jianglian,密码：1145141919810');
            this.$router.push({path: '/'});
          }
        
      }).catch(e => {
          console.log("get islogin resp err")
        })
      // console.log("in GetUserMsg");
      console.log("Create end")
  }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

.oj_nav {
  position:absolute;
  left:0px;
  width:100%;
}
.el-menu-item {
  padding-left:15px;
  padding-right:15px;
}
.log_buttons {
  position: absolute;
  right:20px;
}
.main_page {
  width:100%;
  height:100%;
}
</style>
