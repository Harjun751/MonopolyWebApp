<script setup>
import MonopolyLogo from "../components/MonopolyLogo.vue"
import MonopolyBoard from "../components/MonopolyBoard.vue"
import Modal from "../components/Modal.vue"
import { config } from "../config"

import { reactive, computed, onMounted } from 'vue'

const state = reactive({ 
    turn: 0,
    boardSpaces: null,
    turnLogs: null,
    stats: null,
    simEnded: false,

    
    currentTurnPlayers: null,
 })

onMounted(async () => {
    await fetch("http://localhost:8080/api/v1/board",{
        method:"GET",
        })
        .then(response => response.json())
        .then(response => {
            state.boardSpaces = response.boardSpaces;
        })
    await fetch("http://localhost:8080/api/v1/logs?players="+config.getPlayers()+"&maxTurns="+config.getTurns(),{
        method:"GET",
        })
        .then(response => response.json())
        .then(response => {
            state.turnLogs = response;
        })
    await fetch("http://localhost:8080/api/v1/stats?players="+config.getPlayers()+"&maxTurns="+config.getTurns(),{
        method:"GET",
        })
        .then(response => response.json())
        .then(response => {
            state.stats = response;
        })
})



let turnInterval = null;
function changeInterval(interval){
    clearInterval(turnInterval);
    turnInterval = setInterval(function(){
        if (state.turn >= state.turnLogs.length-1){
            state.simEnded = true;
            clearInterval(turnInterval);
        } else {
            state.turn++;
        }
    }, interval)
}
function stopInterval(){
    clearInterval(turnInterval);
}


const logList = computed(() => {
    if (state.turnLogs!=null){
        return state.turnLogs.slice(0,state.turn+1).map(log => log.stringList.join(" \n")).reverse();
    }
})

</script>

<template>
    <div class="test">
        <Modal title="Simulation Finished" content="Pending..." :show=state.simEnded :stats=state.stats />
        <MonopolyLogo />
        <div class="container">
            <div class="grid-container">
                <div class="notice" id="playerList">
                    <div class="noticeTitle">
                        Players
                    </div>
                    <div class="listContainer" v-if="logList">
                        <div class="list" v-for="player in state.turnLogs[0].playerList" v-if="state.turnLogs">
                            Player {{ player.id }}
                        </div>
                    </div>
                </div>

                <div class="notice" id="controls">
                    <div class="noticeTitle">
                        Controls
                    </div>
                    <div class="list">
                        <input class="hidden" type="radio" id="play" name="control">
                        <label class="control" for="play" style="cursor:pointer;" @click="changeInterval(1000)">Play</label>
                        <input class="hidden" type="radio" id="pause" name="control">
                        <label class="control" for="pause" style="cursor:pointer;" @click="stopInterval()">Pause</label>
                        <input class="hidden" type="radio" id="4" name="control">
                        <label class="control" for="4" style="cursor:pointer;" @click="changeInterval(250)">4x Speed</label>
                        <input class="hidden" type="radio" id="16" name="control">
                        <label class="control" for="16" style="cursor:pointer;" @click="changeInterval(62.5)">16x Speed</label>
                        <input class="hidden" type="radio" id="real" name="control">
                        <label class="control" for="real" style="cursor:pointer;" @click="changeInterval(1)">Realtime</label>
                        <div class="control" style="margin-top:20px;">Turn: {{  state.turn+1 }}</div>
                    </div>
                </div>

                <div class="notice" id="logList">
                    <div class="noticeTitle">
                        Log
                    </div>
                    <div class="listContainer" v-if="logList">
                        <div class="list" v-for="log in logList">
                            {{ log }}
                        </div>
                    </div>
                </div>

                <div id="board">
                    <MonopolyBoard :boardSpaces=state.boardSpaces :players=state.turnLogs[state.turn].playerList v-if="state.turnLogs" />
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
.hidden{
    display: none;
}
.control{
    text-align: center;
}
label.control{
    display: block;
    -webkit-user-select: none; /* Safari */
    -ms-user-select: none; /* IE 10 and IE 11 */
    user-select: none;
}
input[type="radio"]:checked + label {
    text-decoration: underline;
}
.container{
    display: flex;
    justify-content: center;
    margin-top: 50px;
}
.grid-container {
    display: inline-grid;
    grid-template-columns: 300px 300px 300px 300px;
    grid-template-rows: 300px 300px;
    row-gap: 80px;
    column-gap: 80px;
}

#playerList {
    font-size: 33px;
    grid-column: 3 / 4;
    grid-row: 1 / 2;
}

#controls {
    font-size: 33px;
    grid-column: 4 / 5;
    grid-row: 1 / 2;
}

#logList {
    grid-column: 3 / 5;
    grid-row: 2 / 3;
}
#board{
    grid-column: 1 / 3;
    grid-row: 1 / 3;
}

.list {
    padding: 5px;
    font-size:25px;
}

.listContainer {
    overflow-y: scroll;
    height: 240px;
    font-size: 19px;
}
@media screen  and (max-width: 1290px){
    .grid-container{
        grid-template-columns: auto auto;
        grid-template-rows: auto auto auto auto;
        width: 90%;
    }
    #playerList {
        grid-column: 1 / 2;
        grid-row: 3 / 4;
    }
    #controls {
        grid-column: 2 / 3;
        grid-row: 3 / 4;
    }

    #logList {
        grid-column: 1 / 3;
        grid-row: 4 / 5;
    }
}

@media screen  and (max-width: 600px){
    #board{
        overflow:scroll;
        width:200vw;
    }
}
</style>