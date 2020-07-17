(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-e96cd5c6"],{3957:function(e,t,n){"use strict";var l=n("4cce"),i=n.n(l);i.a},"4cce":function(e,t,n){},d625:function(e,t,n){"use strict";n.r(t);var l=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"meta-rule-container"},[e._m(0),e._v(" "),n("el-card",{staticStyle:{"margin-bottom":"20px"}},[n("p",{staticClass:"meta-card-header"},[e._v("\n      事件与事件属性的入库设置\n    ")]),e._v(" "),n("p",{staticStyle:{margin:"0","font-size":"14px"}},[e._v("\n      上报事件属性的类型与「事件属性」已有事件的类型不一致时，系统将按规则尝试自动转换，如无法完成则视为异常数据不能入库。\n    ")]),e._v(" "),n("div",{staticClass:"meta-card-form"},[n("el-checkbox",{on:{change:e.setVerify},model:{value:e.eventCheck,callback:function(t){e.eventCheck=t},expression:"eventCheck"}},[e._v("强校验模式")]),e._v(" "),n("el-form",{attrs:{"label-position":"top"}},[n("el-form-item",{attrs:{label:"解析格式:"}},[n("el-button",{attrs:{type:"primary"},on:{click:function(t){return e.addTableRow("event")}}},[e._v("添加")]),e._v(" "),n("el-table",{attrs:{data:e.eventTable,"max-height":"200"}},[n("el-table-column",{attrs:{prop:"name",label:"解析器类型"}}),e._v(" "),n("el-table-column",{attrs:{label:"规则"},scopedSlots:e._u([{key:"default",fn:function(t){return[t.row.update?n("el-input",{directives:[{name:"focus",rawName:"v-focus"}],on:{blur:function(n){return e.updEventRule(t.row)}},model:{value:t.row.rule,callback:function(n){e.$set(t.row,"rule",n)},expression:"scope.row.rule"}}):n("span",[e._v(e._s(t.row.rule)+"\n                ")])]}}])}),e._v(" "),n("el-table-column",{attrs:{label:"操作"},scopedSlots:e._u([{key:"default",fn:function(t){return[n("el-button",{attrs:{type:"text"},on:{click:function(n){return e.updateRow(t.row)}}},[e._v("修改")]),e._v(" "),n("el-popconfirm",{attrs:{confirmButtonText:"确定",cancelButtonText:"取消",icon:"el-icon-info",iconColor:"red",title:"删除该解析器?"},on:{onConfirm:function(n){return e.delRow(t.row,"event")}}},[n("el-button",{staticStyle:{color:"red"},attrs:{slot:"reference",type:"text"},slot:"reference"},[e._v("删除")])],1)]}}])})],1)],1),e._v(" "),n("el-row",e._l(e.events,(function(t){return n("el-col",{staticStyle:{"margin-right":"50px"},attrs:{span:4}},[n("span",[e._v(e._s(t.label))]),e._v(" "),n("p",[e._v("Key规则")]),e._v(" "),n("el-link",{attrs:{type:"primary"},on:{click:function(n){return e.showParserTable(t.name,t.keyRule,"event")}}},[e._v("\n              "+e._s(t.keyRule.length<=0?"未设置":"显示匹配规则")+"\n            ")]),e._v(" "),n("p",[e._v("入库规则")]),e._v(" "),n("el-link",{attrs:{type:"primary"},on:{click:function(n){return e.showCheckTable(t.name,t.rule,"event")}}},[e._v("\n              "+e._s(t.rule.length<=0?"未设置 任意格式均可上报入库":"显示入库规则")+"\n            ")])],1)})),1)],1)],1)]),e._v(" "),n("el-card",[n("p",{staticClass:"meta-card-header"},[e._v("\n      用户属性入库设置\n    ")]),e._v(" "),n("p",{staticStyle:{margin:"0","font-size":"14px"}},[e._v("\n      上报用户属性的类型与「用户属性」已有事件的类型不一致时，系统将按规则自动转换，如无法自动转换则视为异常数据不能入库。\n    ")]),e._v(" "),n("div",{staticClass:"meta-card-form"},[n("el-checkbox",{model:{value:e.userCheck,callback:function(t){e.userCheck=t},expression:"userCheck"}},[e._v("强校验模式")]),e._v(" "),n("el-form",{attrs:{"label-position":"top"}},[n("el-form-item",{attrs:{label:"解析格式:"}},[n("el-button",{attrs:{type:"primary"},on:{click:function(t){return e.addTableRow("profile")}}},[e._v("添加")]),e._v(" "),n("el-table",{attrs:{data:e.profileTable,"max-height":"200"}},[n("el-table-column",{attrs:{prop:"name",label:"解析器类型"}}),e._v(" "),n("el-table-column",{attrs:{prop:"rule",label:"规则"},scopedSlots:e._u([{key:"default",fn:function(t){return[t.row.update?n("el-input",{directives:[{name:"focus",rawName:"v-focus"}],on:{blur:function(n){return e.updProfileRule(t.row)}},model:{value:t.row.rule,callback:function(n){e.$set(t.row,"rule",n)},expression:"scope.row.rule"}}):n("span",[e._v(e._s(t.row.rule)+"\n                ")])]}}])}),e._v(" "),n("el-table-column",{attrs:{label:"操作"},scopedSlots:e._u([{key:"default",fn:function(t){return[n("el-button",{attrs:{type:"text"},on:{click:function(n){return e.updateRow(t.row)}}},[e._v("修改")]),e._v(" "),n("el-popconfirm",{attrs:{confirmButtonText:"确定",cancelButtonText:"取消",icon:"el-icon-info",iconColor:"red",title:"删除该解析器?"},on:{onConfirm:function(n){return e.delRow(t.row,"profile")}}},[n("el-button",{staticStyle:{color:"red"},attrs:{slot:"reference",type:"text"},slot:"reference"},[e._v("删除")])],1)]}}])})],1)],1),e._v(" "),n("el-row",e._l(e.profiles,(function(t){return n("el-col",{staticStyle:{"margin-right":"50px"},attrs:{span:4}},[n("span",[e._v(e._s(t.label))]),e._v(" "),n("p",[e._v("Key规则")]),e._v(" "),n("el-link",{attrs:{type:"primary"},on:{click:function(n){return e.showParserTable(t.name,t.keyRule,"profile")}}},[e._v("\n              "+e._s(t.keyRule.length<=0?"未设置":"显示匹配规则")+"\n            ")]),e._v(" "),n("p",[e._v("入库规则")]),e._v(" "),n("el-link",{attrs:{type:"primary"},on:{click:function(n){return e.showCheckTable(t.name,t.rule,"profile")}}},[e._v("\n              "+e._s(t.rule.length<=0?"未设置 任意格式均可上报入库":"显示入库规则")+"\n            ")])],1)})),1)],1)],1)]),e._v(" "),n("parser-table",{attrs:{visible:e.parserVisible,type:e.type,data:e.data,keyName:e.keyName},on:{"update:visible":function(t){e.parserVisible=t},"update:data":function(t){e.data=t},"find-event-data":e.findEvenKeyRule,"find-profile-data":e.findProfileKeyRule,"close-parser-table":e.handleParserClose}}),e._v(" "),n("check-table",{attrs:{visible:e.checkVisible,type:e.type,data:e.data,keyName:e.keyName},on:{"update:visible":function(t){e.checkVisible=t},"update:data":function(t){e.data=t},"find-event-data":e.findEventCheckRule,"find-profile-data":e.findProfileCheckRule,"close-check-table":e.handleCheckClose}}),e._v(" "),n("add-dialog",{attrs:{visible:e.addVisible,type:e.type,"parse-type":e.inputParseType},on:{"update:visible":function(t){e.addVisible=t},"close-add-rule":e.handleAddClose,"add-event-rule":e.insertEventRule,"add-profile-rule":e.insertProfileRule}})],1)},i=[function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"meta-rule-title"},[n("p",[e._v("入库校验规则设置")])])}],r=(n("28a5"),n("7f7f"),function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("el-dialog",{attrs:{visible:e.visible,"before-close":e.handleClose,width:"20%","append-to-body":""},on:{"update:visible":function(t){e.visible=t},open:e.openDialog}},[n("template",{slot:"title"},[n("p",[e._v("添加解释器")])]),e._v(" "),n("el-form",{attrs:{model:e.form}},[n("el-form-item",{attrs:{label:"解释器类型"}},[n("el-select",{attrs:{"value-key":"key"},model:{value:e.ruleType,callback:function(t){e.ruleType=t},expression:"ruleType"}},e._l(e.parseType,(function(e){return n("el-option",{key:e.key,attrs:{label:e.name,value:e}})})),1)],1),e._v(" "),n("el-form-item",{attrs:{label:"规则"}},[n("el-input",{attrs:{placeholder:"请输入规则"},model:{value:e.form.rule,callback:function(t){e.$set(e.form,"rule",t)},expression:"form.rule"}})],1)],1),e._v(" "),n("div",{attrs:{slot:"footer"},slot:"footer"},[n("el-button",{on:{click:e.handleClose}},[e._v("取消")]),e._v(" "),n("el-button",{attrs:{type:"primary"},on:{click:e.addRule}},[e._v("确定")])],1)],2)}),o=[],a={props:{visible:{required:!0,type:Boolean},type:{required:!0,type:String},parseType:{required:!0,type:Array}},data:function(){return{form:{name:"",ruleKey:-1,rule:""},ruleType:this.parseType[0]}},methods:{openDialog:function(){this.form.name="",this.form.ruleKey=-1,this.form.rule="",this.ruleType={}},handleClose:function(){this.$emit("close-add-rule")},addRule:function(){this.form.name=this.ruleType.name,this.form.ruleKey=this.ruleType.key,"event"==this.type?this.$emit("add-event-rule",this.form):"profile"==this.type&&this.$emit("add-profile-rule",this.form),this.$emit("close-add-rule")}}},u=a,s=n("2877"),c=Object(s["a"])(u,r,o,!1,null,"324afeda",null),d=c.exports,f=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("el-dialog",{attrs:{visible:e.visible,"before-close":e.handleClose},on:{"update:visible":function(t){e.visible=t}}},[n("template",{slot:"title"},[n("p",[e._v("Key规则设置")])]),e._v(" "),n("el-button",{attrs:{type:"primary"},on:{click:e.addTableRow}},[e._v("添加")]),e._v(" "),n("el-table",{attrs:{data:e.data,"max-height":"200"}},[n("el-table-column",{attrs:{prop:"name",label:"匹配规则名"}}),e._v(" "),n("el-table-column",{attrs:{label:"规则"},scopedSlots:e._u([{key:"default",fn:function(t){return[t.row.update?n("el-input",{directives:[{name:"focus",rawName:"v-focus"}],on:{blur:function(n){return e.updRule(t.row)}},model:{value:t.row.rule,callback:function(n){e.$set(t.row,"rule",n)},expression:"scope.row.rule"}}):n("span",[e._v(e._s(t.row.rule))])]}}])}),e._v(" "),n("el-table-column",{attrs:{label:"操作"},scopedSlots:e._u([{key:"default",fn:function(t){return[n("el-button",{attrs:{type:"text"},on:{click:function(n){return e.updateRow(t.row)}}},[e._v("修改")]),e._v(" "),n("el-popconfirm",{attrs:{confirmButtonText:"确定",cancelButtonText:"取消",icon:"el-icon-info",iconColor:"red",title:"删除该解析器?"},on:{onConfirm:function(n){return e.delRow(t.row)}}},[n("el-button",{staticStyle:{color:"red"},attrs:{slot:"reference",type:"text"},slot:"reference"},[e._v("删除")])],1)]}}])})],1),e._v(" "),n("add-dialog",{attrs:{visible:e.addVisible,type:e.type,"parse-type":e.fieldRuleType},on:{"update:visible":function(t){e.addVisible=t},"close-add-rule":e.handleAddClose,"add-event-rule":e.insertEventKeyRule,"add-profile-rule":e.insertProfileKeyRule}})],2)},p=[],h=n("b775");function v(){return Object(h["a"])({url:"/dashboard/rule",method:"get"})}function b(e){return Object(h["a"])({url:"/dashboard/rule",method:"post",data:e})}function m(e){return Object(h["a"])({url:"/dashboard/rule",method:"put",data:e})}function y(e){return Object(h["a"])({url:"/dashboard/rule/".concat(e),method:"delete"})}function k(){return Object(h["a"])({url:"/dashboard/eventKeyRule",method:"get"})}function _(e){return Object(h["a"])({url:"/dashboard/eventKeyRule",method:"post",data:e})}function R(e){return Object(h["a"])({url:"/dashboard/eventKeyRule",method:"put",data:e})}function w(e){return Object(h["a"])({url:"/dashboard/eventKeyRule/".concat(e),method:"delete"})}function C(){return Object(h["a"])({url:"/dashboard/eventCheckRule",method:"get"})}function T(e){return Object(h["a"])({url:"/dashboard/eventCheckRule",method:"post",data:e})}function g(e){return Object(h["a"])({url:"/dashboard/eventCheckRule",method:"put",data:e})}function x(e){return Object(h["a"])({url:"/dashboard/eventCheckRule/".concat(e),method:"delete"})}function j(){return Object(h["a"])({url:"/dashboard/rule/inputParseType",method:"get"})}function O(){return Object(h["a"])({url:"/dashboard/rule/fieldRuleType",method:"get"})}function $(){return Object(h["a"])({url:"/dashboard/profileRule",method:"get"})}function P(e){return Object(h["a"])({url:"/dashboard/profileRule",method:"post",data:e})}function V(e){return Object(h["a"])({url:"/dashboard/profileRule",method:"put",data:e})}function K(e){return Object(h["a"])({url:"/dashboard/profileRule/".concat(e),method:"delete"})}function S(){return Object(h["a"])({url:"/dashboard/profileKeyRule",method:"get"})}function E(e){return Object(h["a"])({url:"/dashboard/profileKeyRule",method:"post",data:e})}function N(e){return Object(h["a"])({url:"/dashboard/profileKeyRule",method:"put",data:e})}function A(e){return Object(h["a"])({url:"/dashboard/profileKeyRule/".concat(e),method:"delete"})}function B(){return Object(h["a"])({url:"/dashboard/profileCheckRule",method:"get"})}function D(e){return Object(h["a"])({url:"/dashboard/profileCheckRule",method:"post",data:e})}function q(e){return Object(h["a"])({url:"/dashboard/profileCheckRule",method:"put",data:e})}function I(e){return Object(h["a"])({url:"/dashboard/profileCheckRule/".concat(e),method:"delete"})}var F={components:{AddDialog:d},props:{type:{required:!0,type:String},data:{type:Array},visible:{required:!0,type:Boolean},keyName:{type:String}},directives:{focus:{inserted:function(e){e.querySelector("input").focus()}}},data:function(){return{addVisible:!1,fieldRuleType:[]}},created:function(){this.findFieldRuleType()},methods:{findFieldRuleType:function(){var e=this;O().then((function(t){e.fieldRuleType=t}))},handleClose:function(){this.$emit("close-parser-table")},addTableRow:function(e){this.addVisible=!0},updateRow:function(e){e.name.toLowerCase().indexOf("json")<=-1&&this.$set(e,"update",!0)},updRule:function(e){var t=this;"event"===e.type?R(e).then((function(e){t.$emit("find-event-data")})):N(e).then((function(e){t.$emit("find-profile-data")})),this.$set(e,"update",!1)},delRow:function(e){var t=this;"event"==e.type?w(e.id).then((function(e){t.$emit("find-event-data")})):A(e.id).then((function(e){t.$emit("find-profile-data")}))},handleAddClose:function(){this.addVisible=!1},insertEventKeyRule:function(e){var t=this;e.key=this.keyName,e.type="event",_(e).then((function(e){t.$emit("find-event-data")}))},insertProfileKeyRule:function(e){var t=this;e.key=this.keyName,e.type="profile",E(e).then((function(e){t.$emit("find-profile-data")}))}}},L=F,z=Object(s["a"])(L,f,p,!1,null,"3db0a83a",null),J=z.exports,G=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("el-dialog",{attrs:{visible:e.visible,"before-close":e.handleClose},on:{"update:visible":function(t){e.visible=t}}},[n("template",{slot:"title"},[n("p",[e._v("入库规则设置")])]),e._v(" "),n("el-button",{attrs:{type:"primary"},on:{click:e.addTableRow}},[e._v("添加")]),e._v(" "),n("el-table",{attrs:{data:e.data,"max-height":"200"}},[n("el-table-column",{attrs:{prop:"name",label:"匹配规则名"}}),e._v(" "),n("el-table-column",{attrs:{label:"规则"},scopedSlots:e._u([{key:"default",fn:function(t){return[t.row.update?n("el-input",{directives:[{name:"focus",rawName:"v-focus"}],on:{blur:function(n){return e.updRule(t.row)}},model:{value:t.row.rule,callback:function(n){e.$set(t.row,"rule",n)},expression:"scope.row.rule"}}):n("span",[e._v(e._s(t.row.rule))])]}}])}),e._v(" "),n("el-table-column",{attrs:{label:"操作"},scopedSlots:e._u([{key:"default",fn:function(t){return[n("el-button",{attrs:{type:"text"},on:{click:function(n){return e.updateRow(t.row)}}},[e._v("修改")]),e._v(" "),n("el-popconfirm",{attrs:{confirmButtonText:"确定",cancelButtonText:"取消",icon:"el-icon-info",iconColor:"red",title:"删除该解析器?"},on:{onConfirm:function(n){return e.delRow(t.row)}}},[n("el-button",{staticStyle:{color:"red"},attrs:{slot:"reference",type:"text"},slot:"reference"},[e._v("删除")])],1)]}}])})],1),e._v(" "),n("add-dialog",{attrs:{visible:e.addVisible,type:e.type,"parse-type":e.fieldRuleType},on:{"update:visible":function(t){e.addVisible=t},"close-add-rule":e.handleAddClose,"add-event-rule":e.insertEventKeyRule,"add-profile-rule":e.insertProfileKeyRule}})],2)},H=[],M={components:{AddDialog:d},props:{type:{required:!0,type:String},data:{type:Array},visible:{required:!0,type:Boolean},keyName:{type:String}},directives:{focus:{inserted:function(e){e.querySelector("input").focus()}}},data:function(){return{addVisible:!1,fieldRuleType:[]}},created:function(){this.findFieldRuleType()},methods:{findFieldRuleType:function(){var e=this;O().then((function(t){e.fieldRuleType=t}))},handleClose:function(){this.$emit("close-check-table")},addTableRow:function(e){this.addVisible=!0},updateRow:function(e){e.name.toLowerCase().indexOf("json")<=-1&&this.$set(e,"update",!0)},updRule:function(e){var t=this;"event"===e.type?g(e).then((function(e){t.$emit("find-event-data")})):q(e).then((function(e){t.$emit("find-profile-data")})),this.$set(e,"update",!1)},delRow:function(e){var t=this;"event"==e.type?x(e.id).then((function(e){t.$emit("find-event-data")})):I(e.id).then((function(e){t.$emit("find-profile-data")}))},handleAddClose:function(){this.addVisible=!1},insertEventKeyRule:function(e){var t=this;e.key=this.keyName,e.type="event",T(e).then((function(e){t.$emit("find-event-data")}))},insertProfileKeyRule:function(e){var t=this;e.key=this.keyName,e.type="profile",D(e).then((function(e){t.$emit("find-profile-data")}))}}},Q=M,U=Object(s["a"])(Q,G,H,!1,null,"a14a395e",null),W=U.exports;function X(){return Object(h["a"])({url:"/dashboard/appVerify",method:"get"})}function Y(e){return Object(h["a"])({url:"/dashboard/appVerify",method:"put",data:e})}var Z={components:{AddDialog:d,ParserTable:J,CheckTable:W},directives:{focus:{inserted:function(e){e.querySelector("input").focus()}}},data:function(){return{eventCheck:!1,userCheck:!1,addVisible:!1,parserVisible:!1,checkVisible:!1,type:"",keyName:"",eventTable:[],profileTable:[],profileRule:"",data:[],events:[{label:"接收用户「设备ID」",name:"device_id",keyRule:[],rule:[]},{label:"接收用户「用户ID」",name:"distinct_id",keyRule:[],rule:[]},{label:"接收事件「发生时间」",name:"time",keyRule:[],rule:[]},{label:"接收事件「名称」",name:"event",keyRule:[],rule:[]},{label:"接收事件「项目ID」",name:"project",keyRule:[],rule:[]}],profiles:[{label:"接收用户「设备ID」",name:"device_id",keyRule:[],rule:[]},{label:"接收用户「用户ID」",name:"distinct_id",keyRule:[],rule:[]},{label:"接收用户「发生时间」",name:"time",keyRule:[],rule:[]},{label:"接收用户「项目ID」",name:"project",keyRule:[],rule:[]},{label:"接收用户「type」",name:"profile_user",keyRule:[],rule:[]}],inputParseType:[]}},created:function(){this.findEventRule(),this.findProfileRule(),this.findEvenKeyRule(),this.findProfileKeyRule(),this.findEventCheckRule(),this.findProfileCheckRule(),this.findInputParseType(),this.findAppVerify()},methods:{findEventRule:function(){var e=this;v().then((function(t){e.eventTable=t}))},findProfileRule:function(){var e=this;$().then((function(t){e.profileTable=t}))},findInputParseType:function(){var e=this;j().then((function(t){e.inputParseType=t}))},findAppVerify:function(){var e=this;X().then((function(t){e.eventCheck=t.verify}))},findEvenKeyRule:function(){var e=this;k().then((function(t){var n=function(n){e.events[n].keyRule.splice(0),t.some((function(t){e.events[n].name===t.key&&e.events[n].keyRule.push(t)}))};for(var l in e.events)n(l)}))},findProfileKeyRule:function(){var e=this;S().then((function(t){var n=function(n){e.profiles[n].keyRule.splice(0),t.some((function(t){e.profiles[n].name===t.key&&e.profiles[n].keyRule.push(t)}))};for(var l in e.profiles)n(l)}))},findEventCheckRule:function(){var e=this;C().then((function(t){var n=function(n){e.events[n].rule.splice(0),t.some((function(t){e.events[n].name===t.key&&e.events[n].rule.push(t)}))};for(var l in e.events)n(l)}))},findProfileCheckRule:function(){var e=this;B().then((function(t){var n=function(n){e.profiles[n].rule.splice(0),t.some((function(t){e.profiles[n].name===t.key&&e.profiles[n].rule.push(t)}))};for(var l in e.profiles)n(l)}))},handleAddClose:function(){this.addVisible=!1},handleParserClose:function(){this.parserVisible=!1},handleCheckClose:function(){this.checkVisible=!1},addTableRow:function(e){this.addVisible=!0,this.type=e},showParserTable:function(e,t,n){this.keyName=e,this.data=t,this.parserVisible=!0,this.type=n},showCheckTable:function(e,t,n){this.keyName=e,this.data=t,this.checkVisible=!0,this.type=n},insertEventRule:function(e){b(e).then((function(e){})),this.findEventRule()},insertProfileRule:function(e){this.profileTable.push(e),P(e).then((function(e){})),this.findProfileRule()},updateRow:function(e){e.name.toLowerCase().indexOf("json")<=-1&&this.$set(e,"update",!0)},updEventRule:function(e){this.$set(e,"update",!1),m(e).then((function(e){})),this.findEventRule()},updProfileRule:function(e){0==e.rule.split(" ").join("").length&&(e.rule=""),this.$set(e,"update",!1),V(e).then((function(e){})),this.findProfileRule()},delRow:function(e,t){"event"===t?(y(e.id).then((function(e){})),this.findEventRule()):"profile"===t&&(K(e.id).then((function(e){})),this.findProfileRule())},setVerify:function(e){var t=this,n={verify:e};Y(n).then((function(e){console.log(e),t.eventCheck=e.verify})).catch((function(e){t.eventCheck=!t.eventCheck}))}}},ee=Z,te=(n("3957"),Object(s["a"])(ee,l,i,!1,null,null,null));t["default"]=te.exports}}]);