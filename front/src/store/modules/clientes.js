import axios from 'axios'

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
    allClientes: state => state.clientes,
    cliente: state => state.cliente
};

const actions = {
    async fetchClientes({ commit }) {
        try {
            const response = await axios.get('http://localhost:8085/clientes')

            commit('setClientes', response.data)
        } catch (error) {
            console.error('Error fetching clientes:', error)
        }
    },
    async createCliente({ commit }, cliente) {
        try {
            const response = await axios.post('http://localhost:8085/clientes', cliente);
            commit('newCliente', response.data);
            return response.data; // Asumiendo que el mensaje viene en el campo 'message'
        } catch (error) {
            console.error('Error creating cliente:', error);
            return error.response.data || 'Error al agregar cliente';
        }
    },

    async updateCliente({ commit }, cliente) {
        try {
            const response = await axios.put(`http://localhost:8085/clientes/${cliente.id}`, cliente)
            commit('updateCliente', response.data)
            console.log(response.data);
            return response.data
        } catch (error) {
            console.error('Error updating cliente:', error)
            return error.response.data || 'Error al actualizar cliente';
        }
    },
    async deleteCliente({ commit }, id) {
        try {
            const response = await axios.delete(`http://localhost:8085/clientes/${id}`)
            commit('removeCliente', id)
            return response.data
        } catch (error) {
            console.error('Error deleting cliente:', error)
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
