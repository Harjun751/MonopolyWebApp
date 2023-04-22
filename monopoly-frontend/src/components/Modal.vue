<script setup>
import { computed } from 'vue'
import { config } from "../config"
defineProps(['title','show','stats'])

const URL = computed(() => {
    return "http://localhost:8080/api/v1/statsdownload?players="+config.getPlayers()+"&maxTurns="+config.getTurns();
})

</script>

<template>
    <Transition name="zoop">
        <div class="notice modal" v-if="show">
            <div class="noticeTitle">
                <span>{{ title }}</span>
                <div class="closeBtn" @click="show = !show"><div class="btnContainer"></div></div>
            </div>
            
            <div v-if="stats">
                <div class="notice">
                    <div class="noticeTitle">
                        Most Rent Collected
                    </div>
                    <div class="listContainer">
                        <div class="list" v-for="(rentCollected, property) in stats.rentStats">
                            {{ property }}
                        </div>
                    </div>
                </div>

                <div class="notice">
                    <div class="noticeTitle">
                        Most Landed On
                    </div>
                    <div class="listContainer">
                        <div class="list" v-for="(landedCount, property) in stats.landStats">
                            {{ property }}
                        </div>
                    </div>
                </div>
            </div>

            <div class="notice button">
                <a :href="URL" download="test.csv">
                <img src="../assets/download.png" alt="Download CSV">
                <span>.CSV</span>
                </a>
            </div>
            
        </div>
    </Transition>
</template>

<style scoped>
.notice{
    height: 400px;
    width: 480px;
    display: inline-block;
    margin-left:5px;
    margin-right:5px;
    margin-top:5px;
}
.listContainer{
    font-size: 19px;
    height: 330px;
    overflow-y: scroll;
}
.button{
    width:130px;
    height:50px;
    font-size:33px;
    cursor: pointer;
    padding:5px;
    display: flex;
    justify-content: center;
    align-items: center;
    position: absolute;
    bottom: 20px;
    right: 20px;
}
.button img{
    width:26px;
}
.modal{
    width: 1000px;
    height: 600px;
    position: absolute;
    resize: both;
    padding:5px;
    z-index:1001;
}
.modal .noticeTitle{
    text-align: left;
    padding:5px;
    display: flex;
    align-items: center;
}

.modal .content{
    padding:5px;
}

.modal .closeBtn{
    display: flex;
    justify-content: center;
    align-items: center;
    margin-left: auto;
    width: 45px;
    height:45px;
    border: 3px solid black;
    background-color: red;
    cursor: pointer;
    transition: box-shadow 0.1s, transform 0.1s;
    position: relative;
}
.modal .btnContainer{
    width: 30px;
    height:30px;
    background: -webkit-linear-gradient(-45deg, transparent 0%, transparent 46%, white 46%,  white 56%,transparent 56%, transparent 100%), -webkit-linear-gradient(45deg, transparent 0%, transparent 46%, white 46%,  white 56%,transparent 56%, transparent 100%);
}

.modal .closeBtn:hover{
    box-shadow: 2px 2px;
    transform: translate(-1px,-1px);
}

.modal .closeBtn:active{
    box-shadow: 0px 0px ,inset 2px 2px; 
    transform: translate(1px,1px);
}

/* Animations */
.zoop-enter-active,
.zoop-leave-active {
  transition: width 0.2s ease, height 0.2s ease;
  overflow: clip;
  white-space: nowrap;
}

.zoop-enter-from,
.zoop-leave-to {
  width: 0%;
  height:0%;
}
</style>