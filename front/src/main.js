import './assets/main.css'

import { createApp } from 'vue';
import App from './App.vue';
import router from './router/index.js';
import store from './store/index.js'; // Asegúrate de importar la tienda (store) desde Vuex

const app = createApp(App);

app.use(router);
app.use(store); // Usa la tienda en tu aplicación

app.mount('#app');

