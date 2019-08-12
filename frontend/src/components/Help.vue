<template>
    <div class="help_nav">
        		<img src="../assets/image/help_bg.jpg" class="bg">
		<div class="nav">
			
			<div class="title_nav">
				PAT，ACM，蓝桥杯，只要你做SMOJ，这些通通都归你～
			</div>
			<div class="search_div">
				<input type="text" class="search" id="" value="" placeholder="搜素你喜欢的题目"/>
			</div>
			<div class="bottom_nav">
				<div class="author">
					图片：Ein
				</div>
			</div>
			
		</div>

        <div class="images">
            <div class="card">
                <img src="../assets/image/images_1.jpg" class="image">
                <p class="img_msg">题库</p>
            </div>
             <div class="card">
                <img src="../assets/image/images_2.jpg" class="image">
                <p class="img_msg">比赛</p>
            </div>
             <div class="card">
                <img src="../assets/image/images_3.jpg" class="image">
                <p class="img_msg">排名</p>
            </div>
             <div class="card">
                <img src="../assets/image/images_4.jpg" class="image">
                <p class="img_msg">论坛</p>
            </div>
        </div>

        <div class="block">
          <el-carousel height="450px">
            <el-carousel-item v-for="item in blocks" :key="item">
              <img :src=item alt="" class="block_img">
            </el-carousel-item>
          </el-carousel>
        </div>

        <div class="message">
            <div class="left_message">
                <p class="lanqiao">
                    蓝桥杯
                </p>
                <p class="lanqiao_msg">
                    为推动软件开发技术的发展，促进软件专业技术人才培养，向软件行业输送具有创新能力和实践能力的高端人才，提升高校毕业生的就业竞争力，全面推动行业发展及人才培养进程，工业和信息化部人才交流中心特举办“全国软件专业人才设计与创业大赛”，本赛包括个人赛和团队赛两个比赛项目。
                </p>
            </div>
            <div class="right_message">
                <img src="../assets/image/right_image.jpg" class="right_message_image">
            </div>
        </div>

        <div class="video">
            <video class="introduce" src="../assets/video/introduce.mp4" controls="controls">

            </video>
        </div>

        <div class="bottom">
            <p class="bottom_msg">
                目前我们的团队正在广纳贤才，如果您和我们一样拥有极客精神，<br>
                渴望与我们共同成长，欢迎阁下加入我们的行列中来。<br>
                Copyright © <a href="https://github.com/jianglianEin" style="text-decoration:none;cursor: pointer;">Ein</a>
            </p>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            blocks:[
                require("../assets/image/block1.png"),
                require("../assets/image/block2.jpg")
            ]
        }
    },
}
</script>

<style scoped>
.bottom_msg{

    font-size: 14px;
    line-height: 30px;
    color: #666666;
}
.bottom{
    overflow: hidden;
    width: 40%;
    height: 100px;
    background-color: #F2F2F2;
    margin: 0 auto;
    margin-top: 60px;
    position: relative;
    border-radius: 10%;
}
.introduce{
    width: 60%;
    position: relative;
}
.lanqiao_msg{
    padding: 45px;
    font-family: 'Open Sans', Helvetica, Arial, sans-serif;
    font-size: 17px;
    font-weight: 300;
    color: #666666;
}
.lanqiao{
    margin-top: 100px;
    font-size: 35px;
    color: cadetblue
}
.right_message_image{
    width: 100%;
    height: 100%;
    transform: scale(0.95,0.95);
}
.left_message{
    float: left;
    width: 50%;
    height: 500px;
}
.right_message{
float: right;
width: 50%;
height: 500px;
}
.message{
    width: 60%;
    height: 500px;
    background-color: #F2F2F2;;
    margin: 0 auto;
    margin-top: 60px;
    margin-bottom: 60px;
}
.block_img{
    width: 100%;
    border-radius: 3%;

}
.block{
    width: 50%;
    margin: 0 auto;
    margin-top: 80px;
    opacity: 0.8;
}
.img_msg{
    font-size: 20px;
    margin-top: 5px;
}
.images{
    margin: 0 auto;
    position: relative;
    width: 60%;
    top: 70px;
    display: flex;
    justify-content: space-between;

}
.image{
    cursor: pointer;
    opacity: 0.7;
    filter:alpha(opacity=70);
    border-radius: 5%;
}
.image:hover{
    transform: scale(1.03,1.03);
    opacity: 0.9;
    filter:alpha(opacity=90);
    transition: all .5s;
}
            .bg{
                top:61px; 
                left: 0px;
				height: 480px;
				width: 100%;
				position: absolute;
				z-index:0;
                opacity: 0.7;
			}
			.nav{
                top:61px;
				height: 480px;
				width: 100%;
				/* background-color: #00FFFF; */
				position: relative;
				
			}
				.title_nav{
					
					width: 100%;
					height: 54px;
					line-height: 54px;
					color: #fff;
					font-family: "sy-sans";
					font-size: 44px;
					font-weight: 200;
					text-align: center;
					text-shadow: 0 10px 30px rgba(0,0,0,.3), 0 2px 10px rgba(0,0,0,.2);
                    position: absolute;
                    top: 200px;
                    
				}
				.search{
					margin: 0 auto;
					width: 536px;
					height: 40px;
					padding-left: 5px;
					padding-right: 5px;
                    position: absolute;
                    top: 300px;
                    left: 0px;
                    right: 0px;
				}
				.search_div{
					width: 100%;
					margin: 0 auto;
					text-align: center;
				}

				.bottom_nav{
					width: 100%;
					position: absolute;
					bottom: 20px;
					
					/* background-color: #00FFFF; */
				}
				.author{
					padding-left: 86px;
					padding-right: 86px;
					color: #fff;
					font-size: 14px;
					text-align: right;
				}
.el-carousel__item h3 {
    color: #475669;
    font-size: 14px;
    opacity: 0.75;
    line-height: 150px;
    margin: 0;
  }

  .el-carousel__item:nth-child(2n) {
     background-color: #99a9bf;
  }
  
  .el-carousel__item:nth-child(2n+1) {
     background-color: #d3dce6;
  }

  
  
</style>
