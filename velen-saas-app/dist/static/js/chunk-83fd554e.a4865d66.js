(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-83fd554e"],{"26c5":function(e,t,n){"use strict";var a=n("ce85"),o=n.n(a);o.a},"333d":function(e,t,n){"use strict";var a=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"pagination-container",class:{hidden:e.hidden}},[n("el-pagination",e._b({attrs:{background:e.background,"current-page":e.currentPage,"page-size":e.pageSize,layout:e.layout,"page-sizes":e.pageSizes,total:e.total},on:{"update:currentPage":function(t){e.currentPage=t},"update:current-page":function(t){e.currentPage=t},"update:pageSize":function(t){e.pageSize=t},"update:page-size":function(t){e.pageSize=t},"size-change":e.handleSizeChange,"current-change":e.handleCurrentChange}},"el-pagination",e.$attrs,!1))],1)},o=[];n("c5f6");Math.easeInOutQuad=function(e,t,n,a){return e/=a/2,e<1?n/2*e*e+t:(e--,-n/2*(e*(e-2)-1)+t)};var i=function(){return window.requestAnimationFrame||window.webkitRequestAnimationFrame||window.mozRequestAnimationFrame||function(e){window.setTimeout(e,1e3/60)}}();function r(e){document.documentElement.scrollTop=e,document.body.parentNode.scrollTop=e,document.body.scrollTop=e}function s(){return document.documentElement.scrollTop||document.body.parentNode.scrollTop||document.body.scrollTop}function l(e,t,n){var a=s(),o=e-a,l=20,c=0;t="undefined"===typeof t?500:t;var u=function e(){c+=l;var s=Math.easeInOutQuad(c,a,o,t);r(s),c<t?i(e):n&&"function"===typeof n&&n()};u()}var c={name:"Pagination",props:{total:{required:!0,type:Number},page:{type:Number,default:1},limit:{type:Number,default:20},pageSizes:{type:Array,default:function(){return[10,20,30,50]}},layout:{type:String,default:"total, sizes, prev, pager, next, jumper"},background:{type:Boolean,default:!0},autoScroll:{type:Boolean,default:!0},hidden:{type:Boolean,default:!1}},computed:{currentPage:{get:function(){return this.page},set:function(e){this.$emit("update:page",e)}},pageSize:{get:function(){return this.limit},set:function(e){this.$emit("update:limit",e)}}},methods:{handleSizeChange:function(e){this.$emit("pagination",{page:this.currentPage,limit:e}),this.autoScroll&&l(0,800)},handleCurrentChange:function(e){this.$emit("pagination",{page:e,limit:this.pageSize}),this.autoScroll&&l(0,800)}}},u=c,d=(n("26c5"),n("2877")),p=Object(d["a"])(u,a,o,!1,null,"6b68b94e",null);t["a"]=p.exports},6724:function(e,t,n){"use strict";n("8d41");var a="@@wavesContext";function o(e,t){function n(n){var a=Object.assign({},t.value),o=Object.assign({ele:e,type:"hit",color:"rgba(0, 0, 0, 0.15)"},a),i=o.ele;if(i){i.style.position="relative",i.style.overflow="hidden";var r=i.getBoundingClientRect(),s=i.querySelector(".waves-ripple");switch(s?s.className="waves-ripple":(s=document.createElement("span"),s.className="waves-ripple",s.style.height=s.style.width=Math.max(r.width,r.height)+"px",i.appendChild(s)),o.type){case"center":s.style.top=r.height/2-s.offsetHeight/2+"px",s.style.left=r.width/2-s.offsetWidth/2+"px";break;default:s.style.top=(n.pageY-r.top-s.offsetHeight/2-document.documentElement.scrollTop||document.body.scrollTop)+"px",s.style.left=(n.pageX-r.left-s.offsetWidth/2-document.documentElement.scrollLeft||document.body.scrollLeft)+"px"}return s.style.backgroundColor=o.color,s.className="waves-ripple z-active",!1}}return e[a]?e[a].removeHandle=n:e[a]={removeHandle:n},n}var i={bind:function(e,t){e.addEventListener("click",o(e,t),!1)},update:function(e,t){e.removeEventListener("click",e[a].removeHandle,!1),e.addEventListener("click",o(e,t),!1)},unbind:function(e){e.removeEventListener("click",e[a].removeHandle,!1),e[a]=null,delete e[a]}},r=function(e){e.directive("waves",i)};window.Vue&&(window.waves=i,Vue.use(r)),i.install=r;t["a"]=i},"8d41":function(e,t,n){},"9b3c":function(e,t,n){"use strict";n.r(t);var a=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"app-container"},[n("custom-header",{attrs:{"show-name":e.resolveName()}},[n("span",{attrs:{slot:"title"},slot:"title"},[n("el-dropdown",{on:{command:e.handleSelect}},[n("i",{staticClass:"el-icon-arrow-down el-icon--right"}),e._v(" "),n("el-dropdown-menu",[n("el-dropdown-item",{attrs:{command:""}},[e._v("全部书签")]),e._v(" "),e._l(e.types,(function(t){return n("el-dropdown-item",{attrs:{command:t.command}},[e._v(e._s(t.cname))])}))],2)],1)],1),e._v(" "),n("span",{attrs:{slot:"title"},slot:"title"},[n("el-link",{attrs:{disabled:""}},[e._v("共"+e._s(e.total)+"个")])],1),e._v(" "),n("span",{staticStyle:{"margin-right":"15px"},attrs:{slot:"tools"},slot:"tools"},[n("el-input",{attrs:{placeholder:"请输入内容","suffix-icon":"el-icon-search"},on:{change:e.searchChange},model:{value:e.query.search,callback:function(t){e.$set(e.query,"search",t)},expression:"query.search"}})],1),e._v(" "),n("span",{attrs:{slot:"tools"},slot:"tools"},[n("el-popover",{attrs:{placement:"bottom",width:"500",trigger:"click"}},[n("div",{staticStyle:{display:"flex",height:"200px"}},e._l(e.types,(function(t){return n("div",{staticStyle:{height:"50px",width:"200px",padding:"16px","margin-bottom":"10px"},on:{click:function(n){return e.jump(t)}}},[n("i",{class:t.icon}),e._v(" "),n("span",{attrs:{slot:"title"},slot:"title"},[e._v(e._s(t.cname))])])})),0),e._v(" "),n("el-button",{attrs:{slot:"reference",icon:"el-icon-plus",type:"success"},slot:"reference"},[e._v("\n          新建书签\n        ")])],1)],1)]),e._v(" "),n("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.listLoading,expression:"listLoading"}],staticStyle:{width:"100%"},attrs:{data:e.list,border:"",fit:"","highlight-current-row":""}},[n("el-table-column",{attrs:{label:"书签名称",align:"center","show-overflow-tooltip":!0},scopedSlots:e._u([{key:"default",fn:function(t){var a=t.row;return[n("span",[e._v(e._s(a.name))])]}}])}),e._v(" "),n("el-table-column",{attrs:{label:"统计时段",align:"center","show-overflow-tooltip":!0},scopedSlots:e._u([{key:"default",fn:function(t){var a=t.row;return[n("span",[e._v(e._s(e._f("formatAnalyze")(a.data)))])]}}])}),e._v(" "),n("el-table-column",{attrs:{label:"类型",align:"center","show-overflow-tooltip":!0},scopedSlots:e._u([{key:"default",fn:function(t){var a=t.row;return[n("span",[e._v(e._s(e._f("formatType")(a.type)))])]}}])}),e._v(" "),n("el-table-column",{attrs:{label:"已添加到下列概览中",align:"center","show-overflow-tooltip":!0},scopedSlots:e._u([{key:"default",fn:function(t){var a=t.row;return[n("span",[e._v(e._s(e._f("formatDashboard")(a.dashboards)))])]}}])}),e._v(" "),n("el-table-column",{attrs:{label:"创建时间",align:"center","show-overflow-tooltip":!0},scopedSlots:e._u([{key:"default",fn:function(t){var a=t.row;return[n("span",[e._v(e._s(a.createTime))])]}}])}),e._v(" "),n("el-table-column",{attrs:{label:"#",align:"center","class-name":"small-padding fixed-width"},scopedSlots:e._u([{key:"default",fn:function(e){e.row;return[n("i",{staticClass:"el-icon-edit"})]}}])})],1),e._v(" "),n("pagination",{directives:[{name:"show",rawName:"v-show",value:e.total>0,expression:"total>0"}],attrs:{total:e.total,page:e.query.page,limit:e.query.limit},on:{"update:page":function(t){return e.$set(e.query,"page",t)},"update:limit":function(t){return e.$set(e.query,"limit",t)},pagination:e.fetchBookmarks}})],1)},o=[],i=(n("7f7f"),n("db97"),n("96cf"),n("3b8d")),r=n("75fb"),s=n("bc5d"),l=n("333d"),c=n("6724"),u=n("9cbb"),d={components:{CustomHeader:s["a"],Pagination:l["a"]},directives:{waves:c["a"]},filters:{formatAnalyze:function(e){var t=JSON.parse(e);return t.toDate?"".concat(t.fromDate,"至").concat(t.toDate):"实时统计"},formatDashboard:function(e){if(e.length<1)return"尚未加入任何概览"},formatType:function(e){var t=u["a"].event_type[e];return t?t.cname:"未知类型"}},data:function(){return{listLoading:!1,list:[],total:0,query:{page:1,limit:10,type:"",search:""},types:[{cname:"元数据分析",command:"meta-event",name:"menu-analyze-meta-event",desc:"元数据详细查询分析",icon:"el-icon-hel"}]}},created:function(){var e=Object(i["a"])(regeneratorRuntime.mark((function e(){return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.next=2,this.fetchDashboard();case 2:this.fetchBookmarks();case 3:case"end":return e.stop()}}),e,this)})));function t(){return e.apply(this,arguments)}return t}(),methods:{fetchDashboard:function(){return new Promise((function(e,t){e()}))},fetchBookmarks:function(){var e=this;this.listLoading=!0,Object(r["a"])(this.query).then((function(t){e.total=t.total,e.list=t.items,setTimeout((function(){e.listLoading=!1}),1e3)}))},handleSelect:function(e){this.query.type=e,this.fetchBookmarks()},resolveName:function(){var e="全部书签";for(var t in this.types)if(Object.is(this.types[t].command,this.query.type)){e=this.types[t].cname;break}return e},searchChange:function(){this.fetchBookmarks()},jump:function(e){console.log(e),this.$router.push({name:e.name,query:this.$route.query})}}},p=d,f=n("2877"),m=Object(f["a"])(p,a,o,!1,null,null,null);t["default"]=m.exports},bc5d:function(e,t,n){"use strict";var a=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"custom-header"},[n("div",{staticClass:"custom-header-title"},[n("span",[n("h3",[e._v(e._s(e.showName))])]),e._v(" "),e._t("title")],2),e._v(" "),n("div",{staticClass:"custom-tools"},[e._t("tools")],2)])},o=[],i=(n("e67d"),{name:"CustomHeader",props:{showName:{type:String,default:""}}}),r=i,s=n("2877"),l=Object(s["a"])(r,a,o,!1,null,null,null),c=l.exports;t["a"]=c},ce85:function(e,t,n){},e67d:function(e,t,n){}}]);