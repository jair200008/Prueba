import axios from 'axios';

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
            const response = await axios.get('http://localhost:8085/vehiculos');
            if (Array.isArray(response.data)) {
                commit('setVehiculos', response.data);
            } else {
                commit('setVehiculos', []);
            }
        } catch (error) {
            commit('setVehiculos', []);
        }
    },
    async createVehiculo({ commit }, vehiculo) {

        try {
            const response = await axios.post('http://localhost:8085/vehiculos', vehiculo);
            commit('newVehiculo', response.data);

            return response.data;
        } catch (error) {
            throw error.response ? error.response.data : error.message;
        }
        fetchVehiculos();
    },
    async updateVehiculo({ commit }, vehiculo) {

        try {
            const response = await axios.put(`http://localhost:8085/vehiculos/${vehiculo.codigo}`, vehiculo);

            commit('updateVehiculo', response.data);
            return response.data;
        } catch (error) {
            throw error.response ? error.response.data : error.message;
        }
    },
    async deleteVehiculo({ commit }, id) {
        try {
            const response = await axios.delete(`http://localhost:8085/vehiculos/${id}`);
            commit('removeVehiculo', id);
            console.log(response.data);
            return response.data;
        } catch (error) {
            console.error('Error deleting vehiculo:', error);
            throw error.response ? error.response.data : error.message;
        }
    }
};

const mutations = {
    setVehiculos: (state, vehiculos) => {
        state.vehiculos = [];
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
        state.vehiculos = state.vehiculos.filter(vehiculo => vehiculo !== id);
    }
};

export default {
    state,
    getters,
    actions,
    mutations
};