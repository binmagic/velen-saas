<template>
  <el-container class="right-dashboard" style="height: 100%;background: #ffffff">
      <el-header>
        <span v-bind:contenteditable="contented" @blur="spanChange" style="line-height: 60px;font-size: 20px;font-weight: 600;">
            漏斗分析
        </span>
        <a><i class="el-icon-edit"></i></a>
      </el-header>
      <el-main>

      </el-main>
  </el-container>
</template>

<script>
  export default {
    name: 'overview',

    data() {
      return {
        input:'',
        tabName:'second',
        contented:true,
      }
    },
    methods:{
      handleInput(val){
        this.input=val
      },
      spanChange(e){
        console.log(e.target.innerText)
      }
    }
  }
</script>
