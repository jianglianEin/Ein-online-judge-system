import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/HelloWorld'
import HomeMsg from '../components/HomeMsg.vue'
import Problems from '../components/Problems.vue'
import Competition from '../components/Competition.vue'
import Ranklist from '../components/RankList.vue'
import Discussion from '../components/Discussion.vue'
import Problem from '../components/Problem.vue'
import UserMsg from '../components/UserMsg.vue'
import CompetitionMsg from '../components/CompetitionMsg.vue'
import Solution from '../components/Solution.vue'
import DiscussionMsg from '../components/DiscussionMsg.vue'
import AddDiscussion from '../components/AddDiscussion.vue'
import SoultionMsg from '../components/SolutinoMsg.vue'
import SoultionOfCompetitionMsg from '../components/SolutinoMsgOfCompetitionMsg.vue'
import CompetitionProblemMsg from '../components/Competition_Problem.vue'
import Help from '../components/Help.vue'
 
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      component: Home,
      
      children: [
        {
          path:'',
          component:HomeMsg
        },
        {
          path:'/problems',
          component:Problems,
          meta: {  requiresAuth: true    
          }  
        },
        {
          path:'/competition',
          component:Competition,
          meta: {  requiresAuth: true    
          }  
        },
        {
          path:'/ranklist',
          component:Ranklist,
          meta: {  requiresAuth: true    
          }  
        },
        {
          path:'/solution',
          component:Solution,
          meta: {  requiresAuth: true    
          }
        },
        {
          path:'/discussion',
          component:Discussion,
          meta: {  requiresAuth: true    
          }  
        },
        {
          path:'/discussion/addDiscussion',
          component:AddDiscussion,
          meta: {  requiresAuth: true    
          }  
        },
        {
          path:'/discussion/:id',
          component:DiscussionMsg,
          meta: {  requiresAuth: true    
          }  
        },
        {
          path:'/problems/:id',
          component:Problem,
          meta: {  requiresAuth: true    
          }  
        },
        {
          path:'/user/:id',
          component:UserMsg,
          meta: {  requiresAuth: true    
          }  
        },
        {
          path:'/competition/:id',
          component:CompetitionMsg,
          meta: {  requiresAuth: true    
          }  
        },
        {
          path:'/competition_problems/:competitionId/:problemId',
          component:CompetitionProblemMsg,
          meta: {  requiresAuth: true    
          }  
        },
        {
          path:'/solution/:id',
          component:SoultionMsg,
          meta: {  requiresAuth: true    
          }  
        },
        {
          path:'/solutionOfCompetition/:id',
          component:SoultionOfCompetitionMsg,
          meta: {  requiresAuth: true    
          }  
        },
        {
          path:'/help',
          component:Help
        }
        
      ]
    }
  ]
})
