<template>
    <div class="discussionMsg">
        <div class="top_div">
                <h1 style="text-align: left;">{{discussionMsg.title}}</h1>
                <el-button type="danger" style="float:right" @click="RemoveDiscussion()" v-if="isSelf"><i class="el-icon-edit"></i> 删除该贴</el-button>
                <img :src=tempIcon+discussionMsg.lz.icon @click="$router.push({path:'/user/'+discussionMsg.lz.id})"　style="height: 50px;width:50px; float:left;border-radius:50%;-webkit-border-radius:50%;-moz-border-radius:50%;margin-right:20px;"></img>
                <h5 style="text-align: left;"><a style="color: #4183c4;text-decoration:none;cursor: pointer; " @click="$router.push({path:'/user/'+discussionMsg.lz.id})">{{discussionMsg.lz.username}}</a> 于{{discussionMsg.startDate}}发表</h5>
                
        </div>
        <div class="main_div">
            <div class="discussion_div">
                <h5 style="text-align: left;">{{discussionMsg.discription}}</h5>
            </div>
            <hr style="width:100%;">
            <div class="main_div" v-for="reply in replys" style="text-align: left;">
                <img :src=tempIcon+reply.user.icon @click="$router.push({path:'/user/'+reply.user.id})"　style="height: 50px;width:50px; float:left;border-radius:50%;-webkit-border-radius:50%;-moz-border-radius:50%;margin-right:20px;"></img>
               <h5 style="text-align: left;"><a style="color: #4183c4;text-decoration:none;cursor: pointer; " @click="$router.push({path:'/user/'+reply.user.id})">{{reply.user.username}}</a> 于{{reply.postDate}}发表</h5>
               <h5 style="text-align: left;">{{reply.msg}}</h5>
            </div>
        </div>
        <div class="buttom_div" style="text-align: left;">
            <el-form label-position="top" label-width="80px" :model="replyForm" :rules="rules"　ref="replyForm">
              <el-form-item label="回复" prop="reply">
                <el-input v-model="replyForm.reply" type="textarea" rows="10"></el-input>
              </el-form-item>
              <el-button type="info" style="float:left" @click="reply('replyForm')"><i class="el-icon-edit"></i> 提交</el-button>
            </el-form>
        </div>
    </div>
</template>

<script>
export default {
    inject:['reload'],
    data() {
        return {
            isSelf:false,
            replyForm: {
            reply: '',
            },
            rules: {
            reply: [
              { required: true, message: '请输入内容', trigger: 'blur' },
              { min: 10, max: 255, message: '长度在 10 到 255 个字符', trigger: 'blur' }
            ]
            } ,
        tempIcon:"http://localhost:8080/",
        discussionMsg: {
            id:"",
            title:"",
            discription:"",
            startDate:"",
            lz:{
                icon:""
            } 
        },
        replys:[]
        }
    },

    methods: {
        RemoveDiscussion(){
            const formdata = new FormData();

            formdata.append('bbsId',this.$route.params.id);

            var url = this.Notic + '/delete';
            this.$http({
            url: url,
            method: 'post',
            data: formdata,
            headers: { 'Content-Type': 'multipart/form-data' }
              }).then(res=>{
                  console.log(res.data)
                  if(res.data.success){
                    this.$message({
                    message: '删除成功！',
                    type: 'success'
                  })
                this.$router.push('/discussion');
                    
                  }else{
                  this.$message.error(res.data.message);
                  }
                  
              }).catch(e => {
                this.$message.error(e);
              })
        },
        getReplys(){
            var urlForReply = this.Notic + '/getReplys';

              this.$http({
              method:'get',
              url: urlForReply,
              params: {
                bbsId:this.$route.params.id
              }
              }).then(res=>{
                  console.log(res.data)
                  if(res.data.success){
                    const message = JSON.parse(res.data.message);
                    console.log(message);
                    this.replys = message;
                  }else{
                    this.$message.error('没有更多的数据！');
                  }
              }).catch(e => {
                this.$message.error('出现错误！');
              })
        },

        showDetailedNotic(){
            var id = this.$route.params.id;
            var url = this.Notic + '/showDetailedNotic';
            this.$http({
              method:'get',
              url: url,
              params: {
                bbsId:id
              }
              }).then(res=>{
                  console.log(res.data)
                  if(res.data.success){
                    const message = JSON.parse(res.data.message);
                    console.log(message);
                    this.discussionMsg = message;

                
                if(JSON.parse(sessionStorage.getItem('user')).id===this.discussionMsg.lz.id){
                //     console.log(JSON.parse(sessionStorage.getItem('user')).id);
                // console.log(this.discussionMsg.lz.id);
                    this.isSelf = true;
                }
                  }else{
                    this.$message.error('没有更多的数据！');
                  }
              }).catch(e => {
                this.$message.error('出现错误！');
              })
        },
        reply(formName){
            this.$refs[formName].validate((valid) => {
            if (valid) {
                const formdata = new FormData();

                formdata.append('username',JSON.parse(sessionStorage.getItem('user')).username);
                formdata.append('bbsId',this.discussionMsg.id);
                formdata.append('msg',this.replyForm.reply);
                var date = new Date();
                var year = date.getFullYear();
                var month = date.getMonth()+1;
                var day = date.getDate();
                var hour = date.getHours();
                var minute = date.getMinutes();
                var second = date.getSeconds();
                var postDate = year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second
                formdata.append('postDate',postDate);
                
  
                var url = this.Notic + '/reply'
                this.$http({
                url:url,
                method:'post',
                data:formdata,
                headers: { 'Content-Type': 'multipart/form-data' }
                }).then(res => {
                console.log(res.data.success);
                if(res.data.success){
                    this.$message({
                    message: '回复成功',
                    type: 'success'
                  })
                  this.reload();
                const message = JSON.parse(res.data.message);
                console.log(message);
                
                    }
                }).catch(e => {
                    console.log("")
                })
            }else{
                this.$message.error("输入错误，请改正．．");
                }
            });
        }
    },
    created() {
        this.showDetailedNotic();
        this.getReplys();
    },
}
</script>

<style>
.discussionMsg {
   margin: 0 auto;
   top:65px;
   width: 70%;
   height:100%;
   position:relative;
  }
  .discussion_div {
    color: #333;
   text-align: center;
   margin:0 auto;
   width:100%;
   padding:30px;
   line-height:20px;
   margin-top:80px;
   border:solid;
   border-width:1px;
   border-color:gray;
   border-radius:10px;
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