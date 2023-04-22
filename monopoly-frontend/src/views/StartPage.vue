<script setup>
import monopolyLogo from "../components/MonopolyLogo.vue"
import Configuration from "../components/Configuration.vue"
import {ref} from "vue"

const showConfig = ref(false)
</script>

<template>
  <div id="logo">
    <monopolyLogo />
  </div>
  <div class="container">
    <router-link to="/simulation"><div class="notice button" id="run">Run Simulation</div></router-link>
    <div class="notice button" id="config" @click="showConfig = !showConfig">Configure</div>
    <Configuration :show="showConfig" @close="showConfig=false" />
  </div>
</template>

<style scoped>
.container{
  margin-top:10%;
}
.button{
  cursor: pointer;
  font-size: 37px;
  margin: auto;
  text-align: center;
  overflow: hidden;

  background-size: 200% 100%;
  background-image: linear-gradient(90deg, white 50%, var(--alt-green-color) 50%);
  -webkit-transition: background-position 0.5s, color 0.5s;
  -moz-transition: background-position 0.5s, color 0.5s;
  transition: background-position 0.5s, color 0.5s;
}
#config{
  height: 60px;
  width:235px;
  margin-top:50px;
}
#run{
  height: 60px;
  width:380px;
}

.button:hover{
  background-position: -100% 0%;
  color:white;
}
#logo{
  margin:auto;
  width:285px;
}


@media screen  and (max-width: 600px){
  #config{
    height: 60px;
    width:60%;
    font-size:30px;
    padding-top:8px;
  }
  #run{
    height: 60px;
    width:90%;
    font-size:30px;
    padding-top:8px;
    margin-top:80px;
  }
}
</style>
