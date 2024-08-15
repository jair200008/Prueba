import axiosInstance from './axiosConfig';
import { handleApiError } from './errorHandler';

const API_URL = '/clientes';

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

const getters = {
    allClientes: state =>
        state.clientes.map(cliente => ({
            ...cliente,
            nombre_completo: `${cliente.primer_nombre || ''} ${cliente.segundo_nombre || ''} ${cliente.primer_apellido || ''} ${cliente.segundo_apellido || ''}`.trim()
        })),
    cliente: state => state.cliente
};

const actions = {
    async fetchClientes({ commit }) {
        try {
            const response = await axiosInstance.get(API_URL);
            commit('setClientes', response.data);
        } catch (error) {
            console.error('Error al obtener clientes:', handleApiError(error));
        }
    },
    async createCliente({ commit }, cliente) {
        try {
            const response = await axiosInstance.post(API_URL, cliente);
            commit('newCliente', response.data);
            return response.data;
        } catch (error) {
            console.error('Error al crear cliente:', handleApiError(error));
        }
    },
    async updateCliente({ commit }, cliente) {
        try {
            const response = await axiosInstance.put(`${API_URL}/${cliente.id}`, cliente);
            commit('updateCliente', response.data);
            return response.data;
        } catch (error) {
            console.error('Error al actualizar cliente:', handleApiError(error));
        }
    },
    async deleteCliente({ commit }, id) {
        try {
            await axiosInstance.delete(`${API_URL}/${id}`);
            commit('removeCliente', id);
        } catch (error) {
            console.error('Error al eliminar cliente:', handleApiError(error));
        }
    }
};

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

export default {
    state,
    getters,
    actions,
    mutations
};
