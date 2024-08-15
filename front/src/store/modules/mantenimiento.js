import axios from 'axios';

// Configuración general
const API_URL = 'http://localhost:8085/mantenimientos';
const ERROR_MESSAGE = 'Error al procesar la solicitud';

// Estado inicial
const state = {
    mantenimientos: [],
    mantenimiento: {
        codigo: '',
        estado: '',
        cod_vehiculo: '',
        fecha: '',
        cod_mecanico: '',
        presupuesto: 0,
    },
    mantenimientoPorMecanico: null,
};

// Getters
const getters = {
    allMantenimientos: state => state.mantenimientos,
    mantenimiento: state => state.mantenimiento,
    mantenimientoPorMecanico: state => state.mantenimientoPorMecanico,
};

// Acciones
const actions = {
    async fetchMaintenancesByMecanico({ commit }, codigoMecanico) {
        try {
            const response = await axios.get(`${API_URL}/${codigoMecanico}`);
            commit('setMantenimientoPorMecanico', response.data);
        } catch (error) {
            console.error('Error fetching mantenimientos by mecanico:', error);
        }
    },

    async createMantenimiento({ commit }, mantenimiento) {
        try {
            const response = await axios.post(API_URL, mantenimiento);
            commit('newMantenimiento', response.data);
            return response.data;
        } catch (error) {
            return error.response?.data || ERROR_MESSAGE;
        }
    },

    async updateMantenimiento({ commit }, mantenimiento) {
        try {
            const response = await axios.put(`${API_URL}/${mantenimiento.codigo}`, mantenimiento);
            commit('updateMantenimiento', response.data);
            return response.data;
        } catch (error) {
            return error.response?.data || ERROR_MESSAGE;
        }
    },

    async deleteMantenimiento({ commit }, codigo) {
        try {
            await axios.delete(`${API_URL}/${codigo}`);
            commit('removeMantenimiento', codigo);
        } catch (error) {
            console.error('Error deleting mantenimiento:', error);
        }
    },
};

// Mutaciones
const mutations = {
    setMantenimientos: (state, mantenimientos) => (state.mantenimientos = mantenimientos),
    setMantenimiento: (state, mantenimiento) => (state.mantenimiento = mantenimiento),
    setMantenimientoPorMecanico: (state, mantenimiento) => {
        state.mantenimientoPorMecanico = mantenimiento;
    },
    newMantenimiento: (state, mantenimiento) => state.mantenimientos.push(mantenimiento),
    updateMantenimiento: (state, updatedMantenimiento) => {
        const index = state.mantenimientos.findIndex(m => m.codigo === updatedMantenimiento.codigo);
        if (index !== -1) {
            state.mantenimientos.splice(index, 1, updatedMantenimiento);
        }
    },
    removeMantenimiento: (state, codigo) => {
        state.mantenimientos = state.mantenimientos.filter(m => m.codigo !== codigo);
    },
};

export default {
    state,
    getters,
    actions,
    mutations,
};
