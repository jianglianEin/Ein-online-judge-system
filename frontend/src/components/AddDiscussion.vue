<template>
    <div class="addDiscussion">
        <div class="main_div">
           <el-form label-position="top" label-width="80px" :model="discussionForm" :rules="rules"　ref="discussionForm">
              <el-form-item label="标题"　prop="title">
                <el-input v-model="discussionForm.title"></el-input>
              </el-form-item>
              <el-form-item label="内容" prop="discription">
                <el-input v-model="discussionForm.discription" type="textarea" rows="10"></el-input>
              </el-form-item>
              <el-button type="info" style="float:left" @click="addDiscussion('discussionForm')"><i class="el-icon-edit"></i> 提交</el-button>
            </el-form>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            discussionForm: {
            title: '',
            discription: ''
            },
            rules: {
            title: [
              { required: true, message: '请输入题目', trigger: 'blur' },
              { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
            ],
            discription: [
              { required: true, message: '请输入内容', trigger: 'blur' },
              { min: 10, max: 255, message: '长度在 10 到 255 个字符', trigger: 'blur' }
            ]
            }   
        }
    },
    methods: {
        addDiscussion(formName){
            this.$refs[formName].validate((valid) => {
            if (valid) {
                const formdata = new FormData();

                formdata.append('Lz_name',JSON.parse(sessionStorage.getItem('user')).username);
                formdata.append('discription',this.discussionForm.discription);
                var date = new Date();
                var year = date.getFullYear();
                var month = date.getMonth()+1;
                var day = date.getDate();
                var hour = date.getHours();
                var minute = date.getMinutes();
                var second = date.getSeconds();
                var startDate = year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second
                formdata.append('startDate',startDate);
                formdata.append('title',this.discussionForm.title);
  
                var url = this.Notic + '/add'
                this.$http({
                url:url,
                method:'post',
                data:formdata,
                headers: { 'Content-Type': 'multipart/form-data' }
                }).then(res => {
                console.log(res.data.success);
                if(res.data.success){
                    this.$message({
                    message: '创建成功',
                    type: 'success'
                  })
                  this.$router.push('/discussion');
                    }
                }).catch(e => {
                    console.log("")
                })
            }else{
                this.$message.error("输入错误，请改正．．");
                }
            });
        }
    }
}
</script>

<style>
.addDiscussion {
   margin: 0 auto;
   top:65px;
   width: 70%;
   height:100%;
   position:relative;
   text-align:left;
  }
.main_div {
   width: 80%;
   position:relative;
   margin:10px auto;
   margin-top:0px;
}
</style>