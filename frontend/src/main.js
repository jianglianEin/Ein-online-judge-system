// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import Elements from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css';
// import ace from 'ace-builds'
// import componentsInstall from './components/install'
import axios from 'axios'

import userinfo from './assets/js/userinfo'

Vue.prototype.User = '/user';
Vue.prototype.Upload = '/upload'
Vue.prototype.Competition = '/competition'
Vue.prototype.Notic = '/notic'
Vue.prototype.Problem = '/problem'
Vue.prototype.Solution = '/solution'

Vue.prototype.userinfo = userinfo;
// Vue.use(componentsInstall)
// Vue.use(ace)
Vue.prototype.$http = axios;
Vue.config.productionTip = false;
Vue.use(Elements);
/* eslint-disable no-new */
var VueCodeMirror = require('vue-codemirror-lite')

Vue.use(VueCodeMirror)
router.beforeEach((to,from,next)=>{
  if(to.path==='/') next();
  else{
    if(to.meta.requiresAuth&&!sessionStorage.getItem("user")){
      console.log("in router")
      next({path:'/'})
    }else{
      console.log("in router have user")
      next();
    }
  }
})

new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
