(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-e96cd5c6"],{3957:function(e,t,n){"use strict";var l=n("4cce"),i=n.n(l);i.a},"4cce":function(e,t,n){},d625:function(e,t,n){"use strict";n.r(t);var l=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"meta-rule-container"},[e._m(0),e._v(" "),n("el-card",{staticStyle:{"margin-bottom":"20px"}},[n("p",{staticClass:"meta-card-header"},[e._v("\n      事件与事件属性的入库设置\n    ")]),e._v(" "),n("p",{staticStyle:{margin:"0","font-size":"14px"}},[e._v("\n      上报事件属性的类型与「事件属性」已有事件的类型不一致时，系统将按规则尝试自动转换，如无法完成则视为异常数据不能入库。\n    ")]),e._v(" "),n("div",{staticClass:"meta-card-form"},[n("el-checkbox",{on:{change:e.setVerify},model:{value:e.eventCheck,callback:function(t){e.eventCheck=t},expression:"eventCheck"}},[e._v("强校验模式")]),e._v(" "),n("el-form",{attrs:{"label-position":"top"}},[n("el-form-item",{attrs:{label:"解析格式:"}},[n("el-button",{attrs:{type:"primary"},on:{click:function(t){return e.addTableRow("event")}}},[e._v("添加")]),e._v(" "),n("el-table",{attrs:{data:e.eventTable,"max-height":"200"}},[n("el-table-column",{attrs:{prop:"name",label:"解析器类型"}}),e._v(" "),n("el-table-column",{attrs:{label:"规则"},scopedSlots:e._u([{key:"default",fn:function(t){return[t.row.update?n("el-input",{directives:[{name:"focus",rawName:"v-focus"}],on:{blur:function(n){return e.updEventRule(t.row)}},model:{value:t.row.rule,callback:function(n){e.$set(t.row,"rule",n)},expression:"scope.row.rule"}}):n("span",[e._v(e._s(t.row.rule)+"\n                ")])]}}])}),e._v(" "),n("el-table-column",{attrs:{label:"操作"},scopedSlots:e._u([{key:"default",fn:function(t){return[n("el-button",{attrs:{type:"text"},on:{click:function(n){return e.updateRow(t.row)}}},[e._v("修改")]),e._v(" "),n("el-popconfirm",{attrs:{confirmButtonText:"确定",cancelButtonText:"取消",icon:"el-icon-info",iconColor:"red",title:"删除该解析器?"},on:{onConfirm:function(n){return e.delRow(t.row,"event")}}},[n("el-button",{staticStyle:{color:"red"},attrs:{slot:"reference",type:"text"},slot:"reference"},[e._v("删除")])],1)]}}])})],1)],1),e._v(" "),n("el-row",e._l(e.events,(function(t){return n("el-col",{staticStyle:{"margin-right":"50px","margin-top":"20px"},attrs:{span:4}},[n("span",[e._v(e._s(t.name))]),e._v(" "),n("p",{staticClass:"rule-comment"},[e._v(e._s(t.comment))]),e._v(" "),n("p",[e._v("Key规则")]),e._v(" "),t.keyRule?n("el-link",{on:{click:function(n){return e.showUpdateDialog(t.value,"eventKeyRule")}}},[e._v("\n              "+e._s(t.keyRule?t.keyRule.rule:"设置KeyRule")+"\n            ")]):n("el-link",{attrs:{type:"primary"},on:{click:function(n){return e.showParserTable(t.value,"eventKeyRule")}}},[e._v("\n              "+e._s(t.keyRule?t.keyRule.rule:"设置KeyRule")+"\n            ")]),e._v(" "),n("p",[e._v("入库规则")]),e._v(" "),n("el-link",[e._v("\n              "+e._s(t.keyRule?t.keyRule.valueRule:"未设置,任意字段可入库")+"\n            ")])],1)})),1)],1)],1)]),e._v(" "),n("el-card",[n("p",{staticClass:"meta-card-header"},[e._v("\n      用户属性入库设置\n    ")]),e._v(" "),n("p",{staticStyle:{margin:"0","font-size":"14px"}},[e._v("\n      上报用户属性的类型与「用户属性」已有事件的类型不一致时，系统将按规则自动转换，如无法自动转换则视为异常数据不能入库。\n    ")]),e._v(" "),n("div",{staticClass:"meta-card-form"},[n("el-checkbox",{model:{value:e.userCheck,callback:function(t){e.userCheck=t},expression:"userCheck"}},[e._v("强校验模式")]),e._v(" "),n("el-form",{attrs:{"label-position":"top"}},[n("el-form-item",{attrs:{label:"解析格式:"}},[n("el-button",{attrs:{type:"primary"},on:{click:function(t){return e.addTableRow("profile")}}},[e._v("添加")]),e._v(" "),n("el-table",{attrs:{data:e.profileTable,"max-height":"200"}},[n("el-table-column",{attrs:{prop:"name",label:"解析器类型"}}),e._v(" "),n("el-table-column",{attrs:{prop:"rule",label:"规则"},scopedSlots:e._u([{key:"default",fn:function(t){return[t.row.update?n("el-input",{directives:[{name:"focus",rawName:"v-focus"}],on:{blur:function(n){return e.updProfileRule(t.row)}},model:{value:t.row.rule,callback:function(n){e.$set(t.row,"rule",n)},expression:"scope.row.rule"}}):n("span",[e._v(e._s(t.row.rule)+"\n                ")])]}}])}),e._v(" "),n("el-table-column",{attrs:{label:"操作"},scopedSlots:e._u([{key:"default",fn:function(t){return[n("el-button",{attrs:{type:"text"},on:{click:function(n){return e.updateRow(t.row)}}},[e._v("修改")]),e._v(" "),n("el-popconfirm",{attrs:{confirmButtonText:"确定",cancelButtonText:"取消",icon:"el-icon-info",iconColor:"red",title:"删除该解析器?"},on:{onConfirm:function(n){return e.delRow(t.row,"profile")}}},[n("el-button",{staticStyle:{color:"red"},attrs:{slot:"reference",type:"text"},slot:"reference"},[e._v("删除")])],1)]}}])})],1)],1),e._v(" "),n("el-row",e._l(e.profiles,(function(t){return n("el-col",{staticStyle:{"margin-right":"50px","margin-top":"20px"},attrs:{span:4}},[n("span",[e._v(e._s(t.label))]),e._v(" "),n("p",[e._v("Key规则")]),e._v(" "),n("el-link",{attrs:{type:"primary"},on:{click:function(n){return e.showParserTable(t.name,"profile")}}}),e._v(" "),n("p",[e._v("入库规则")]),e._v(" "),n("el-link",{attrs:{type:"primary"}})],1)})),1)],1)],1)]),e._v(" "),n("add-dialog",{attrs:{visible:e.addVisible,type:e.type,"parse-type":e.showType,"field-type":e.fieldType},on:{"update:visible":function(t){e.addVisible=t},"close-add-rule":e.handleAddClose,"add-event-rule":e.insertEventRule,"add-profile-rule":e.insertProfileRule,"add-key-rule":e.insertEventKeyRule}}),e._v(" "),n("update-dialog",{attrs:{visible:e.updateVisible,type:e.type,"parse-type":e.showType},on:{"update:visible":function(t){e.updateVisible=t},"close-update-rule":e.handleUpdateClose,"update-key-rule":e.handleUpdateEventKeyRule}})],1)},i=[function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"meta-rule-title"},[n("p",[e._v("入库校验规则设置")])])}],r=(n("28a5"),n("7f7f"),function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("el-dialog",{attrs:{visible:e.visible,"before-close":e.handleClose,width:"20%","append-to-body":""},on:{"update:visible":function(t){e.visible=t},open:e.openDialog}},[n("template",{slot:"title"},[n("p",[e._v("添加解释器")])]),e._v(" "),n("el-form",{attrs:{model:e.form}},[n("el-form-item",{attrs:{label:"解释器类型"}},[n("el-select",{attrs:{"value-key":"key"},model:{value:e.ruleType,callback:function(t){e.ruleType=t},expression:"ruleType"}},e._l(e.parseType,(function(e){return n("el-option",{key:e.key,attrs:{label:e.name,value:e}})})),1)],1),e._v(" "),n("el-form-item",{attrs:{label:"规则"}},[n("el-input",{attrs:{placeholder:"请输入规则"},model:{value:e.form.rule,callback:function(t){e.$set(e.form,"rule",t)},expression:"form.rule"}})],1),e._v(" "),n("el-form-item",{attrs:{label:"入库规则设置"}},[n("el-input",{attrs:{placeholder:"输入入库规则",disabled:"parseRule"===e.fieldType},model:{value:e.form.valueRule,callback:function(t){e.$set(e.form,"valueRule",t)},expression:"form.valueRule"}})],1)],1),e._v(" "),n("div",{attrs:{slot:"footer"},slot:"footer"},[n("el-button",{on:{click:e.handleClose}},[e._v("取消")]),e._v(" "),n("el-button",{attrs:{type:"primary"},on:{click:e.addRule}},[e._v("确定")])],1)],2)}),o=[],a={props:{visible:{required:!0,type:Boolean},type:{required:!0,type:String},parseType:{required:!0,type:Array},fieldType:{required:!0,type:String}},data:function(){return{form:{name:"",ruleKey:-1,rule:"",valueRule:""},ruleType:this.parseType[0]}},methods:{openDialog:function(){this.form.name="",this.form.ruleKey=-1,this.form.rule="",this.form.valueRule="",this.ruleType={}},handleClose:function(){this.$emit("close-add-rule")},addRule:function(){switch(this.form.name=this.ruleType.name,this.form.ruleKey=this.ruleType.key,this.type){case"event":this.$emit("add-event-rule",this.form);break;case"profile":this.$emit("add-profile-rule",this.form);break;case"eventKeyRule":this.$emit("add-key-rule",this.form);break}this.$emit("close-add-rule")}}},u=a,s=n("2877"),d=Object(s["a"])(u,r,o,!1,null,"4f95f0b3",null),c=d.exports,f=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("el-dialog",{attrs:{visible:e.visible,"before-close":e.handleClose,width:"20%","append-to-body":""},on:{"update:visible":function(t){e.visible=t},open:e.openDialog}},[n("template",{slot:"title"},[n("p",[e._v("修改key规则")])]),e._v(" "),n("el-form",{attrs:{model:e.form}},[n("el-form-item",{attrs:{label:"解释器类型"}},[n("el-select",{attrs:{"value-key":"key"},model:{value:e.ruleType,callback:function(t){e.ruleType=t},expression:"ruleType"}},e._l(e.parseType,(function(e){return n("el-option",{key:e.key,attrs:{label:e.name,value:e}})})),1)],1),e._v(" "),n("el-form-item",{attrs:{label:"规则"}},[n("el-input",{attrs:{placeholder:"请输入规则"},model:{value:e.form.rule,callback:function(t){e.$set(e.form,"rule",t)},expression:"form.rule"}})],1),e._v(" "),n("el-form-item",{attrs:{label:"入库规则设置"}},[n("el-input",{attrs:{placeholder:"输入入库规则"},model:{value:e.form.valueRule,callback:function(t){e.$set(e.form,"valueRule",t)},expression:"form.valueRule"}})],1)],1),e._v(" "),n("div",{attrs:{slot:"footer"},slot:"footer"},[n("el-button",{on:{click:e.handleClose}},[e._v("取消")]),e._v(" "),n("el-button",{attrs:{type:"primary"},on:{click:e.addRule}},[e._v("确定")])],1)],2)},p=[],h={props:{visible:{required:!0,type:Boolean},type:{required:!0,type:String},parseType:{required:!0,type:Array},date:{type:Object}},data:function(){return{form:{name:"",ruleKey:-1,rule:"",valueRule:""},ruleType:this.parseType[0]}},methods:{openDialog:function(){this.form.name="",this.form.ruleKey=-1,this.form.rule="",this.form.valueRule="",this.ruleType={},this.from=JSON.parse(JSON.stringify(this.data)),this.ruleType.name=this.from.name,this.ruleType.key=this.from.ruleKey},handleClose:function(){this.$emit("close-update-rule")},addRule:function(){switch(this.form.name=this.ruleType.name,this.form.ruleKey=this.ruleType.key,this.type){case"eventKeyRule":this.$emit("update-key-rule",this.form);break}this.$emit("close-update-rule")}}},v=h,y=Object(s["a"])(v,f,p,!1,null,"cc0ed252",null),m=y.exports,b=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("el-dialog",{attrs:{visible:e.visible,"before-close":e.handleClose},on:{"update:visible":function(t){e.visible=t}}},[n("template",{slot:"title"},[n("p",[e._v("Key规则设置")])]),e._v(" "),n("el-button",{attrs:{type:"primary"},on:{click:e.addTableRow}},[e._v("添加")]),e._v(" "),n("el-table",{attrs:{data:e.data,"max-height":"200"}},[n("el-table-column",{attrs:{prop:"name",label:"匹配规则名"}}),e._v(" "),n("el-table-column",{attrs:{label:"规则"},scopedSlots:e._u([{key:"default",fn:function(t){return[t.row.update?n("el-input",{directives:[{name:"focus",rawName:"v-focus"}],on:{blur:function(n){return e.updRule(t.row)}},model:{value:t.row.rule,callback:function(n){e.$set(t.row,"rule",n)},expression:"scope.row.rule"}}):n("span",[e._v(e._s(t.row.rule))])]}}])}),e._v(" "),n("el-table-column",{attrs:{label:"入库规则"},scopedSlots:e._u([{key:"default",fn:function(t){return[t.row.update?n("el-input",{directives:[{name:"focus",rawName:"v-focus"}],on:{blur:function(n){return e.updRule(t.row)}},model:{value:t.row.valueRule,callback:function(n){e.$set(t.row,"valueRule",n)},expression:"scope.row.valueRule"}}):n("span",[e._v(e._s(t.row.valueRule))])]}}])}),e._v(" "),n("el-table-column",{attrs:{label:"操作"},scopedSlots:e._u([{key:"default",fn:function(t){return[n("el-button",{attrs:{type:"text"},on:{click:function(n){return e.updateRow(t.row)}}},[e._v("修改")]),e._v(" "),n("el-popconfirm",{attrs:{confirmButtonText:"确定",cancelButtonText:"取消",icon:"el-icon-info",iconColor:"red",title:"删除该解析器?"},on:{onConfirm:function(n){return e.delRow(t.row)}}},[n("el-button",{staticStyle:{color:"red"},attrs:{slot:"reference",type:"text"},slot:"reference"},[e._v("删除")])],1)]}}])})],1),e._v(" "),n("add-dialog",{attrs:{visible:e.addVisible,type:e.type,"parse-type":e.fieldRuleType,"field-type":"keyRule"},on:{"update:visible":function(t){e.addVisible=t},"close-add-rule":e.handleAddClose,"add-event-rule":e.insertEventKeyRule,"add-profile-rule":e.insertProfileKeyRule}})],2)},R=[],_=n("b775");function k(){return Object(_["a"])({url:"/dashboard/rule",method:"get"})}function w(e){return Object(_["a"])({url:"/dashboard/rule",method:"post",data:e})}function T(e){return Object(_["a"])({url:"/dashboard/rule",method:"put",data:e})}function C(e){return Object(_["a"])({url:"/dashboard/rule/".concat(e),method:"delete"})}function g(){return Object(_["a"])({url:"/dashboard/eventKeyRule",method:"get"})}function x(e){return Object(_["a"])({url:"/dashboard/eventKeyRule",method:"post",data:e})}function $(e){return Object(_["a"])({url:"/dashboard/eventKeyRule",method:"put",data:e})}function K(e){return Object(_["a"])({url:"/dashboard/eventKeyRule/".concat(e),method:"delete"})}function j(e){return Object(_["a"])({url:"/dashboard/eventCheckRule",method:"post",data:e})}function O(e){return Object(_["a"])({url:"/dashboard/eventCheckRule",method:"put",data:e})}function V(e){return Object(_["a"])({url:"/dashboard/eventCheckRule/".concat(e),method:"delete"})}function S(){return Object(_["a"])({url:"/dashboard/rule/inputParseType",method:"get"})}function E(){return Object(_["a"])({url:"/dashboard/rule/fieldRuleType",method:"get"})}function P(){return Object(_["a"])({url:"/dashboard/profileRule",method:"get"})}function N(e){return Object(_["a"])({url:"/dashboard/profileRule",method:"post",data:e})}function A(e){return Object(_["a"])({url:"/dashboard/profileRule",method:"put",data:e})}function q(e){return Object(_["a"])({url:"/dashboard/profileRule/".concat(e),method:"delete"})}function D(){return Object(_["a"])({url:"/dashboard/profileKeyRule",method:"get"})}function B(e){return Object(_["a"])({url:"/dashboard/profileKeyRule",method:"post",data:e})}function F(e){return Object(_["a"])({url:"/dashboard/profileKeyRule",method:"put",data:e})}function U(e){return Object(_["a"])({url:"/dashboard/profileKeyRule/".concat(e),method:"delete"})}function I(e){return Object(_["a"])({url:"/dashboard/profileCheckRule",method:"post",data:e})}function J(e){return Object(_["a"])({url:"/dashboard/profileCheckRule",method:"put",data:e})}function L(e){return Object(_["a"])({url:"/dashboard/profileCheckRule/".concat(e),method:"delete"})}var z={components:{AddDialog:c},props:{type:{required:!0,type:String},data:{type:Array},visible:{required:!0,type:Boolean},keyName:{type:String}},directives:{focus:{inserted:function(e){e.querySelector("input").focus()}}},data:function(){return{addVisible:!1,fieldRuleType:[]}},created:function(){this.findFieldRuleType()},methods:{findFieldRuleType:function(){var e=this;E().then((function(t){e.fieldRuleType=t}))},handleClose:function(){this.$emit("close-parser-table")},addTableRow:function(e){this.addVisible=!0},updateRow:function(e){e.name.toLowerCase().indexOf("json")<=-1&&this.$set(e,"update",!0)},updRule:function(e){var t=this;"event"===e.type?$(e).then((function(e){t.$emit("find-event-data")})):F(e).then((function(e){t.$emit("find-profile-data")})),this.$set(e,"update",!1)},delRow:function(e){var t=this;"event"==e.type?K(e.id).then((function(e){t.$emit("find-event-data")})):U(e.id).then((function(e){t.$emit("find-profile-data")}))},handleAddClose:function(){this.addVisible=!1},insertEventKeyRule:function(e){var t=this;e.key=this.keyName,e.type="event",x(e).then((function(e){t.$emit("find-event-data")}))},insertProfileKeyRule:function(e){var t=this;e.key=this.keyName,e.type="profile",B(e).then((function(e){t.$emit("find-profile-data")}))}}},G=z,H=Object(s["a"])(G,b,R,!1,null,"52e2c484",null),M=H.exports,Q=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("el-dialog",{attrs:{visible:e.visible,"before-close":e.handleClose},on:{"update:visible":function(t){e.visible=t}}},[n("template",{slot:"title"},[n("p",[e._v("入库规则设置")])]),e._v(" "),n("el-button",{attrs:{type:"primary"},on:{click:e.addTableRow}},[e._v("添加")]),e._v(" "),n("el-table",{attrs:{data:e.data,"max-height":"200"}},[n("el-table-column",{attrs:{prop:"name",label:"匹配规则名"}}),e._v(" "),n("el-table-column",{attrs:{label:"规则"},scopedSlots:e._u([{key:"default",fn:function(t){return[t.row.update?n("el-input",{directives:[{name:"focus",rawName:"v-focus"}],on:{blur:function(n){return e.updRule(t.row)}},model:{value:t.row.rule,callback:function(n){e.$set(t.row,"rule",n)},expression:"scope.row.rule"}}):n("span",[e._v(e._s(t.row.rule))])]}}])}),e._v(" "),n("el-table-column",{attrs:{label:"操作"},scopedSlots:e._u([{key:"default",fn:function(t){return[n("el-button",{attrs:{type:"text"},on:{click:function(n){return e.updateRow(t.row)}}},[e._v("修改")]),e._v(" "),n("el-popconfirm",{attrs:{confirmButtonText:"确定",cancelButtonText:"取消",icon:"el-icon-info",iconColor:"red",title:"删除该解析器?"},on:{onConfirm:function(n){return e.delRow(t.row)}}},[n("el-button",{staticStyle:{color:"red"},attrs:{slot:"reference",type:"text"},slot:"reference"},[e._v("删除")])],1)]}}])})],1),e._v(" "),n("add-dialog",{attrs:{visible:e.addVisible,type:e.type,"parse-type":e.fieldRuleType},on:{"update:visible":function(t){e.addVisible=t},"close-add-rule":e.handleAddClose,"add-event-rule":e.insertEventKeyRule,"add-profile-rule":e.insertProfileKeyRule}})],2)},W=[],X={components:{AddDialog:c},props:{type:{required:!0,type:String},data:{type:Array},visible:{required:!0,type:Boolean},keyName:{type:String}},directives:{focus:{inserted:function(e){e.querySelector("input").focus()}}},data:function(){return{addVisible:!1,fieldRuleType:[]}},created:function(){this.findFieldRuleType()},methods:{findFieldRuleType:function(){var e=this;E().then((function(t){e.fieldRuleType=t}))},handleClose:function(){this.$emit("close-check-table")},addTableRow:function(e){this.addVisible=!0},updateRow:function(e){e.name.toLowerCase().indexOf("json")<=-1&&this.$set(e,"update",!0)},updRule:function(e){var t=this;"event"===e.type?O(e).then((function(e){t.$emit("find-event-data")})):J(e).then((function(e){t.$emit("find-profile-data")})),this.$set(e,"update",!1)},delRow:function(e){var t=this;"event"==e.type?V(e.id).then((function(e){t.$emit("find-event-data")})):L(e.id).then((function(e){t.$emit("find-profile-data")}))},handleAddClose:function(){this.addVisible=!1},insertEventKeyRule:function(e){var t=this;e.key=this.keyName,e.type="event",j(e).then((function(e){t.$emit("find-event-data")}))},insertProfileKeyRule:function(e){var t=this;e.key=this.keyName,e.type="profile",I(e).then((function(e){t.$emit("find-profile-data")}))}}},Y=X,Z=Object(s["a"])(Y,Q,W,!1,null,"a14a395e",null),ee=Z.exports;function te(){return Object(_["a"])({url:"/dashboard/appVerify",method:"get"})}function ne(e){return Object(_["a"])({url:"/dashboard/appVerify",method:"put",data:e})}function le(){return Object(_["a"])({url:"/dashboard/eventFieldType",method:"get"})}var ie={components:{AddDialog:c,ParserTable:M,CheckTable:ee,UpdateDialog:m},directives:{focus:{inserted:function(e){e.querySelector("input").focus()}}},data:function(){return{eventCheck:!1,userCheck:!1,addVisible:!1,updateVisible:!1,parserVisible:!1,checkVisible:!1,type:"",keyName:"",eventTable:[],profileTable:[],profileRule:"",data:{},events:[],profiles:[{label:"接收用户「设备ID」",name:"device_id",keyRule:[],rule:[]},{label:"接收用户「用户ID」",name:"distinct_id",keyRule:[],rule:[]},{label:"接收用户「发生时间」",name:"time",keyRule:[],rule:[]},{label:"接收用户「项目ID」",name:"project",keyRule:[],rule:[]},{label:"接收用户「type」",name:"profile_user",keyRule:[],rule:[]}],inputParseType:[],fieldRuleType:[],fieldType:"",showType:[]}},created:function(){this.findEventRule(),this.findProfileRule(),this.findEvenKeyRule(),this.findProfileKeyRule(),this.findInputParseType(),this.findAppVerify(),this.findFieldRuleType()},methods:{findFieldRuleType:function(){var e=this;E().then((function(t){e.fieldRuleType=t}))},findEventRule:function(){var e=this;k().then((function(t){e.eventTable=t}))},findProfileRule:function(){var e=this;P().then((function(t){e.profileTable=t}))},findInputParseType:function(){var e=this;S().then((function(t){e.inputParseType=t}))},findAppVerify:function(){var e=this;te().then((function(t){e.eventCheck=t.verify}))},findEvenKeyRule:function(){var e=this;le().then((function(t){e.events=t,g().then((function(t){e.events.some((function(e){t.some((function(t){e.value!==t.key||(e["keyRule"]=t)}))}))}))}))},findProfileKeyRule:function(){var e=this;D().then((function(t){var n=function(n){e.profiles[n].keyRule.splice(0),t.some((function(t){e.profiles[n].name===t.key&&e.profiles[n].keyRule.push(t)}))};for(var l in e.profiles)n(l)}))},handleAddClose:function(){this.addVisible=!1},handleUpdateClose:function(){this.updateVisible=!1},handleParserClose:function(){this.parserVisible=!1},handleCheckClose:function(){this.checkVisible=!1},addTableRow:function(e){this.showType=this.inputParseType,this.addVisible=!0,this.type=e,this.fieldType="parseRule"},showParserTable:function(e,t){this.keyName=e,this.addVisible=!0,this.type=t,this.showType=this.fieldRuleType,this.fieldType="keyRule"},showUpdateDialog:function(e,t){this.keyName=e,this.updateVisible=!0,this.showType=this.fieldRuleType,this.type=t},showCheckTable:function(e,t){this.checkVisible=!0,this.type=t},insertEventRule:function(e){w(e).then((function(e){})),this.findEventRule()},insertProfileRule:function(e){this.profileTable.push(e),N(e).then((function(e){})),this.findProfileRule()},insertEventKeyRule:function(e){var t=this;e.key=this.keyName,e.type="event",x(e).then((function(e){t.findEvenKeyRule()}))},handleUpdateEventKeyRule:function(e){},updateRow:function(e){e.name.toLowerCase().indexOf("json")<=-1&&this.$set(e,"update",!0)},updEventRule:function(e){var t=this;this.$set(e,"update",!1),T(e).then((function(e){t.findEventRule()}))},updProfileRule:function(e){0==e.rule.split(" ").join("").length&&(e.rule=""),this.$set(e,"update",!1),A(e).then((function(e){})),this.findProfileRule()},delRow:function(e,t){"event"===t?(C(e.id).then((function(e){})),this.findEventRule()):"profile"===t&&(q(e.id).then((function(e){})),this.findProfileRule())},setVerify:function(e){var t=this,n={verify:e};ne(n).then((function(e){console.log(e),t.eventCheck=e.verify})).catch((function(e){t.eventCheck=!t.eventCheck}))}}},re=ie,oe=(n("3957"),Object(s["a"])(re,l,i,!1,null,null,null));t["default"]=oe.exports}}]);