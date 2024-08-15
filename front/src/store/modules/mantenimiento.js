// Ejemplo de uso en un módulo Vuex (como el módulo de mantenimientos)
import axiosInstance from './axiosConfig';
import { handleApiError } from './errorHandler';

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

const getters = {
    allMantenimientos: (state) => state.mantenimientos,
    mantenimiento: (state) => state.mantenimiento,
    mantenimientoPorMecanico: (state) => state.mantenimientoPorMecanico,
};

const actions = {
    async fetchMaintenancesByMecanico({ commit }, codigoMecanico) {
        try {
            const response = await axiosInstance.get(`/mantenimientos/${codigoMecanico}`);
            commit('setMantenimientoPorMecanico', response.data);
        } catch (error) {
            console.error('Error al obtener mantenimientos por mecánico:', handleApiError(error));
        }
    },
    async createMantenimiento({ commit }, mantenimiento) {
        try {
            const response = await axiosInstance.post('/mantenimientos', mantenimiento);
            commit('newMantenimiento', response.data);
            return response.data;
        } catch (error) {
            throw handleApiError(error);
        }
    },
    async updateMantenimiento({ commit }, mantenimiento) {
        try {
            const response = await axiosInstance.put(`/mantenimientos/${mantenimiento.codigo}`, mantenimiento);
            commit('updateMantenimiento', response.data);
            return response.data;
        } catch (error) {
            throw handleApiError(error);
        }
    },
    async deleteMantenimiento({ commit }, codigo) {
        try {
            const response = await axiosInstance.delete(`/mantenimientos/${codigo}`);
            commit('removeMantenimiento', codigo);
            return response.data;
        } catch (error) {
            console.error('Error al eliminar mantenimiento:', handleApiError(error));
        }
    },
};

const mutations = {
    setMantenimientos: (state, mantenimientos) => (state.mantenimientos = mantenimientos),
    setMantenimiento: (state, mantenimiento) => (state.mantenimiento = mantenimiento),
    setMantenimientoPorMecanico: (state, mantenimiento) => {
        state.mantenimientoPorMecanico = mantenimiento;
    },
    newMantenimiento: (state, mantenimiento) => state.mantenimientos.push(mantenimiento),
    updateMantenimiento: (state, updatedMantenimiento) => {
        const index = state.mantenimientos.findIndex((m) => m.codigo === updatedMantenimiento.codigo);
        if (index !== -1) {
            state.mantenimientos.splice(index, 1, updatedMantenimiento);
        }
    },
    removeMantenimiento: (state, codigo) => {
        state.mantenimientos = state.mantenimientos.filter((m) => m.codigo !== codigo);
    },
};

export default {
    state,
    getters,
    actions,
    mutations,
};
