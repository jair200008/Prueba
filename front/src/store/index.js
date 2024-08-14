import { createStore } from 'vuex';
import cliente from './modules/clientes.js';
import Mecanico from "./modules/Mecanicos.js";
import vehiculos from "./modules/vehiculos.js";

export default createStore({
    modules: {
        cliente,
        Mecanico,
        vehiculos
    },
});
