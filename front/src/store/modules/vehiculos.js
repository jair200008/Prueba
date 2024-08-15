// vehiculos.js
import axiosInstance from './axiosConfig.js';
import { handleApiError } from './errorHandler.js';

const API_URL = '/vehiculos';

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

const getters = {
    allVehiculos: state => {
        return state.vehiculos.map(vehiculo => ({
            ...vehiculo,
            id: vehiculo.codigo || vehiculo.cod_vehiculo
        }));
    },
    vehiculo: state => state.vehiculo
};

const actions = {
    async fetchVehiculos({ commit }) {
        try {
            const response = await axiosInstance.get(API_URL);
            commit('setVehiculos', Array.isArray(response.data) ? response.data : []);
        } catch (error) {
            handleApiError(error);
            commit('setVehiculos', []);
        }
    },

    async createVehiculo({ commit }, vehiculo) {
        try {
            const response = await axiosInstance.post(API_URL, vehiculo);
            commit('newVehiculo', response.data);
            return response.data;
        } catch (error) {
            handleApiError(error);
            throw error.response ? error.response.data : error.message;
        }
    },

    async updateVehiculo({ commit }, vehiculo) {
        try {
            const response = await axiosInstance.put(`${API_URL}/${vehiculo.codigo}`, vehiculo);
            commit('updateVehiculo', response.data);
            return response.data;
        } catch (error) {
            handleApiError(error);
            throw error.response ? error.response.data : error.message;
        }
    },

    async deleteVehiculo({ commit }, id) {
        try {
            const response = await axiosInstance.delete(`${API_URL}/${id}`);
            commit('removeVehiculo', id);
            return response.data;
        } catch (error) {
            handleApiError(error);
            throw error.response ? error.response.data : error.message;
        }
    }
};

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
