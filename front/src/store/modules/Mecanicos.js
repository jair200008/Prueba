import axiosInstance from './axiosConfig.js'; // Asegúrate de que el archivo y ruta sean correctos

const API_URL = '/mecanicos';
const ERROR_MESSAGE = 'Error al procesar la solicitud';

// Estado inicial
const state = {
    mecanicos: [],
    mecanico: {
        tipo_documento: '',
        documento: '',
        primer_nombre: '',
        segundo_nombre: '',
        primer_apellido: '',
        segundo_apellido: '',
        celular: '',
        direccion: '',
        email: '',
        estado: ''
    }
};

// Getters
const getters = {
    allMecanicos: state =>
        state.mecanicos.map(mecanico => ({
            ...mecanico,
            nombre_completo: `${mecanico.primer_nombre || ''} ${mecanico.segundo_nombre || ''} ${mecanico.primer_apellido || ''} ${mecanico.segundo_apellido || ''}`.trim()
        })),
    mecanico: state => state.mecanico
};

// Acciones
const actions = {
    async fetchMecanicos({ commit }) {
        try {
            const response = await axiosInstance.get(API_URL);
            commit('setMecanicos', response.data);
        } catch (error) {
            return handleApiError(error);
        }
    },

    async createMecanico({ commit }, mecanico) {
        try {
            const response = await axiosInstance.post(API_URL, mecanico);
            commit('newMecanico', response.data);
            return response.data;
        } catch (error) {
            return handleApiError(error);
        }
    },

    async updateMecanico({ commit }, mecanico) {
        try {
            const response = await axiosInstance.put(`${API_URL}/${mecanico.id}`, mecanico);
            commit('updateMecanico', response.data);
            return response.data;
        } catch (error) {
            return handleApiError(error);
        }
    },

    async deleteMecanico({ commit }, id) {
        try {
            await axiosInstance.delete(`${API_URL}/${id}`);
            commit('removeMecanico', id);
        } catch (error) {
            return handleApiError(error);
        }
    }
};

// Mutaciones
const mutations = {
    setMecanicos: (state, mecanicos) => (state.mecanicos = mecanicos),
    setMecanico: (state, mecanico) => (state.mecanico = mecanico),
    newMecanico: (state, mecanico) => state.mecanicos.push(mecanico),
    updateMecanico: (state, updatedMecanico) => {
        const index = state.mecanicos.findIndex(mecanico => mecanico.id === updatedMecanico.id);
        if (index !== -1) {
            state.mecanicos.splice(index, 1, updatedMecanico);
        }
    },
    removeMecanico: (state, id) => {
        state.mecanicos = state.mecanicos.filter(mecanico => mecanico.id !== id);
    }
};

// Función para manejo de errores
const handleApiError = (error) => {
    console.error(error);
    return error.response?.data || ERROR_MESSAGE;
};

export default {
    state,
    getters,
    actions,
    mutations
};
