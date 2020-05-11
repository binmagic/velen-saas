<template>
  <div class="dashboard-editor-container">

    <panel-group @handleSetLineChartData="handleSetLineChartData" :reportCount="reportCount"/>

    <line-chart :chart-data="lineChartData"/>

    <el-row :gutter="8">
          <el-col
            :xs="{span: 24}"
            :sm="{span: 24}"
            :md="{span: 24}"
            :lg="{span: 12}"
            :xl="{span: 12}"
            style="padding-right:8px;margin-bottom:30px;"
          >
            <pie-chart :chart-data="reportDistribute" />
          </el-col>
        </el-row>

  </div>
</template>

<script>
  import LineChart from './components/LineChart'
  import PanelGroup from './components/PanelGroup'
  import PieChart from './components/PieChart'

  let lineChartData = {
    reports: {
      xAxisData: ['2019-7-12', '2019-7-13'],
      datas: []
    }
  }


  const pieChartData = {
    distribute: {
      xAxisData: ['异常发热', '耗电过高'],
      datas: []
    }
  }

  export default {
    name: 'Dashboard',
    components: {
      LineChart,
      PanelGroup,
      PieChart
    },
    data() {
      return {
        lineChartData: lineChartData.reports,
        reportCount: 0,
        reportDistribute: pieChartData.distribute
      }
    },
    async created(){

    },
    methods: {

    }
  }
</script>

<style lang="scss" scoped>
  .dashboard-editor-container {
    padding: 32px;
    background-color: rgb(240, 242, 245);
    position: relative;
    height: 100%;
  }

</style>
