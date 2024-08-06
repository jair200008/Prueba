import {createRouter, createWebHistory} from 'vue-router'
import Inicio from '../views/Inicio.vue'
import Clientes from '../views/ClienteView.vue'

const routes = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'home',
            component: Inicio
        },
        {
            path: '/inicio',
            name: 'inicio',
            component: () => Inicio
        },
        {
            path: '/clientes',
            name: 'clientes',
            component: () => Clientes

        }
    ]
})
export default routes