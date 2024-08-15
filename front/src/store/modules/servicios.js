import axios from 'axios';

// URL base de la API
const API_BASE_URL = 'http://localhost:8085';

// Función para buscar coincidencias
const buscarCoincidencias = async (searchQuery) => {
    try {
        const response = await axios.get(`${API_BASE_URL}/servicios?search=${searchQuery}`);
        return response.data;
    } catch (error) {
        console.error("Error buscando coincidencias:", error);
        throw error.response ? error.response.data : error.message;
    }
};

// Función para enviar un repuesto
const enviarRepuesto = async (repuesto) => {
    try {
        const response = await axios.post(`${API_BASE_URL}/items/repuestos`, {
            nombre: repuesto.nombre,
            precio: repuesto.precio,
            unidades: repuesto.unidades,
            proveedor: repuesto.proveedor
        });
        return response.data;
    } catch (error) {
        console.error("Error enviando repuesto:", error);
        throw error.response ? error.response.data : error.message;
    }
};

// Función para enviar un servicio
const enviarServicio = async (servicio) => {
    try {
        const response = await axios.post(`${API_BASE_URL}/items/servicios`, {
            nombre_servicio: servicio.nombre,
            precio: servicio.precio,
        });
        return response.data;
    } catch (error) {
        console.error("Error enviando servicio:", error);
        throw error.response ? error.response.data : error.message;
    }
};

export default {
    buscarCoincidencias,
    enviarRepuesto,
    enviarServicio
};
