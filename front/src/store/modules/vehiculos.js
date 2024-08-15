import axios from 'axios';

// Configuración general
const API_URL = 'http://localhost:8085/vehiculos';
const ERROR_MESSAGE = 'Error al procesar la solicitud';

// Estado inicial
const state = {
    vehiculos: [],
    vehiculo: {
        placa: '',
        color: '',
        marca: {
            codigo: '',
            nombre_marca: ''
        },
        cliente: {
            tipo_documentos: '',
            documento: '',
            primer_nombre: '',
            segundo_nombre: '',
            primer_apellido: '',
            segundo_apellido: '',
            celular: '',
            direccion: '',
            email: ''
        },
        fotos: [
            {
                ruta: '',
                cod_mantenimiento: '',
                codigo: ''
            }
        ]
    }
};

// Getters
const getters = {
    allVehiculos: state => {
        return state.vehiculos.map(vehiculo => ({
            ...vehiculo,
            id: vehiculo.codigo || vehiculo.cod_vehiculo
        }));
    },
    vehiculo: state => state.vehiculo
};

// Acciones
const actions = {
    async fetchVehiculos({ commit }) {
        try {
            const response = await axios.get(API_URL);
            commit('setVehiculos', Array.isArray(response.data) ? response.data : []);
        } catch (error) {
            console.error('Error fetching vehiculos:', error);
            commit('setVehiculos', []);
        }
    },

    async createVehiculo({ commit }, vehiculo) {
        try {
            const response = await axios.post(API_URL, vehiculo);
            commit('newVehiculo', response.data);
            return response.data;
        } catch (error) {
            console.error('Error creating vehiculo:', error);
            throw error.response ? error.response.data : error.message;
        }
    },

    async updateVehiculo({ commit }, vehiculo) {
        try {
            const response = await axios.put(`${API_URL}/${vehiculo.codigo}`, vehiculo);
            commit('updateVehiculo', response.data);
            return response.data;
        } catch (error) {
            console.error('Error updating vehiculo:', error);
            throw error.response ? error.response.data : error.message;
        }
    },

    async deleteVehiculo({ commit }, id) {
        try {
            const response = await axios.delete(`${API_URL}/${id}`);
            commit('removeVehiculo', id);
            return response.data;
        } catch (error) {
            console.error('Error deleting vehiculo:', error);
            throw error.response ? error.response.data : error.message;
        }
    }
};

// Mutaciones
const mutations = {
    setVehiculos: (state, vehiculos) => {
        state.vehiculos = vehiculos;
    },

    newVehiculo: (state, vehiculo) => {
        state.vehiculos.push(vehiculo);
    },

    updateVehiculo: (state, updatedVehiculo) => {
        const index = state.vehiculos.findIndex(vehiculo => vehiculo.codigo === updatedVehiculo.codigo);
        if (index !== -1) {
            state.vehiculos.splice(index, 1, updatedVehiculo);
        }
    },

    removeVehiculo: (state, id) => {
        state.vehiculos = state.vehiculos.filter(vehiculo => vehiculo.codigo !== id);
    }
};

export default {
    state,
    getters,
    actions,
    mutations
};
