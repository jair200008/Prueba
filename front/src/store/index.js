import { createStore } from 'vuex';
import cliente from './modules/clientes.js';

export default createStore({
    modules: {
        cliente
    }
});
