import { createRouter, createWebHistory } from 'vue-router'
import Inicio from '../views/Inicio.vue'

const routes = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'home',
            component: () => import('../views/Mantenimiento.vue')
        },
        {
            path: '/clientes',
            name: 'clientes',
            component: () => import('../views/ClienteView.vue')
        },
        {
            path: '/mecanicos',
            name: 'mecanicos',
            component: () => import('../views/MecanicoView.vue')
        },
        {
            path: '/vehiculos',
            name: 'vehiculos',
            component: () => import('../views/VehiculosView.vue')
        },
        {
            path: '/mantenimiento',
            name: 'mantenimiento',
            component: () => import('../views/Mantenimiento.vue')
        }
    ]
})

export default routes