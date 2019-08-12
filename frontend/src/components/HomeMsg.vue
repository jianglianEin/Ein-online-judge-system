<template>
   <div class="homemsg"> 
      <div class="homemsg_left">
         <div class="notice">
            <h4 style="float:left;margin-left:10px;">公告</h4>
            <hr width="100%"/>
            <el-table
              :data="notice_data"
              style="width: 100%">
              <el-table-column
                prop="title"
                label="标题">
                <template slot-scope="scope"><a style="color: #4183c4;text-decoration:none;cursor: pointer;" @click="$router.push({path:'/discussion/'+scope.row.id})">{{ scope.row.title }}</a></template>
              </el-table-column>
              <el-table-column
                prop="startDate"
                label="时间"
                width="180">
              </el-table-column>
            </el-table>
         </div>
         <div class="rank">
            <h4 style="float:left;margin-left:10px;">排名</h4>
            <hr width="100%"/>
            <el-table
              :data="rank_data"
              style="width: 100%">
              <el-table-column
                 type="index"
                 width="50">
               </el-table-column>
              <el-table-column
                prop="username"
                label="用户名">
                <template slot-scope="scope"><a style="color: #4183c4;text-decoration:none;cursor: pointer;" @click="$router.push({path:'/user/'+scope.row.id})">{{ scope.row.username }}</a></template>
              </el-table-column>
              <el-table-column
                prop="discription"
                label="个性签名"
                >
              </el-table-column>
              <el-table-column
                prop="passNum"
                label="通过数"
                width="90">
              </el-table-column>
            </el-table>
         </div>
      </div>
      <div class="homemsg_right">
         <div class="search">
            <h4 style="float:left;margin-left:10px;">OJが大好き!</h4>
            <hr width="100%"/>
            <img src="../assets/logo.png" class="search_icon">
            <el-select v-model="value8" filterable placeholder="请输入题号：" style="width:90%;" >
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
         </div>
         <div class="curr_competition">
            <h4 style="float:left;margin-left:10px;">近期比赛</h4>
            <hr width="100%"/>
            <el-table
              :data="competition_data"
              style="width: 100%">
              <el-table-column
                prop="title"
                label="比赛名称">
                <template slot-scope="scope"><a style="color: #4183c4;text-decoration:none;cursor: pointer;" @click="$router.push({path:'/competition/'+scope.row.id})">{{ scope.row.title }}</a></template>
              </el-table-column>
              <el-table-column
                prop="startDate"
                label="开始时间">
              </el-table-column>
            </el-table>
         </div>
         <!--<div class="links">
         </div>-->
      </div>
   </div>
</template>

<script>
export default {
      data() {
        return {
         competition_data:[],
          notice_data: [],
          rank_data:[],
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
        value8: ''
        }
      },
   created:function () {
      console.log("Create start");

      //刷新后重新赋值
  
      var competition_url = this.Competition + '/get_resent_competition'
      this.$http({
        url:competition_url,
        method:'get'
      }).then(res => {
        console.log(res.data);
        if(res.data.success){
          const message = JSON.parse(res.data.message);
          console.log(message)
          
          message.forEach( v=> {
             this.competition_data.push(v);
          })
        }else{

          }
        
      }).catch(e => {
          
        })

      var notic_url = this.Notic + '/get_resent_notic'
      this.$http({
        url:notic_url,
        method:'get'
      }).then(res => {
        console.log(res.data);
        if(res.data.success){
          const message = JSON.parse(res.data.message);
          console.log(message)
          
          message.forEach( v=> {
             this.notice_data.push(v);
          })
        }else{

          }
        
      }).catch(e => {
          
        })

      var user_url = this.User + '/get_resent_rank'
      this.$http({
        url:user_url,
        method:'get'
      }).then(res => {
        console.log(res.data);
        if(res.data.success){
          const message = JSON.parse(res.data.message);
          console.log(message)
          
          message.forEach( v=> {
             this.rank_data.push(v);
          })
        }else{

          }
        
      }).catch(e => {
          
        })

      
      console.log("Create end")
  }
}

</script>

<style>
.search_icon {
    top:0px;
    right:0px;
    width:60%;
    height:100%;
    cursor:pointer;
}
.curr_competition {
   color: #333;
   text-align: center;
   margin:0 auto;
   width:90%;
   line-height:20px;
   margin-top:30px;
   padding:20px;
   border:solid;
   border-width:1px;
   border-color:gray;
   border-radius:10px;
}
.search {
   color: #333;
   text-align: center;
   margin:0 auto;
   width:90%;
   line-height:20px;
   padding:20px;
   border:solid;
   border-width:1px;
   border-color:gray;
   border-radius:10px;
}
.notice {
   color: #333;
   text-align: center;
   margin:0 auto;
   width:90%;
   line-height:20px;
   padding:20px;
   border:solid;
   border-width:1px;
   border-color:gray;
   border-radius:10px;
}
.rank{
   color: #333;
   text-align: center;
   margin:0 auto;
   margin-top:30px;
   width:90%;
   line-height:20px;
   padding:20px;
   border:solid;
   border-width:1px;
   border-color:gray;
   border-radius:10px;
}
.homemsg {
   margin: 0 auto;
   top:65px;
   width: 70%;
   height:100%;
   position:relative;
  }
.homemsg_left{
   float:left;
   width: 60%;
   height:90%;

   position:relative;
   margin:10px;
  }
  .homemsg_right{
   float:right;
   width: 30%;
   height:90%;
   position:relative;
   margin:10px;
   
  }
</style>
