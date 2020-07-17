<template>
  <div :style="{width:width}">
    <div class="chart-header">
      <el-date-picker
        v-model="datePicker"
        type="datetimerange"
        size="small"
        :default-time="['00:00:00', '23:59:00']"
        range-separator="至"
        start-placeholder="开始日期"
        end-placeholder="结束日期"
        @change="dateChange">
      </el-date-picker>
      <p class="chart-span">
        {{chartName}}
      </p>
      <div style="display: inline-block;">
        <el-button size="small">
          显示设置<i class="el-icon-arrow-down el-icon--right"></i>
        </el-button>
        <el-dropdown trigger="click" @command="clickCheck" visible-arrow="false">
          <el-button size="small">
            {{magicType.name}}<i class="el-icon-arrow-down el-icon--right"></i>
          </el-button>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item v-for="type in chartsType" :command="type">{{type.name}}</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
        <el-dropdown trigger="click" @command="xAxisCheck" visible-arrow="false">
          <el-button size="small">
            {{dateCheck.name}}<i class="el-icon-arrow-down el-icon--right"></i>
          </el-button>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item v-for="unit in inputUnit" :command="unit">{{unit.name}}</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </div>
    <div id="custom-charts" :style="{width: width ,height:height}"></div>
  </div>
</template>

<script>
  export default {
    name: "custom-charts",
    props:{
      chartName:{
        required: true,
        type: String
      },
      width:{
        type: String,
        default:'100%'
      },
      height: {
        type: String,
        default:'450px'
      },
      timeRange: {
        type: Array,
        default(){
          let defaultDate = new Date()
          defaultDate.setHours(0)
          defaultDate.setMinutes(0)
          defaultDate.setSeconds(0)
          let defaultStartDate = new Date(defaultDate.setDate(defaultDate.getDate()-1))
          let defaultEndDate = new Date(defaultDate.setHours(23))
          return [defaultStartDate,defaultEndDate]
        }
      },
      chartType: {
        type: Object,
        default(){
          return {name: '线图', value: 'line'}
        }
      },
      dateType:{
        type: Object,
        default(){
          return {name:'按天',value:'day'}
        }
      },
      xAxis: {
        type: Array,
      },
      data:{
        type:Array
      }
    },
    data() {
      return {
        datePicker: this.timeRange,
        chartsType: [{name: '线图', value: 'line'}, {name: '柱图', value: 'bar'}, {name: '饼图', value: 'pie'}, {name: '累积', value: 'stack'}],
        magicType: this.chartType,
        inputUnit:[{name:'按分钟',value:'minute'},{name:'按小时',value:'hour'},{name:'按天',value:'day'},{name:'按周',value:'week'},{name:'按月',value:'month'}],
        dateCheck: this.dateType,
      }
    },
    methods: {
      clickCheck(command) {
        var flag=true
        this.magicType = command
        this.$emit('chart-type-change',this.magicType)
        if (command.value==='stack'){

        }else{
          if (command.value=='pie')
            flag = false
          this.setCharts(command.value,flag)
        }
      },
      xAxisCheck(command) {
        this.dateCheck = command
        if (this.datePicker){
          if (this.dateCheck.value==='minute'){
            let startDate = new Date(this.datePicker[0])
            let endDate = new Date(this.datePicker[1])
            startDate = new Date(endDate.getTime() - 24 * 60 * 60 * 1000 + (60 * 1000))
            this.datePicker = [startDate.toString(),endDate.toString()]
            this.$message('按分钟查看，时间范围一次最多展示 1 天');
          }
          this.$emit('dateChange', {datePicker: this.datePicker,dateType: this.dateCheck})
        }
      },
      dateChange(){
        if (this.datePicker){
          this.$emit('dateChange', {datePicker: this.datePicker,dateType: this.dateCheck})
        }
      },
      setCharts(type,flag) {
        var myCharts = this.$echarts.init(document.getElementById('custom-charts'))
        var option = {
          grid:{
            bottom: 100
          },
          tooltip: {
            show:flag,
            trigger: 'axis'
          },
          legend: {
            show: true,
            bottom: 50,
            data: ['日报表']
          },
          toolbox: {
            show: true,
            dataView: {
              show: true
            }
          },
          xAxis: {
            show: flag,
            type: 'category',
            boundaryGap: true,
            data: ['2017-11-1', '2017-11-2', '2017-11-3', '2017-11-4', '2017-11-5', '2017-11-6', '2017-11-7', '2017-11-8', '2017-11-9', '2017-11-10']
          },
          yAxis: {
            show: flag,
            type: 'value'
          },
          dataZoom: [
            {
              type: 'slider',
              show: false,
              xAxisIndex: [0],
              start: 0,
              end: 100,
            }
          ],
          series: [
            {
              name: '日报表',
              type: type,
              //areaStyle: {},
              data: [2,1,2,1,3,1,2,5,4,5],
              radius:type==='pie' ? ['40%','65%'] : null,
              center: type==='pie' ? ["50%", "45%"]: null,
              label:{
                normal:{
                  show:!flag,
                  position:'outside', //标签的位置
                  textStyle : {
                    fontWeight : 300 ,
                    fontSize : 16    //文字的字体大小
                  },
                  formatter:'{d}%'
                }
              }
            }
          ]
        };
        // 使用刚指定的配置项和数据显示图表。
        myCharts.setOption(option);
      }
    },
    mounted() {
      this.setCharts(this.magicType.value,this.magicType.value==='pie'?false:true)
    }
  }
</script>

<style scoped>
  .chart-header{

  }
  .chart-span{
    display: inline-block;
    width:60%;
    line-height: 32px;
    text-align: center;
    font-size: 18px;
    font-weight: 500;
    text-align: center;
}
</style>
