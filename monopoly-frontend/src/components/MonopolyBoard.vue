<script setup>
import { ref, computed } from 'vue'
const props = defineProps(['boardSpaces','players'])

function calcCol(position) {
  if ((position>=10 && position<=20)){
    return 1;
  } else if ((position>=30 && position<=39) || position==0){
    return 11;
  }
  else if (position<10){
    return Math.abs(10-position)+1;
  } else if (position<30) {
    return Math.abs(20-position)+1;
  }
}

function calcRow(position){
  if (position>=0 && position<=10){
    return 11;
  } else if (position<=30 && position>=20){
    return 1;
  } else if (position>30) {
    return Math.abs(30-position)+1
  } else if (position>10){
    return Math.abs(position-20)+1
  }
}

function getColorStyle(color){
  return (color==1 ? { 'background-color': 'var(--board-color-1)' }
        : color==2 ? { 'background-color': 'var(--board-color-2)' }
        : color==3 ? { 'background-color': 'var(--board-color-3)' }
        : color==4 ? { 'background-color': 'var(--board-color-4)' }
        : color==5 ? { 'background-color': 'var(--board-color-5)' }
        : color==6 ? { 'background-color': 'var(--board-color-6)' }
        : color==7 ? { 'background-color': 'var(--board-color-7)' }
        : color==8 ? { 'background-color': 'var(--board-color-8)' }
        : color==9 ? { 'background-color': 'var(--board-color-9)' }
        : {}
  );
}

</script>

<template>
<div class="board">
  <div v-for="property in boardSpaces" :key="property.position" class="item property" :style="{ 'grid-column-start': calcCol(parseInt(property.position)), 'grid-row-start': calcRow(parseInt(property.position)) }">

    <div v-if="property.color" class="color" :style="getColorStyle(property.color)"></div>
    <div class="title" v-if="property.chanceSpace==true">Chance</div>
    <div class="title" v-else-if="property.chanceSpace==false">Community Chest</div>
    <div class="title" v-else-if="property.position!=30"><span>{{ property.name }}</span></div>

    <div class="price" v-if="property.value!=null">{{ property.value }}</div>
    <div class="price" v-else>{{ property.buyCost }}</div>
  </div>
  <div class="item special" style="grid-column-start:11;grid-row-start:11;">
    <span style="transform:rotate(-45deg)">GO</span>
  </div>
  <div class="item special" style="grid-column-start:1;grid-row-start:11;">
    <span style="transform:rotate(45deg)">Jail</span>
  </div>
  <div class="item special" style="grid-column-start:1;grid-row-start:1;">
    <span style="transform:rotate(0deg)">Free parking</span>
  </div>
  <div class="item special" style="grid-column-start:11;grid-row-start:1;">
    <span style="transform:rotate(0deg)">Go to Jail</span>
  </div>

  <div v-for="player in players" :key="player.id" class="player" :style="{ 'grid-column-start': calcCol(parseInt(player.currPosition)), 'grid-row-start': calcRow(parseInt(player.currPosition)) }">
    {{  player.id }}
  </div>
</div>
</template>

<style scoped>
.special{
  display: flex;
  align-items: center;
  justify-content: center;
}
.special span{
  font-size:20px;
  text-align: center;
}
.board{
  background-color: var(--board-theme);
  width:100%;
  height:100%;
  display: grid;
  grid-template-columns: 2fr repeat(9,1fr) 2fr;
  grid-template-rows: 2fr repeat(9,1fr) 2fr;
  font-size: 8px;
  border: 3px solid black;
  transition: 2s;
}
.item{
  border: 1px solid black;
}
.title{
  width: 0;
  min-width: 100%;
  text-overflow:ellipsis;
  overflow: hidden;
  overflow-wrap: break-word;
  hyphens: auto;

  text-align: center;
  padding:3px;
}
.price{
  width:100%;
  padding:5px;
  text-align: center;
}
.color{
  height:15px;
  width:100%;
  border-bottom:1px solid black;
}

.player{
  border-radius:25px;
  border:1px solid black;
  background-color: red;
  width:25px;
  height:25px;
  color:white;
  font-size:15px;
  display: flex;
  justify-content: center;
  align-content: center;
}
</style>