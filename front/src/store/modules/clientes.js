import axios from 'axios';

// Configuración general
const API_URL = 'http://localhost:8085/clientes';
const ERROR_MESSAGE = 'Error al procesar la solicitud';

// Estado inicial
const state = {
    clientes: [],
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
    }
};

// Getters
const getters = {
    allClientes: state =>
        state.clientes.map(cliente => ({
            ...cliente,
            nombre_completo: `${cliente.primer_nombre || ''} ${cliente.segundo_nombre || ''} ${cliente.primer_apellido || ''} ${cliente.segundo_apellido || ''}`.trim()
        })),
    cliente: state => state.cliente
};

// Acciones
const actions = {
    async fetchClientes({ commit }) {
        try {
            const response = await axios.get(API_URL);
            commit('setClientes', response.data);
        } catch (error) {
            return handleApiError(error);
        }
    },
    async createCliente({ commit }, cliente) {
        try {
            const response = await axios.post(API_URL, cliente);
            commit('newCliente', response.data);
            return response.data;
        } catch (error) {
            return handleApiError(error);
        }
    },
    async updateCliente({ commit }, cliente) {
        try {
            const response = await axios.put(`${API_URL}/${cliente.id}`, cliente);
            commit('updateCliente', response.data);
            return response.data;
        } catch (error) {
            return handleApiError(error);
        }
    },
    async deleteCliente({ commit }, id) {
        try {
            await axios.delete(`${API_URL}/${id}`);
            commit('removeCliente', id);
        } catch (error) {
            return handleApiError(error);
        }
    }
};

// Mutaciones
const mutations = {
    setClientes: (state, clientes) => (state.clientes = clientes),
    setCliente: (state, cliente) => (state.cliente = cliente),
    newCliente: (state, cliente) => state.clientes.push(cliente),
    updateCliente: (state, updatedCliente) => {
        const index = state.clientes.findIndex(cliente => cliente.id === updatedCliente.id);
        if (index !== -1) {
            state.clientes.splice(index, 1, updatedCliente);
        }
    },
    removeCliente: (state, id) => {
        state.clientes = state.clientes.filter(cliente => cliente.id !== id);
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
