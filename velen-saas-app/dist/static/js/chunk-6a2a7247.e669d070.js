(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-6a2a7247"],{"0c0d":function(t,e,a){"use strict";var n=a("1dad"),s=a.n(n);s.a},"1dad":function(t,e,a){},"2b74":function(t,e,a){"use strict";var n=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",[a("el-table",{ref:"tableMain",staticStyle:{width:"100%"},attrs:{data:t.insideTableData}},t._l(t.insideColumns,(function(t){return a("el-table-column",{attrs:{label:t.label,align:t.align,width:t.width}})})),1)],1)},s=[],i={name:"Table",props:{value:{type:Array,default:function(){return[]}},columns:{type:Array,default:function(){return[]}},editable:{type:Boolean,default:!1},searchable:{type:Boolean,default:!1},searchPlace:{type:String,default:"top"}},data:function(){return{insideColumns:[],insideTableData:[],searchKey:"",searchValue:"",default_column:{align:"center",width:"auto"}}},watch:{columns:function(t){this.handleColumns(t),this.setDefaultSearchKey()},value:function(){this.handleTableData()}},mounted:function(){this.handleColumns(this.columns),this.setDefaultSearchKey(),this.handleTableData()},methods:{supportEdit:function(t,e){},supportHandle:function(t){},handleColumns:function(t){var e=this;this.insideColumns=t.map((function(t,a){var n=Object.assign({},e.default_column,t);return n.editable&&(n=e.supportEdit(n,a)),"handle"===n.key&&(n=e.supportHandle(n)),n}))},setDefaultSearchKey:function(t){},handleTableData:function(){this.insideTableData=this.value.map((function(t,e){var a=t;return a.initRowIndex=e,a}))}}},o=i,r=a("2877"),l=Object(r["a"])(o,n,s,!1,null,null,null),c=l.exports;e["a"]=c},"7d99":function(t,e,a){"use strict";a.d(e,"c",(function(){return s})),a.d(e,"a",(function(){return i})),a.d(e,"d",(function(){return o})),a.d(e,"b",(function(){return r}));var n=a("b775");function s(t){return Object(n["a"])({url:"/metadata/event",method:"get",params:t})}function i(t){return Object(n["a"])({url:"/metadata/event",method:"post",data:t})}function o(t){return Object(n["a"])({url:"/metadata/event/prop",method:"get",params:t})}function r(t){return Object(n["a"])({url:"/metadata/event/prop",method:"post",data:t})}},bc5d:function(t,e,a){"use strict";var n=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"custom-header"},[a("div",{staticClass:"custom-header-title"},[a("span",[a("h3",[t._v(t._s(t.showName))])]),t._v(" "),t._t("title")],2),t._v(" "),a("div",{staticClass:"custom-tools"},[t._t("tools")],2)])},s=[],i=(a("e67d"),{name:"CustomHeader",props:{showName:{type:String,default:""}}}),o=i,r=a("2877"),l=Object(r["a"])(o,n,s,!1,null,null,null),c=l.exports;e["a"]=c},e67d:function(t,e,a){},ed63:function(t,e,a){"use strict";a.r(e);var n=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"app-container"},[a("custom-header",{attrs:{"show-name":t.$route.meta.title}},[a("div",{attrs:{slot:"tools"},slot:"tools"},[a("el-popover",{attrs:{placement:"bottom",width:"200",trigger:"click"}},[a("el-button",{attrs:{slot:"reference",icon:"el-icon-folder-add"},slot:"reference"}),t._v(" "),a("el-form",{attrs:{model:t.formData}},[a("el-form-item",{attrs:{label:"书签名"}},[a("el-input",{model:{value:t.formData.name,callback:function(e){t.$set(t.formData,"name",e)},expression:"formData.name"}})],1),t._v(" "),a("el-form-item",{attrs:{label:"备注"}},[a("el-input",{attrs:{type:"textarea"},model:{value:t.formData.comment,callback:function(e){t.$set(t.formData,"comment",e)},expression:"formData.comment"}})],1),t._v(" "),a("el-form-item",[a("el-button",[t._v("取消")]),t._v(" "),a("el-button",{attrs:{type:"primary"},on:{click:t.submitData}},[t._v("确定")])],1)],1)],1)],1)]),t._v(" "),a("el-container",{staticClass:"main"},[a("el-header",{staticClass:"meta-event-header"},[a("div",{staticStyle:{height:"50px"}},[a("span",[t._v("元事件")]),t._v(" "),a("el-select",{on:{change:t.handleSelectEvent},model:{value:t.switchEvent,callback:function(e){t.switchEvent=e},expression:"switchEvent"}},t._l(t.meta_events,(function(t){return a("el-option",{key:t.id,attrs:{label:t.showName,value:t.id}})})),1)],1)]),t._v(" "),a("el-main",[a("el-row",{staticStyle:{display:"flex","align-items":"center"}},[a("el-col",{attrs:{span:8}},[a("el-date-picker",{attrs:{type:"daterange",align:"right","unlink-panels":"","range-separator":"至","start-placeholder":"开始日期","end-placeholder":"结束日期"},model:{value:t.originQuery.dateRange,callback:function(e){t.$set(t.originQuery,"dateRange",e)},expression:"originQuery.dateRange"}})],1),t._v(" "),a("el-col",{staticStyle:{"text-align":"center"},attrs:{span:8}},[a("h4",[t._v(t._s(t.getMetaEventName()))])]),t._v(" "),a("el-col",{attrs:{span:8}})],1),t._v(" "),a("custom-table",{attrs:{columns:t.columns,value:t.data}})],1)],1)],1)},s=[],i=(a("db97"),a("7f7f"),a("2b74")),o=a("bc5d"),r=a("b775");function l(t){return Object(r["a"])({url:"/query/meta-event",method:"post",data:t})}var c=a("7d99"),u=a("75fb"),d={components:{CustomTable:i["a"],CustomHeader:o["a"]},data:function(){return{id:"",columns:[],data:[],switchEvent:"",meta_events:[],meta_props:{},query:{},originQuery:{dateRange:[]},formData:{name:"",comment:""},saveData:{data:"",name:"",config:"",type:"/meta_event_analytics/"}}},created:function(){this.fetchMetaEventProps(),this.fetchMetaEvent()},methods:{submitData:function(){var t=this;console.log("xxxxxsubmitdata"),this.saveData.name=this.formData.name,this.saveData.config=JSON.stringify({comment:this.comment}),this.saveData.data=JSON.stringify({measures:this.switchEvent,filter:{},chartsType:"pie",rangeText:"",from_date:"2020-06-08",to_date:"2020-06-09"}),Object(u["b"])(this.saveData).then((function(e){t.id=e.id}))},fetchData:function(){var t=this;l(this.query).then((function(e){t.data=e.items}))},fetchMetaEventProps:function(){var t=this;Object(c["d"])().then((function(e){for(var a in e.items)t.meta_props[e.items[a].id]=e.items[a]}))},fetchMetaEvent:function(){var t=this;Object(c["c"])().then((function(e){t.meta_events=e.items}))},handleSelectEvent:function(){var t=[];for(var e in this.meta_events){var a=this.meta_events[e];if(Object.is(a.id,this.switchEvent)){t=a.propIds;break}}this.handleColumn(t),this.fetchData()},handleColumn:function(t){for(var e in console.log(t),t){var a=this.meta_props[t[e]];this.columns.push({prop:a.name,label:a.showName})}},getMetaEventName:function(){for(var t in this.meta_events){var e=this.meta_events[t];if(Object.is(e.id,this.switchEvent))return e.showName}}}},m=d,h=(a("0c0d"),a("2877")),f=Object(h["a"])(m,n,s,!1,null,null,null);e["default"]=f.exports}}]);