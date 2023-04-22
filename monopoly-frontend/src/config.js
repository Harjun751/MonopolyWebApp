import { reactive } from "@vue/reactivity"

const config = {
    state: reactive({
      players:5,
      maxTurns:1000,
    }),
    setPlayers(newValue) {
        this.state.players = newValue
    },
    getPlayers(){
        return this.state.players;
    },
    setTurns(newValue) {
        this.state.maxTurns = newValue
    },
    getTurns(){
        return this.state.maxTurns;
    },
  }
export {config};