<template>
    <div class="ranklist">
        <div class="top_div">
            <div class="top_right">
                <el-select v-model="value8" filterable placeholder="请输入用户名字：" style="width:50%;float:right" >
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
              :data="rank_data"
              style="width: 100%">
              <el-table-column
                 type="index"
                 width="50">
               </el-table-column>
              <el-table-column
                prop="username"
                label="用户名">
                <template slot-scope="scope"><a herf="" style="color: #4183c4;text-decoration:none;cursor: pointer; " @click="$router.push({path:'/user/'+scope.row.id})">{{ scope.row.username }}</a></template>
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
        <div class="buttom_div">
            <el-pagination
              @current-change="PageChange"
              :page-size="rankNum"
              background
              layout="prev, pager, next"
              :total="totalRank">
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
        rank_data:[],
          pageNum : 1,
          rankNum : 50,
          totalRank: 1000,
        }
    },
    methods: {
      PageChange(pageNum){
        console.log(pageNum)
        this.pageNum = pageNum;
        this.getRankUser(pageNum,this.rankNum);
      },
      getRankUser(pageNum,rankNum){
        var url = this.User + '/getRankUser';
        this.$http({
          method:'get',
          url: url,
          params: {
            pageNum:pageNum,
            rankNum:rankNum
          }
          }).then(res=>{
              console.log(res.data)
              if(res.data.success){
                const message = JSON.parse(res.data.message);
                console.log(message);
                this.rank_data = message;
              }else{
                this.$message.error('没有更多的数据！');
              }
          }).catch(e => {
            this.$message.error('出现错误！');
          })
      },
      getCount(){
        var url = this.User + '/getCount';
        this.$http({
          method:'get',
          url: url,
          }).then(res=>{
              console.log(res.data)
              if(res.data.success){
                const message = JSON.parse(res.data.message);
                console.log(message);
                this.totalRank = message;
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
      this.getRankUser(this.pageNum,this.rankNum);
    },
}
</script>

<style>
.ranklist {
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