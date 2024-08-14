import axios from 'axios'

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

const getters = {
    allMecanicos: state => state.mecanicos.map(mecanico => ({
        ...mecanico,
        nombre_completo: `${mecanico.primer_nombre || ''} ${mecanico.segundo_nombre || ''} ${mecanico.primer_apellido || ''} ${mecanico.segundo_apellido || ''}`.trim()
    })),
    mecanico: state => state.mecanico
};

const actions = {
    async fetchMecanicos({ commit }) {
        try {
            const response = await axios.get('http://localhost:8085/mecanicos')
            commit('setMecanicos', response.data)
        } catch (error) {
        }
    },
    async createMecanico({ commit }, mecanico) {
        try {
            const response = await axios.post('http://localhost:8085/mecanicos', mecanico);
            commit('newMecanico', response.data);
            return response.data;
        } catch (error) {
            throw error.response ? error.response.data : error.message;
        }
    },
    async updateMecanico({ commit }, mecanico) {
        try {
            const response = await axios.put(`http://localhost:8085/mecanicos/${mecanico.id}`, mecanico)
            commit('updateMecanico', response.data)
            return response.data
        } catch (error) {
            throw error.response ? error.response.data : error.message;
        }
    },
    async deleteMecanico({ commit }, id) {
        try {
            const response = await axios.delete(`http://localhost:8085/mecanicos/${id}`)
            commit('removeMecanico', id)
            return response.data
        } catch (error) {
            throw error.response ? error.response.data : error.message;
        }
    }
};

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

export default {
    state,
    getters,
    actions,
    mutations
};
