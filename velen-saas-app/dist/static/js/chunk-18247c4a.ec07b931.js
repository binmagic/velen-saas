(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-18247c4a"],{"26c5":function(t,e,a){"use strict";var l=a("ce85"),n=a.n(l);n.a},"333d":function(t,e,a){"use strict";var l=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"pagination-container",class:{hidden:t.hidden}},[a("el-pagination",t._b({attrs:{background:t.background,"current-page":t.currentPage,"page-size":t.pageSize,layout:t.layout,"page-sizes":t.pageSizes,total:t.total},on:{"update:currentPage":function(e){t.currentPage=e},"update:current-page":function(e){t.currentPage=e},"update:pageSize":function(e){t.pageSize=e},"update:page-size":function(e){t.pageSize=e},"size-change":t.handleSizeChange,"current-change":t.handleCurrentChange}},"el-pagination",t.$attrs,!1))],1)},n=[];a("c5f6");Math.easeInOutQuad=function(t,e,a,l){return t/=l/2,t<1?a/2*t*t+e:(t--,-a/2*(t*(t-2)-1)+e)};var o=function(){return window.requestAnimationFrame||window.webkitRequestAnimationFrame||window.mozRequestAnimationFrame||function(t){window.setTimeout(t,1e3/60)}}();function r(t){document.documentElement.scrollTop=t,document.body.parentNode.scrollTop=t,document.body.scrollTop=t}function s(){return document.documentElement.scrollTop||document.body.parentNode.scrollTop||document.body.scrollTop}function i(t,e,a){var l=s(),n=t-l,i=20,c=0;e="undefined"===typeof e?500:e;var u=function t(){c+=i;var s=Math.easeInOutQuad(c,l,n,e);r(s),c<e?o(t):a&&"function"===typeof a&&a()};u()}var c={name:"Pagination",props:{total:{required:!0,type:Number},page:{type:Number,default:1},limit:{type:Number,default:20},pageSizes:{type:Array,default:function(){return[10,20,30,50]}},layout:{type:String,default:"total, sizes, prev, pager, next, jumper"},background:{type:Boolean,default:!0},autoScroll:{type:Boolean,default:!0},hidden:{type:Boolean,default:!1}},computed:{currentPage:{get:function(){return this.page},set:function(t){this.$emit("update:page",t)}},pageSize:{get:function(){return this.limit},set:function(t){this.$emit("update:limit",t)}}},methods:{handleSizeChange:function(t){this.$emit("pagination",{page:this.currentPage,limit:t}),this.autoScroll&&i(0,800)},handleCurrentChange:function(t){this.$emit("pagination",{page:t,limit:this.pageSize}),this.autoScroll&&i(0,800)}}},u=c,p=(a("26c5"),a("2877")),d=Object(p["a"])(u,l,n,!1,null,"6b68b94e",null);e["a"]=d.exports},"42a5":function(t,e,a){"use strict";a.r(e);var l=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"app-container"},[a("custom-header",{attrs:{"show-name":t.$route.meta.title}},[a("div",{attrs:{slot:"tools"},slot:"tools"},[a("el-dropdown",{attrs:{trigger:"click"},on:{command:t.handleClickPlus}},[a("span",[a("el-button",{staticStyle:{"margin-left":"20px"},attrs:{icon:"el-icon-plus",type:"success"}})],1),t._v(" "),a("el-dropdown-menu",{attrs:{slot:"dropdown"},slot:"dropdown"},[a("el-dropdown-item",{attrs:{command:"manuallyCreate"}},[t._v("手动创建")]),t._v(" "),a("el-dropdown-item",{attrs:{command:"batchUpload"}},[t._v("批量上传")])],1)],1)],1)]),t._v(" "),a("div",[a("el-form",{attrs:{inline:!0,size:"mini"}},[a("el-form-item",{attrs:{label:"上报数据"}},[a("el-input")],1),t._v(" "),a("el-form-item",{attrs:{label:"显示状态"}},[a("el-select",{attrs:{value:"11"}},[a("el-option",{attrs:{label:"全部",value:"shanghai"}}),t._v(" "),a("el-option",{attrs:{label:"显示",value:"beijing"}}),t._v(" "),a("el-option",{attrs:{label:"隐藏",value:"beijing"}})],1)],1),t._v(" "),a("el-form-item",{attrs:{label:"数据类型"}},[a("el-select",{attrs:{value:"22"}},[a("el-option",{attrs:{label:"全部",value:"shanghai"}}),t._v(" "),a("el-option",{attrs:{label:"NUMBER",value:"beijing"}}),t._v(" "),a("el-option",{attrs:{label:"STRING",value:"beijing"}})],1)],1),t._v(" "),a("el-form-item",[a("div",{staticStyle:{"align-items":"center","margin-right":"20px"},attrs:{slot:"tools"},slot:"tools"},[a("el-checkbox",[t._v("只显示公共属性")])],1),t._v(" "),a("el-input",{attrs:{placeholder:"请输入内容","suffix-icon":"el-icon-search"},model:{value:t.query.search,callback:function(e){t.$set(t.query,"search",e)},expression:"query.search"}})],1)],1)],1),t._v(" "),a("event-create",{attrs:{enable:t.componentFlag.enableDrawer},on:{"update:enable":function(e){return t.$set(t.componentFlag,"enableDrawer",e)},"on-create-event":t.handleCreatedEvent}}),t._v(" "),a("el-table",{directives:[{name:"loading",rawName:"v-loading",value:t.listLoading,expression:"listLoading"}],staticStyle:{width:"100%"},attrs:{data:t.list,border:"",fit:"","highlight-current-row":""}},[a("el-table-column",{attrs:{label:"事件名",align:"center","show-overflow-tooltip":!0},scopedSlots:t._u([{key:"default",fn:function(e){var l=e.row;return[a("span",[t._v(t._s(l.name))])]}}])}),t._v(" "),a("el-table-column",{attrs:{label:"事件显示名",align:"center","show-overflow-tooltip":!0},scopedSlots:t._u([{key:"default",fn:function(e){var l=e.row;return[a("span",[t._v(t._s(l.showName))])]}}])}),t._v(" "),a("el-table-column",{attrs:{label:"上报数据",align:"center","show-overflow-tooltip":!0},scopedSlots:t._u([{key:"default",fn:function(e){var l=e.row;return[a("span",[t._v(t._s(l.upload))])]}}])}),t._v(" "),a("el-table-column",{attrs:{label:"是否接收",align:"center","show-overflow-tooltip":!0},scopedSlots:t._u([{key:"default",fn:function(e){var l=e.row;return[a("span",[t._v(t._s(l.accept))])]}}])}),t._v(" "),a("el-table-column",{attrs:{label:"过去30天入库",align:"center","show-overflow-tooltip":!0},scopedSlots:t._u([{key:"default",fn:function(e){var l=e.row;return[a("span",[t._v(t._s(l.lastMonthSave))])]}}])}),t._v(" "),a("el-table-column",{attrs:{label:"应埋点平台",align:"center","show-overflow-tooltip":!0},scopedSlots:t._u([{key:"default",fn:function(e){var l=e.row;return t._l(l.platform,(function(e){return a("el-tag",{staticStyle:{"margin-left":"20px"}},[a("span",[t._v(t._s(e))])])}))}}])}),t._v(" "),a("el-table-column",{attrs:{label:"标签",align:"center","show-overflow-tooltip":!0},scopedSlots:t._u([{key:"default",fn:function(e){var l=e.row;return[a("span",t._l(l.tag,(function(e){return a("el-tag",[t._v(t._s(e))])})),1)]}}])}),t._v(" "),a("el-table-column",{attrs:{label:"上次修改时间",align:"center","show-overflow-tooltip":!0},scopedSlots:t._u([{key:"default",fn:function(e){var l=e.row;return[a("span",[t._v(t._s(l.updateTime))])]}}])}),t._v(" "),a("el-table-column",{attrs:{label:"创建人",align:"center","show-overflow-tooltip":!0},scopedSlots:t._u([{key:"default",fn:function(e){var l=e.row;return[a("span",[t._v(t._s(l.createUser))])]}}])}),t._v(" "),a("el-table-column",{attrs:{label:"触发时机",align:"center","show-overflow-tooltip":!0},scopedSlots:t._u([{key:"default",fn:function(e){var l=e.row;return[a("span",[t._v(t._s(l.timing))])]}}])}),t._v(" "),a("el-table-column",{attrs:{label:"备注",align:"center","show-overflow-tooltip":!0},scopedSlots:t._u([{key:"default",fn:function(e){var l=e.row;return[a("span",[t._v(t._s(l.remark))])]}}])}),t._v(" "),a("el-table-column",{attrs:{fixed:"right",label:"#",align:"center","class-name":"small-padding fixed-width"},scopedSlots:t._u([{key:"default",fn:function(e){e.row;return[a("i",{staticClass:"el-icon-edit"}),t._v(" "),a("i",{staticClass:"el-icon-more"})]}}])})],1),t._v(" "),a("pagination",{directives:[{name:"show",rawName:"v-show",value:t.total>0,expression:"total>0"}],attrs:{total:t.total,page:t.query.page,limit:t.query.limit},on:{"update:page":function(e){return t.$set(t.query,"page",e)},"update:limit":function(e){return t.$set(t.query,"limit",e)},pagination:t.fetchData}})],1)},n=[],o=a("7d99"),r=a("bc5d"),s=a("333d"),i=a("6724"),c=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("el-drawer",{attrs:{title:"创建",visible:t.enable,"before-close":t.hideDrawer,direction:"rtl",size:"50%"},on:{"update:visible":function(e){t.enable=e}}},[a("div",{staticStyle:{"margin-left":"20px","margin-right":"20px"}},[a("el-form",{staticStyle:{"padding-right":"50px"},attrs:{"label-width":"auto","label-position":"left","hide-required-asterisk":""}},[a("el-form-item",{attrs:{label:"事件显示名"}},[a("el-input",{model:{value:t.formData.showName,callback:function(e){t.$set(t.formData,"showName",e)},expression:"formData.showName"}})],1),t._v(" "),a("el-form-item",{attrs:{label:"事件名"}},[a("el-input",{model:{value:t.formData.name,callback:function(e){t.$set(t.formData,"name",e)},expression:"formData.name"}})],1),t._v(" "),a("el-form-item",{attrs:{label:"应埋点平台"}},[a("el-select",{staticStyle:{width:"100%"},attrs:{multiple:""},model:{value:t.formData.platform,callback:function(e){t.$set(t.formData,"platform",e)},expression:"formData.platform"}},t._l(t.$const.metadata.platform,(function(t){return a("el-option",{attrs:{label:t,value:t}})})),1)],1),t._v(" "),a("el-form-item",{attrs:{label:"触发时机"}},[a("el-input",{attrs:{type:"textarea","show-word-limit":!0,maxlength:200,rows:2,placeholder:"请输入内容"},model:{value:t.formData.timing,callback:function(e){t.$set(t.formData,"timing",e)},expression:"formData.timing"}})],1),t._v(" "),a("el-form-item",{attrs:{label:"标签"}},[t._l(t.formData.tag,(function(e){return a("el-tag",{attrs:{closable:""}},[t._v(t._s(e))])})),t._v(" "),t.componentFlag.enableTagAdd?a("el-select",{attrs:{placeholder:"请选择",filterable:"",multiple:""},on:{blur:function(e){t.componentFlag.enableTagAdd=!1}},model:{value:t.selectTag,callback:function(e){t.selectTag=e},expression:"selectTag"}},t._l(t.tags,(function(t){return a("el-option",{key:t.id,attrs:{label:t.name,value:t.id}})})),1):a("el-button",{attrs:{size:"small"},on:{click:function(e){t.componentFlag.enableTagAdd=!0}}},[t._v("+")])],2)],1),t._v(" "),a("el-form",{attrs:{"label-position":"top"}},[a("el-form-item",{staticClass:"custom-prop",attrs:{label:"自定义属性"}},[a("el-row",{staticStyle:{background:"rgb(241, 240, 240)",display:"flex","margin-bottom":"10px"},attrs:{gutter:20}},[a("el-col",{attrs:{span:8}},[t._v("属性名")]),t._v(" "),a("el-col",{attrs:{span:8}},[t._v("属性显示名")]),t._v(" "),a("el-col",{attrs:{span:8}},[t._v("数据类型")])],1),t._v(" "),t._l(t.selectProperties,(function(e){return e.isCommon||e.isPerset?t._e():a("el-row",{staticStyle:{background:"white",display:"flex","margin-bottom":"10px"},attrs:{gutter:20}},[a("el-col",{attrs:{span:8}},[a("el-input",{attrs:{disabled:"",value:e.name}})],1),t._v(" "),a("el-col",{attrs:{span:8}},[a("el-input",{attrs:{disabled:"",value:e.showName}})],1),t._v(" "),a("el-col",{attrs:{span:6}},[a("el-select",{attrs:{value:e.type,disabled:""}},t._l(t.$const.metadata.data_type,(function(t){return a("el-option",{attrs:{label:t,value:t}})})),1)],1),t._v(" "),a("el-col",{attrs:{span:2}},[a("i",{staticClass:"el-icon-close",on:{click:function(a){return t.handleDeleteProp(e)}}})])],1)})),t._v(" "),a("el-row",{staticStyle:{background:"white"}},[a("el-col",[a("el-popover",{attrs:{placement:"bottom",trigger:"click",width:"500"}},[a("div",{staticStyle:{display:"flex","margin-bottom":"20px"}},[a("el-button",{attrs:{type:"success"},on:{click:function(e){return t.handleAddToList()}}},[t._v("添加")])],1),t._v(" "),a("el-table",{ref:"propertiesTable",staticStyle:{width:"100%"},attrs:{data:t.properties,border:"",fit:"","highlight-current-row":""}},[a("el-table-column",{attrs:{type:"selection",width:"55"}}),t._v(" "),a("el-table-column",{attrs:{label:"属性名",align:"center","show-overflow-tooltip":!0},scopedSlots:t._u([{key:"default",fn:function(e){var l=e.row;return[a("span",[t._v(t._s(l.name))])]}}])}),t._v(" "),a("el-table-column",{attrs:{label:"属性显示名",align:"center","show-overflow-tooltip":!0},scopedSlots:t._u([{key:"default",fn:function(e){var l=e.row;return[a("span",[t._v(t._s(l.showName))])]}}])}),t._v(" "),a("el-table-column",{attrs:{label:"属性类型",align:"center","show-overflow-tooltip":!0},scopedSlots:t._u([{key:"default",fn:function(e){var l=e.row;return[a("span",[t._v(t._s(l.type))])]}}])})],1),t._v(" "),a("i",{staticClass:"el-icon-plus",attrs:{slot:"reference"},slot:"reference"})],1)],1)],1)],2),t._v(" "),a("el-form-item",{staticClass:"custom-prop",attrs:{label:"公共属性"}},[a("el-row",{staticStyle:{background:"rgb(241, 240, 240)",display:"flex","margin-bottom":"10px"},attrs:{gutter:20}},[a("el-col",{attrs:{span:8}},[t._v("属性名")]),t._v(" "),a("el-col",{attrs:{span:8}},[t._v("属性显示名")]),t._v(" "),a("el-col",{attrs:{span:8}},[t._v("数据类型")])],1),t._v(" "),t._l(t.selectProperties,(function(e){return e.isCommon?a("el-row",{staticStyle:{background:"white",display:"flex","margin-bottom":"10px"},attrs:{gutter:20}},[a("el-col",{attrs:{span:8}},[a("el-input",{attrs:{disabled:"",value:e.name}})],1),t._v(" "),a("el-col",{attrs:{span:8}},[a("el-input",{attrs:{disabled:"",value:e.showName}})],1),t._v(" "),a("el-col",{attrs:{span:6}},[a("el-select",{attrs:{value:e.type,disabled:""}},t._l(t.$const.metadata.data_type,(function(t){return a("el-option",{attrs:{label:t,value:t}})})),1)],1),t._v(" "),a("el-col",{attrs:{span:2}},[a("i",{staticClass:"el-icon-close",on:{click:function(a){return t.handleDeleteCommonProp(e)}}})])],1):t._e()})),t._v(" "),a("el-row",{staticStyle:{background:"white"}},[a("el-col",[a("i",{staticClass:"el-icon-plus"})])],1)],2),t._v(" "),a("el-form-item",{staticClass:"custom-prop",attrs:{label:"预制属性"}},[a("el-row",{staticStyle:{background:"rgb(241, 240, 240)",display:"flex","margin-bottom":"10px"},attrs:{gutter:20}},[a("el-col",{attrs:{span:8}},[t._v("属性名")]),t._v(" "),a("el-col",{attrs:{span:8}},[t._v("属性显示名")]),t._v(" "),a("el-col",{attrs:{span:8}},[t._v("数据类型")])],1),t._v(" "),t._l(t.selectProperties,(function(e){return e.isPreset?a("el-row",{staticStyle:{background:"white",display:"flex","margin-bottom":"10px"},attrs:{gutter:20}},[a("el-col",{attrs:{span:8}},[a("el-input",{attrs:{disabled:"",value:e.name}})],1),t._v(" "),a("el-col",{attrs:{span:8}},[a("el-input",{attrs:{disabled:"",value:e.showName}})],1),t._v(" "),a("el-col",{attrs:{span:6}},[a("el-select",{attrs:{value:e.type,disabled:""}},t._l(t.$const.metadata.data_type,(function(t){return a("el-option",{attrs:{label:t,value:t}})})),1)],1),t._v(" "),a("el-col",{attrs:{span:2}},[a("i",{staticClass:"el-icon-close",on:{click:function(a){return t.handleDeletePresetProp(t.formData.presetProp,e)}}})])],1):t._e()})),t._v(" "),a("el-row",{staticStyle:{background:"white"}},[a("el-col",[a("el-popover",{attrs:{placement:"bottom",width:"200",trigger:"click"}},[a("i",{staticClass:"el-icon-plus"})])],1)],1)],2)],1)],1),t._v(" "),a("div",{staticStyle:{width:"100%","text-align":"right","line-height":"50px",background:"white","box-shadow":"rgb(194, 210, 230) -7px -1px 2px 0px","padding-right":"50px",position:"absolute",bottom:"0",clear:"both"}},[a("span",{staticStyle:{"margin-right":"30px"}},[a("el-button",{attrs:{type:"text"}},[t._v("取消")])],1),t._v(" "),a("span",[a("el-button",{attrs:{type:"success",loading:t.componentFlag.createLoading},on:{click:t.handleSubmit}},[t._v("确认")])],1)])])},u=[],p=(a("db97"),{name:"EventCreate",components:{},directives:{},props:{enable:{type:Boolean,default:!1}},data:function(){return{formData:{platform:[],timing:"",example:"",type:"",name:"",showName:"",tag:[],propIds:[]},componentFlag:{enableTagAdd:!1,createLoading:!1},selectTag:"",tags:[],properties:[],selectProperties:[]}},created:function(){this.fetchMetaEventProp(),this.fetchMetaEventProp()},methods:{hideDrawer:function(){this.$emit("update:enable",!1)},handleSubmit:function(){var t=this;for(var e in this.formData.propIds=[],this.selectProperties)this.formData.propIds.push(this.selectProperties[e].id);this.componentFlag.createLoading=!0,Object(o["a"])(this.formData).then((function(e){t.componentFlag.createLoading=!1,t.$message.success("success"),t.$emit("on-create-event")})).catch((function(e){t.componentFlag.createLoading=!1}))},handleDeleteProp:function(t){for(var e in this.selectProperties)if(Object.is(this.selectProperties[e].id,t.id)){this.$delete(this.selectProperties,e);break}},handleAddToList:function(){var t=this.$refs.propertiesTable.store.states.selection;for(var e in t){var a=!0;for(var l in this.selectProperties)if(Object.is(this.selectProperties[l].id,t[e].id)){a=!1;break}a&&this.selectProperties.push(t[e])}},fetchMetaEventProp:function(){var t=this;Object(o["d"])().then((function(e){for(var a in t.properties=e.items,t.properties)(t.properties[a].isCommon||t.properties[a].isPerset)&&t.selectProperties.push(t.properties)}))}}}),d=p,m=(a("48f6"),a("2877")),f=Object(m["a"])(d,c,u,!1,null,null,null),v=f.exports,g=v,h={components:{CustomHeader:r["a"],Pagination:s["a"],EventCreate:g},directives:{waves:i["a"]},data:function(){return{total:0,list:[],listLoading:!1,query:{page:1,limit:10},componentFlag:{enableDrawer:!1}}},created:function(){this.fetchData()},methods:{fetchData:function(){var t=this;this.listLoading=!0,Object(o["c"])(this.query).then((function(e){t.total=e.total,t.list=e.items,t.listLoading=!1}))},handleClickPlus:function(t){switch(t){case"manuallyCreate":this.componentFlag.enableDrawer=!0;break}},handleCreatedEvent:function(){}}},b=h,_=Object(m["a"])(b,l,n,!1,null,null,null);e["default"]=_.exports},"48e7":function(t,e,a){},"48f6":function(t,e,a){"use strict";var l=a("48e7"),n=a.n(l);n.a},6724:function(t,e,a){"use strict";a("8d41");var l="@@wavesContext";function n(t,e){function a(a){var l=Object.assign({},e.value),n=Object.assign({ele:t,type:"hit",color:"rgba(0, 0, 0, 0.15)"},l),o=n.ele;if(o){o.style.position="relative",o.style.overflow="hidden";var r=o.getBoundingClientRect(),s=o.querySelector(".waves-ripple");switch(s?s.className="waves-ripple":(s=document.createElement("span"),s.className="waves-ripple",s.style.height=s.style.width=Math.max(r.width,r.height)+"px",o.appendChild(s)),n.type){case"center":s.style.top=r.height/2-s.offsetHeight/2+"px",s.style.left=r.width/2-s.offsetWidth/2+"px";break;default:s.style.top=(a.pageY-r.top-s.offsetHeight/2-document.documentElement.scrollTop||document.body.scrollTop)+"px",s.style.left=(a.pageX-r.left-s.offsetWidth/2-document.documentElement.scrollLeft||document.body.scrollLeft)+"px"}return s.style.backgroundColor=n.color,s.className="waves-ripple z-active",!1}}return t[l]?t[l].removeHandle=a:t[l]={removeHandle:a},a}var o={bind:function(t,e){t.addEventListener("click",n(t,e),!1)},update:function(t,e){t.removeEventListener("click",t[l].removeHandle,!1),t.addEventListener("click",n(t,e),!1)},unbind:function(t){t.removeEventListener("click",t[l].removeHandle,!1),t[l]=null,delete t[l]}},r=function(t){t.directive("waves",o)};window.Vue&&(window.waves=o,Vue.use(r)),o.install=r;e["a"]=o},"7d99":function(t,e,a){"use strict";a.d(e,"c",(function(){return n})),a.d(e,"a",(function(){return o})),a.d(e,"d",(function(){return r})),a.d(e,"b",(function(){return s}));var l=a("b775");function n(t){return Object(l["a"])({url:"/metadata/event",method:"get",params:t})}function o(t){return Object(l["a"])({url:"/metadata/event",method:"post",data:t})}function r(t){return Object(l["a"])({url:"/metadata/event/prop",method:"get",params:t})}function s(t){return Object(l["a"])({url:"/metadata/event/prop",method:"post",data:t})}},"8d41":function(t,e,a){},bc5d:function(t,e,a){"use strict";var l=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"custom-header"},[a("div",{staticClass:"custom-header-title"},[a("span",[a("h3",[t._v(t._s(t.showName))])]),t._v(" "),t._t("title")],2),t._v(" "),a("div",{staticClass:"custom-tools"},[t._t("tools")],2)])},n=[],o=(a("e67d"),{name:"CustomHeader",props:{showName:{type:String,default:""}}}),r=o,s=a("2877"),i=Object(s["a"])(r,l,n,!1,null,null,null),c=i.exports;e["a"]=c},ce85:function(t,e,a){},e67d:function(t,e,a){}}]);