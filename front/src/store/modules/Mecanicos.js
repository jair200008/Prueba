import axios from 'axios';

// Configuración general
const API_URL = 'http://localhost:8085/mecanicos';
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
    // Obtener todos los mecánicos
    async fetchMecanicos({ commit }) {
        try {
            const response = await axios.get(API_URL);
            commit('setMecanicos', response.data);
        } catch (error) {
            return handleApiError(error);
        }
    },

    // Crear un nuevo mecánico
    async createMecanico({ commit }, mecanico) {
        try {
            const response = await axios.post(API_URL, mecanico);
            commit('newMecanico', response.data);
            return response.data;
        } catch (error) {
            return handleApiError(error);
        }
    },

    // Actualizar un mecánico existente
    async updateMecanico({ commit }, mecanico) {
        try {
            const response = await axios.put(`${API_URL}/${mecanico.id}`, mecanico);
            commit('updateMecanico', response.data);
            return response.data;
        } catch (error) {
            return handleApiError(error);
        }
    },

    // Eliminar un mecánico
    async deleteMecanico({ commit }, id) {
        try {
            await axios.delete(`${API_URL}/${id}`);
            commit('removeMecanico', id);
        } catch (error) {
            return handleApiError(error);
        }
    }
};

// Mutaciones
const mutations = {
    // Establecer todos los mecánicos
    setMecanicos: (state, mecanicos) => (state.mecanicos = mecanicos),

    // Establecer un mecánico individual
    setMecanico: (state, mecanico) => (state.mecanico = mecanico),

    // Añadir un nuevo mecánico
    newMecanico: (state, mecanico) => state.mecanicos.push(mecanico),

    // Actualizar un mecánico existente
    updateMecanico: (state, updatedMecanico) => {
        const index = state.mecanicos.findIndex(mecanico => mecanico.id === updatedMecanico.id);
        if (index !== -1) {
            state.mecanicos.splice(index, 1, updatedMecanico);
        }
    },

    // Eliminar un mecánico
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
