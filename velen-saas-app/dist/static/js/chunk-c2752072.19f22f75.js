(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-c2752072"],{2017:function(t,n,o){"use strict";var s=o("b12d"),e=o.n(s);e.a},"4aad":function(t,n,o){},"9ed6":function(t,n,o){"use strict";o.r(n);var s=function(){var t=this,n=t.$createElement,o=t._self._c||n;return o("div",{staticClass:"login-container"},[o("el-form",{ref:"loginForm",staticClass:"login-form",attrs:{model:t.loginForm,rules:t.loginRules,"auto-complete":"on","label-position":"left"}},[o("div",{staticClass:"title-container"},[o("h3",{staticClass:"title"},[t._v("Login Form")])]),t._v(" "),o("el-form-item",{attrs:{prop:"username"}},[o("span",{staticClass:"svg-container"},[o("svg-icon",{attrs:{"icon-class":"user"}})],1),t._v(" "),o("el-input",{ref:"username",attrs:{placeholder:"account",name:"account",type:"text",tabindex:"1","auto-complete":"on"},model:{value:t.loginForm.account,callback:function(n){t.$set(t.loginForm,"account",n)},expression:"loginForm.account"}})],1),t._v(" "),o("el-form-item",{attrs:{prop:"password"}},[o("span",{staticClass:"svg-container"},[o("svg-icon",{attrs:{"icon-class":"password"}})],1),t._v(" "),o("el-input",{key:t.passwordType,ref:"password",attrs:{type:t.passwordType,placeholder:"Password",name:"password",tabindex:"2","auto-complete":"on"},nativeOn:{keyup:function(n){return!n.type.indexOf("key")&&t._k(n.keyCode,"enter",13,n.key,"Enter")?null:t.handleLogin(n)}},model:{value:t.loginForm.password,callback:function(n){t.$set(t.loginForm,"password",n)},expression:"loginForm.password"}}),t._v(" "),o("span",{staticClass:"show-pwd",on:{click:t.showPwd}},[o("svg-icon",{attrs:{"icon-class":"password"===t.passwordType?"eye":"eye-open"}})],1)],1),t._v(" "),o("el-button",{staticStyle:{width:"100%","margin-bottom":"30px"},attrs:{loading:t.loading,type:"primary"},nativeOn:{click:function(n){return n.preventDefault(),t.handleLogin(n)}}},[t._v("Login")]),t._v(" "),o("div",{staticClass:"tips"},[o("span",{staticStyle:{"margin-right":"20px"}},[t._v("username: admin")]),t._v(" "),o("span",[t._v(" password: any")])])],1)],1)},e=[],a=o("db72"),i=(o("61f7"),o("2f62")),r={name:"Login",data:function(){return{loginForm:{account:"admin",password:"111111",grantType:"password"},loginRules:{},loading:!1,passwordType:"password",redirect:void 0}},watch:{$route:{handler:function(t){this.redirect=t.query&&t.query.redirect},immediate:!0}},methods:Object(a["a"])({},Object(i["b"])({login:"handleLogin"}),{showPwd:function(){var t=this;"password"===this.passwordType?this.passwordType="":this.passwordType="password",this.$nextTick((function(){t.$refs.password.focus()}))},handleLogin:function(){var t=this;this.$refs.loginForm.validate((function(n){if(!n)return console.log("error submit!!"),!1;t.loading=!0,t.login(t.loginForm).then((function(){t.$router.push({path:t.redirect||"/"}),t.loading=!1})).catch((function(){t.loading=!1}))}))}})},c=r,l=(o("2017"),o("df31"),o("2877")),d=Object(l["a"])(c,s,e,!1,null,"0c8a7537",null);n["default"]=d.exports},b12d:function(t,n,o){},df31:function(t,n,o){"use strict";var s=o("4aad"),e=o.n(s);e.a}}]);