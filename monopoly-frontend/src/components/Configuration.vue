<script setup>
import { config } from "../config"
defineProps(['show','stats'])
const emit = defineEmits()
</script>

<template>
    <Transition name="zoop">
        <div class="notice modal" v-if="show">
            <div class="noticeTitle">
                <span>Configuration</span>
                <div class="closeBtn" @click="this.$emit('close')"><div class="btnContainer"></div></div>
            </div>

            <div class="config">
                <span>Players: </span>
                <div class="control decrement" @click="config.setPlayers(config.getPlayers()-1)"></div>
                <div class="value">{{ config.getPlayers() }}</div>
                <div class="control increment" @click="config.setPlayers(config.getPlayers()+1)"></div>
            </div>

            <div class="config">
                <span>Turns: </span>
                <div class="control decrement" @click="config.setTurns(config.getTurns()-1)"></div>
                <input class="value" v-model="config.state.maxTurns" type="text">
                <div class="control increment" @click="config.setTurns(Number(config.getTurns())+1)"></div>
            </div>
        </div>
    </Transition>
</template>

<style scoped>
.value{
    padding:0 9px;
}
input.value{
    all:unset;
    font-size: 33px;
    text-decoration: underline;
    display: inline;
    width:100px;
    text-align: center;
}
.control{
    width: 0px;
    height: 0px;
    border-style: solid;
    border-width: 16px 0 16px 32px;
    border-color: transparent transparent transparent black;
    transform: rotate(180deg);
    position: relative;
    cursor: pointer;
    top:3px;
}
.control:after{
    content:'';
    width: 0px;
    height: 0px;
    border-style: solid;
    border-width: 12px 0 12px 24px;
    border-color: transparent transparent transparent white;
    transform: rotate(0deg);
    z-index: 1;
    position: absolute;
    top:-12px;
    left:-29.5px;
    transition: border-color 0.3s;
}
.control:hover:after{
    border-color: transparent transparent transparent var(--alt-green-color);
}
.increment{
    transform: rotate(0deg);
}
.config{
    font-size:33px;
    margin:20px;
}
.config div{
    display: inline-block;
}
.modal{
    width: 500px;
    height: 300px;
    position: absolute;
    padding:5px;
    z-index:1001;
    top:50%;
    left:50%;
    transform: translate(-50%, -50%);
}
.modal .noticeTitle{
    text-align: left;
    padding:5px;
    display: flex;
    align-items: center;
    margin-bottom:50px;
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