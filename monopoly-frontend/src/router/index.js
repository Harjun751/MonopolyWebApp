import { createRouter, createWebHistory } from 'vue-router'
import StartPage from '../views/StartPage.vue'
import Simulation from '../views/Simulation.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: StartPage
    },
    {
      path: '/simulation',
      name: 'simulation',
      component: Simulation
    },
  ]
})

export default router
